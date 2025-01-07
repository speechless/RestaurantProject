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
        JList<QuantiteCommande> orderList = new JList<>(model);
        orderList.setCellRenderer(new OrderedItemRenderer());

        JScrollPane sp = new JScrollPane(orderList);

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

                    orderedItemsListModel.clear();
                    for (QuantiteCommande quantiteCommande : this.commande.getCompositionCommande()) {
                        orderedItemsListModel.addElement(quantiteCommande);
                    }

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

            for (int i = 0; i < orderedItemsListModel.getSize(); i++) {
                QuantiteCommande q = orderedItemsListModel.get(i);
                if (q.getProduit().equals(product)) {
                    this.commande = rq.ajouterProduitCommande(this.commande, product);

                    orderedItemsListModel.clear();
                    for (QuantiteCommande quantiteCommande : this.commande.getCompositionCommande()) {
                        orderedItemsListModel.addElement(quantiteCommande);
                    }
                    return;
                }
            }


            // Lors de l'ajout dans la commande d'un item qui n'était pas présent, il y a un problème qui fait
            // que quand on veut en rajouter un autre il y a duplication dans la bdd
            // Le problème se règle quand on relance l'application
            this.commande = rq.ajouterProduitCommande(this.commande, product);

            orderedItemsListModel.clear();
            for (QuantiteCommande quantiteCommande : this.commande.getCompositionCommande()) {
                orderedItemsListModel.addElement(quantiteCommande);
            }


        }
    }

    public DefaultListModel<QuantiteCommande> getOrderedItemsListModel() {
        return this.orderedItemsListModel;
    }
}
