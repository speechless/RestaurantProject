package vue.utils;

import modele.Commandable;
import modele.Commande;

import javax.swing.*;
import java.util.List;

public class CommandListItem {
    // === Classe pour représenter un élément ===
        private String date;
        private String prixTTC;
        private String numTable;

        public CommandListItem(String date, String prixTTC, String numTable) {
            this.date = date;
            this.prixTTC = prixTTC;
            this.numTable = numTable;
        }

        public String getDate() {
            return date;
        }

        public String getPrixTTC() {
            return prixTTC;
        }

        public String getNumTable() {return numTable;}

    public static JList<CommandListItem> createList(List<Commande> lc){
        DefaultListModel<CommandListItem> listModel = new DefaultListModel<>();
        for(Commande c : lc){
            listModel.addElement(new CommandListItem(
                    c.getDateDebut(),
                    "Prix TTC: "+c.getTotalTTC(),
                    "Numéro de table: "+c.getNumTable()));
        }

        JList<CommandListItem> list = new JList<>(listModel);
        return list;
    }


}

