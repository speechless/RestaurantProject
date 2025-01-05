package vue.utils.order;

import modele.Commandable;
import requete.RequeteRestaurant;
import vue.utils.ButtonTemplates;
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
        gbcItemsList.weightx = 1.0;
        gbcItemsList.weighty = 1.0;
        gbcItemsList.fill = GridBagConstraints.BOTH; // Remplit la zone allouée
        gbcItemsList.insets = new Insets(5, 5, 5, 5);
        JPanel listPanel = new JPanel(new BorderLayout());
        listPanel.add(sp, BorderLayout.CENTER);
        listPanel.setPreferredSize(new Dimension((int) (this.getWidth() * 0.8), sp.getPreferredSize().height));
        this.add(listPanel, gbcItemsList);

        // Bouton d'ajout
        addButton = ButtonTemplates.setupClassicButton("Ajouter à la commande", () -> {
            orderContentPanel.addProductToOrder(listContent.getSelectedValue());
        });

        gbcItemsList.gridy = 1;
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.add(addButton, BorderLayout.CENTER);
        buttonPanel.setPreferredSize(new Dimension((int) (this.getWidth() * 0.8), addButton.getPreferredSize().height));

        this.add(buttonPanel, gbcItemsList);
    }

}
