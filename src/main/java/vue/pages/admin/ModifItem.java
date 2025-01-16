package vue.pages.admin;

import modele.CategorieItem;
import modele.Item;
import requete.RequeteRestaurant;
import vue.pages.PageContent;
import vue.pages.PageManager;
import vue.pages.TypeAffichage;
import vue.utils.ButtonTemplates;
import java.util.Locale;

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
        // Création des labels avec une police plus grande
        Font labelFont = new Font("Arial", Font.BOLD, 16);
        Font fieldFont = new Font("Arial", Font.PLAIN, 14);

        this.labelNom = new JLabel("Nom :");
        this.labelNom.setFont(labelFont);

        this.labelPrixHT = new JLabel("Prix HT :");
        this.labelPrixHT.setFont(labelFont);

        this.labelTauxTVA = new JLabel("TVA :");
        this.labelTauxTVA.setFont(labelFont);

        this.labelCategorie = new JLabel("Catégorie :");
        this.labelCategorie.setFont(labelFont);

        this.labelVisibilite = new JLabel("Visibilité :");
        this.labelVisibilite.setFont(labelFont);

        // Champs de texte
        this.champNom = new JTextField(this.item.getNom());
        this.champNom.setFont(fieldFont);
        this.champNom.setColumns(30);

        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.ENGLISH);
        numberFormat.setMinimumFractionDigits(2);

        this.champPrixHT = new JFormattedTextField(numberFormat);
        this.champPrixHT.setFont(fieldFont);
        this.champPrixHT.setColumns(10);
        this.champPrixHT.setValue(item.getPrixHT());

        this.champTauxTVA = new JFormattedTextField(numberFormat);
        this.champTauxTVA.setFont(fieldFont);
        this.champTauxTVA.setColumns(5);
        this.champTauxTVA.setValue(item.getTauxTVA());

        // ComboBox avec fond blanc et police modifiée
        this.champCategorie = new JComboBox<>(CategorieItem.values());
        this.champCategorie.setFont(fieldFont);
        this.champCategorie.setSelectedItem(item.getCategorie());
        this.champCategorie.setBackground(Color.WHITE); // Applique un fond gris clair
        this.champCategorie.setOpaque(true); // Active le remplissage de l'arrière-plan
        this.champCategorie.setRenderer(new BasicComboBoxRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof CategorieItem) {
                    setText(((CategorieItem) value).label);
                }
                setBackground(Color.WHITE); // Fond blanc pour chaque option
                return this;
            }
        });

        // Case à cocher avec style et police
        this.champVisibilite = new JCheckBox();
        this.champVisibilite.setFont(fieldFont);
        this.champVisibilite.setSelected(this.item.getVisibiliteCarte());
        // Boutons avec actions

        this.boutonValider = ButtonTemplates.setupClassicButton("Valider les modifications", () -> {
            item.setNom(champNom.getText());
            item.setPrixHT(((Number) champPrixHT.getValue()).doubleValue());
            item.setTauxTVA(((Number) champTauxTVA.getValue()).doubleValue());
            item.setCategorie((CategorieItem) champCategorie.getSelectedItem());
            item.setVisibiliteCarte(champVisibilite.isSelected());

            item = RequeteRestaurant.getInstance().saveItem(item);
            PageManager.getInstance().showPage(new ModifMenuPage(TypeAffichage.BOTH));
        });

        this.boutonSupprimer = ButtonTemplates.setupClassicButton("Supprimer le produit", () -> {
            RequeteRestaurant.getInstance().deleteItem(item);
            PageManager.getInstance().showPage(new ModifMenuPage(TypeAffichage.BOTH));
        });
    }

    @Override
    public JPanel getContentPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Bouton retour admin
        JButton topButton = ButtonTemplates.setupClassicButton("Retour",
                () -> PageManager.getInstance().showPage(new ModifMenuPage(TypeAffichage.BOTH)));
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(topButton);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Formulaire de modification
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Ajout des champs et labels
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(labelNom, gbc);

        gbc.gridx = 1;
        formPanel.add(champNom, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(labelPrixHT, gbc);

        gbc.gridx = 1;
        formPanel.add(champPrixHT, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(labelTauxTVA, gbc);

        gbc.gridx = 1;
        formPanel.add(champTauxTVA, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(labelCategorie, gbc);

        gbc.gridx = 1;
        formPanel.add(champCategorie, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(labelVisibilite, gbc);

        gbc.gridx = 1;
        formPanel.add(champVisibilite, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Boutons de validation et suppression
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.add(boutonValider);
        buttonPanel.add(boutonSupprimer);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        return mainPanel;
    }
}
