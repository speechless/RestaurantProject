package vue.pages;
import requete.RequeteRestaurant;
import vue.utils.*;
import vue.pages.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;


public class MenuPage implements PageContent {

    @Override
    public JPanel getContentPanel() {
        // Panneau principal avec GridBagLayout
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        RequeteRestaurant rr = RequeteRestaurant.getInstance();

        // Panneau secondaire avec BorderLayout
        JPanel borderPanel = new JPanel(new BorderLayout());

        // Titre de la section
        JButton topButton = Templates.setupClassicButton("Retour page d'accueil",
                () -> PageManager.getInstance().showPage(new MainPage()));
        topButton.setFont(new Font("Arial", Font.PLAIN, 12));
        topButton.setMargin(new Insets(0, 0, 0, 0)); // Supprime les marges internes
        topButton.setPreferredSize(new Dimension(150, 30));

        // Ajouter le bouton au panneau BorderLayout
        borderPanel.add(topButton, BorderLayout.WEST);

        // Ajouter borderPanel au mainPanel avec GridBagConstraints
        gbc.gridx = 0; // Colonne
        gbc.gridy = 0; // Ligne
        gbc.gridwidth = 2; // Étend sur deux colonnes
        gbc.weightx = 1.0; // S'étend horizontalement
        gbc.weighty = 0.0; // Pas de poids vertical
        gbc.fill = GridBagConstraints.HORIZONTAL; // Remplir horizontalement
        gbc.insets = new Insets(5, 5, 5, 5); // Marges autour
        mainPanel.add(borderPanel, gbc);

        //////////////////////////////////////////////////////////////////////////////////////////////////
        // Carte des menus
        JPanel leftMenuSection = new JPanel(new BorderLayout());
        leftMenuSection.setBorder(new EmptyBorder(20, 20, 20, 10)); // Marges autour de la section

        // Titre de la section
        JLabel st = new JLabel("Carte du restaurant", JLabel.CENTER);
        st.setFont(new Font("Arial", Font.BOLD, 16));
        st.setBorder(new EmptyBorder(10, 0, 10, 0)); // Marges autour du titre
        leftMenuSection.add(st, BorderLayout.NORTH);

        // Liste
        JList<MenuListItem> l = rr.parseListCommandables();
        JScrollPane sp = Templates.setupMenuScrollPane(l);
        leftMenuSection.add(sp, BorderLayout.CENTER);

        // Ajouter la section au mainPanel
        gbc.gridx = 0; // Colonne
        gbc.gridy = 1; // Ligne
        gbc.gridwidth = 2; // Étend sur deux colonnes
        gbc.weightx = 1.0; // S'étend horizontalement
        gbc.weighty = 1.0; // Prend tout l'espace vertical
        gbc.fill = GridBagConstraints.BOTH; // Remplit horizontalement et verticalement
        gbc.insets = new Insets(5, 5, 5, 5); // Marges autour
        mainPanel.add(leftMenuSection, gbc);

        return mainPanel;
    }

}
