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

        JPanel borderPanel= Templates.returnMenuButton(gbc);

        mainPanel.add(borderPanel, gbc);

        //////////////////////////////////////////////////////////////////////////////////////////////////
        // Carte des menus
        JPanel leftMenuSection = new JPanel(new BorderLayout());
        leftMenuSection.setBorder(new EmptyBorder(20, 20, 20, 10)); // Marges autour de la section

        // Titre de la section
        JLabel st = new JLabel("Commandes passées", JLabel.CENTER);
        st.setFont(new Font("Arial", Font.BOLD, 16));
        st.setBorder(new EmptyBorder(10, 0, 10, 0)); // Marges autour du titre
        leftMenuSection.add(st, BorderLayout.NORTH);

        // Liste
        JList<CommandListItem> l = CommandListItem.createList();
        JScrollPane sp = Templates.setupScrollPane(l);
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
