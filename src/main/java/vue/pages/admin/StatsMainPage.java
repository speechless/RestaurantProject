package vue.pages.admin;

import com.toedter.calendar.JDateChooser;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import org.jfree.data.category.DefaultCategoryDataset;
import requete.RequeteFiltres;
import vue.pages.PageContent;
import vue.pages.PageManager;
import vue.utils.ButtonTemplates;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;


public class StatsMainPage implements PageContent {

    private boolean platIsActive = true;
    private boolean boissonIsActive = true;
    private boolean menuIsActive = true;
    private boolean autreIsActive = true;
    private boolean entreeIsActive = true;
    private boolean poissonIsActive = true;
    private boolean viandeIsActive = true;
    private boolean fromageIsActive = true;
    private boolean dessertIsActive = true;
    private boolean aucuneIsActive = true;

    private JPanel mainContentPanel;
    private JDateChooser dateChooser;
    private JComboBox<String> comboBoxSelect;
    private JPanel diagram;

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

        JButton leftButton = ButtonTemplates.setupClassicButton("Retour",
                () -> PageManager.getInstance().showPage(new AdminMainPage()));
        topBar.add(leftButton, BorderLayout.WEST);

        return topBar;
    }

    private JPanel createMainContentPanel() {
        mainContentPanel = new JPanel();
        mainContentPanel.setLayout(new BoxLayout(mainContentPanel, BoxLayout.Y_AXIS));
        mainContentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Ajouter les sous-sections
        mainContentPanel.add(createOptionsPanel());
        mainContentPanel.add(createFilterPanel());
        mainContentPanel.add(createDatePanel());
        mainContentPanel.add(createConfirmPanel());
        diagram = createDefaultChartPanel();
        mainContentPanel.add(diagram);

        return mainContentPanel;
    }

    private JPanel createOptionsPanel() {
        JPanel optionsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel vueSelectLabel = new JLabel("Sélectionner une option :");
        String[] optionsSelect = {"Semaine", "Mois", "Annee", "Global"};
        comboBoxSelect = new JComboBox<>(optionsSelect);
        optionsPanel.add(vueSelectLabel);
        optionsPanel.add(comboBoxSelect);
        return optionsPanel;
    }

    private JPanel createFilterPanel() {
        JPanel filterPanel = new JPanel(new GridLayout(1, 10, 5, 5));
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
        JButton entreeFiltreButton = ButtonTemplates.setupSingleToggleButton("Entrée",
                () -> setEntreeIsActive(true),
                () -> setEntreeIsActive(false));
        JButton poissonFiltreButton = ButtonTemplates.setupSingleToggleButton("Poisson",
                () -> setPoissonIsActive(true),
                () -> setPoissonIsActive(false));
        JButton viandeFiltreButton = ButtonTemplates.setupSingleToggleButton("Viande",
                () -> setViandeIsActive(true),
                () -> setViandeIsActive(false));
        JButton fromageFiltreButton = ButtonTemplates.setupSingleToggleButton("Fromage",
                () -> setFromageIsActive(true),
                () -> setFromageIsActive(false));
        JButton dessertFiltreButton = ButtonTemplates.setupSingleToggleButton("Dessert",
                () -> setDessertIsActive(true),
                () -> setDessertIsActive(false));
        JButton aucuneFiltreButton = ButtonTemplates.setupSingleToggleButton("Aucune",
                () -> setAucuneIsActive(true),
                () -> setAucuneIsActive(false));

        filterPanel.add(platFiltreButton);
        filterPanel.add(boissonFiltreButton);
        filterPanel.add(menuFiltreButton);
        filterPanel.add(autreFiltreButton);
        filterPanel.add(entreeFiltreButton);
        filterPanel.add(poissonFiltreButton);
        filterPanel.add(viandeFiltreButton);
        filterPanel.add(fromageFiltreButton);
        filterPanel.add(dessertFiltreButton);
        filterPanel.add(aucuneFiltreButton);

        return filterPanel;
    }

    private JPanel createDatePanel() {
        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel dateLabel = new JLabel("Sélectionner un jour de la semaine / mois / année :");
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

        JPanel confirmPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton confirmButton = ButtonTemplates.setupClassicButton("Appliquer les filtres",
                () -> {
                    // Récupération directe de la date du JDateChooser
                    Date selectedDate = dateChooser.getDate();
                    if (selectedDate == null) {
                        selectedDate = new Date(); // Par défaut, la date actuelle
                    }
                    String formattedDate = dateFormat.format(selectedDate);

                    setupDiagramGlobal((String) comboBoxSelect.getSelectedItem(), formattedDate);
                }
        );

        confirmPanel.add(confirmButton);
        return confirmPanel;
    }


    private JPanel createDefaultChartPanel() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        JFreeChart chart = ChartFactory.createBarChart(
                "Ventes par catégorie",
                "Semaine",
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

    private JPanel createChartPanel(Object[][] data) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for(Object[] d : data){
                dataset.addValue((Integer) d[1], "", d[0].toString());
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Ventes par catégorie", // Titre
                "Catégorie",             // Axe X
                "Nombre de ventes",           // Axe Y
                dataset
        );

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 400));

        JPanel chartContainerPanel = new JPanel();
        chartContainerPanel.setLayout(new BorderLayout());
        chartContainerPanel.add(chartPanel, BorderLayout.CENTER);
        return chartContainerPanel;
    }

    private void setupDiagramGlobal(String dateOption,String date) {
        System.out.println(date+" "+ dateOption);
        RequeteFiltres rf = RequeteFiltres.getInstance();

        List<String> selectedCategories = getSelectedCategories();

        Object[][] data = rf.getQuantiteVenteCategorie(date, dateOption, selectedCategories);
        mainContentPanel.remove(diagram); // Supprime l'ancien diagramme
        diagram = createChartPanel(data);
        System.out.println(Arrays.deepToString(data));

        mainContentPanel.add(diagram); // Ajoute le nouveau diagramme
        mainContentPanel.revalidate();
        mainContentPanel.repaint();
    }

    private List<String> getSelectedCategories() {
        List<String> selectedCategories = new ArrayList<>();
        if (isPlatIsActive()) selectedCategories.add("Plat");
        if (isMenuIsActive()) selectedCategories.add("Menu");
        if (isBoissonIsActive()) selectedCategories.add("Boisson");
        if (isAutreIsActive()) selectedCategories.add("Autre");
        if (isEntreeIsActive()) selectedCategories.add("Entrée");
        if (isPoissonIsActive()) selectedCategories.add("Poisson");
        if (isViandeIsActive()) selectedCategories.add("Viande");
        if (isFromageIsActive()) selectedCategories.add("Fromage");
        if (isDessertIsActive()) selectedCategories.add("Dessert");
        if (isAucuneIsActive()) selectedCategories.add("Aucune");
        return selectedCategories;
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

    public boolean isEntreeIsActive() {
        return entreeIsActive;
    }

    public void setEntreeIsActive(boolean entreeIsActive) {
        this.entreeIsActive = entreeIsActive;
    }

    public boolean isPoissonIsActive() {
        return poissonIsActive;
    }

    public void setPoissonIsActive(boolean poissonIsActive) {
        this.poissonIsActive = poissonIsActive;
    }

    public boolean isViandeIsActive() {
        return viandeIsActive;
    }

    public void setViandeIsActive(boolean viandeIsActive) {
        this.viandeIsActive = viandeIsActive;
    }

    public boolean isFromageIsActive() {
        return fromageIsActive;
    }

    public void setFromageIsActive(boolean fromageIsActive) {
        this.fromageIsActive = fromageIsActive;
    }

    public boolean isDessertIsActive() {
        return dessertIsActive;
    }

    public void setDessertIsActive(boolean dessertIsActive) {
        this.dessertIsActive = dessertIsActive;
    }

    public boolean isAucuneIsActive() {
        return aucuneIsActive;
    }

    public void setAucuneIsActive(boolean aucuneIsActive) {
        this.aucuneIsActive = aucuneIsActive;
    }
}
