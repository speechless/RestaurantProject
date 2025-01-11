package vue.utils;

import modele.Commandable;
import modele.QuantiteCommande;

import javax.swing.*;
import java.awt.*;

public class CommandableRenderer extends JPanel implements ListCellRenderer<Commandable> {
    private JLabel titleLabel;
    private JLabel priceLabel;

    public CommandableRenderer() {
        setLayout(new BorderLayout(10, 0)); // Espacement horizontal entre les composants
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Marges internes
        setOpaque(true);

        // Composants
        JPanel textPanel = new JPanel(new GridLayout(3, 1)); // Titre, description et prix
        titleLabel = new JLabel();
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(Color.white);

        priceLabel = new JLabel();
        priceLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        priceLabel.setOpaque(true);
        priceLabel.setBackground(Color.white);

        textPanel.add(titleLabel);
        textPanel.add(priceLabel);
        textPanel.setOpaque(true);
        textPanel.setBackground(Color.white);

        add(textPanel, BorderLayout.CENTER); // Texte au centre
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Commandable> list, Commandable value, int index, boolean isSelected, boolean cellHasFocus) {

        // Configuration des valeurs
        titleLabel.setText(value.getNom());
        priceLabel.setText(String.format("HT: %.2f € | TTC: %.2f €", value.getPrixHT(), value.getPrixHT()*(1+ value.getTauxTVA())));


        // Couleurs de sélection
        if (isSelected) {
            setBackground(Commons.getSecondaryColor());
        }
        else {
            setBackground(Color.white);
        }

        return this;
    }
}
