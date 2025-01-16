package vue.utils;

import vue.pages.MainPage;
import vue.pages.PageManager;

import javax.swing.*;
import java.awt.*;

/**
 * Classe de templates pour l'utilisation et la création de boutons
 */
public class ButtonTemplates {

    /**
     *
     * @param text Texte dans le boutton
     * @param action Action à faire à l'activation du bouton
     * @return JButton - Bouton
     */
    public static JButton setupClassicButton(String text, Runnable action) {
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

    /**
     * Renvoie un bouton de retour à la page d'accueil en fonction
     * de l'affichage de la page contenante
     *
     * @param gbc Gestionnaire d'affichage du JPanel contenant
     * @return JPanel - Le bouton
     */
    public static JPanel returnMenuButton(GridBagConstraints gbc) {
        JPanel borderPanel = new JPanel(new BorderLayout());

        // Titre de la section
        JButton topButton = setupClassicButton("Retour page d'accueil",
                () -> PageManager.getInstance().showPage(new MainPage()));

        topButton.setFont(new Font("Arial", Font.PLAIN, 12));
        topButton.setMargin(new Insets(5, 0, 5, 0)); // Supprime les marges internes
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

    /**
     * Renvoie un bouton de retour à la page d'accueil en fonction
     * de l'affichage de la page contenante qui n'utilise pas GridBagConstraints
     *
     * @return JPanel - Le bouton
     */
    public static JPanel returnMenuButtonSimple() {
        JPanel borderPanel = new JPanel(new BorderLayout());

        // Titre de la section
        JButton topButton = setupClassicButton("Retour page d'accueil",
                () -> PageManager.getInstance().showPage(new MainPage()));

        topButton.setFont(new Font("Arial", Font.PLAIN, 12));
        topButton.setMargin(new Insets(5, 0, 5, 0)); // Supprime les marges internes
        topButton.setPreferredSize(new Dimension(150, 30));

        borderPanel.add(topButton, BorderLayout.WEST);

        return borderPanel;
    }

    /**
     * Renvoie un bouton à 2 états
     *
     * @param text Texte dans le bouton
     * @param actionOn Action à faire à l'activation du bouton
     * @param actionOff Action à faire à la désactivation du bouton
     * @return JButton
     */
    public static JButton setupSingleToggleButton(String text, Runnable actionOn, Runnable actionOff) {
        //VISUEL
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.setFocusPainted(false);
        button.setRolloverEnabled(false);
        button.setBackground(Commons.getSecondaryColor());


        // Utilisation d'une variable pour suivre l'état du bouton
        boolean[] isActive = {true};

        button.addActionListener(e -> {
            isActive[0] = !isActive[0];  // Inverse l'état du bouton
            if (isActive[0]) {
                button.setBackground(Commons.getSecondaryColor());  // Bouton activé
                actionOn.run();
            } else {
                button.setBackground(Color.WHITE);  // Bouton désactivé
                actionOff.run();
            }
        });

        return button;
    }

}
