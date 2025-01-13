package vue.utils.order;

import modele.Commandable;
import modele.QuantiteCommande;
import requete.RequeteRestaurant;
import vue.utils.ButtonTemplates;
import vue.utils.CommandableRenderer;
import vue.utils.Templates;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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
        listContent.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    // Récupérer l'index de l'élément cliqué
                    int index = listContent.locationToIndex(e.getPoint());

                    // Vérifier si un élément valide est cliqué
                    if (index != -1) {
                        // Récupérer l'élément correspondant
                        Commandable selectedItem = model.getElementAt(index);

                        // Passer l'élément à la méthode
                        orderContentPanel.addProductToOrder(selectedItem);
                    }
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
