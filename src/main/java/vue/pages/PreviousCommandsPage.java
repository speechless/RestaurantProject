package vue.pages;

import requete.RequeteRestaurant;
import vue.utils.ButtonTemplates;
import vue.utils.listItem.CommandListItem;
import vue.utils.Templates;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Page d'affichage de toutes les commandes finalisées
 */

public class PreviousCommandsPage implements PageContent {
    @Override
    public JPanel getContentPanel() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        RequeteRestaurant rr = RequeteRestaurant.getInstance();

        JPanel borderPanel = ButtonTemplates.returnMenuButton(gbc);

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
        // Carte des menus
        JPanel leftMenuSection = new JPanel(new BorderLayout());
        leftMenuSection.setBorder(new EmptyBorder(0, 20, 10, 10)); // Marges autour de la section
        // Liste
        JList<CommandListItem> listPastCommands =  CommandListItem.createList(rr.getCommandesTerminees());

        listPastCommands.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    PageManager.getInstance().showPage(new PastCommandPage(listPastCommands.getSelectedValue().getId()));
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

        JScrollPane sp = Templates.setupCommandeScrollPane(listPastCommands);
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
