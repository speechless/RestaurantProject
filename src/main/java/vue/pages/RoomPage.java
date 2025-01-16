package vue.pages;

import vue.utils.ButtonTemplates;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Page d'affichage de l'agencement de la salle (Non aboutti)
 */
public class RoomPage implements PageContent {

    @Override
    public JPanel getContentPanel() {
        // Panneau principal avec GridBagLayout
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Panneau secondaire avec BorderLayout
        JPanel borderPanel = new JPanel(new BorderLayout());

        // Titre de la section
        JButton topButton = ButtonTemplates.setupClassicButton("Retour page d'accueil",
                () -> PageManager.getInstance().showPage(new MainPage()));

        topButton.setFont(new Font("Arial", Font.PLAIN, 12));
        topButton.setMargin(new Insets(0, 0, 0, 0)); // Supprime les marges internes
        topButton.setPreferredSize(new Dimension(150, 30));

        // Ajouter le bouton au panneau BorderLayout
        borderPanel.add(topButton, BorderLayout.WEST);

        // Ajouter borderPanel au mainPanel avec GridBagConstraints
        gbc.gridx = 0; // Colonne
        gbc.gridy = 0; // Ligne
        gbc.gridwidth = 2; // Étend sur deux colonnes
        gbc.weightx = 1.0; // S'étend horizontalement
        gbc.weighty = 0.0; // Pas de poids vertical
        gbc.fill = GridBagConstraints.HORIZONTAL; // Remplir horizontalement
        gbc.insets = new Insets(5, 5, 5, 5); // Marges autour
        mainPanel.add(borderPanel, gbc);

        //////////////////////////////////////////////////////////////////////////////////////////////////
        // Grille 10x10
        JPanel gridPanel = new JPanel(new GridLayout(10, 10)); // Crée une grille
        gridPanel.setPreferredSize(new Dimension(400, 400)); // Taille de la grille

        // Définir les tableaux pour les cases noires et grises
        ArrayList<int[]> blackCells = new ArrayList<>(); // Liste pour les positions des cases noires
        ArrayList<GrayCell> grayCells = new ArrayList<>(); // Liste pour les cases grises avec numéros

        ArrayList<int[]> blackPositions = new ArrayList<>();

        // Tableau des positions des cases noires (exemple prédéfini)
        for (int j = 0; j < 10; j++) {
            for (int k = 0; k < 10; k++) {
                if((j==0 || j==9 || k==0 || k==9) && !(k==2 && j==9)) {
                    blackPositions.add(new int[]{j, k});
                }
            }
        }

        // Tableau des cases grises avec les numéros (exemple prédéfini)
        GrayCell[] grayPositions = {
                new GrayCell(1, 2, 2), new GrayCell(1, 1, 1),
                new GrayCell(2, 1, 3),new GrayCell(2, 2, 4),
                new GrayCell(4, 4, 5), new GrayCell(5, 4, 6),
                new GrayCell(5, 5, 8), new GrayCell(4, 5, 7)
        };

        // Ajouter les positions des cases noires dans le tableau
        for (int[] pos : blackPositions) {
            blackCells.add(pos); // Ajoute la position de chaque case noire
        }

        // Ajouter les cases grises avec numéros dans le tableau
        for (GrayCell grayCell : grayPositions) {
            grayCells.add(grayCell); // Ajoute la case grise avec son numéro
        }

        // Remplir la grille avec des cases
        for (int i = 0; i < 10 * 10; i++) {
            JPanel cell = new JPanel();
            cell.setPreferredSize(new Dimension(80,80));
            JLabel label = new JLabel();

            // Calcul de la position x et y de chaque case dans la grille
            int x = i % 10; // Position x
            int y = i / 10; // Position y

            boolean isBlack = false;
            boolean isGray = false;

            // Vérifier si la case est noire
            for (int[] blackPos : blackCells) {
                if (blackPos[0] == x && blackPos[1] == y) {
                    cell.setBackground(Color.BLACK); // Noire
                    isBlack = true;
                    break;
                }
            }

            // Vérifier si la case est grise
            for (GrayCell grayCell : grayCells) {
                if (grayCell.x == x && grayCell.y == y) {
                    cell.setBackground(Color.GRAY); // Grise
                    label.setText(String.format("%02d", grayCell.number)); // Affiche le numéro sur la case grise
                    label.setForeground(Color.BLACK);
                    label.setHorizontalAlignment(SwingConstants.CENTER);
                    label.setVerticalAlignment(SwingConstants.CENTER);
                    isGray = true;
                    break;
                }
            }

            // Si la case est ni noire ni grise, c'est une case blanche
            if (!isBlack && !isGray) {
                cell.setBackground(Color.WHITE); // Blanche
            }

            cell.add(label); // Ajouter le label à la cellule
            gridPanel.add(cell); // Ajouter la cellule à la grille
        }

        // Ajouter la grille au mainPanel
        gbc.gridx = 0; // Colonne
        gbc.gridy = 1; // Ligne
        gbc.gridwidth = 2; // Étend sur deux colonnes
        gbc.weightx = 1.0; // S'étend horizontalement
        gbc.weighty = 1.0; // Prend tout l'espace vertical
        gbc.fill = GridBagConstraints.NONE; // Remplir horizontalement
        gbc.insets = new Insets(5, 5, 5, 5); // Marges autour
        mainPanel.add(gridPanel, gbc); // Ajouter la grille au panneau principal

        return mainPanel;
    }

    // Classe interne pour représenter les cases grises avec leur numéro
    class GrayCell {
        int x, y, number;

        public GrayCell(int x, int y, int number) {
            this.x = x;
            this.y = y;
            this.number = number;
        }

        @Override
        public String toString() {
            return "Position: (" + x + ", " + y + "), Number: " + number;
        }
    }
}

