package vue.pages.admin;

import com.toedter.calendar.JDateChooser;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import requete.RequeteFiltres;
import vue.pages.PageContent;
import vue.utils.ButtonTemplates;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

//TODO relier les boutons du haut, brancher le diagramme, mettre en place la vue produit

public class StatsMainPage implements PageContent {

    private boolean platIsActive = true;
    private boolean boissonIsActive = true;
    private boolean menuIsActive = true;
    private boolean autreIsActive = true;
    private JDateChooser dateChooser;
    private JComboBox<String> comboBoxSelect;

    @Override
    public JPanel getContentPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Ajouter la barre supérieure
        mainPanel.add(createTopBar(), BorderLayout.NORTH);

        // Ajouter le contenu principal
        mainPanel.add(createMainContentPanel(), BorderLayout.CENTER);

        return mainPanel;
    }

    private JPanel createTopBar() {
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JButton leftButton = ButtonTemplates.setupClassicButton("Accueil", () -> System.out.println("Accueil cliqué"));
        JButton rightButton = ButtonTemplates.setupClassicButton("Déconnexion", () -> System.out.println("Déconnexion cliqué"));

        topBar.add(leftButton, BorderLayout.WEST);
        topBar.add(rightButton, BorderLayout.EAST);

        return topBar;
    }

    private JPanel createMainContentPanel() {
        JPanel mainContentPanel = new JPanel();
        mainContentPanel.setLayout(new BoxLayout(mainContentPanel, BoxLayout.Y_AXIS));
        mainContentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Ajouter les sous-sections
        mainContentPanel.add(createOptionsPanel());
        mainContentPanel.add(createFilterPanel());
        mainContentPanel.add(createDatePanel());
        mainContentPanel.add(createConfirmPanel());
        mainContentPanel.add(createChartPanel());

        return mainContentPanel;
    }

    private JPanel createOptionsPanel() {
        JPanel optionsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel vueSelectLabel = new JLabel("Sélectionner une option :");
        String[] optionsSelect = {"Semaine", "Mois", "Année", "Global"};
        comboBoxSelect = new JComboBox<>(optionsSelect);
        optionsPanel.add(vueSelectLabel);
        optionsPanel.add(comboBoxSelect);
        return optionsPanel;
    }

    private JPanel createFilterPanel() {
        JPanel filterPanel = new JPanel(new GridLayout(1, 4, 5, 5));
        filterPanel.setBorder(BorderFactory.createTitledBorder("Sélectionner les catégories"));

        JButton platFiltreButton = ButtonTemplates.setupSingleToggleButton("Plat",
                () -> setPlatIsActive(true),
                () -> setPlatIsActive(false));
        JButton boissonFiltreButton = ButtonTemplates.setupSingleToggleButton("Boisson",
                () -> setBoissonIsActive(true),
                () -> setBoissonIsActive(false));
        JButton menuFiltreButton = ButtonTemplates.setupSingleToggleButton("Menu",
                () -> setMenuIsActive(true),
                () -> setMenuIsActive(false));
        JButton autreFiltreButton = ButtonTemplates.setupSingleToggleButton("Autre",
                () -> setAutreIsActive(true),
                () -> setAutreIsActive(false));

        filterPanel.add(platFiltreButton);
        filterPanel.add(boissonFiltreButton);
        filterPanel.add(menuFiltreButton);
        filterPanel.add(autreFiltreButton);

        return filterPanel;
    }

    private JPanel createDatePanel() {
        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel dateLabel = new JLabel("Sélectionner le mois et / ou l'année :");
        dateChooser = new JDateChooser();
        dateChooser.setMaxSelectableDate(new Date());
        dateChooser.setDate(new Date()); // Initialisation à la date actuelle
        dateChooser.setPreferredSize(new Dimension(120, 25));

        datePanel.add(dateLabel);
        datePanel.add(dateChooser);
        return datePanel;
    }

    private JPanel createConfirmPanel() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        // Formater la date
        String formattedDate = dateFormat.format(dateChooser.getDate());

        JPanel confirmPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton confirmButton = ButtonTemplates.setupClassicButton("Appliquer les filtres",
                () -> setupDiagramGlobal(isPlatIsActive(), isMenuIsActive(), isBoissonIsActive(), isAutreIsActive(),
                        (String) comboBoxSelect.getSelectedItem(),formattedDate));
        confirmPanel.add(confirmButton);
        return confirmPanel;
    }

    private JPanel createChartPanel() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(10, "Catégorie A", "Janvier");
        dataset.addValue(15, "Catégorie A", "Février");
        dataset.addValue(20, "Catégorie A", "Mars");

        JFreeChart chart = ChartFactory.createBarChart(
                "Ventes Mensuelles",
                "Mois",
                "Ventes",
                dataset
        );

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 400));

        JPanel chartContainerPanel = new JPanel();
        chartContainerPanel.setLayout(new BorderLayout());
        chartContainerPanel.add(chartPanel, BorderLayout.CENTER);

        return chartContainerPanel;
    }

    private void setupDiagramGlobal(boolean plat, boolean menu, boolean boisson, boolean autre,String dateOption,String date) {
        RequeteFiltres rf = RequeteFiltres.getInstance();
        LocalDate d1 = LocalDate.of(2025,1,1);

        List<String> selectedCategories = new ArrayList<>();
        if (plat) selectedCategories.add("Plat");
        if (menu) selectedCategories.add("Menu");
        if (boisson) selectedCategories.add("Boisson");
        if (autre) selectedCategories.add("Autre");

        System.out.println(dateOption+" "+date);
        Object[][] data = rf.getQuantiteVenteCategorie(d1, LocalDate.now(), selectedCategories);
        System.out.println(Arrays.deepToString(data));
    }

    public boolean isPlatIsActive() {
        return platIsActive;
    }

    public void setPlatIsActive(boolean platIsActive) {
        this.platIsActive = platIsActive;
    }

    public boolean isBoissonIsActive() {
        return boissonIsActive;
    }

    public void setBoissonIsActive(boolean boissonIsActive) {
        this.boissonIsActive = boissonIsActive;
    }

    public boolean isMenuIsActive() {
        return menuIsActive;
    }

    public void setMenuIsActive(boolean menuIsActive) {
        this.menuIsActive = menuIsActive;
    }

    public boolean isAutreIsActive() {
        return autreIsActive;
    }

    public void setAutreIsActive(boolean autreIsActive) {
        this.autreIsActive = autreIsActive;
    }
}
