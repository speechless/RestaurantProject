package vue.utils;

import javax.swing.*;
import java.awt.*;


public class CommandListItemRenderer extends JPanel implements ListCellRenderer<CommandListItem>{
    // === Renderer personnalisé pour JList ===
        /*private JLabel prixLabel;
        private JLabel dateLabel;
        private JLabel numTableLabel;*/
        private JLabel Label;

        public CommandListItemRenderer() {
            setLayout(new BorderLayout(0,10));
            setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Marges internes
            setOpaque(true); // Nécessaire pour changer les couleurs d'arrière-plan
            Label = new JLabel();
/*
            dateLabel = new JLabel();
            dateLabel.setFont(new Font("Arial", Font.BOLD, 14)); // Titre en gras

            prixLabel = new JLabel();
            prixLabel.setFont(new Font("Arial", Font.ITALIC, 12)); // Description en italique

            numTableLabel = new JLabel();
            numTableLabel.setFont(new Font("Arial", Font.PLAIN, 12));

            add(dateLabel, BorderLayout.NORTH);
            add(numTableLabel,BorderLayout.CENTER);
            add(prixLabel, BorderLayout.SOUTH);*/
            add(Label,BorderLayout.CENTER);
        }
    @Override
        public Component getListCellRendererComponent(JList<? extends CommandListItem> list, CommandListItem value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            Label.setText(String.format(
                    "<html><body style='padding:5px;'>Date de la commande: %s<br>%s<br>%s</body></html>",
                    value.getDate(), value.getPrixTTC(), value.getNumTable()
            ));
            /*numTableLabel.setText(value.getNumTable());
            prixLabel.setText(value.getPrixTTC());*/

            // Couleurs pour l'état sélectionné ou non
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

