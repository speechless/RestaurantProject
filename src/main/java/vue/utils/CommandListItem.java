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
        private int id;

        public CommandListItem(String date, String prixTTC, String numTable, int id) {
            this.date = date;
            this.prixTTC = prixTTC;
            this.numTable = numTable;
            this.id = id;
        }

        public String getDate() {
            return date;
        }

        public String getPrixTTC() {
            return prixTTC;
        }

        public String getNumTable() {return numTable;}

    public int getId() {
        return id;
    }

    public static JList<CommandListItem> createList(List<Commande> lc){
        DefaultListModel<CommandListItem> listModel = new DefaultListModel<>();
        for(Commande c : lc){
            listModel.addElement(new CommandListItem(
                    c.getDateDebut(),
                    "Prix TTC: "+c.getTotalTTC(),
                    "Numéro de table: "+c.getNumTable(),
                    c.getId()));
        }

        JList<CommandListItem> list = new JList<>(listModel);
        return list;
    }


}

