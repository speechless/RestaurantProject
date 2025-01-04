package vue.utils.order;

import modele.Commandable;
import modele.Commande;
import modele.QuantiteCommande;
import requete.RequeteRestaurant;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class OrderContentPanel extends JPanel {

    private Commande commande;
    private DefaultListModel<QuantiteCommande> orderedItemsListModel;
    private RequeteRestaurant rq = RequeteRestaurant.getInstance();

    public OrderContentPanel(Commande commande) {
        this.commande = commande;

        this.setLayout(new GridBagLayout());
        this.setBorder(new EmptyBorder(20, 20, 20, 10)); // Marges autour de la section

        GridBagConstraints gbcOrderContentList = new GridBagConstraints();

        // Modèle de liste
        DefaultListModel<QuantiteCommande> model = new DefaultListModel<>();
        for (QuantiteCommande quantiteCommande : commande.getCompositionCommande()) {
            model.addElement(quantiteCommande);
        }
        System.out.println(this.commande.getCompositionCommande());
        this.orderedItemsListModel = model;

        // JList avec un renderer personnalisé
        JList<QuantiteCommande> orderList = new JList<>(model);
        orderList.setCellRenderer(new OrderedItemRenderer());

        JScrollPane sp = new JScrollPane(orderList);

        gbcOrderContentList.gridx = 0;
        gbcOrderContentList.gridy = 0;
        this.add(sp, gbcOrderContentList);

        // Bouton de suppression
        JButton removeButton = new JButton("Retirer de la commande");
        removeButton.addActionListener(e -> {
            QuantiteCommande elementSelectionne = orderList.getSelectedValue();
            System.out.println("sélectionné :");
            System.out.println(elementSelectionne);
            if (elementSelectionne != null) {
                System.out.println("retrait de la commande");
                this.commande.retraitCommande(elementSelectionne.getProduit());
                System.out.println("commande :");
                System.out.println(this.commande.getCompositionCommande());
                System.out.println("séletionné :");
                System.out.println(elementSelectionne);
                if (elementSelectionne.getQuantite() <= 0) {
                    model.removeElement(elementSelectionne);
                }
                else {
                    model.set(orderList.getSelectedIndex(), elementSelectionne);
                }

                System.out.println(this.commande.getCompositionCommande());
                rq.saveCommande(this.commande);
            }
        });

        gbcOrderContentList.gridx = 0;
        gbcOrderContentList.gridy = 1;
        this.add(removeButton, gbcOrderContentList);
    }

    public void addProductToOrder(Commandable product) {

        if (product != null) {
            boolean found = false;

            for (int i = 0; i < orderedItemsListModel.getSize(); i++) {
                QuantiteCommande q = orderedItemsListModel.get(i);
                if (q.getProduit().equals(product)) {
                    System.out.println("found");
                    this.commande.ajoutCommande(product);
                    orderedItemsListModel.set(i, q);
                    found = true;
                }
            }

            if (!found) {

                QuantiteCommande q = this.commande.ajoutCommande(product);
                orderedItemsListModel.addElement(q);
            }

            System.out.println(this.commande.getCompositionCommande());
            rq.saveCommande(this.commande);
        }
    }

    public DefaultListModel<QuantiteCommande> getOrderedItemsListModel() {
        return this.orderedItemsListModel;
    }
}
