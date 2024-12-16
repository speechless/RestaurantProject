package vue.pages;

import vue.utils.CommandListItem;
import vue.utils.Templates;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class PreviousCommandsPage implements PageContent {
    @Override
    public JPanel getContentPanel() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JPanel borderPanel = Templates.returnMenuButton(gbc);

        mainPanel.add(borderPanel, gbc);

        //////////////////////////////////////////////////////////////////////////////////////////////////
        // Titre de la page
        JLabel titleLabel = new JLabel("Commandes passées", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setBorder(new EmptyBorder(10, 0, 10, 0)); // Marges autour du titre

        gbc.gridx = 0;
        gbc.gridy = 1; // Placer après le bouton du template
        gbc.gridwidth = 2; // Étendre sur deux colonnes
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        mainPanel.add(titleLabel, gbc);

        //////////////////////////////////////////////////////////////////////////////////////////////////
        // Section de filtrage
        JPanel filterPanel = new JPanel(new GridBagLayout());
        filterPanel.setBorder(new EmptyBorder(10, 10, 10, 10)); // Marges autour de la section

        // Boutons pour les types de filtrage
        JPanel typeButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton type1Button = Templates.setupSingleToggleButton("Type1",
                ()-> System.out.println("Type1 activé"),
                ()-> System.out.println("Type1 désactivé"));

        JButton type2Button = Templates.setupSingleToggleButton("Type2",
                ()-> System.out.println("Type2 activé"),
                ()-> System.out.println("Type2 désactivé"));
        JButton type3Button = Templates.setupSingleToggleButton("Type3",
                ()-> System.out.println("Type3 activé"),
                ()-> System.out.println("Type3 désactivé"));
        JButton type4Button = Templates.setupSingleToggleButton("Type4",
                ()-> System.out.println("Type4 activé"),
                ()-> System.out.println("Type4 désactivé"));

        JButton type5Button = Templates.setupSingleToggleButton("Type5",
                ()-> System.out.println("Type5 activé"),
                ()-> System.out.println("Type5 désactivé"));

        // Ajouter des boutons pour d'autres types si nécessaire
        typeButtonPanel.add(type1Button);
        typeButtonPanel.add(type2Button);
        typeButtonPanel.add(type3Button);
        typeButtonPanel.add(type4Button);
        typeButtonPanel.add(type5Button);

        // Barre de recherche
        JPanel searchPanel = new JPanel(new BorderLayout());
        JLabel searchLabel = new JLabel("Rechercher : ");
        JTextField searchField = new JTextField(15);
        searchPanel.add(searchLabel, BorderLayout.WEST);
        searchPanel.add(searchField, BorderLayout.CENTER);

        // Ajout des deux sous-sections au panel de filtrage
        gbc.gridx = 0;
        gbc.gridy = 2; // Après le titre
        gbc.gridwidth = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        filterPanel.add(typeButtonPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        filterPanel.add(searchPanel, gbc);

        // Ajouter le panneau de filtrage au mainPanel
        gbc.gridx = 0;
        gbc.gridy = 2; // Après le titre
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        mainPanel.add(filterPanel, gbc);

        //////////////////////////////////////////////////////////////////////////////////////////////////
        // Carte des menus
        JPanel leftMenuSection = new JPanel(new BorderLayout());
        leftMenuSection.setBorder(new EmptyBorder(0, 20, 10, 10)); // Marges autour de la section
        // Liste
        JList<CommandListItem> l = CommandListItem.createList(null);
        JScrollPane sp = Templates.setupScrollPane(l);
        leftMenuSection.add(sp, BorderLayout.CENTER);

        // Ajouter la section au mainPanel
        gbc.gridx = 0; // Colonne
        gbc.gridy = 3; // Après la section de filtrage
        gbc.gridwidth = 2; // Étend sur deux colonnes
        gbc.weightx = 1.0; // S'étend horizontalement
        gbc.weighty = 1.0; // Prend tout l'espace vertical
        gbc.fill = GridBagConstraints.BOTH; // Remplit horizontalement et verticalement
        gbc.insets = new Insets(5, 5, 5, 5); // Marges autour
        mainPanel.add(leftMenuSection, gbc);

        return mainPanel;
    }
}
