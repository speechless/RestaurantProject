package vue.utils.menu;

import vue.utils.Commons;

import javax.swing.*;
import java.awt.*;

public class MenuListItemRenderer extends JPanel implements ListCellRenderer<MenuListItem> {
    private final JLabel  imageLabel;
    private final JLabel titleLabel;
    private final JPanel textPanel;
    private final JLabel priceLabel;
    private final JLabel visibilityLabel;


    public MenuListItemRenderer() {
        setLayout(new BorderLayout(10, 0)); // Espacement horizontal entre les composants
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Marges internes
        setBackground(Color.white);
        setOpaque(true);

        // Composants
        imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(50, 50)); // Limiter la taille de l'image

        textPanel = new JPanel(new GridLayout(3, 1)); // Titre, description et prix
        titleLabel = new JLabel();
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        titleLabel.setOpaque(true);


        priceLabel = new JLabel();
        priceLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        priceLabel.setOpaque(true);

        textPanel.add(titleLabel);
        textPanel.add(priceLabel);
        textPanel.setOpaque(true);
        textPanel.setBackground(Color.red);

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
        priceLabel.setText(String.format("HT: %.2f € | TTC: %.2f €", value.getPriceHT(), value.getPriceTTC()));
        visibilityLabel.setBackground(value.isVisible() ? Color.GREEN : Color.GRAY);


        if(isSelected){
            textPanel.setBackground(Commons.getSecondaryColor());
            priceLabel.setBackground(Commons.getSecondaryColor());
            titleLabel.setBackground(Commons.getSecondaryColor());
        }
        else{
            textPanel.setBackground(value.isItemInMenu() ? Commons.getPrimaryColor() : Color.white);
            priceLabel.setBackground(value.isItemInMenu() ? Commons.getPrimaryColor() : Color.white);
            titleLabel.setBackground(value.isItemInMenu() ? Commons.getPrimaryColor() : Color.white);
        }





        return this;
    }
}
