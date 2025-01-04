package vue.utils;

import javax.swing.*;
import java.awt.*;


public class CommandListItemRenderer extends JPanel implements ListCellRenderer<CommandListItem>{
        private JLabel Label;

        public CommandListItemRenderer() {
            setLayout(new BorderLayout(0,10));
            setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Marges internes
            setOpaque(true); // Nécessaire pour changer les couleurs d'arrière-plan
            Label = new JLabel();
            add(Label,BorderLayout.CENTER);
        }
    @Override
        public Component getListCellRendererComponent(JList<? extends CommandListItem> list, CommandListItem value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            Label.setText(String.format(
                    "<html><body style='padding:5px;'>Date de la commande: %s<br>%s<br>%s</body></html>",
                    value.getDate(), value.getPrixTTC(), value.getNumTable()
            ));

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

