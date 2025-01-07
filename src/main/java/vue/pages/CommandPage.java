package vue.pages;

import modele.Commande;
import requete.RequeteRestaurant;
import vue.utils.ButtonTemplates;
import vue.utils.order.OrderContentPanel;
import vue.utils.order.OrderMenuListPanel;

import javax.swing.*;
import java.awt.*;

public class CommandPage implements PageContent {
    private final RequeteRestaurant rq = RequeteRestaurant.getInstance();
    private Commande commande;

    private final OrderContentPanel orderContentPanel;
    private final OrderMenuListPanel orderMenuListPanel;

    public CommandPage() {
        this.commande = new Commande();
        this.commande = this.rq.saveCommande(this.commande);

        this.orderContentPanel = new OrderContentPanel(this.commande,false);
        this.orderMenuListPanel = new OrderMenuListPanel(this.orderContentPanel);
    }

    public CommandPage(int id) {
        this.commande = this.rq.getCommande(id);

        this.orderContentPanel = new OrderContentPanel(this.commande,false);
        this.orderMenuListPanel = new OrderMenuListPanel(this.orderContentPanel);
    }

    @Override
    public JPanel getContentPanel() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JPanel borderPanel = new JPanel(new BorderLayout());

        // Bouton retour accueil
        JButton topButton = ButtonTemplates.setupClassicButton("Retour page d'accueil",
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
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        mainPanel.add(borderPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1; // Une seule colonne
        gbc.weightx = 0.5; // 50% de l'espace horizontal
        gbc.weighty = 1.0; // Prendre tout l'espace vertical
        gbc.fill = GridBagConstraints.BOTH; // Remplir complètement
        mainPanel.add(this.orderContentPanel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0.5; // 50% de l'espace horizontal
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(this.orderMenuListPanel, gbc);

        return mainPanel;
    }

}
