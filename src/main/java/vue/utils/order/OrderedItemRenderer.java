package vue.utils.order;

import modele.QuantiteCommande;

import javax.swing.*;
import java.awt.*;

public class OrderedItemRenderer extends JPanel implements ListCellRenderer<QuantiteCommande> {
    private final JLabel nameLabel = new JLabel();
    private final JLabel quantityLabel = new JLabel();

    public OrderedItemRenderer() {
        this.add(nameLabel, BorderLayout.WEST);
        this.add(quantityLabel, BorderLayout.EAST);

    }

    @Override
    public Component getListCellRendererComponent(JList<? extends QuantiteCommande> list, QuantiteCommande value, int index, boolean isSelected, boolean cellHasFocus) {
        this.nameLabel.setText(value.getProduit().getNom());
        this.quantityLabel.setText("x" + value.getQuantite());
        this.setOpaque(true);

        // Couleurs de sélection
        if (isSelected) {
            setBackground(Color.orange);
        } else {
            setBackground(Color.white);
        }

        return this;
    }
}
