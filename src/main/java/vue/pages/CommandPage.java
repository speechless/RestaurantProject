package vue.pages;

import jakarta.persistence.PersistenceException;
import modele.Commandable;
import modele.Commande;
import modele.QuantiteCommande;
import requete.RequeteRestaurant;
import vue.utils.ButtonTemplates;
import vue.utils.order.OrderContentPanel;
import vue.utils.order.OrderMenuListPanel;
import vue.actions.CreateTicket;

import javax.swing.text.NumberFormatter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.NumberFormat;

import javax.swing.*;
import java.awt.*;

public class CommandPage implements PageContent {
    private final RequeteRestaurant rq = RequeteRestaurant.getInstance();
    private Commande commande;

    private final OrderContentPanel orderContentPanel;
    private final OrderMenuListPanel orderMenuListPanel;

    public CommandPage() {
        this.commande = new Commande();
        this.commande = this.rq.saveCommande(this.commande);

        this.orderContentPanel = new OrderContentPanel(this.commande,false);
        this.orderMenuListPanel = new OrderMenuListPanel();

        configurerInteractions();
    }

    public CommandPage(int id) {
        this.commande = this.rq.getCommande(id);

        this.orderContentPanel = new OrderContentPanel(this.commande,false);
        this.orderMenuListPanel = new OrderMenuListPanel();

        configurerInteractions();
    }

    private void configurerInteractions() {
        this.orderContentPanel.orderList.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    // Récupérer l'index de l'élément cliqué
                    int index = orderContentPanel.orderList.locationToIndex(e.getPoint());

                    // Vérifier si un élément valide est cliqué
                    if (index != -1) {
                        // Récupérer l'élément correspondant
                        QuantiteCommande selectedItem = orderContentPanel.orderList.getModel().getElementAt(index);

                        // Passer l'élément à la méthode
                        commande = orderContentPanel.removeProductFromOrder(selectedItem);
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        });

        orderMenuListPanel.listContent.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    // Récupérer l'index de l'élément cliqué
                    int index = orderMenuListPanel.listContent.locationToIndex(e.getPoint());

                    // Vérifier si un élément valide est cliqué
                    if (index != -1) {
                        // Récupérer l'élément correspondant
                        Commandable selectedItem = orderMenuListPanel.listContent.getModel().getElementAt(index);

                        // Passer l'élément à la méthode
                        commande = orderContentPanel.addProductToOrder(selectedItem);
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        });
    }

    @Override
    public JPanel getContentPanel() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Configurer un NumberFormatter pour permettre uniquement les nombres entiers
        NumberFormat format = NumberFormat.getIntegerInstance();
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Integer.class); // Type attendu
        formatter.setAllowsInvalid(false);     // Bloque les entrées invalides
        formatter.setMinimum(0);               // Valeur minimale autorisée
        formatter.setMaximum(1000);            // Valeur maximale autorisée
        JFormattedTextField numberField = new JFormattedTextField(formatter);

        JPanel borderPanel = new JPanel(new BorderLayout());

        // Bouton retour accueil
        JButton topButton = ButtonTemplates.setupClassicButton("Retour page d'accueil",
                () -> {
            try {
                RequeteRestaurant.getInstance().changeNumTable(commande, (Integer) numberField.getValue());
                PageManager.getInstance().showPage(new MainPage());
            }catch (PersistenceException e){
                PageManager.getInstance().showErrorMessage(
                        "Une commande a été donnée trop récemment avec la même table.\n" +
                                "Veuillez changer la table de cette commande.");
            }
        });
        topButton.setFont(new Font("Arial", Font.PLAIN, 12));
        topButton.setMargin(new Insets(0, 0, 0, 0)); // Supprime les marges internes
        topButton.setPreferredSize(new Dimension(150, 30));

        // Ajouter le bouton au panneau BorderLayout
        borderPanel.add(topButton, BorderLayout.WEST);

        // Bouton suppression commande
        JButton deleteCommandeButton = ButtonTemplates.setupClassicButton("Supprimer la commande",
                () -> {
                    RequeteRestaurant.getInstance().deleteCommande(commande);
                    PageManager.getInstance().showPage(new MainPage());
                });
        deleteCommandeButton.setFont(new Font("Arial", Font.PLAIN, 12));
        deleteCommandeButton.setMargin(new Insets(0, 0, 0, 0)); // Supprime les marges internes
        deleteCommandeButton.setPreferredSize(new Dimension(150, 30));

        // Ajouter le bouton au panneau BorderLayout
        borderPanel.add(deleteCommandeButton, BorderLayout.EAST);

        // Ajouter borderPanel au mainPanel avec GridBagConstraints
        gbc.gridx = 0; // Colonne
        gbc.gridy = 0; // Ligne
        gbc.gridwidth = 2; // Étend sur deux colonnes
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        mainPanel.add(borderPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1; // Une seule colonne
        gbc.weightx = 0.5; // 50% de l'espace horizontal
        gbc.weighty = 0.6; // Prendre tout l'espace vertical
        gbc.fill = GridBagConstraints.BOTH; // Remplir complètement
        mainPanel.add(this.orderContentPanel, gbc);

        JPanel numTablePanel = new JPanel();
        numberField.setColumns(10); // Largeur en colonnes
        numberField.setValue(commande.getNumTable());
        JLabel labelTable = new JLabel("Numéro de table :");

        numTablePanel.add(labelTable);
        numTablePanel.add(numberField);

        JButton confirmButton = ButtonTemplates.setupClassicButton ("Confirmer la commande",()->{
            try {
                Integer value = (Integer) numberField.getValue();
                if (value != null && value != 0) {
                    try{
                        RequeteRestaurant.getInstance().changeNumTable(commande,(Integer) numberField.getValue());
                        RequeteRestaurant.getInstance().finaliserCommande(commande);
                        CreateTicket.printTicket(commande);
                        PageManager.getInstance().showPage(new MainPage());
                    }catch (PersistenceException e){
                        PageManager.getInstance().showErrorMessage(
                                "Une erreur s'est produite lors de la validation de la commande.\n" +
                                        "Il doit y avoir un conflit avec les valeurs d'une autre commande.\n" +
                                        "Vérifiez le numéros de table avec l'horaire correspondant.");
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Le numéro de table est invalide.");
                }
            }catch (NullPointerException npe){
                JOptionPane.showMessageDialog(null, "Le numéro de table est vide.");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });


        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1; // Une seule colonne
        gbc.weightx = 0.5; // 50% de l'espace horizontal
        gbc.weighty = 0.2; // Prendre tout l'espace vertical
        mainPanel.add(numTablePanel,gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0.5; // 50% de l'espace horizontal
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(this.orderMenuListPanel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1; // Une seule colonne
        gbc.weightx = 0.5; // 50% de l'espace horizontal
        gbc.weighty = 0.2; // Prendre tout l'espace vertical
        mainPanel.add(confirmButton,gbc);

        return mainPanel;
    }
}
