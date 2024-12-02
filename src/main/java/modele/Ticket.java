package modele;

import jakarta.persistence.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Entity
public class Ticket extends Recu {
    @Column(nullable = false)
    private int numTransaction;

    public Ticket() {}

    public Ticket(Commande commandeSource) {
        super(commandeSource);
        this.numTransaction = CompteurManager.getNextNumTransactionTicket();

    }

    public String genererTexte() {
        String nomRestaurant = "MON RESTO";

        StringBuilder affichageTicket = new StringBuilder();
        affichageTicket.append(nomRestaurant + "\n");
        affichageTicket.append("Transaction n°" + numTransaction + "\n");
        affichageTicket.append(String.format("%td/%tm/%ty %tH:%tM", this.getDateCreation(),  this.getDateCreation(),  this.getDateCreation(),  this.getDateCreation(),  this.getDateCreation()) + "\n");
        affichageTicket.append("Produits commandés :\n");

        for (QuantiteCommande quantiteCommande: getCommandeSource().getCompositionCommande()) {
            Commandable produit = quantiteCommande.getProduit();
            int quantite = quantiteCommande.getQuantite();

            affichageTicket.append(String.format("\tx%s %s (%.2f €/unité) : %.2f €\n", quantite, produit.getNom(), produit.getPrixHT(), produit.getPrixHT() * quantite));
        }

        affichageTicket.append(String.format("Total TVA à 5.5%% : %.2f €\n", this.getCommandeSource().getMontantTVA5_5()));
        affichageTicket.append(String.format("Total TVA à 10%% : %.2f €\n", this.getCommandeSource().getMontantTVA10()));
        affichageTicket.append(String.format("Total TVA à 20%% : %.2f €\n", this.getCommandeSource().getMontantTVA20()));

        affichageTicket.append(String.format("Total TTC : %.2f €", this.getCommandeSource().getTotalTTC()));

        return affichageTicket.toString();
    }
}
