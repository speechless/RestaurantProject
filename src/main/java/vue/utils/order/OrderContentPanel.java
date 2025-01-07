package vue.utils.order;

import modele.Commandable;
import modele.Commande;
import modele.QuantiteCommande;
import requete.RequeteRestaurant;
import vue.utils.ButtonTemplates;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class OrderContentPanel extends JPanel {

    private Commande commande;
    private JList<QuantiteCommande> orderList;
    private DefaultListModel<QuantiteCommande> orderedItemsListModel;
    private RequeteRestaurant rq = RequeteRestaurant.getInstance();

    public OrderContentPanel(Commande commande, boolean completed) {
        this.commande = commande;

        this.setLayout(new GridBagLayout());
        this.setBorder(new EmptyBorder(20, 20, 20, 10)); // Marges autour de la section

        // Modèle de liste
        DefaultListModel<QuantiteCommande> model = new DefaultListModel<>();
        for (QuantiteCommande quantiteCommande : commande.getCompositionCommande()) {
            model.addElement(quantiteCommande);
        }
        System.out.println(this.commande.getCompositionCommande());
        this.orderedItemsListModel = model;

        GridBagConstraints gbcOrderContentList = new GridBagConstraints();
        gbcOrderContentList.gridx = 0;
        gbcOrderContentList.gridy = 0;
        gbcOrderContentList.weightx = 1.0;
        gbcOrderContentList.weighty = 1.0;
        gbcOrderContentList.fill = GridBagConstraints.BOTH; // La liste remplit tout l'espace disponible
        gbcOrderContentList.insets = new Insets(5, 5, 5, 5);

        // JList avec un renderer personnalisé
        this.orderList = new JList<>(model);
        this.orderList.setCellRenderer(new OrderedItemRenderer());

        JScrollPane sp = new JScrollPane(this.orderList);

        // Largeur à 80% via un panneau intermédiaire
        JPanel listPanel = new JPanel(new BorderLayout());
        listPanel.add(sp, BorderLayout.CENTER);
        listPanel.setPreferredSize(new Dimension((int) (this.getWidth() * 0.8), (int)(this.getHeight() * 0.8)));
        this.add(sp, gbcOrderContentList);
        if(!completed) {
            // Bouton de suppression
            JButton removeButton = ButtonTemplates.setupClassicButton("Retirer de la commande", () -> {
                QuantiteCommande elementSelectionne = orderList.getSelectedValue();
                if (elementSelectionne != null) {
                    this.commande = rq.retirerProduitCommande(this.commande, elementSelectionne.getProduit());

                    actualiserAffichage();

                    System.out.println(this.commande.getCompositionCommande());
                }
            });

            removeButton.setPreferredSize(new Dimension(200, 30));

            gbcOrderContentList.gridy = 1;
            this.add(removeButton, gbcOrderContentList);
        }

    }

    public void addProductToOrder(Commandable product) {
        if (product != null) {
            this.commande = rq.ajouterProduitCommande(this.commande, product);
            actualiserAffichage();
        }
    }

    private void actualiserAffichage() {
        QuantiteCommande quantiteSelectionnee = this.orderList.getSelectedValue();

        orderedItemsListModel.clear();
        for (QuantiteCommande quantiteCommande : this.commande.getCompositionCommande()) {
            orderedItemsListModel.addElement(quantiteCommande);
            if (quantiteCommande.equals(quantiteSelectionnee)) {
                this.orderList.setSelectedValue(quantiteSelectionnee, true);
            }
        }

    }
}
