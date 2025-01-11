package requete;

import jakarta.persistence.*;
import modele.*;
import vue.utils.MenuListItem;
import vue.utils.Commons;

import javax.swing.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.time.LocalDate;


public class RequeteRestaurant {
    private static RequeteRestaurant instance;

    private EntityManagerFactory emf;


    private RequeteRestaurant() {
        this.emf = Requete.getInstance().getEmf();
    }

    public static RequeteRestaurant getInstance() {
        if (instance == null) {
            instance = new RequeteRestaurant();
        }
        return instance;
    }

    public Restaurant createRestaurant(Restaurant restaurant){
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();

        try {
            et.begin();
            em.persist(restaurant);

            et.commit();
            return restaurant;
        }
        finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public Restaurant getRestaurant(String SIRENNumber){
        EntityManager em = emf.createEntityManager();
        try{
            String strQuery = "SELECT r FROM Restaurant r " +
                    " WHERE r.SIRENNumber = :SIRENNumber";
            Query query = em.createQuery(strQuery);
            query.setParameter("SIRENNumber", SIRENNumber);
            Restaurant r = (Restaurant) query.getSingleResult();
            return r;
        }catch (NoResultException e){
            System.out.println("Aucun restaurant trouvé");
            return null;
        }

    }

    public void modifRestaurant(String champNom,String champAddresse,String champTVA,
                                      String champTel,String champSIREN){
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();

        try {
            et.begin();
            Restaurant r = Commons.mainGetRestaurant();
            r.setName(champNom);
            r.setAddress(champAddresse);
            r.setTVANumber(champTVA);
            r.setPhoneNumber(champTel);
            r.setSIRENNumber(champSIREN);

            em.merge(r);

            et.commit();
        }
        finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public List<Commandable> getCommandables() {
        EntityManager em = emf.createEntityManager();
        String strQuery = "SELECT c FROM Commandable c ORDER BY c.nom";
        Query query = em.createQuery(strQuery);
        List<Commandable> commandables = query.getResultList();
        return commandables;
    }

    public JList<MenuListItem> parseListCommandables() {
        Commons commons = new Commons();
        List<Commandable> items = getCommandables();
        DefaultListModel<MenuListItem> listModel = new DefaultListModel<>();

        for (Commandable i : items) {
            if (i instanceof Menu) {
                listModel.addElement(new MenuListItem(
                        i.getNom(),
                        commons.loadImage("img/Whiteboard.png"),
                        i.getPrixHT(),
                        i.getPrixHT() * (1 + i.getTauxTVA()),
                        i.isVisibiliteCarte(), false));
                for (Item k : getItemsFromMenu(i.getId())) {
                    listModel.addElement(new MenuListItem(
                            " * " + i.getNom() + "---" + k.getNom(),
                            commons.loadImage(""),
                            0,
                            0,
                            false, true));
                }
            } else {
                listModel.addElement(new MenuListItem(i.getNom(),
                        commons.loadImage(""),
                        i.getPrixHT(),
                        i.getPrixHT() * (1 + i.getTauxTVA()),
                        i.isVisibiliteCarte(), false));
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

    public List<Commande> getCommandesTermineesMain(int limite) {
        EntityManager em = emf.createEntityManager();
        String strQuery = "SELECT c FROM Commande c WHERE finalise = true ORDER BY c.dateDebut DESC limit :limite";
        Query query = em.createQuery(strQuery);
        query.setParameter("limite", limite);
        List<Commande> commandes = query.getResultList();
        return commandes;
    }

    public List<Item> getItemsFromMenu(int menuId) {
        EntityManager em = emf.createEntityManager();
        String strQuery = "SELECT i FROM Menu m " +
                "JOIN m.listeItems i WHERE m.id = :menuId";
        Query query = em.createQuery(strQuery);
        query.setParameter("menuId", menuId);
        List<Item> commandes = query.getResultList();
        return commandes;

    }

    public Commande getCommande(int id) {
        EntityManager em = emf.createEntityManager();
        String strQuery = "SELECT c FROM Commande c WHERE c.id = :id";
        Query query = em.createQuery(strQuery);
        query.setParameter("id", id);

        Commande commande = (Commande) query.getSingleResult();
        return commande;
    }


    public QuantiteCommande creerQuantiteCommande(Commande commande, Commandable commandable) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();

        try {
            et.begin();

            QuantiteCommande quantiteCommande = new QuantiteCommande(commande, commandable, 1);
            em.persist(quantiteCommande);

            et.commit();
            return quantiteCommande;
        }
        finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public Commande retirerProduitCommande(Commande commande, Commandable produit) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();

        try {
            et.begin();
            commande.retraitCommande(produit);
            commande = em.merge(commande);
            //produit = em.merge(produit);

            //System.out.println("persist");
            et.commit();
        }
        finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }

        return commande;
    }

    public Commande ajouterProduitCommande(Commande commande, Commandable produit) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();

        try {
            et.begin();
            commande.ajoutCommande(produit);
            //quantiteCommande = em.merge(quantiteCommande);
//            if (quantiteCommande.getQuantite() == 1) {
//                em.persist(quantiteCommande);
//            }
            commande = em.merge(commande);
            //produit = em.merge(produit);


            //System.out.println("persist");
            et.commit();
        }
        finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }

        return commande;
    }

    public Commande saveCommande(Commande commande) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();

        try {
            et.begin();
            commande = em.merge(commande);
            System.out.println("persist");
            et.commit();
        }
        /*catch (Exception ex) {
            System.out.println("exception : " + ex);
            System.out.println("rollback");
            et.rollback();
        }*/
        finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }

        return commande;
    }



    public static void main(String[] args) {
        RequeteRestaurant rr = new RequeteRestaurant();
        Restaurant r = rr.getRestaurant("12345678910");
        System.out.println(r);

        Restaurant r2 = rr.getRestaurant("123456710");
        System.out.println(r2);

    }
}
