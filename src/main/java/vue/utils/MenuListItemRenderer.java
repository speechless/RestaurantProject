package vue.utils;

import javax.swing.*;
import java.awt.*;

public class MenuListItemRenderer extends JPanel implements ListCellRenderer<MenuListItem> {
    private JLabel imageLabel;
    private JLabel titleLabel;
    private JLabel descriptionLabel;
    private JLabel priceLabel;
    private JLabel visibilityLabel;


    public MenuListItemRenderer() {
        setLayout(new BorderLayout(10, 0)); // Espacement horizontal entre les composants
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Marges internes
        setOpaque(true);

        // Composants
        imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(50, 50)); // Limiter la taille de l'image

        JPanel textPanel = new JPanel(new GridLayout(3, 1)); // Titre, description et prix
        titleLabel = new JLabel();
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));

        descriptionLabel = new JLabel();
        descriptionLabel.setFont(new Font("Arial", Font.ITALIC, 12));

        priceLabel = new JLabel();
        priceLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        textPanel.add(titleLabel);
        textPanel.add(descriptionLabel);
        textPanel.add(priceLabel);

        visibilityLabel = new JLabel();
        visibilityLabel.setPreferredSize(new Dimension(15, 15)); // Petit carré pour la visibilité
        visibilityLabel.setOpaque(true);

        add(imageLabel, BorderLayout.WEST); // Image à gauche
        add(textPanel, BorderLayout.CENTER); // Texte au centre
        add(visibilityLabel, BorderLayout.EAST); // Visibilité à droite
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends MenuListItem> list, MenuListItem value, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
        // Configuration des valeurs
        imageLabel.setIcon(value.getImage());
        titleLabel.setText(value.getTitle());
        descriptionLabel.setText(value.getDescription());
        priceLabel.setText(String.format("HT: %.2f € | TTC: %.2f €", value.getPriceHT(), value.getPriceTTC()));
        visibilityLabel.setBackground(value.isVisible() ? Color.GREEN : Color.GRAY);

        // Couleurs de sélection
        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

        return this;
    }
}
