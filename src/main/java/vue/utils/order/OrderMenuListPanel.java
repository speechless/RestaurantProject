package vue.utils.order;

import modele.Commandable;
import requete.RequeteRestaurant;
import vue.pages.TypeAffichage;
import vue.utils.Templates;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class OrderMenuListPanel extends JPanel {

    public JList<Commandable> listContent;

    public OrderMenuListPanel() {

        this.setLayout(new GridBagLayout());
        this.setBorder(new EmptyBorder(20, 20, 20, 10)); // Marges autour de la section

        RequeteRestaurant rq = RequeteRestaurant.getInstance();

        // Modèle de liste
        DefaultListModel<Commandable> model = new DefaultListModel<>();
        model.addAll(rq.getCommandables(TypeAffichage.BOTH));

        // JList avec un renderer personnalisé
        listContent = new JList<>(model);
        listContent.setCellRenderer(new CommandableRenderer());


        JScrollPane sp = Templates.setupCommandableScrollPane(listContent);

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
    }

}
