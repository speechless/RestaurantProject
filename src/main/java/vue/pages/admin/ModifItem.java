package vue.pages.admin;

import modele.CategorieItem;
import modele.Item;
import requete.RequeteRestaurant;
import vue.pages.PageContent;
import vue.pages.PageManager;
import vue.pages.TypeAffichage;
import vue.utils.ButtonTemplates;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import java.awt.*;
import java.text.NumberFormat;

public class ModifItem implements PageContent {
    private Item item;

    private JButton boutonValider;
    private JButton boutonSupprimer;

    // Champs de modification
    private JTextField champNom;
    private JFormattedTextField champPrixHT;
    private JFormattedTextField champTauxTVA;
    private JComboBox<CategorieItem> champCategorie;
    private JCheckBox champVisibilite;

    // Labels pour les champs
    private JLabel labelNom;
    private JLabel labelPrixHT;
    private JLabel labelTauxTVA;
    private JLabel labelCategorie;
    private JLabel labelVisibilite;

    public ModifItem() {
        this.item = new Item();
        this.item = RequeteRestaurant.getInstance().saveItem(this.item);

        initComponents();
    }

    public ModifItem(int id) {
        this.item = RequeteRestaurant.getInstance().getItem(id);

        initComponents();
    }

    private void initComponents() {
        this.labelNom = new JLabel("Nom");
        this.labelPrixHT = new JLabel("Prix HT");
        this.labelTauxTVA = new JLabel("TVA");
        this.labelCategorie = new JLabel("Catégorie");
        this.labelVisibilite = new JLabel("Visibilité");

        this.champNom = new JTextField(this.item.getNom());
        this.champNom.setColumns(30);

        NumberFormat numberFormat = NumberFormat.getNumberInstance();

        this.champPrixHT = new JFormattedTextField(numberFormat);
        this.champPrixHT.setColumns(10);
        this.champPrixHT.setValue(item.getPrixHT());

        this.champTauxTVA = new JFormattedTextField(numberFormat);
        this.champTauxTVA.setColumns(5);
        this.champTauxTVA.setValue(item.getTauxTVA());

        this.champCategorie = new JComboBox<>(CategorieItem.values());
        this.champCategorie.setSelectedItem(item.getCategorie());
        this.champCategorie.setRenderer(new BasicComboBoxRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof CategorieItem) {
                    setText(((CategorieItem) value).label);
                }
                return this;
            }
        });

        this.champVisibilite = new JCheckBox();
        this.champVisibilite.setSelected(this.item.getVisibiliteCarte());

        this.boutonValider = new JButton("Valider les modifications");
        this.boutonValider.addActionListener(e -> {
            item.setNom(champNom.getText());
            item.setPrixHT((Double)champPrixHT.getValue());
            item.setTauxTVA((Double)champTauxTVA.getValue());
            item.setCategorie((CategorieItem) champCategorie.getSelectedItem());
            item.setVisibiliteCarte(champVisibilite.isSelected());

            item = RequeteRestaurant.getInstance().saveItem(item);
        });

        this.boutonSupprimer = new JButton("Supprimer le produit");
        this.boutonSupprimer.addActionListener(e -> {
            RequeteRestaurant.getInstance().deleteItem(item);
            PageManager.getInstance().showPage(new ModifMenuPage(TypeAffichage.ITEM));
        });
    }

    @Override
    public JPanel getContentPanel() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JPanel borderPanel = new JPanel(new BorderLayout());

        // Bouton retour admin
        JButton topButton = ButtonTemplates.setupClassicButton("Retour page d'administration",
                () -> PageManager.getInstance().showPage(new AdminMainPage()));
        topButton.setFont(new Font("Arial", Font.PLAIN, 12));
        topButton.setMargin(new Insets(0, 0, 0, 0)); // Supprime les marges internes
        topButton.setPreferredSize(new Dimension(150, 30));

        // Ajouter le bouton au panneau BorderLayout
        borderPanel.add(topButton, BorderLayout.WEST);

        // Ajouter borderPanel au mainPanel avec GridBagConstraints
        gbc.gridx = 0; // Colonne
        gbc.gridy = 0; // Ligne
        gbc.gridwidth = 2; // Étend sur deux colonnes
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        mainPanel.add(borderPanel, gbc);


        JPanel labels = new JPanel(new GridLayout(0, 1));
        labels.add(labelNom);
        labels.add(labelPrixHT);
        labels.add(labelTauxTVA);
        labels.add(labelCategorie);
        labels.add(labelVisibilite);

        JPanel champs = new JPanel(new GridLayout(0, 1));
        champs.add(champNom);
        champs.add(champPrixHT);
        champs.add(champTauxTVA);
        champs.add(champCategorie);
        champs.add(champVisibilite);

        mainPanel.add(labels);
        mainPanel.add(champs);

        mainPanel.add(boutonValider);
        mainPanel.add(boutonSupprimer);

        return mainPanel;
    }
}
