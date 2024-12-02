package modele;

import jakarta.persistence.*;

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
    private final Date dateDebut;

    private double montantTVA5_5;
    private double montantTVA10;
    private double montantTVA20;
    private double totalHT;
    private double totalTTC;

    private int hashcode;
    private boolean finalise;

    public Commande() {
        this.compositionCommande = new HashSet<>();
        this.listeRecus = new ArrayList<>();
        this.dateDebut = new Date();
        this.numTable = 0;
    }

    public Commande(int numTable) {
        this();
        this.numTable = numTable;
    }

    public void ajoutCommande(Commandable commandable) {
        boolean flag = false;

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

        this.hashcode = hashCode();
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

    public Facture creerFacture(String nomClient, String prenomClient, Adresse adresseClient, int numeroTVAClient) {
        if (!finalise) {
            finaliserCommande();
        }

        Facture facture = new Facture(this, nomClient, prenomClient, adresseClient, numeroTVAClient);
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



}
