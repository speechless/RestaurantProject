package modele;

import jakarta.persistence.*;
/*
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;*/
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(
                columnNames = {"numTable", "dateDebut"}
        )
})
public class Commande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(mappedBy = "commandeSource", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuantiteCommande> compositionCommande;

    @OneToMany(mappedBy = "commandeSource", cascade = CascadeType.ALL)
    private List<Recu> listeRecus;

    private int numTable;
    private final String dateDebut;
    @Column(columnDefinition = "NUMERIC(5,2)")
    private double montantTVA5_5;
    @Column(columnDefinition = "NUMERIC(5,2)")
    private double montantTVA10;
    @Column(columnDefinition = "NUMERIC(5,2)")
    private double montantTVA20;
    @Column(columnDefinition = "NUMERIC(10,2)")
    private double totalHT;
    @Column(columnDefinition = "NUMERIC(10,2)")
    private double totalTTC;

    //private int hashcode;
    private boolean finalise;

    public Commande() {
        this.compositionCommande = new ArrayList<>();
        this.listeRecus = new ArrayList<>();

        LocalDateTime now = LocalDateTime.now();
        // Format spécifique (jusqu'aux minutes)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        // Formater la date
        this.dateDebut = now.format(formatter);
        this.numTable = 0;
        this.finalise = false;
    }

    public Commande(int numTable) {
        this();
        this.numTable = numTable;
    }

    public List<Recu> getListeRecus() {
        return listeRecus;
    }

    public int getNumTable() {
        return numTable;
    }

    public String getDateDebut() {
        return dateDebut;
    }

    public boolean isFinalise() {
        return finalise;
    }

    public QuantiteCommande ajoutCommande(Commandable commandable) {
        boolean found = false;
        QuantiteCommande quantiteAffectee = null;

        for (QuantiteCommande quantiteCommande : this.compositionCommande) {
            if (quantiteCommande.getProduit().equals(commandable)) {
                quantiteCommande.add();
                quantiteAffectee = quantiteCommande;
                found = true;
            }
        }

        if (!found) {
            quantiteAffectee = new QuantiteCommande(this, commandable, 1);
            this.compositionCommande.add(quantiteAffectee);
        }

        //TODO changer pour pas tout recaculer
        recalculerPrixEtTVA();

        return quantiteAffectee;
    }

    public void retraitCommande(Commandable commandable) {
        for (QuantiteCommande quantiteCommande : this.compositionCommande) {
            if (quantiteCommande.getProduit().equals(commandable)) {

                quantiteCommande.subtract();
                if (quantiteCommande.getQuantite() == 0) {
                    System.out.println("supprimé : " + commandable.getNom());
                    this.compositionCommande.remove(quantiteCommande);
                    recalculerPrixEtTVA();
                    return;
                }
            }
        }
        //TODO: changer pour pas tout recalculer
        recalculerPrixEtTVA();
    }

    public List<QuantiteCommande> getCompositionCommande() {
        return compositionCommande;
    }

    public void finaliserCommande() {
        recalculerPrixEtTVA();
        //this.hashcode = hashCode();
        this.finalise = true;
    }

    public void recalculerPrixEtTVA() {
        this.totalHT = 0;
        this.totalTTC = 0;
        this.montantTVA5_5 = 0;
        this.montantTVA10 = 0;
        this.montantTVA20 = 0;

        for (QuantiteCommande quantiteCommande : compositionCommande) {
            Commandable produit = quantiteCommande.getProduit();
            int quantite = quantiteCommande.getQuantite();

            totalHT += produit.getPrixHT() * quantite;
            totalTTC += produit.getPrixHT() * quantite * (1 + produit.getTauxTVA());

            double prixTVA = produit.getPrixHT() * produit.getTauxTVA();
            if (produit.getTauxTVA() == 0.055) {
                montantTVA5_5 += prixTVA;
            }
            else if (produit.getTauxTVA() == 0.10) {
                montantTVA10 += prixTVA;
            }
            else if (produit.getTauxTVA() == 0.2) {
                montantTVA20 += prixTVA;
            }
        }
    }

    public Ticket creerTicket() {
        if (!finalise) {
            finaliserCommande();
        }

        Ticket ticket = new Ticket(this);
        this.listeRecus.add(ticket);
        return ticket;
    }

    public Facture creerFacture(String nomClient, String prenomClient, String telephoneClient, String mailClient, int numeroTVAClient) {
        if (!finalise) {
            finaliserCommande();
        }

        Facture facture = new Facture(this, nomClient, prenomClient, telephoneClient, mailClient, numeroTVAClient);
        this.listeRecus.add(facture);
        return facture;
    }

    public double getMontantTVA5_5() {
        return montantTVA5_5;
    }

    public double getMontantTVA10() {
        return montantTVA10;
    }

    public double getMontantTVA20() {
        return montantTVA20;
    }

    public double getTotalHT() {
        return totalHT;
    }

    public double getTotalTTC() {
        return totalTTC;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Commande commande = (Commande) o;
        return numTable == commande.numTable && Objects.equals(dateDebut, commande.dateDebut);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numTable, dateDebut);
    }

    @Override
    public String toString() {
        String compCmd = "[";
        for (QuantiteCommande c : compositionCommande) {
            compCmd+=c.getProduit();
            compCmd+=";";
        }
        compCmd+=numTable;
        compCmd+=";";
        compCmd+=dateDebut;
        compCmd+=";";
        compCmd+=totalTTC;
        return compCmd;
        /*return compCmd +
                "numTable=" + numTable +
                ", dateDebut=" + dateDebut +
                ", montantTVA5_5=" + montantTVA5_5 +
                ", montantTVA10=" + montantTVA10 +
                ", montantTVA20=" + montantTVA20 +
                ", totalHT=" + totalHT +
                ", totalTTC=" + totalTTC;
                //", hashcode=" + hashcode +
                //", finalise=" + finalise

    */}
}
