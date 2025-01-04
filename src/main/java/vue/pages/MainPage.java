package vue.pages;
import requete.RequeteRestaurant;
import vue.utils.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MainPage implements PageContent {

    @Override
    public JPanel getContentPanel() {
        // Création d'un panneau principal
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbcMain = new GridBagConstraints();
        RequeteRestaurant rq = new RequeteRestaurant();

//////////////////////////////////////////////////////////////////////////////////////////////////
        //Listes commandes en cours (gauche)

        // Ajout de la section avec la liste
        JPanel leftSection = new JPanel(new GridBagLayout());
        leftSection.setBorder(new EmptyBorder(20, 20, 20, 10)); // Marges autour de la section
        GridBagConstraints gbcLeft = new GridBagConstraints();

        // Titre de la section
        JLabel sectionTitle = new JLabel("Liste des commandes en cours", JLabel.CENTER);
        sectionTitle.setFont(new Font("Arial", Font.BOLD, 16));
        sectionTitle.setBorder(new EmptyBorder(10, 0, 10, 0)); // Marges autour du titre
        gbcLeft.gridx = 0; //1ère ligne
        gbcLeft.gridy = 0; //1ère colonne
        gbcLeft.weightx = 1.0;
        gbcLeft.fill = GridBagConstraints.HORIZONTAL; //Prend toute la largueur
        leftSection.add(sectionTitle, gbcLeft);

        JButton createNewCommandButton = ButtonTemplates.setupClassicButton("Nouvelle commande",
                () -> PageManager.getInstance().showPage(new RoomPage()));
        createNewCommandButton.setPreferredSize(new Dimension(0, 50)); // Hauteur fixe de 50px
        gbcLeft.gridy = 1; //2ème colonne
        gbcLeft.weightx = 1.0;
        gbcLeft.fill = GridBagConstraints.HORIZONTAL; //Prend toute la largueur
        leftSection.add(createNewCommandButton, gbcLeft);

        //Import BDD
        JList<CommandListItem> list1 =  CommandListItem.createList(rq.getCommandesCourantes());
        JScrollPane scrollPane = Templates.setupCommandeScrollPane(list1);

        gbcLeft.gridy = 2; //3ème colonne
        gbcLeft.weighty = 1.0; // Prend tout l'espace vertical restant
        gbcLeft.fill = GridBagConstraints.BOTH; // Remplit horizontalement et verticalement
        leftSection.add(scrollPane, gbcLeft);

        // Section gauche
        gbcMain.gridx = 0; //1ère ligne de la page
        gbcMain.gridy = 0; //1ère colonne de la page
        gbcMain.weightx = 0.3; // 30% de la largeur
        gbcMain.weighty = 1.0; // Prendre tout l'espace vertical
        gbcMain.fill = GridBagConstraints.BOTH;

        mainPanel.add(leftSection, gbcMain);

//////////////////////////////////////////////////////////////////////////////////////////////////

// Espace vide central
        gbcMain.gridx = 1;  //2ème ligne de la page
        gbcMain.weightx = 0.3; // 30% de la largeur
        JPanel spacerX = new JPanel();
        spacerX.setOpaque(false);
        mainPanel.add(spacerX, gbcMain);


//////////////////////////////////////////////////////////////////////////////////////////////////

        // Section droite
        JPanel rightSection = new JPanel();
        gbcMain.gridx = 2; //3ème ligne de la page
        gbcMain.weightx = 0.4; // 40% de la largeur
        mainPanel.add(rightSection, gbcMain);


        // Diviser la section droite
        rightSection.setLayout(new GridBagLayout());
        GridBagConstraints gbcRight = new GridBagConstraints();
        gbcRight.gridx = 0; //1ère ligne de la section de droite
        gbcRight.fill = GridBagConstraints.BOTH;
        gbcRight.weightx = 1.0;

// Partie 1 : Carte des menus
        gbcRight.gridy = 0; //1ère colonne
        gbcRight.weighty = 0.2; // 20% de hauteur
        JPanel topSection = new JPanel(new BorderLayout());
        topSection.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel topTitle = new JLabel("Carte des menus");
        topSection.add(topTitle, BorderLayout.NORTH);

        // Conteneur pour centrer le bouton
        JPanel topButtonContainer = new JPanel(new GridBagLayout()); // Centrage parfait avec GridBagLayout
        topButtonContainer.setOpaque(false); // Transparence pour ne pas perturber le style

        JButton topButton = ButtonTemplates.setupClassicButton("Voir la carte",
                () -> PageManager.getInstance().showPage(new MenuPage()));

        // Limiter la largeur du bouton
        topButton.setPreferredSize(new Dimension(250, 40)); // Largeur et hauteur spécifiques
        topButtonContainer.add(topButton);
        topSection.add(topButtonContainer, BorderLayout.CENTER);

        rightSection.add(topSection, gbcRight);

// Partie 2 : Plan de salle
        gbcRight.gridy = 1; //2ème ligne
        gbcRight.weighty = 0.2; // 20% de hauteur
        JPanel middleSection = new JPanel(new BorderLayout());
        middleSection.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Conteneur pour centrer le bouton
        JPanel midButtonContainer = new JPanel(new GridBagLayout()); // Centrage parfait avec GridBagLayout
        midButtonContainer.setOpaque(false); // Transparence pour ne pas perturber le style

        JLabel middleTitle = new JLabel("Plan de salle");
        JButton middleButton = ButtonTemplates.setupClassicButton("Voir plan de salle",
                ()-> PageManager.getInstance().showPage(new RoomPage()));
        middleSection.add(middleTitle, BorderLayout.NORTH);

        // Limiter la largeur du bouton
        middleButton.setPreferredSize(new Dimension(250, 40)); // Largeur et hauteur spécifiques
        midButtonContainer.add(middleButton);
        middleSection.add(midButtonContainer, BorderLayout.CENTER);

        rightSection.add(middleSection, gbcRight);

// Partie 3 : Spacer
        gbcRight.gridy = 2; //3ème ligne
        gbcRight.weighty = 0.1;
        JPanel spacerY = new JPanel();
        spacerY.setOpaque(false); // Rendre le panneau transparent
        rightSection.add(spacerY, gbcRight);

// Partie 4 : Commandes récentes
        gbcRight.gridy = 3; //4ème ligne
        gbcRight.weighty = 0.5; // 30% de hauteur
        JPanel bottomSection = new JPanel(new BorderLayout());
        bottomSection.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel bottomTitle = new JLabel("Commandes terminées");

        //import depuis la BDD
        JList<CommandListItem> list2 =  CommandListItem.createList(rq.getCommandesTermineesMain());

        //Diminuer la taille du ScrollPane
        list2.setFixedCellHeight(100);
        list2.setVisibleRowCount(4);

        JScrollPane scrollPane2 = Templates.setupCommandeScrollPane(list2);
        int preferredHeight = list2.getFixedCellHeight() * list2.getVisibleRowCount();
        scrollPane2.setPreferredSize(new Dimension(100, preferredHeight));

        //Bouton Voir Plus
        JButton viewMoreButton = ButtonTemplates.setupClassicButton("Voir Plus",
                ()-> PageManager.getInstance().showPage(new PreviousCommandsPage()));

        // Ajout des composants de la section du bas
        bottomSection.add(bottomTitle, BorderLayout.NORTH);
        bottomSection.add(scrollPane2, BorderLayout.CENTER);
        bottomSection.add(viewMoreButton, BorderLayout.SOUTH);

        rightSection.add(bottomSection, gbcRight);

//////////////////////////////////////////////////////////////////////////////////////////////////

        //Couleurs
        mainPanel.setBackground(Commons.getPrimaryColor());
        rightSection.setBackground(Commons.getPrimaryColor());
        leftSection.setBackground(Commons.getPrimaryColor());
        middleSection.setBackground(Commons.getPrimaryColor());
        topSection.setBackground(Commons.getPrimaryColor());
        bottomSection.setBackground(Commons.getPrimaryColor());

        return mainPanel;
    }
}
