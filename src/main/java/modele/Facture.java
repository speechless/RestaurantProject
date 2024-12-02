package modele;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Facture extends Recu {

    @Column(nullable = false)
    private int numFacture;
    private String nomClient;
    private String prenomClient;
    private String telephoneClient;
    private String mailClient;
    private int numeroTVAClient;
    private final String conditions = "Aucune condition";

    public Facture(Commande commandeSource, String nomClient, String prenomClient, String telephoneClient, String mailClient) {
        super(commandeSource);
        this.numFacture = CompteurManager.getNextNumFacture();

        this.nomClient = nomClient;
        this.prenomClient = prenomClient;
        this.telephoneClient = telephoneClient;
        this.mailClient = mailClient;
        this.numeroTVAClient = 0;
    }

    public Facture(Commande commandeSource, String nomClient, String prenomClient, String telephoneClient, String mailClient, int numeroTVAClient) {
        this(commandeSource, nomClient, prenomClient, telephoneClient, mailClient);
        this.numeroTVAClient = numeroTVAClient;
    }

    public Facture() {

    }

    public String genererTexte() {
        String nomRestaurant = "MON RESTO";

        StringBuilder affichageTicket = new StringBuilder();
        affichageTicket.append(nomRestaurant + "\n");
        affichageTicket.append("Facture n°" + numFacture + "\n");
        affichageTicket.append("Client : " + nomClient.toUpperCase() + " " + prenomClient + "\n");
        affichageTicket.append("Téléphone : " + telephoneClient + "\n");
        affichageTicket.append("Mail : " + mailClient + "\n");
        affichageTicket.append("Numéro TVA client : " + numeroTVAClient + "\n");
        affichageTicket.append("Produits commandés :\n");

        affichageTicket.append(String.format("%50s | %15s   | %10s | %10s\n", "Nom", "Prix unitaire", "Quantité", "Prix total"));
        for (QuantiteCommande quantiteCommande: this.getCommandeSource().getCompositionCommande()) {
            Commandable produit = quantiteCommande.getProduit();
            int quantite = quantiteCommande.getQuantite();

            affichageTicket.append(String.format("%50s | %15s € | %10s | %10s €\n", produit.getNom(), produit.getPrixHT(), quantite, produit.getPrixHT() * quantite));
        }

        affichageTicket.append(conditions + "\n");

        return affichageTicket.toString();
    }
}
