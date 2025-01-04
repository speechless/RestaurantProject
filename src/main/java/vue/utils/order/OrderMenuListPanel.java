package vue.utils.order;

import modele.Commandable;
import modele.QuantiteCommande;
import requete.RequeteRestaurant;
import vue.utils.CommandableRenderer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class OrderMenuListPanel extends JPanel {

    private JButton addButton;

    public OrderMenuListPanel(OrderContentPanel orderContentPanel) {

        this.setLayout(new GridBagLayout());
        this.setBorder(new EmptyBorder(20, 20, 20, 10)); // Marges autour de la section

        RequeteRestaurant rq = RequeteRestaurant.getInstance();

        // Modèle de liste
        DefaultListModel<Commandable> model = new DefaultListModel<>();
        model.addAll(rq.getCommandables());

        // JList avec un renderer personnalisé
        JList<Commandable> listContent = new JList<>(model);
        listContent.setCellRenderer(new CommandableRenderer());

        JScrollPane sp = new JScrollPane(listContent);

        GridBagConstraints gbcItemsList = new GridBagConstraints();

        gbcItemsList.gridx = 0;
        gbcItemsList.gridy = 0;
        this.add(sp, gbcItemsList);

        // Bouton d'ajout
        addButton = new JButton("Ajouter à la commande");
        addButton.addActionListener(e -> {
            orderContentPanel.addProductToOrder(listContent.getSelectedValue());
        });


        gbcItemsList.gridx = 0;
        gbcItemsList.gridy = 1;
        this.add(addButton, gbcItemsList);
    }
}
