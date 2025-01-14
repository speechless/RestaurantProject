package Test;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;

public class ChartExample {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Diagramme avec JFreeChart");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);

            // Données pour le diagramme
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            dataset.addValue(10, "Catégorie A", "Janvier");
            dataset.addValue(15, "Catégorie A", "Février");
            dataset.addValue(20, "Catégorie A", "Mars");

            // Créer le graphique
            JFreeChart chart = ChartFactory.createBarChart(
                    "Ventes Mensuelles", // Titre
                    "Mois",             // Axe X
                    "Ventes",           // Axe Y
                    dataset
            );

            // Ajouter le graphique à un JPanel
            ChartPanel chartPanel = new ChartPanel(chart);
            frame.add(chartPanel);

            frame.setVisible(true);
        });
    }
}
