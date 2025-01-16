package vue.pages.admin;

import modele.Commandable;
import modele.Item;
import modele.Menu;
import requete.RequeteRestaurant;
import vue.pages.PageContent;
import vue.pages.PageManager;
import vue.pages.TypeAffichage;
import vue.utils.ButtonTemplates;
import vue.utils.menu.ItemsListPanel;
import vue.utils.menu.MenuContentPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ModifMenu implements PageContent {
    private Menu menu;

    private MenuContentPanel menuContentPanel;
    private ItemsListPanel itemsListPanel;

    private JButton boutonValider;
    private JButton boutonSupprimer;


    // Champs de modification
    private JTextField champNom;
    private JCheckBox champVisibilite;

    // Labels pour les champs
    private JLabel labelNom;
    private JLabel labelVisibilite;

    public ModifMenu() {
        this.menu = new Menu();
        this.menu = RequeteRestaurant.getInstance().saveMenu(this.menu);

        initComponents();
        configurerInteractions();
    }
    public ModifMenu(int id) {
        this.menu = RequeteRestaurant.getInstance().getMenu(id);

        initComponents();
        configurerInteractions();
    }

    private void initComponents() {
        this.menuContentPanel = new MenuContentPanel(this.menu);
        this.itemsListPanel = new ItemsListPanel();

        this.labelNom = new JLabel("Nom");
        this.labelVisibilite = new JLabel("Visibilité");

        this.champNom = new JTextField(this.menu.getNom());
        this.champNom.setColumns(30);

        this.champVisibilite = new JCheckBox();
        this.champVisibilite.setSelected(this.menu.getVisibiliteCarte());

        this.boutonValider = ButtonTemplates.setupClassicButton ("Valider les modifications", () -> {
            menu.setNom(champNom.getText());
            menu.recalculerprixHT();
            menu.recalculerTVA();
            menu.setVisibiliteCarte(champVisibilite.isSelected());

            menu = RequeteRestaurant.getInstance().saveMenu(menu);
        });
    }

    private void configurerInteractions() {
        this.menuContentPanel.menuContentList.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    // Récupérer l'index de l'élément cliqué
                    int index = menuContentPanel.menuContentList.locationToIndex(e.getPoint());

                    // Vérifier si un élément valide est cliqué
                    if (index != -1) {
                        // Récupérer l'élément correspondant
                        Item selectedItem = menuContentPanel.menuContentList.getModel().getElementAt(index);

                        // Passer l'élément à la méthode
                        menu = menuContentPanel.removeProductFromMenu(selectedItem);
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

        this.itemsListPanel.listContent.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    // Récupérer l'index de l'élément cliqué
                    int index = itemsListPanel.listContent.locationToIndex(e.getPoint());

                    // Vérifier si un élément valide est cliqué
                    if (index != -1) {
                        // Récupérer l'élément correspondant
                        Commandable selectedItem = itemsListPanel.listContent.getModel().getElementAt(index);

                        // Passer l'élément à la méthode
                        menu = menuContentPanel.addProductToMenu((Item)selectedItem);
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
    }

    @Override
    public JPanel getContentPanel() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JPanel borderPanel = new JPanel(new BorderLayout());

        // Bouton retour accueil
        JButton topButton = ButtonTemplates.setupClassicButton("Retour page d'administration",
                () -> PageManager.getInstance().showPage(new AdminMainPage()));
        topButton.setFont(new Font("Arial", Font.PLAIN, 12));
        topButton.setMargin(new Insets(0, 0, 0, 0)); // Supprime les marges internes
        topButton.setPreferredSize(new Dimension(150, 30));

        // Ajouter le bouton au panneau BorderLayout
        borderPanel.add(topButton, BorderLayout.WEST);

        // Bouton suppression commande
        boutonSupprimer = ButtonTemplates.setupClassicButton("Supprimer le menu",
                () -> {
                    RequeteRestaurant.getInstance().deleteMenu(menu);
                    PageManager.getInstance().showPage(new ModifMenuPage(TypeAffichage.MENU));
                });
        boutonSupprimer.setFont(new Font("Arial", Font.PLAIN, 12));
        boutonSupprimer.setMargin(new Insets(0, 0, 0, 0)); // Supprime les marges internes
        boutonSupprimer.setPreferredSize(new Dimension(150, 30));

        // Ajouter le bouton au panneau BorderLayout
        borderPanel.add(boutonSupprimer, BorderLayout.EAST);

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
        gbc.weighty = 0.6; // Prendre tout l'espace vertical
        gbc.fill = GridBagConstraints.BOTH; // Remplir complètement
        mainPanel.add(this.menuContentPanel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0.5; // 50% de l'espace horizontal
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(this.itemsListPanel, gbc);

        JPanel parametresPanel = new JPanel();

        parametresPanel.add(labelNom);
        parametresPanel.add(champNom);
        parametresPanel.add(labelVisibilite);
        parametresPanel.add(champVisibilite);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1; // Une seule colonne
        gbc.weightx = 0.5; // 50% de l'espace horizontal
        gbc.weighty = 0.2; // Prendre tout l'espace vertical
        mainPanel.add(parametresPanel,gbc);


        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1; // Une seule colonne
        gbc.weightx = 0.5; // 50% de l'espace horizontal
        gbc.weighty = 0.2; // Prendre tout l'espace vertical
        mainPanel.add(boutonValider,gbc);

        return mainPanel;
    }
}
