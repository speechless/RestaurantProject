package vue.pages.admin;

import modele.Restaurant;
import requete.RequeteRestaurant;
import vue.pages.CommandPage;
import vue.pages.MainPage;
import vue.pages.PageContent;
import vue.pages.PageManager;
import vue.utils.ButtonTemplates;
import vue.utils.Commons;

import javax.swing.*;
import java.awt.*;

public class RestaurantInfoPage implements PageContent {
    @Override
    public JPanel getContentPanel() {
        Restaurant restaurant = Commons.mainGetRestaurant();
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Créer un panneau pour les champs et les labels
        JPanel gridPanel = new JPanel(new GridLayout(6, 2, 10, 10));

        // Ajouter les labels et les champs texte
        JLabel label1 = new JLabel("Nom du restaurant :");
        JTextField champNom = new JTextField(restaurant.getName());

        JLabel label2 = new JLabel("Adresse :");
        JTextField champAddresse = new JTextField(restaurant.getAddress());

        JLabel label3 = new JLabel("Numéro de TVA :");
        JTextField champTVA = new JTextField(restaurant.getTVANumber());

        JLabel label4 = new JLabel("Téléphone :");
        JTextField champTel = new JTextField(restaurant.getPhoneNumber());

        JLabel label5 = new JLabel("Numéro de SIREN :");
        JTextField champSIREN = new JTextField(restaurant.getSIRENNumber());

        gridPanel.add(label1);
        gridPanel.add(champNom);

        gridPanel.add(label2);
        gridPanel.add(champAddresse);

        gridPanel.add(label3);
        gridPanel.add(champTVA);

        gridPanel.add(label4);
        gridPanel.add(champTel);

        gridPanel.add(label5);
        gridPanel.add(champSIREN);


        JButton bouton = ButtonTemplates.setupClassicButton("Confirmer", () -> {
            RequeteRestaurant.getInstance().modifRestaurant(
                    champNom.getText(),
                    champAddresse.getText(),
                    champTVA.getText(),
                    champTel.getText(),
                    champSIREN.getText()
            );
            PageManager.getInstance().showPage(new AdminMainPage());
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(bouton); // Centrer le bouton

        // Ajouter les sous-panneaux au panneau principal
        panel.add(gridPanel, BorderLayout.CENTER); // Panneau central avec la grille
        panel.add(buttonPanel, BorderLayout.SOUTH); // Bouton en bas
        return panel;
    }
}
