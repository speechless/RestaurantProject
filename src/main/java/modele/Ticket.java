package modele;

import jakarta.persistence.*;
import requete.RequeteRestaurant;

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
        Restaurant restaurant = RequeteRestaurant.getInstance().getRestaurant();

        StringBuilder finalTicket = new StringBuilder(
                String.format("<html>"
                + "<div style='text-align:center;'><h1>%s</h1></div><br><br>"
                + "<div style='text-align:center;'>Adresse du restaurant : %s</div><br>"
                + "<div style='text-align:center;'>SIRET : %s</div><br>"
                + "<div style='text-align:center;'>TVA : %s</div><br>"
                + "<b>Table %s</b><br>"
                + "&nbsp;&nbsp;&nbsp;&nbsp;SLIM"
                + "<br><br>"
                + "Produits commandés :<br>",
                restaurant.getName(),restaurant.getAddress(), restaurant.getSIRENNumber(),restaurant.getTVANumber(),
                getCommandeSource().getNumTable()));

        finalTicket.append("<table style='width:100%; border-collapse:collapse;'>");
        finalTicket.append("<tr><th>Quantité</th><th>Produit</th><th>Prix Unitaire</th><th>Total</th></tr>");

        for (QuantiteCommande quantiteCommande: getCommandeSource().getCompositionCommande()) {
            Commandable produit = quantiteCommande.getProduit();
            int quantite = quantiteCommande.getQuantite();

            finalTicket.append(String.format(
                    "<tr><td>%d</td><td>%s</td><td>%.2f €</td><td>%.2f €</td></tr>",
                    quantite, produit.getNom(), produit.getPrixHT(), produit.getPrixHT() * quantite
            ));

        }
        finalTicket.append("</table><br>");

        if(this.getCommandeSource().getMontantTVA5_5() != 0)
            finalTicket.append(String.format("<br><br>Total TVA à 5.5%% : %.2f €<br>", this.getCommandeSource().getMontantTVA5_5()));
        if(this.getCommandeSource().getMontantTVA10() != 0)
            finalTicket.append(String.format("Total TVA à 10%% : %.2f €<br>", this.getCommandeSource().getMontantTVA10()));
        if(this.getCommandeSource().getMontantTVA20() != 0)
            finalTicket.append(String.format("Total TVA à 20%% : %.2f €<br>", this.getCommandeSource().getMontantTVA20()));

        finalTicket.append(String.format("<br><div style='text-align:center;'><b>Total TTC : %.2f €</b></div><br>",
                this.getCommandeSource().getTotalTTC()));

        finalTicket.append(String.format("<br><div style='text-align:center;'><b>DOCUMENT PROVISOIRE</b></div>"
                + "<br>Transation n°%s<br>"
                + "%td/%tm/%ty %tH:%tM<br>",
                numTransaction,
                this.getDateCreation(),  this.getDateCreation(),  this.getDateCreation(),
                this.getDateCreation(),  this.getDateCreation()));

        finalTicket.append(String.format("<br><br><div style='text-align:center;'><b>A BIENTOT</b></div>"));

        return  finalTicket.toString();
    }
}
