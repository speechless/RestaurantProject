package vue.actions;

import modele.Commande;
import modele.Ticket;
import vue.pages.PageManager;


import javax.swing.*;
import java.io.IOException;

public class CreateTicket{
    private static boolean isPopupOpened = false;


    public static void printTicket(Commande c) throws IOException {
        PageManager pg = PageManager.getInstance();
        JFrame page = pg.getFrame();
        if (!isPopupOpened) {
            isPopupOpened = true;

            JDialog popup = new JDialog(page, "Ticket de caisse "+c.getId(), false);
            popup.setSize(400, 800);
            popup.setLocationRelativeTo(page);
            Ticket t = new Ticket(c);

            JTextPane textPane = new JTextPane();
            textPane.setContentType("text/html"); // Déclarer un contenu HTML
            textPane.setText(t.genererTexte());
            textPane.setEditable(false);
            textPane.setOpaque(false);

            popup.add(textPane);

            // Permet de détecter la fermeture de la pop-up
            popup.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    isPopupOpened = false; // Réinitialiser le booléen quand le pop-up se ferme
                }
            });

            // Afficher le pop-up
            popup.setVisible(true);
        }

    }


}
