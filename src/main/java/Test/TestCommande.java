package Test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import modele.Commande;
import modele.Item;
import modele.Menu;

public class TestCommande {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("RestaurantPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();

        try {
            et.begin();
 ////////////////////////////////////////////////////////////////////////////////////////////////////
            Menu menu1 = new Menu("Menu1");

            Item item1 = new Item(10.5, 0.055, "Pâtes au beurre", "Plat", true);
            Item item2 = new Item(4, 0.2, "Coca cola", "Boisson", true);
            Item item3 = new Item(2, 0.1, "Café", "Autre", true);

            menu1.ajouterItem(item1);
            menu1.ajouterItem(item2);
            menu1.ajouterItem(item3);

            Menu menu2 = new Menu("Menu2");

            Item item4 = new Item(15.5, 0.055, "Pâtes carbo", "Plat", true);
            Item item5 = new Item(4, 0.2, "Sprite", "Boisson", true);

            menu2.ajouterItem(item4);
            menu2.ajouterItem(item5);

            Menu menu3 = new Menu("Menu3");
            menu3.ajouterItem(item2);
            menu3.ajouterItem(item4);
////////////////////////////////////////////////////////////////////////////////////////////////////
            Commande com1 = new Commande(10);
            com1.ajoutCommande(menu1);
            com1.finaliserCommande();
            em.persist(com1);

            Commande com2 = new Commande(7);
            com2.ajoutCommande(menu2);
            em.persist(com2);

            Commande c3 = new Commande(12);
            c3.ajoutCommande(menu3);
            c3.ajoutCommande(item3);
            c3.finaliserCommande();
            em.persist(c3);

            et.commit();


        }
//        catch (Exception ex) {
//            System.out.println("exception : " + ex);
//            System.out.println("rollback");
//            et.rollback();
//        }
        finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
            if (emf != null && emf.isOpen()) {
                emf.close();
            }
        }


    }
}
