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

            Item item1 = new Item(10.5, 0.055, "Pates au beurre", "Plat", true);
            Item item2 = new Item(4, 0.2, "Coca cola cherry", "Boisson", true);
            Item item3 = new Item(2, 0.1, "Chocolat chuad", "Autre", true);

            menu1.ajouterItem(item1);
            menu1.ajouterItem(item2);
            menu1.ajouterItem(item3);

            Menu menu2 = new Menu("Menu2");

            Item item4 = new Item(15.5, 0.055, "Pâtes bolo", "Plat", true);
            Item item5 = new Item(4, 0.2, "7up", "Boisson", true);

            menu2.ajouterItem(item4);
            menu2.ajouterItem(item5);

            Menu menu3 = new Menu("Menu3");
            menu3.ajouterItem(item2);
            menu3.ajouterItem(item4);

////////////////////////////////////////////////////////////////////////////////////////////////////
            Commande com1 = new Commande(21);
            com1.ajoutCommande(menu3);
            com1.finaliserCommande();
            em.persist(com1);

            Commande com2 = new Commande(75);
            com2.ajoutCommande(menu2);
            com2.ajoutCommande(item2);
            em.persist(com2);

            Commande c1 = new Commande(2);
            c1.ajoutCommande(menu1);
            com2.ajoutCommande(item1);
            c1.finaliserCommande();
            em.persist(c1);

            Commande com3 = new Commande(25);
            com3.ajoutCommande(menu3);
            com3.finaliserCommande();
            em.persist(com3);

            Commande com4 = new Commande(72);
            com4.ajoutCommande(menu1);
            em.persist(com4);

            Commande c2 = new Commande(23);
            c2.ajoutCommande(menu2);
            c2.ajoutCommande(item3);
            c2.finaliserCommande();
            em.persist(c2);

            Commande com5 = new Commande(5);
            com5.ajoutCommande(menu3);
            com5.finaliserCommande();
            em.persist(com5);

            Commande com6 = new Commande(1);
            com6.ajoutCommande(menu1);
            em.persist(com6);

            Commande c3 = new Commande(9);
            c3.ajoutCommande(menu2);
            c3.ajoutCommande(item5);
            c3.finaliserCommande();
            em.persist(c3);

            et.commit();
            System.out.println("Génération de la base terminée");

        }
        catch (Exception ex) {
            System.out.println("exception : " + ex);
            System.out.println("rollback");
            et.rollback();
        }
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
