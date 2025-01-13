package vue.actions;

import modele.Commande;
import modele.Facture;
import vue.pages.MainPage;
import vue.pages.PageContent;

import javax.swing.*;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import vue.pages.PageManager;
import vue.utils.ButtonTemplates;

public class CreateBill implements PageContent {
    private Commande commande;
    private Facture facture;

    private JTextField champNom;
    private JTextField champPrenom;
    private JTextField champTel;
    private JTextField champMail;
    private JTextField champNumTVA;
    private JTextField champConditions;


    public CreateBill(Commande c){
        facture = new Facture();
        commande=c;
    }

    @Override
    public JPanel getContentPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Créer un panneau pour les champs et les labels
        JPanel gridPanel = new JPanel(new GridLayout(6, 2, 10, 10));

        // Ajouter les labels et les champs texte
        JLabel label1 = new JLabel("Nom* :");
        champNom = new JTextField();

        JLabel label2 = new JLabel("Prénom* :");
        champPrenom = new JTextField();

        JLabel label3 = new JLabel("Email* :");
        champMail = new JTextField();

        JLabel label4 = new JLabel("Téléphone* :");
        champTel = new JTextField();

        JLabel label5 = new JLabel("Numéro TVA client :");
        champNumTVA = new JTextField();

        JLabel label6 = new JLabel("Conditions de paiement :");
        champConditions = new JTextField();

        gridPanel.add(label1);
        gridPanel.add(champNom);

        gridPanel.add(label2);
        gridPanel.add(champPrenom);

        gridPanel.add(label3);
        gridPanel.add(champMail);

        gridPanel.add(label4);
        gridPanel.add(champTel);

        gridPanel.add(label5);
        gridPanel.add(champNumTVA);

        gridPanel.add(label6);
        gridPanel.add(champConditions);

        // Ajouter le bouton sous les champs
        JButton bouton = ButtonTemplates.setupClassicButton("Confirmer",
                this::printBill);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(bouton); // Centrer le bouton

        // Ajouter les sous-panneaux au panneau principal
        panel.add(gridPanel, BorderLayout.CENTER); // Panneau central avec la grille
        panel.add(buttonPanel, BorderLayout.SOUTH); // Bouton en bas

        return panel; // Retourner le panneau
    }

    public Facture getFacture() {
        return facture;
    }

    public void printBill() {
        facture= new Facture(commande,champNom.getText(),champPrenom.getText(),champTel.getText(),champMail.getText(),
                champNumTVA.getText(),champConditions.getText());

        try {
            // Générer le PDF en mémoire
            ByteArrayOutputStream pdfStream = generatePdf();

            // Créer un fichier temporaire pour ouvrir le PDF dans le navigateur
            File tempPdfFile = File.createTempFile("Facture"+commande.getId()+"-", ".pdf");
            tempPdfFile.deleteOnExit();

            // Écrire les données du flux PDF dans ce fichier temporaire
            try (FileOutputStream fos = new FileOutputStream(tempPdfFile)) {
                fos.write(pdfStream.toByteArray());
            }

            // Ouvrir le fichier PDF dans le navigateur ou lecteur PDF par défaut
            Desktop.getDesktop().browse(tempPdfFile.toURI());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erreur : " + ex.getMessage());
        }

        PageManager.getInstance().showPage(new MainPage());
    }

    /**
     * Génère un PDF avec du texte en mémoire et retourne un ByteArrayOutputStream
     * @return Un ByteArrayOutputStream contenant le PDF généré
     */
    public ByteArrayOutputStream generatePdf() {
        try {
            // Créer un flux en mémoire pour le PDF
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            // Initialiser PdfWriter et PdfDocument
            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdfDoc = new PdfDocument(writer);
            this.getFacture().generateDocument(pdfDoc);

            return outputStream; // Retourner le flux en mémoire

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
