package vue.utils;

import javax.swing.*;
import java.awt.*;


public class CommandListItemRenderer extends JPanel implements ListCellRenderer<CommandListItem>{
    // === Renderer personnalisé pour JList ===
        private JLabel titleLabel;
        private JLabel descriptionLabel;

        public CommandListItemRenderer() {
            setLayout(new BorderLayout(0,0));
            setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Marges internes
            setOpaque(true); // Nécessaire pour changer les couleurs d'arrière-plan

            titleLabel = new JLabel();
            titleLabel.setFont(new Font("Arial", Font.BOLD, 14)); // Titre en gras

            descriptionLabel = new JLabel();
            descriptionLabel.setFont(new Font("Arial", Font.ITALIC, 12)); // Description en italique

            add(titleLabel, BorderLayout.NORTH);
            add(descriptionLabel, BorderLayout.CENTER);
        }
    @Override
        public Component getListCellRendererComponent(JList<? extends CommandListItem> list, CommandListItem value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            titleLabel.setText(value.getTitle());
            descriptionLabel.setText(value.getDescription());

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

