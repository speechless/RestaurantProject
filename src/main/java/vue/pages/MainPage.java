package vue.pages;
import vue.utils.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MainPage implements PageContent {

    @Override
    public JPanel getContentPanel() {
        // Création d'un panneau principal
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();


//////////////////////////////////////////////////////////////////////////////////////////////////
        //Listes commandes en cours (gauche)

        // Ajout de la section avec la liste
        JPanel leftSection = new JPanel(new BorderLayout());
        leftSection.setBorder(new EmptyBorder(20, 20, 20, 10)); // Marges autour de la section

        // Titre de la section
        JLabel sectionTitle = new JLabel("Liste des commandes en cours", JLabel.CENTER);
        sectionTitle.setFont(new Font("Arial", Font.BOLD, 16));
        sectionTitle.setBorder(new EmptyBorder(10, 0, 10, 0)); // Marges autour du titre
        leftSection.add(sectionTitle, BorderLayout.NORTH);

        // Listes commandes en cours (gauche)
        DefaultListModel<CommandListItem> listModel = new DefaultListModel<>();

        //Remplir liste
        listModel.addElement(new CommandListItem("Élément 1", "Description pour l'élément 1."));

        JList<CommandListItem> list = new JList<>(listModel);
        JScrollPane scrollPane = Templates.setupScrollPane(list);
        leftSection.add(scrollPane, BorderLayout.CENTER);

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

        JButton topButton = Templates.setupClassicButton("Voir la carte",
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
        JButton middleButton = Templates.setupClassicButton("Voir plan de salle",
                ()-> PageManager.getInstance().showPage(new RoomPage()));
        middleSection.add(middleTitle, BorderLayout.NORTH);

        // Limiter la largeur du bouton
        middleButton.setPreferredSize(new Dimension(250, 40)); // Largeur et hauteur spécifiques
        midButtonContainer.add(middleButton);
        middleSection.add(midButtonContainer, BorderLayout.CENTER);

        rightSection.add(middleSection, gbc2);

// Partie 3 : Spacer
        gbc2.gridy = 2;
        gbc2.weighty = 0.3;
        JPanel spacerY = new JPanel();
        spacerY.setOpaque(false); // Rendre le panneau transparent
        rightSection.add(spacerY, gbc2);

// Partie 4 : Commandes récentes
        gbc2.gridy = 3;
        gbc2.weighty = 0.3; // 30% de hauteur
        JPanel bottomSection = new JPanel(new BorderLayout());
        bottomSection.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel bottomTitle = new JLabel("Commandes terminées");

        JList<CommandListItem> list2 =  CommandListItem.createList();
        list2.setFixedCellHeight(40);
        list2.setVisibleRowCount(6);

        JScrollPane scrollPane2 = Templates.setupScrollPane(list2);
        scrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        int preferredHeight = list2.getFixedCellHeight() * list2.getVisibleRowCount();
        scrollPane2.setPreferredSize(new Dimension(300, preferredHeight));

        JButton viewMoreButton = Templates.setupClassicButton("Voir Plus",
                ()-> PageManager.getInstance().showPage(new PreviousCommandsPage()));

        // Ajout des composants
        bottomSection.add(bottomTitle, BorderLayout.NORTH);
        bottomSection.add(scrollPane2, BorderLayout.CENTER);
        bottomSection.add(viewMoreButton, BorderLayout.SOUTH);
        rightSection.add(bottomSection, gbc2);
//////////////////////////////////////////////////////////////////////////////////////////////////
//Couleurs
        mainPanel.setBackground(Templates.getPrimaryColor());
        rightSection.setBackground(Templates.getPrimaryColor());
        leftSection.setBackground(Templates.getPrimaryColor());
        middleSection.setBackground(Templates.getPrimaryColor());
        topSection.setBackground(Templates.getPrimaryColor());
        bottomSection.setBackground(Templates.getPrimaryColor());

        return mainPanel;
    }
}
