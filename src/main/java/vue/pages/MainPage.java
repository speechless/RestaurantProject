package vue.pages;
import requete.RequeteRestaurant;
import vue.utils.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MainPage implements PageContent {

    @Override
    public JPanel getContentPanel() {
        // Création d'un panneau principal
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        RequeteRestaurant rq = RequeteRestaurant.getInstance();

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
        gbcLeft.gridx = 0;
        gbcLeft.gridy = 0;
        gbcLeft.weightx = 1.0;
        gbcLeft.fill = GridBagConstraints.HORIZONTAL;
        leftSection.add(sectionTitle, gbcLeft);

        JButton createNewCommandButton = ButtonTemplates.setupClassicButton("Nouvelle commande",
                () -> PageManager.getInstance().showPage(new CommandPage()));

        createNewCommandButton.setPreferredSize(new Dimension(0, 50)); // Hauteur fixe de 50px
        gbcLeft.gridy = 1;
        gbcLeft.weightx = 1.0;
        gbcLeft.fill = GridBagConstraints.HORIZONTAL;
        leftSection.add(createNewCommandButton, gbcLeft);

        //Remplir liste
        //listModel.addElement(new CommandListItem("Élément 1", "Description pour l'élément 1."));
        JList<CommandListItem> commandesCourantesList =  CommandListItem.createList(rq.getCommandesCourantes());
        commandesCourantesList.addMouseListener(new MouseListener() {
           @Override
           public void mouseClicked(MouseEvent e) {
               if (e.getClickCount() == 2) {
                   PageManager.getInstance().showPage(new CommandPage(commandesCourantesList.getSelectedValue().getId()));
               }
           }

           @Override
           public void mousePressed(MouseEvent e) {}
           @Override
           public void mouseReleased(MouseEvent e) {}
           @Override
           public void mouseEntered(MouseEvent e) {}
           @Override
           public void mouseExited(MouseEvent e) {}
       });

        JScrollPane scrollPane = Templates.setupCommandeScrollPane(commandesCourantesList);
        gbcLeft.gridy = 2;
        gbcLeft.weighty = 1.0; // Prend tout l'espace vertical restant
        gbcLeft.fill = GridBagConstraints.BOTH; // Remplit horizontalement et verticalement
        leftSection.add(scrollPane, gbcLeft);

        // Section gauche
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3; // 30% de la largeur
        gbc.weighty = 1.0; // Prendre tout l'espace vertical
        gbc.fill = GridBagConstraints.BOTH;

        mainPanel.add(leftSection, gbc);

//////////////////////////////////////////////////////////////////////////////////////////////////
// Espace vide central
        gbc.gridx = 1;
        gbc.weightx = 0.3;
        JPanel spacerX = new JPanel();
        spacerX.setOpaque(false);
        mainPanel.add(spacerX, gbc);


//////////////////////////////////////////////////////////////////////////////////////////////////

        // Section droite
        JPanel rightSection = new JPanel();
        gbc.gridx = 2;
        gbc.weightx = 0.4;
        mainPanel.add(rightSection, gbc);


        // Diviser la section droite
        rightSection.setLayout(new GridBagLayout());
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.gridx = 0;
        gbc2.fill = GridBagConstraints.BOTH;
        gbc2.weightx = 1.0;

// Partie 1 : Carte des menus
        gbc2.gridy = 0;
        gbc2.weighty = 0.2; // 20% de hauteur
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

        rightSection.add(topSection, gbc2);

// Partie 2 : Plan de salle
        gbc2.gridy = 1;
        gbc2.weighty = 0.2; // 20% de hauteur
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

        rightSection.add(middleSection, gbc2);

// Partie 3 : Spacer
        gbc2.gridy = 2;
        gbc2.weighty = 0.1;
        JPanel spacerY = new JPanel();
        spacerY.setOpaque(false); // Rendre le panneau transparent
        rightSection.add(spacerY, gbc2);

// Partie 4 : Commandes récentes
        gbc2.gridy = 3;
        gbc2.weighty = 0.5; // 30% de hauteur
        JPanel bottomSection = new JPanel(new BorderLayout());
        bottomSection.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel bottomTitle = new JLabel("Commandes terminées");

        //import depuis la BDD

        JList<CommandListItem> commandesFiniesList =  CommandListItem.createList(rq.getCommandesTermineesMain(4));
        commandesFiniesList.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    PageManager.getInstance().showPage(new PastCommandPage(commandesFiniesList.getSelectedValue().getId()));
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        });


        commandesFiniesList.setFixedCellHeight(100);
        commandesFiniesList.setVisibleRowCount(4);

        JScrollPane scrollPane2 = Templates.setupCommandeScrollPane(commandesFiniesList);
        int preferredHeight = commandesFiniesList.getFixedCellHeight() * commandesFiniesList.getVisibleRowCount();
        scrollPane2.setPreferredSize(new Dimension(100, preferredHeight));

        JButton viewMoreButton = ButtonTemplates.setupClassicButton("Voir Plus",
                ()-> PageManager.getInstance().showPage(new PreviousCommandsPage()));

        // Ajout des composants
        bottomSection.add(bottomTitle, BorderLayout.NORTH);
        bottomSection.add(scrollPane2, BorderLayout.CENTER);
        bottomSection.add(viewMoreButton, BorderLayout.SOUTH);
        rightSection.add(bottomSection, gbc2);
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
