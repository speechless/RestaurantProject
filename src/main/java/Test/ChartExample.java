package Test;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import com.toedter.calendar.JDateChooser;

import java.awt.*;
import java.util.Calendar;
import java.util.Date;

public class ChartExample {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Diagramme avec JFreeChart et Limites de Date");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setLayout(new BorderLayout(10, 10));

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
            chartPanel.setPreferredSize(new java.awt.Dimension(600, 400));

            // Créer un JDateChooser avec limites
            JDateChooser dateChooser = new JDateChooser();

            // Définir la date minimale et maximale
            Calendar minDate = Calendar.getInstance();
            minDate.set(2023, Calendar.JANUARY, 1); // Date minimale : 1er Janvier 2023
            Calendar maxDate = Calendar.getInstance();
            maxDate.set(2025, Calendar.DECEMBER, 31); // Date maximale : 31 Décembre 2025

            dateChooser.setMinSelectableDate(minDate.getTime());
            dateChooser.setMaxSelectableDate(maxDate.getTime());

            // Initialiser à la date actuelle
            dateChooser.setDate(new Date());
            dateChooser.setPreferredSize(new java.awt.Dimension(150, 30));

            // Ajouter le sélecteur de date au panneau de contrôle
            JPanel controlPanel = new JPanel();
            controlPanel.add(new JLabel("Choisissez une date (2023-2025) :"));
            controlPanel.add(dateChooser);

            // Ajouter les composants au JFrame
            frame.add(chartPanel, BorderLayout.CENTER); // Le graphique au centre
            frame.add(controlPanel, BorderLayout.SOUTH); // Le sélecteur de date en bas

            frame.setVisible(true);
        });
    }
}
