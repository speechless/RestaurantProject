package requete;

import jakarta.persistence.*;
import modele.*;
import vue.pages.PageManager;
import vue.pages.TypeAffichage;
import vue.utils.MenuListItem;
import vue.utils.Commons;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;


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

    public List<Commandable> getCommandables(TypeAffichage type) {
        EntityManager em = emf.createEntityManager();

        String strQuery;
        if (type == TypeAffichage.ITEM) {
            strQuery = "SELECT i FROM Item i ORDER BY i.nom";
        }
        else if (type == TypeAffichage.MENU) {
            strQuery = "SELECT m FROM Menu m ORDER BY m.nom";
        }
        else {
            strQuery = "SELECT c FROM Commandable c ORDER BY c.nom";
        }
        Query query = em.createQuery(strQuery);
        List<Commandable> commandables = query.getResultList();
        return commandables;
    }

    public JList<MenuListItem> parseListCommandables(TypeAffichage type) {
        Commons commons = new Commons();
        List<Commandable> items = getCommandables(type);
        DefaultListModel<MenuListItem> listModel = new DefaultListModel<>();

        for (Commandable i : items) {
            if (i instanceof Menu) {
                if (type == TypeAffichage.MENU || type == TypeAffichage.BOTH) {
                    listModel.addElement(new MenuListItem(
                            i.getNom(),
                            commons.loadImage("img/Whiteboard.png"),
                            i.getPrixHT(),
                            i.getPrixHT() * (1 + i.getTauxTVA()),
                            i.isVisibiliteCarte(), false, false, i.getId()));
                    for (Item k : getItemsFromMenu(i.getId())) {
                        listModel.addElement(new MenuListItem(
                                " * " + i.getNom() + "---" + k.getNom(),
                                commons.loadImage(""),
                                0,
                                0,
                                false, true, true, i.getId()));
                    }
                }
            } else {
                if (type == TypeAffichage.ITEM || type == TypeAffichage.BOTH) {
                    listModel.addElement(new MenuListItem(i.getNom(),
                            commons.loadImage(""),
                            i.getPrixHT(),
                            i.getPrixHT() * (1 + i.getTauxTVA()),
                            i.isVisibiliteCarte(), false, true, i.getId()));
                }
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

    public Item getItem(int id) {
        EntityManager em = emf.createEntityManager();
        String strQuery = "SELECT i FROM Item i WHERE i.id = :id";
        Query query = em.createQuery(strQuery);
        query.setParameter("id", id);

        Item item = (Item) query.getSingleResult();
        return item;
    }

    public Menu getMenu(int id) {
        EntityManager em = emf.createEntityManager();
        String strQuery = "SELECT m FROM Menu m WHERE m.id = :id";
        Query query = em.createQuery(strQuery);
        query.setParameter("id", id);

        Menu menu = (Menu) query.getSingleResult();
        return menu;
    }

    public Commande getCommande(int id) {
        EntityManager em = emf.createEntityManager();
        String strQuery = "SELECT c FROM Commande c WHERE c.id = :id";
        Query query = em.createQuery(strQuery);
        query.setParameter("id", id);

        Commande commande = (Commande) query.getSingleResult();
        return commande;
    }

    public Commande changeNumTable(Commande commande, int numTable) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();

        try {
            et.begin();
            commande.setNumTable(numTable);
            em.merge(commande);

            et.commit();
            return commande;
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
            commande = em.merge(commande);

            et.commit();
        }
        finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }

        return commande;
    }

    public Menu retirerProduitMenu(Menu menu, Item item) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();

        try {
            et.begin();

            menu.supprimerItem(item);
            menu = em.merge(menu);

            et.commit();
        }
        finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }

        return menu;
    }

    public Menu ajouterProduitMenu(Menu menu, Item item) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();

        try {
            et.begin();

            menu.ajouterItem(item);
            menu = em.merge(menu);

            et.commit();
        }
        finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }

        return menu;
    }

    public Menu saveMenu(Menu menu) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();

        try {
            et.begin();
            menu = em.merge(menu);

            // Mise à jour des commandes contenant le menu
            String strQuery = "SELECT c FROM Commande c " +
                    "JOIN c.compositionCommande compo " +
                    "WHERE compo.produit.id = :produitId";
            Query query = em.createQuery(strQuery);
            query.setParameter("produitId", menu.getId());
            List<Commande> commandesAffectees = query.getResultList();

            for (Commande commande : commandesAffectees) {
                commande.recalculerPrixEtTVA();
                saveCommande(commande);
            }

            et.commit();
        }
        catch (Exception ex) {
            et.rollback();
            PageManager.getInstance().showErrorMessage("Un problème a eu lieu lors de la sauvegarde d'un menu");
        }
        finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }

        return menu;
    }

    public Item saveItem(Item item) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();

        try {
            et.begin();
            item = em.merge(item);

            // Mise à jour des menus contenant l'item
            List<Menu> menusAffectes = new ArrayList<>();
            String strQ = "SELECT m FROM Menu m " +
                    "JOIN m.listeItems compo " +
                    "WHERE compo.id = :produitId";
            Query q = em.createQuery(strQ);
            q.setParameter("produitId", item.getId());
            menusAffectes = q.getResultList();

            for (Menu menu : menusAffectes) {
                menu.recalculerTVA();
                menu.recalculerprixHT();
                saveMenu(menu);
            }

            // Mise à jour des commandes contenant directement l'item
            String strQuery = "SELECT c FROM Commande c " +
                    "JOIN c.compositionCommande compo " +
                    "WHERE compo.produit.id = :produitId";
            Query query = em.createQuery(strQuery);
            query.setParameter("produitId", item.getId());
            List<Commande> commandesAffectees = query.getResultList();

            for (Commande commande : commandesAffectees) {
                commande.recalculerPrixEtTVA();
                saveCommande(commande);
            }

            et.commit();
        }
        catch (Exception ex) {
            et.rollback();
            PageManager.getInstance().showErrorMessage("Un problème a eu lieu lors de la sauvegarde d'un produit");
        }
        finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }

        return item;
    }

    public Commande saveCommande(Commande commande) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();

        try {
            et.begin();
            commande = em.merge(commande);
            et.commit();
        }
        catch (Exception ex) {
            et.rollback();
            PageManager.getInstance().showErrorMessage("Un problème a eu lieu lors de la sauvegarde d'une commande");
        }
        finally {
            if (em.isOpen()) {
                em.close();
            }
        }

        return commande;
    }

    public Commande finaliserCommande(Commande commande) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();

        try {
            et.begin();
            commande.finaliserCommande();
            commande = em.merge(commande);
            et.commit();
        }
        finally {
            if (em.isOpen()) {
                em.close();
            }
        }

        return commande;
    }


    public void deleteCommande(Commande commande) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();

        try {
            et.begin();
            Commande managedCommande = em.find(Commande.class, commande.getId());
            if (managedCommande != null) {
                em.remove(managedCommande);
            } else {
                System.out.println("La commande n'existe pas dans la base de données.");
            }

            et.commit();
        } catch (Exception e) {
            if (et.isActive()) {
                et.rollback();
            }
            throw e; // Propager l'exception pour une gestion ultérieure
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }

    public void deleteMenu(Menu menu) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();

        try {
            et.begin();
            Menu managedMenu = em.find(Menu.class, menu.getId());
            if (managedMenu != null) {
                em.remove(managedMenu);
            } else {
                System.out.println("Le menu n'existe pas dans la base de données.");
            }

            et.commit();
        } catch (Exception e) {
            if (et.isActive()) {
                et.rollback();
            }
            throw e; // Propager l'exception pour une gestion ultérieure
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }

    public void deleteItem(Item item) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();

        try {
            et.begin();
            Item managedItem = em.find(Item.class, item.getId());
            if (managedItem != null) {
                em.remove(managedItem);
            } else {
                System.out.println("Le produit n'existe pas dans la base de données.");
            }

            et.commit();
        } catch (Exception e) {
            if (et.isActive()) {
                et.rollback();
            }
            throw e; // Propager l'exception pour une gestion ultérieure
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }



    public static void main(String[] args) {
        RequeteRestaurant rr = new RequeteRestaurant();
        Restaurant r = rr.getRestaurant("12345678910");
        System.out.println(r);

        Restaurant r2 = rr.getRestaurant("123456710");
        System.out.println(r2);

    }
}
