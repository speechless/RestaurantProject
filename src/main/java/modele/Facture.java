package modele;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import com.itextpdf.layout.Document;
import requete.RequeteRestaurant;

import java.util.Objects;

@Entity
public class Facture extends Recu {

    @Column(nullable = false)
    private int numFacture;
    private String nomClient;
    private String prenomClient;
    private String telephoneClient;
    private String mailClient;
    private String numeroTVAClient;
    private String conditions = "Aucune condition";

    public Facture(Commande commandeSource, String nomClient, String prenomClient, String telephoneClient, String mailClient) {
        super(commandeSource);
        this.numFacture = CompteurManager.getNextNumFacture();

        this.nomClient = nomClient;
        this.prenomClient = prenomClient;
        this.telephoneClient = telephoneClient;
        this.mailClient = mailClient;
        this.numeroTVAClient = "0";
    }

    public Facture(Commande commandeSource, String nomClient, String prenomClient, String telephoneClient, String mailClient, String numeroTVAClient) {
        this(commandeSource, nomClient, prenomClient, telephoneClient, mailClient);
        this.numeroTVAClient = numeroTVAClient;
    }
    public Facture(Commande commandeSource, String nomClient, String prenomClient, String telephoneClient, String mailClient, String numeroTVAClient,String conditions) {
        this(commandeSource, nomClient, prenomClient, telephoneClient, mailClient);
        this.numeroTVAClient = numeroTVAClient;
        this.conditions=conditions;
    }

    public Facture() {

    }


    public String genererTexte() {
        Restaurant restaurant = RequeteRestaurant.getInstance().getRestaurant();
        StringBuilder finalFacture = new StringBuilder(
                String.format("<html>"
                        + "<div style='text-align:center;'><h1>%s</h1></div><br><br>"
                        + "<div style='text-align:center;'>Adresse du restaurant : %s</div><br>"
                        + "<div style='text-align:center;'>SIREN : %s</div><br>"
                        + "<div style='text-align:center;'>TVA : %s</div><br>",
                        restaurant.getName(), restaurant.getAddress(), restaurant.getSIRENNumber(),restaurant.getTVANumber()));

        finalFacture.append(String.format("Facture n°%s<br>", numFacture));
        finalFacture.append(String.format("Client : %s %s<br>", nomClient.toUpperCase(), prenomClient));
        finalFacture.append(String.format("Téléphone : %s<br>", telephoneClient));
        finalFacture.append(String.format("Mail : %s<br>", mailClient));
        finalFacture.append(String.format("Numéro TVA client : %s<br>", numeroTVAClient));

        finalFacture.append(String.format("<b>Table %s</b><br>"
                        + "&nbsp;&nbsp;&nbsp;&nbsp;SLIM"
                        + "<br><br>"
                        + "Produits commandés :<br>",getCommandeSource().getNumTable()));



        finalFacture.append("<table style='width:100%; border-collapse:collapse;'>");
        finalFacture.append("<tr><th>Quantité</th><th>Produit</th><th>Prix Unitaire</th><th>Total</th></tr>");

        for (QuantiteCommande quantiteCommande: getCommandeSource().getCompositionCommande()) {
            Commandable produit = quantiteCommande.getProduit();
            int quantite = quantiteCommande.getQuantite();

            finalFacture.append(String.format(
                    "<tr><td>%d</td><td>%s</td><td>%.2f €</td><td>%.2f €</td></tr>",
                    quantite, produit.getNom(), produit.getPrixHT(), produit.getPrixHT() * quantite
            ));

        }
        finalFacture.append("</table><br>");

        finalFacture.append(String.format("<br><br>Total TVA à 5.5%% : %.2f €<br>", this.getCommandeSource().getMontantTVA5_5()));
        finalFacture.append(String.format("Total TVA à 10%% : %.2f €<br>", this.getCommandeSource().getMontantTVA10()));
        finalFacture.append(String.format("Total TVA à 20%% : %.2f €<br>", this.getCommandeSource().getMontantTVA20()));

        finalFacture.append(String.format("<br><div style='text-align:center;'><b>Total TTC : %.2f €</b></div><br>",
                this.getCommandeSource().getTotalTTC()));

        finalFacture.append(String.format("<br><div style='text-align:center;'><b>FACTURE</b></div>"
                        + "<br>Transation n°%s<br>"
                        + "%td/%tm/%ty %tH:%tM<br>",
                numFacture,
                this.getDateCreation(),  this.getDateCreation(),  this.getDateCreation(),
                this.getDateCreation(),  this.getDateCreation()));


        return  finalFacture.toString();
    }

    public void generateDocument(PdfDocument pdfDoc){
        Restaurant restaurant = RequeteRestaurant.getInstance().getRestaurant();
        // Créer un document
        Document document = new Document(pdfDoc);

        // Ajouter un titre centré
        Paragraph title = new Paragraph("Facture - "+ restaurant.getName())
                .setTextAlignment(TextAlignment.CENTER) // Centrer le texte
                .setBold() // Texte en gras
                .setFontSize(20); // Taille du texte
        document.add(title);

        Paragraph restauGeneralInfos = new Paragraph("Adresse du restaurant : "+restaurant.getAddress()+"\n" +
                "SIREN : "+restaurant.getSIRENNumber()+"\n" +
                "TVA : "+restaurant.getTVANumber()+"\n")
                .setTextAlignment(TextAlignment.LEFT); // Aligné à gauche
        document.add(restauGeneralInfos);

        Paragraph numTableText = new Paragraph("\n"+"Table "+getCommandeSource().getNumTable()+"\n")
                .setTextAlignment(TextAlignment.LEFT) // Aligné à gauche
                .setBold();
        document.add(numTableText);

        Paragraph details = new Paragraph("Facture n°" + numFacture + "\n" +
                "Client : "+ nomClient.toUpperCase() +" "+ prenomClient + "\n" +
                "Téléphone : "+ telephoneClient + "\n" +
                "Mail : "+ mailClient +"\n"
                +"Numéro TVA client : "+(!Objects.equals(numeroTVAClient,"") ? numeroTVAClient : "Pas de numéro de TVA Client")+"\n"
                +"Condition : "+ (!Objects.equals(conditions, "") ? conditions : "Aucune condition")+"\n\n"
                +"Produits commandés :\n")
                .setTextAlignment(TextAlignment.LEFT); // Aligné à gauche
        document.add(details);

        // Ajouter un tableau
        float[] pointColumnWidths = { 100F, 100F, 100F, 100F }; // Largeurs des colonnes
        Table table = new Table(pointColumnWidths);
        table.addCell(new Cell().add(new Paragraph("Quantité")));
        table.addCell(new Cell().add(new Paragraph("Produit")));
        table.addCell(new Cell().add(new Paragraph("Prix unitaire")));
        table.addCell(new Cell().add(new Paragraph("Total")));

        for (QuantiteCommande quantiteCommande: getCommandeSource().getCompositionCommande()) {
            Commandable produit = quantiteCommande.getProduit();
            int quantite = quantiteCommande.getQuantite();

            table.addCell(new Cell().add(new Paragraph(String.valueOf((quantite)))));
            table.addCell(new Cell().add(new Paragraph( produit.getNom())));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(produit.getPrixHT()))));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(produit.getPrixHT() * quantite))));
        }

        document.add(table);

        Paragraph TVAText5_5 = new Paragraph(String.format("\nTotal TVA à 5.5%% : "+
                this.getCommandeSource().getMontantTVA5_5()+"€\n"));
        Paragraph TVAText10 = new Paragraph(String.format("Total TVA à 10%% : "+
                this.getCommandeSource().getMontantTVA10()+"€\n"));
        Paragraph TVAText20 = new Paragraph(String.format("Total TVA à 20%% : "+
                this.getCommandeSource().getMontantTVA20()+"€\n"));
        Paragraph TotalTTC = new Paragraph(String.format("Total TTC : "+
                this.getCommandeSource().getTotalTTC()+"€\n"));

        document.add(TVAText5_5);
        document.add(TVAText10);
        document.add(TVAText20);
        document.add(TotalTTC);



        // Ajouter un texte en bas
        Paragraph footer = new Paragraph(
                        "Transation n°"+numFacture+"\n"
                        + String.format("%td/%tm/%ty %tH:%tM",
                this.getDateCreation(),  this.getDateCreation(),  this.getDateCreation(),
                this.getDateCreation(),  this.getDateCreation()))
                .setTextAlignment(TextAlignment.RIGHT);
        document.add(footer);

        Paragraph aBientot = new Paragraph("A BIENTOT CHEZ "+ restaurant.getName())
                .setTextAlignment(TextAlignment.CENTER) // Centrer le texte
                .setBold() // Texte en gras
                .setFontSize(20); // Taille du texte
        document.add(aBientot);

        document.close();
    }
}
