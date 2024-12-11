package vue.utils;

import javax.swing.*;

public class CommandListItem {
    // === Classe pour représenter un élément ===
        private String title;
        private String description;

        public CommandListItem(String title, String description) {
            this.title = title;
            this.description = description;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public static JList<CommandListItem> createList(){
        DefaultListModel<CommandListItem> listModel2 = new DefaultListModel<>();
        listModel2.addElement(new CommandListItem("Élément 1", "Description pour l'élément 1."));
        listModel2.addElement(new CommandListItem("Élément 2", "Description pour l'élément 2."));
        listModel2.addElement(new CommandListItem("Élément 3", "Description pour l'élément 3."));
        listModel2.addElement(new CommandListItem("Élément 4", "Description pour l'élément 4."));
        listModel2.addElement(new CommandListItem("Élément 1", "Description pour l'élément 1."));
        listModel2.addElement(new CommandListItem("Élément 2", "Description pour l'élément 2."));

        JList<CommandListItem> list2 = new JList<>(listModel2);
        return list2;
    }
}

