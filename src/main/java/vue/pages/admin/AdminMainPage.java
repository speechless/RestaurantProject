package vue.pages.admin;

import vue.pages.*;

import vue.utils.Templates;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AdminMainPage implements PageContent {

    @Override
    public JPanel getContentPanel() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Carte des menus
        JPanel leftAdminSection = new JPanel(new GridBagLayout());
        leftAdminSection.setBorder(new EmptyBorder(20, 20, 20, 10)); // Marges autour de la section

        // Titre de la section
        JPanel modifCarte = new JPanel(new GridLayout(2, 1, 5, 5)); // Utilise GridLayout pour uniformiser
        JLabel st = new JLabel("Gérer la carte du restaurant", JLabel.CENTER);
        st.setFont(new Font("Arial", Font.BOLD, 16));
        st.setBorder(new EmptyBorder(10, 0, 10, 0)); // Marges autour du titre
        modifCarte.add(st, BorderLayout.NORTH); // Ajouter dans la position NORTH
        JButton modifCarteButton = Templates.setupClassicButton("Modifier la carte",
                () -> {
                    System.out.println("Page de la modification de carte");
                });
        modifCarte.add(modifCarteButton, BorderLayout.SOUTH); // Ajouter dans la position SOUTH

        // Ajouter la section au leftAdminSection
        gbc.gridx = 0; // Colonne
        gbc.gridy = 0; // Ligne
        gbc.gridwidth = 1; // Étend sur une colonne
        gbc.weightx = 1.0; // S'étend horizontalement
        gbc.weighty = 0.3; // Prend tout l'espace vertical
        gbc.fill = GridBagConstraints.BOTH; // Remplit horizontalement et verticalement
        gbc.insets = new Insets(5, 5, 5, 5); // Marges autour
        leftAdminSection.add(modifCarte, gbc);

        //////////////////////////////////////////////////////////////////////////////////////////////////
// Espace vide central
        gbc.gridy = 1;
        gbc.weighty = 0.4;
        JPanel spacerY = new JPanel();
        spacerY.setOpaque(false);
        leftAdminSection.add(spacerY, gbc);

        // Section des options avancées
        JPanel modifCartePlus = new JPanel(new BorderLayout()); // Utilisation explicite de BorderLayout
        JLabel titleModifPlus = new JLabel("Options avancées", JLabel.CENTER);
        titleModifPlus.setFont(new Font("Arial", Font.BOLD, 16));
        titleModifPlus.setBorder(new EmptyBorder(10, 0, 10, 0)); // Marges autour du titre
        modifCartePlus.add(titleModifPlus, BorderLayout.NORTH);

        JPanel buttonsPanel = new JPanel(new GridLayout(2, 1, 5, 5)); // Grille pour les boutons
        JButton modifMenusButton = Templates.setupClassicButton("Modifier les menus",
                () -> {
                    System.out.println("Page de la modification des menus");
                });
        buttonsPanel.add(modifMenusButton);
        JButton modifItemsButton = Templates.setupClassicButton("Modifier les items",
                () -> {
                    System.out.println("Page de la modification des items");
                });
        buttonsPanel.add(modifItemsButton);
        modifCartePlus.add(buttonsPanel, BorderLayout.CENTER);

        // Ajouter au leftAdminSection
        gbc.gridy = 2; // Ligne suivante
        gbc.weighty = 0.5; // Partage égal de l'espace vertical
        leftAdminSection.add(modifCartePlus, gbc);

        // Ajouter la section au mainPanel
        gbc.gridx = 0; // Colonne
        gbc.gridy = 0; // Ligne
        gbc.gridwidth = 1; // Étend sur une colonne
        gbc.weightx = 0.3; // S'étend horizontalement
        gbc.weighty = 1; // Prend tout l'espace vertical
        gbc.fill = GridBagConstraints.BOTH; // Remplit horizontalement et verticalement
        mainPanel.add(leftAdminSection, gbc);


        //////////////////////////////////////////////////////////////////////////////////////////////////
// Espace vide central
        gbc.gridx = 1;
        gbc.weightx = 0.3;
        JPanel spacerX = new JPanel();
        spacerX.setOpaque(false);
        mainPanel.add(spacerX, gbc);
        //////////////////////////////////////////////////////////////////////////////////////////////////

        JPanel rightAdminSection = new JPanel(new GridBagLayout());
        rightAdminSection.setBorder(new EmptyBorder(20, 20, 20, 10)); // Marges autour de la section

        // Titre de la section
        JPanel analytics = new JPanel(new GridLayout(2, 1, 5, 5)); // Utilise GridLayout pour uniformiser
        JLabel analyticsTitle = new JLabel("Analytics", JLabel.CENTER);
        analyticsTitle.setFont(new Font("Arial", Font.BOLD, 16));
        analyticsTitle.setBorder(new EmptyBorder(10, 0, 10, 0)); // Marges autour du titre
        analytics.add(analyticsTitle, BorderLayout.NORTH); // Ajouter dans la position NORTH
        JButton SellsHistory = Templates.setupClassicButton("Modifier la carte",
                () -> {
                    System.out.println("Page de la modification de carte");
                });
        analytics.add(SellsHistory, BorderLayout.SOUTH); // Ajouter dans la position SOUTH

        // Ajouter la section au leftAdminSection
        gbc.gridx = 0; // Colonne
        gbc.gridy = 0; // Ligne
        gbc.gridwidth = 1; // Étend sur une colonne
        gbc.weightx = 1.0; // S'étend horizontalement
        gbc.weighty = 0.3; // Prend tout l'espace vertical
        gbc.fill = GridBagConstraints.BOTH; // Remplit horizontalement et verticalement
        gbc.insets = new Insets(5, 5, 5, 5); // Marges autour
        rightAdminSection.add(analytics, gbc);

        //////////////////////////////////////////////////////////////////////////////////////////////////

        // Espace vide central
        gbc.gridy = 1;
        gbc.weighty = 0.4;
        JPanel spacerY2 = new JPanel();
        spacerY.setOpaque(false);
        rightAdminSection.add(spacerY2, gbc);

        // Section des options avancées
        JPanel modifRestaurant = new JPanel(new BorderLayout()); // Utilisation explicite de BorderLayout
        JLabel titleModifRestaurant = new JLabel("Gérer les informations du restaurant", JLabel.CENTER);
        titleModifRestaurant.setFont(new Font("Arial", Font.BOLD, 16));
        titleModifRestaurant.setBorder(new EmptyBorder(10, 0, 10, 0)); // Marges autour du titre
        modifRestaurant.add(titleModifRestaurant, BorderLayout.NORTH);

        JPanel buttonsPanelRight = new JPanel(new GridLayout(2, 1, 5, 5)); // Grille pour les boutons
        JButton roomModifButton = Templates.setupClassicButton("Modifier la salle",
                () -> {
                    System.out.println("Page de la modification de la salle");
                });
        buttonsPanelRight.add(roomModifButton);
        JButton modifInfosRestaurant = Templates.setupClassicButton("Modifier les infos du restau",
                () -> {
                    System.out.println("Page de la modification des infos du restau");
                });
        buttonsPanelRight.add(modifInfosRestaurant);
        modifRestaurant.add(buttonsPanelRight, BorderLayout.CENTER);

        // Ajouter au leftAdminSection
        gbc.gridy = 2; // Ligne suivante
        gbc.weighty = 0.5; // Partage égal de l'espace vertical
        rightAdminSection.add(modifRestaurant, gbc);

        // Ajouter la section au mainPanel
        gbc.gridx = 2; // Colonne
        gbc.gridy = 0; // Ligne
        gbc.gridwidth = 1; // Étend sur une colonne
        gbc.weightx = 0.3; // S'étend horizontalement
        gbc.weighty = 1; // Prend tout l'espace vertical
        gbc.fill = GridBagConstraints.BOTH; // Remplit horizontalement et verticalement
        mainPanel.add(rightAdminSection, gbc);
        return mainPanel;
    }
}
