package vue.pages;

import modele.Commande;
import requete.RequeteRestaurant;
import vue.utils.Templates;
import vue.utils.order.OrderContentPanel;
import vue.utils.order.OrderMenuListPanel;

import javax.swing.*;
import java.awt.*;

public class CommandPage implements PageContent {
    private RequeteRestaurant rq = RequeteRestaurant.getInstance();
    private Commande commande;

    private OrderContentPanel orderContentPanel;
    private OrderMenuListPanel orderMenuListPanel;

    public CommandPage() {
        this.commande = new Commande();
        this.rq.saveCommande(this.commande);

        this.orderContentPanel = new OrderContentPanel(this.commande);
        this.orderMenuListPanel = new OrderMenuListPanel(this.orderContentPanel);
    }

    public CommandPage(int id) {
        this.commande = this.rq.getCommande(id);

        this.orderContentPanel = new OrderContentPanel(this.commande);
        this.orderMenuListPanel = new OrderMenuListPanel(this.orderContentPanel);
    }

    @Override
    public JPanel getContentPanel() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();


        JPanel borderPanel = new JPanel(new BorderLayout());

        // Bouton retour accueil
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
        //gbc.gridwidth = 2; // Étend sur deux colonnes
        //gbc.weightx = 1.0; // S'étend horizontalement
        //gbc.weighty = 0.0; // Pas de poids vertical
        gbc.fill = GridBagConstraints.HORIZONTAL; // Remplir horizontalement
        gbc.insets = new Insets(5, 5, 5, 5); // Marges autour
        mainPanel.add(borderPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(this.orderContentPanel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        mainPanel.add(this.orderMenuListPanel, gbc);

        return mainPanel;
    }

}
