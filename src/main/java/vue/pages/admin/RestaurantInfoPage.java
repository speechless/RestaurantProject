package vue.pages.admin;

import modele.Restaurant;
import requete.RequeteRestaurant;
import vue.pages.MainPage;
import vue.pages.PageContent;
import vue.pages.PageManager;
import vue.utils.ButtonTemplates;
import vue.utils.Commons;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 * Page de création et de modification du restaurant, toutes les infos
 */
public class RestaurantInfoPage implements PageContent {

    Restaurant restaurant;

    public RestaurantInfoPage() {
        this.restaurant = Commons.mainGetRestaurant();

        if (this.restaurant == null) {
            this.restaurant = new Restaurant();
        }

    }

    @Override
    public JPanel getContentPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        if (Commons.mainGetRestaurant() != null) {
            JPanel borderPanel = ButtonTemplates.returnMenuButtonSimple();
            mainPanel.add(borderPanel, BorderLayout.NORTH);
        }
        else {
            JPanel borderPanel = new JPanel();
            JLabel titre = new JLabel("Saisissez les informations de votre restaurant");
            titre.setFont(new Font("Arial", Font.PLAIN, 40));

            borderPanel.add(titre);
            mainPanel.add(borderPanel, BorderLayout.NORTH);
        }

        // Utiliser GridBagLayout pour une disposition flexible
        JPanel gridPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Espacement autour des composants
        gbc.fill = GridBagConstraints.HORIZONTAL; // Étendre horizontalement les champs texte
        gbc.weightx = 1; // Permet aux champs texte de prendre tout l'espace horizontal
        gbc.weighty = 0; // Pas d'étirement vertical par défaut

        // Définir les labels et champs texte
        JLabel nameLabel = new JLabel("Nom du restaurant :");
        JTextField champNom = new JTextField(restaurant.getName());
        champNom.setPreferredSize(new Dimension(200, 30)); // Hauteur augmentée

        JLabel addressLabel = new JLabel("Adresse :");
        JTextField champAddresse = new JTextField(restaurant.getAddress());
        champAddresse.setPreferredSize(new Dimension(200, 30));

        JLabel tvaLabel = new JLabel("Numéro de TVA :");
        JTextField champTVA = new JTextField(restaurant.getTVANumber());
        champTVA.setPreferredSize(new Dimension(200, 30));

        JLabel telLabel = new JLabel("Téléphone :");
        JTextField champTel = new JTextField(restaurant.getPhoneNumber());
        champTel.setPreferredSize(new Dimension(200, 30));

        JLabel sirenLabel = new JLabel("Numéro de SIREN :");
        JTextField champSIREN = new JTextField(restaurant.getSIRENNumber());
        champSIREN.setPreferredSize(new Dimension(200, 30));

        //Empecher la modification de l'ID du restaurant
        if (this.restaurant != null) {
            champSIREN.setEnabled(false);
        }

        // Ajouter les composants au panneau avec GridBagLayout
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST; // Alignement à droite pour les labels
        gridPanel.add(nameLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST; // Alignement à gauche pour les champs texte
        gridPanel.add(champNom, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.EAST;
        gridPanel.add(addressLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gridPanel.add(champAddresse, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.EAST;
        gridPanel.add(tvaLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gridPanel.add(champTVA, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.EAST;
        gridPanel.add(telLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gridPanel.add(champTel, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.EAST;
        gridPanel.add(sirenLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gridPanel.add(champSIREN, gbc);

        // Ajouter le bouton "Confirmer"
        JButton bouton = ButtonTemplates.setupClassicButton("Confirmer", () -> {
            if(!Objects.equals(champNom.getText(), "") &&!Objects.equals(champAddresse.getText(), "")
            && !Objects.equals(champTVA.getText(), "") && !Objects.equals(champTel.getText(), "")
            && !Objects.equals(champSIREN.getText(), "")) {

                restaurant.setName(champNom.getText());
                restaurant.setAddress(champAddresse.getText());
                restaurant.setTVANumber(champTVA.getText());
                restaurant.setPhoneNumber(champTel.getText());
                restaurant.setSIRENNumber(champSIREN.getText());

                Commons.setRestaurant(RequeteRestaurant.getInstance().saveRestaurant(this.restaurant));
                PageManager.getInstance().showPage(new MainPage());
            }else{
                JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs.");
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(bouton);

        // Ajouter les sous-panneaux au panneau principal
        mainPanel.add(gridPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        return mainPanel;
    }


    // Méthode utilitaire pour ajouter une ligne avec label et champ texte
    private void addRow(JPanel panel, GridBagConstraints gbc, String labelText, String textFieldValue) {
        JLabel label = new JLabel(labelText);
        JTextField textField = new JTextField(textFieldValue);

        gbc.gridx = 0; // Colonne du label
        gbc.weightx = 0; // Pas de redimensionnement pour le label
        panel.add(label, gbc);

        gbc.gridx = 1; // Colonne du champ texte
        gbc.weightx = 1; // Le champ texte s'étend
        panel.add(textField, gbc);
    }


}
