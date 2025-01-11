package vue.utils;

import vue.pages.*;
import vue.pages.admin.AdminMainPage;
import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Templates {
    /**
     * Créer la barre supérieure de la page
     *
     * @param admin Si le bouton sur la page renvoie vers la page admin
     *              ou la page d'accueil
     * @return JPanel - La barre en haut de la page
     */
    public static JPanel createTopBar(boolean admin) {
        // Création de la barre supérieure
        JPanel topBar = new JPanel(new GridBagLayout());
        topBar.setBackground(Color.white);
        topBar.setPreferredSize(new Dimension(0, 60)); // Hauteur de la barre
        topBar.setBorder(new MatteBorder(0, 0, 1, 0, new Color(0, 0, 0, 50))); // Ombre

        // Layout GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.NONE;
        gbc.weighty = 1.0; // Centrage vertical
        gbc.gridy = 0; // Ligne unique

        gbc.anchor = GridBagConstraints.WEST; // Logo à gauche
        gbc.gridx = 0;
        gbc.weightx = 0.1; //largeur de 10% de la page
        JLabel logoLabel = new JLabel(Commons.mainGetRestaurant().getName());
        logoLabel.setFont(new Font("Arial", Font.BOLD, 16));
        logoLabel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 10)); // Marges
        topBar.add(logoLabel, gbc);

        //Bouton à droite
        if(admin){
            setupTopBarButton(topBar, gbc,"Admin page",
                    () -> PageManager.getInstance().showPage(new AdminMainPage()));
        }else{
            setupTopBarButton(topBar, gbc,"Main page",
                    () -> PageManager.getInstance().showPage(new MainPage()));
        }


        // Ajout d'un espace flexible entre les éléments
        gbc.gridx = 1; // Cellule intermédiaire
        gbc.weightx = 1.0; // Prendre tout l'espace restant
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JPanel spacer = new JPanel(); // Panneau transparent
        spacer.setOpaque(false);
        topBar.add(spacer, gbc);
        return topBar;
    }

    /**
     * Fonction pour positioner le bouton dans la TopBar
     * (à ne utiliser que la fonction createTopBar)
     *
     * @param topBar Barre contenante
     * @param gbc Gestionnaire de grille de la barre
     * @param text Texte dans le bouton
     * @param action Action à faire à l'activation du bouton
     */
    private static void setupTopBarButton(JPanel topBar, GridBagConstraints gbc, String text, Runnable action) {
        // Bouton Admin à droite
        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridx = 1; // Deuxième colonne
        gbc.weightx = 0.9; // Poids plus élevé pour pousser le bouton à droite

        //Création d'un contenant à au bouton pour gérer l'affichage
        JPanel buttonWrapper = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        buttonWrapper.setOpaque(false); //Pas de fond dans le contenant
        buttonWrapper.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 30)); // Marges

        JButton button = new JButton(text);
        Color secondaryColor = new Color(32, 32, 246);
        button.setForeground(secondaryColor);
        button.setOpaque(false);
        button.setContentAreaFilled(false); //Ne remplie pas tout l'espace disponible
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        buttonWrapper.add(button);

        //Effectuer les actions
        button.addActionListener(e -> action.run());
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                button.setForeground(new Color(20, 20, 20));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                button.setForeground(new Color(32, 32, 246));
            }
        });
        //Ajout à la barre
        topBar.add(buttonWrapper, gbc);
    }

    /**
     * Créer un menu déroulant avec une liste de commandes donnée
     *
     * @param list Liste de commande (type: JList<CommandListItem>)
     *
     * @return JScrollPane - Menu déroulant avec les commandes affichées
     */
    public static JScrollPane setupCommandeScrollPane(JList<CommandListItem> list) {
        list.setCellRenderer(new CommandListItemRenderer());

        // Ajouter la liste dans un JScrollPane
        JScrollPane scrollPane = new JScrollPane(list);

        scrollPane.getVerticalScrollBar().setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(107, 107, 107); // Couleur de la barre
                this.trackColor = new Color(230, 230, 230); // Couleur de l'arrière-plan
            }

            //Changer les affichages par défaut des boutons de déroulement
            @Override
            protected JButton createDecreaseButton(int orientation) {
                return createZeroButton();
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                return createZeroButton();
            }

            private JButton createZeroButton() {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(0, 0));
                button.setMinimumSize(new Dimension(0, 0));
                button.setMaximumSize(new Dimension(0, 0));
                return button;
            }
        });
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200))); // Bordure de la liste
        return scrollPane;

    }


    /**
     * Créer un menu déroulant avec une liste de menus donnée
     *
     * @param list Liste de menus (type: JList<MenuListItem>)
     *
     * @return JScrollPane - Menu déroulant avec les commandes affichées
     */
    public static JScrollPane setupMenuScrollPane(JList<MenuListItem> list) {
        // Définir un renderer pour afficher les MenuListItem
        list.setCellRenderer(new MenuListItemRenderer());

        // Ajouter la liste dans un JScrollPane
        JScrollPane scrollPane = new JScrollPane(list);

        // Personnalisation de la barre de défilement
        scrollPane.getVerticalScrollBar().setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {

            //Changer les affichages par défaut des boutons de déroulement
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(107, 107, 107); // Couleur de la barre
                this.trackColor = new Color(230, 230, 230); // Couleur de l'arrière-plan
            }

            @Override
            protected JButton createDecreaseButton(int orientation) {
                return createZeroButton();
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                return createZeroButton();
            }

            private JButton createZeroButton() {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(0, 0));
                button.setMinimumSize(new Dimension(0, 0));
                button.setMaximumSize(new Dimension(0, 0));
                return button;
            }
        });

        // Ajuster la taille maximale des cellules pour limiter la hauteur des lignes
        list.setFixedCellHeight(60); // Chaque ligne a une hauteur fixe de 60 pixels
        list.setVisibleRowCount(6);  // Nombre maximal de lignes visibles sans scrolling

        // Désactiver les bordures par défaut et ajouter une bordure personnalisée
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200))); // Bordure de la liste

        return scrollPane;
    }


}
