package vue.utils.menu;

import modele.Item;
import modele.Menu;
import requete.RequeteRestaurant;
import vue.utils.order.CommandableRenderer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MenuContentPanel extends JPanel {

    private Menu menu;
    public JList<Item> menuContentList;
    private DefaultListModel<Item> menuContentListModel;
    private RequeteRestaurant rq = RequeteRestaurant.getInstance();

    public MenuContentPanel(Menu menu) {
        this.menu = menu;

        this.setLayout(new GridBagLayout());
        this.setBorder(new EmptyBorder(20, 20, 20, 10)); // Marges autour de la section

        // Modèle de liste
        DefaultListModel<Item> model = new DefaultListModel<>();
        for (Item item : menu.getListeItems()) {
            model.addElement(item);
        }

        this.menuContentListModel = model;

        GridBagConstraints gbcOrderContentList = new GridBagConstraints();
        gbcOrderContentList.gridx = 0;
        gbcOrderContentList.gridy = 0;
        gbcOrderContentList.weightx = 1.0;
        gbcOrderContentList.weighty = 1.0;
        gbcOrderContentList.fill = GridBagConstraints.BOTH; // La liste remplit tout l'espace disponible
        gbcOrderContentList.insets = new Insets(5, 5, 5, 5);

        // JList avec un renderer personnalisé
        menuContentList = new JList<>(model);
        menuContentList.setCellRenderer(new CommandableRenderer());


        JScrollPane sp = new JScrollPane(this.menuContentList);

        // Largeur à 80% via un panneau intermédiaire
        JPanel listPanel = new JPanel(new BorderLayout());
        listPanel.add(sp, BorderLayout.CENTER);
        listPanel.setPreferredSize(new Dimension((int) (this.getWidth() * 0.8), (int)(this.getHeight() * 0.8)));
        this.add(sp, gbcOrderContentList);

    }

    public Menu removeProductFromMenu(Item elementSelectionne){
        this.menu = rq.retirerProduitMenu(this.menu, elementSelectionne);

        menuContentListModel.clear();
        for (Item item : this.menu.getListeItems()) {
            menuContentListModel.addElement(item);
        }

        return this.menu;
    }

    public Menu addProductToMenu(Item product) {
        if (product != null) {
            this.menu = rq.ajouterProduitMenu(this.menu, product);
            actualiserAffichage();
        }

        return this.menu;
    }

    private void actualiserAffichage() {
        Item itemSelectionnee = this.menuContentList.getSelectedValue();

        menuContentListModel.clear();
        for (Item item : this.menu.getListeItems()) {
            menuContentListModel.addElement(item);
            if (item.equals(itemSelectionnee)) {
                this.menuContentList.setSelectedValue(itemSelectionnee, true);
            }
        }
    }
}
