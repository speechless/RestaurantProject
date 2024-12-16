package requete;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import modele.*;
import vue.utils.MenuListItem;
import vue.utils.Templates;

import javax.lang.model.element.QualifiedNameable;
import javax.swing.*;
import java.util.HashMap;
import java.util.List;

public class RequeteRestaurant {

    private EntityManagerFactory emf;

    public RequeteRestaurant() {
        this.emf = Persistence.createEntityManagerFactory("RestaurantPU");
    }

    public List<Commandable> getCommandables() {
        EntityManager em = emf.createEntityManager();
        String strQuery = "SELECT c FROM Commandable c ORDER BY c.nom";
        Query query = em.createQuery(strQuery);
        List<Commandable> commandables = query.getResultList();
        return commandables;
    }

    public JList<MenuListItem> parseListCommandables(){
        List<Commandable> items = getCommandables();
        DefaultListModel<MenuListItem> listModel = new DefaultListModel<>();
        Templates t = new Templates();

        for(Commandable i : items){
            if(i instanceof Menu){
                listModel.addElement(new MenuListItem(
                        i.getNom(),
                        t.loadImage("img/Whiteboard.png"),
                        i.getPrixHT(),
                        i.getPrixHT() * (1+i.getTauxTVA()),
                        i.isVisibiliteCarte(),false));
                for(Item k : getItemsFromMenu(i.getId())){
                    listModel.addElement(new MenuListItem(
                            " * "+i.getNom()+"---"+k.getNom(),
                            t.loadImage(""),
                            0,
                            0,
                            false,true));
                }
            }
            else{
                listModel.addElement(new MenuListItem(i.getNom(),
                        t.loadImage(""),
                        i.getPrixHT(),
                        i.getPrixHT() * (1+i.getTauxTVA()),
                        i.isVisibiliteCarte(),false));
            }
        }
        JList<MenuListItem> list = new JList<>(listModel);
        return list;
    }

    public List<Commande> getCommandesCourantes() {
        EntityManager em = emf.createEntityManager();
        String strQuery = "SELECT c FROM Commande c WHERE " +
                "finalise = false ORDER BY c.dateDebut ASC";
        Query query = em.createQuery(strQuery);
        List<Commande> commandes = query.getResultList();
        return commandes;
    }

    public List<Commande> getCommandesTerminees() {
        EntityManager em = emf.createEntityManager();
        String strQuery = "SELECT c FROM Commande c WHERE finalise = true ORDER BY c.dateDebut";
        Query query = em.createQuery(strQuery);
        List<Commande> commandes = query.getResultList();
        return commandes;
    }

    public List<Commande> getCommandesTermineesMain() {
        EntityManager em = emf.createEntityManager();
        String strQuery = "SELECT c FROM Commande c WHERE finalise = true ORDER BY c.dateDebut DESC limit 3";
        Query query = em.createQuery(strQuery);
        List<Commande> commandes = query.getResultList();
        return commandes;
    }

    public List<Item> getItemsFromMenu(int menuId){
        EntityManager em = emf.createEntityManager();
        String strQuery = "SELECT i FROM Menu m " +
                "JOIN m.listeItems i WHERE m.id = :menuId";
        Query query = em.createQuery(strQuery);
        query.setParameter("menuId", menuId);
        List<Item> commandes = query.getResultList();
        return commandes;

    }


//    public List<QuantiteCommande> getVentesParCategorie() {
//        EntityManager em = emf.createEntityManager();
//        String strQuery = "SELECT q FROM QuantiteCommande q join Commande c WHERE c.finalise = true";
//        Query query = em.createQuery(strQuery);
//        List<QuantiteCommande> quantiteCommandes = query.getResultList();
//
//        for (QuantiteCommande q : quantiteCommandes) {
//            if (q.getProduit() instanceof Menu) {
//
//            }
//        }
//        return commandes;
//    }

    public static void main(String[] args) {
        RequeteRestaurant rr = new RequeteRestaurant();
        //System.out.println(rr.getCommandables());
        List<Commandable> x = rr.getCommandables();
        List<Commande> y = rr.getCommandesCourantes();
/*
        for(Commande e : x){
            System.out.println(e.getNumTable());
            System.out.println(e.getTotalTTC());
            System.out.println(e.getDateDebut());
            for(QuantiteCommande i : e.getCompositionCommande()){
                Commandable j = i.getProduit();

                if(j instanceof Menu){
                    System.out.println("*"+j.getNom());
                    for(Item k : rr.getItemsFromMenu(j.getId())){
                        System.out.println("    "+k.getNom());
                    }
                }
                else{
                    System.out.println("-"+j.getNom());
                }

            }*/


        //System.out.println(rr.getVentesParCategorie());
        //List<Item> li = rr.getItemsFromMenu(2);
        //System.out.println(li);
    }
}
