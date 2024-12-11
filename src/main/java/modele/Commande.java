package modele;

import jakarta.persistence.*;
/*
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;*/
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
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

    @OneToMany(mappedBy = "commandeSource", cascade = CascadeType.PERSIST)
    private Set<QuantiteCommande> compositionCommande;

    @OneToMany(mappedBy = "commandeSource", cascade = CascadeType.PERSIST)
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
        this.compositionCommande = new HashSet<>();
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

    public void ajoutCommande(Commandable commandable) {
        //boolean flag = false;

        for (QuantiteCommande quantiteCommande : this.compositionCommande) {
            if (quantiteCommande.getProduit() == commandable) {
                quantiteCommande.add();
                return;
            }
        }

        this.compositionCommande.add(new QuantiteCommande(this, commandable, 1));
    }

    public void retraitCommande(Commandable commandable) {
        for (QuantiteCommande quantiteCommande : this.compositionCommande) {
            if (quantiteCommande.getProduit() == commandable) {
                if (quantiteCommande.getQuantite() == 1) {
                    this.compositionCommande.remove(quantiteCommande);
                    return;
                }
                else {
                    quantiteCommande.subtract();
                }
            }
        }
    }

    public Set<QuantiteCommande> getCompositionCommande() {
        return compositionCommande;
    }

    public void finaliserCommande() {
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
        //this.hashcode = hashCode();
        this.finalise = true;
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
/*
    public int getHashcode() {
        return hashcode;
    }

    @Override
    public int hashCode() {
        try {
            // Concaténation des champs pour générer une chaîne unique
            String concatenatedFields = id + ":" +
                    compositionCommande + ":" +
                    numTable + ":" +
                    dateDebut + ":" +
                    montantTVA5_5 + ":" +
                    montantTVA10 + ":" +
                    montantTVA20 + ":" +
                    totalHT + ":" +
                    totalTTC;

            // Calcul du hachage SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(concatenatedFields.getBytes(StandardCharsets.UTF_8));

            // Limitation à un entier 32 bits en utilisant les premiers octets du hachage
            int hashCode = ((hashBytes[0] & 0xFF) << 24) |
                    ((hashBytes[1] & 0xFF) << 16) |
                    ((hashBytes[2] & 0xFF) << 8) |
                    (hashBytes[3] & 0xFF);

            return hashCode & 0x7FFFFFFF;

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 n'est pas disponible sur ce système", e);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Commande other = (Commande) obj;

        // Comparaison des champs
        return Objects.equals(id, other.id) &&
                Objects.equals(compositionCommande, other.compositionCommande) &&
                Objects.equals(listeRecus, other.listeRecus) &&
                Objects.equals(numTable, other.numTable) &&
                Objects.equals(dateDebut, other.dateDebut) &&
                Double.compare(other.montantTVA5_5, montantTVA5_5) == 0 &&
                Double.compare(other.montantTVA10, montantTVA10) == 0 &&
                Double.compare(other.montantTVA20, montantTVA20) == 0 &&
                Double.compare(other.totalHT, totalHT) == 0 &&
                Double.compare(other.totalTTC, totalTTC) == 0 &&
                Objects.equals(finalise, other.finalise);
    }*/
    @Override
    public String toString() {
        return "Commande{" +
                "id=" + id +
                ", compositionCommande=" + compositionCommande +
                ", listeRecus=" + listeRecus +
                ", numTable=" + numTable +
                ", dateDebut=" + dateDebut +
                ", montantTVA5_5=" + montantTVA5_5 +
                ", montantTVA10=" + montantTVA10 +
                ", montantTVA20=" + montantTVA20 +
                ", totalHT=" + totalHT +
                ", totalTTC=" + totalTTC +
                //", hashcode=" + hashcode +
                ", finalise=" + finalise +
                '}';
    }
}
