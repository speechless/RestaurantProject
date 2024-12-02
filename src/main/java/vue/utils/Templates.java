package vue.utils;
import vue.pages.*;
import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

public class Templates {
    private static final Color MAIN_COLOR = new Color(236, 236, 236);

    public static Color getPrimaryColor(){
        return MAIN_COLOR;
    }

    public ImageIcon loadImage(String path) {
        URL imageUrl = getClass().getClassLoader().getResource(path);

        if (imageUrl != null) {
            ImageIcon icon = new ImageIcon(imageUrl);

            // Redimensionner l'image
            Image image = icon.getImage();
            Image resizedImage = image.getScaledInstance(50,50, Image.SCALE_SMOOTH);

            // Retourner l'ImageIcon redimensionnée
            return new ImageIcon(resizedImage);
        } else {
            System.err.println("Icône non trouvée : " + path);
            return null;
        }
    }

    public static JPanel createTopBar() {
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

        // Logo à gauche
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.weightx = 0.1;
        JLabel logoLabel = new JLabel("LOGO");
        logoLabel.setFont(new Font("Arial", Font.BOLD, 16));
        logoLabel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 10)); // Marges
        topBar.add(logoLabel, gbc);

        setupAdminButton(topBar,gbc);

        // Ajout d'un espace flexible entre les éléments
        gbc.gridx = 1; // Cellule intermédiaire
        gbc.weightx = 1.0; // Prendre tout l'espace restant
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JPanel spacer = new JPanel(); // Panneau transparent
        spacer.setOpaque(false);
        topBar.add(spacer, gbc);
        return topBar;
    }

    private static void setupAdminButton(JPanel topBar,GridBagConstraints gbc){
        // Bouton Admin à droite
        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridx = 1; // Deuxième colonne
        gbc.weightx = 0.9; // Poids plus élevé pour pousser le bouton à droite
        JPanel buttonWrapper = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        buttonWrapper.setOpaque(false);
        buttonWrapper.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 30)); // Marges

        JButton button = new JButton("Page admin");
        Color secondaryColor = new Color(32, 32, 246);
        button.setForeground(secondaryColor);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        buttonWrapper.add(button);

        button.addActionListener(e -> PageManager.getInstance().showPage(new MenuPage()));
        button.addMouseListener(new MouseAdapter() {
            @Override public void mousePressed(MouseEvent e) {button.setForeground(new Color(20, 20, 20));}
            @Override public void mouseReleased(MouseEvent e) {button.setForeground(new Color(32, 32, 246));}
        });

        topBar.add(buttonWrapper, gbc);
    }

    public static JScrollPane setupScrollPane(JList<CommandListItem> list){
        list.setCellRenderer(new CommandListItemRenderer());

        // Ajouter la liste dans un JScrollPane
        JScrollPane scrollPane = new JScrollPane(list);
        // Personnalisation de la barre de défilement
        scrollPane.getVerticalScrollBar().setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(107, 107, 107); // Couleur de la barre
                this.trackColor = new Color(230, 230, 230); // Couleur de l'arrière-plan
            }

            @Override protected JButton createDecreaseButton(int orientation) {return createZeroButton();}
            @Override protected JButton createIncreaseButton(int orientation) {return createZeroButton();}

            private JButton createZeroButton() {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(0, 0));
                button.setMinimumSize(new Dimension(0, 0));
                button.setMaximumSize(new Dimension(0, 0));
                return button;
            }
        });
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200))); // Bordure de la liste
        return  scrollPane;

    }

    public static JScrollPane setupMenuScrollPane(JList<MenuListItem> list) {
        // Définir un renderer pour afficher les MenuListItem
        list.setCellRenderer(new MenuListItemRenderer());

        // Ajouter la liste dans un JScrollPane
        JScrollPane scrollPane = new JScrollPane(list);

        // Personnalisation de la barre de défilement
        scrollPane.getVerticalScrollBar().setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
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


    public static JButton setupClassicButton(String text,Runnable action){
        JButton button = new JButton(text);
        button.addActionListener(e -> action.run());
        button.setBackground(Color.white);
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.setFocusPainted(false);
        button.setRolloverEnabled(false);
        return button;
    }

    public static JPanel returnMenuButton(GridBagConstraints gbc) {
        // Panneau secondaire avec BorderLayout
        JPanel borderPanel = new JPanel(new BorderLayout());

        // Titre de la section
        JButton topButton = Templates.setupClassicButton("Retour page d'accueil",
                () -> PageManager.getInstance().showPage(new MainPage()));

        topButton.setFont(new Font("Arial", Font.PLAIN, 12));
        topButton.setMargin(new Insets(0, 0, 0, 0)); // Supprime les marges internes
        topButton.setPreferredSize(new Dimension(150, 30));

        borderPanel.add(topButton, BorderLayout.WEST);

        // Ajouter borderPanel au mainPanel avec GridBagConstraints
        gbc.gridx = 0; // Colonne
        gbc.gridy = 0; // Ligne
        gbc.gridwidth = 2; // Étend sur deux colonnes
        gbc.weightx = 1.0; // S'étend horizontalement
        gbc.weighty = 0.0; // Pas de poids vertical
        gbc.fill = GridBagConstraints.HORIZONTAL; // Remplir horizontalement
        gbc.insets = new Insets(5, 5, 5, 5); // Marges autour

        return borderPanel;
    }
}
