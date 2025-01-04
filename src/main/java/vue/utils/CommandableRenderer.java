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

        priceLabel = new JLabel();
        priceLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        textPanel.add(titleLabel);
        textPanel.add(priceLabel);

        add(textPanel, BorderLayout.CENTER); // Texte au centre
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Commandable> list, Commandable value, int index, boolean isSelected, boolean cellHasFocus) {

        Color blocked = new Color(240,240,240);
        // Configuration des valeurs
        titleLabel.setText(value.getNom());
        priceLabel.setText(String.format("HT: %.2f € | TTC: %.2f €", value.getPrixHT(), value.getPrixHT()*(1+ value.getTauxTVA())));

        // Couleurs de sélection
        if (isSelected) {
            setBackground(Color.orange);
        }
        else {
            setBackground(Color.white);
        }

        return this;
    }
}
