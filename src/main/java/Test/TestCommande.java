package Test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import modele.Adresse;
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
            Menu menu1 = new Menu("Menu1");

            Item item1 = new Item(12.5, 0.055, "Saucisses", "Plat", true);
            Item item2 = new Item(3, 0.2, "Coca", "Boisson", true);
            Item item3 = new Item(2, 0.1, "Café", "Autre", true);

            System.out.println(menu1);

            menu1.ajouterItem(item1);
            System.out.println(menu1);

            menu1.ajouterItem(item2);
            menu1.ajouterItem(item3);
            System.out.println(menu1);

            Commande com1 = new Commande(10);
            com1.ajoutCommande(menu1);
            com1.ajoutCommande(menu1);
            com1.ajoutCommande(item2);
            com1.ajoutCommande(item3);

            com1.finaliserCommande();

            System.out.println(com1.creerTicket().genererTexte());
            com1.creerTicket();

            Commande com2 = new Commande(7);
            com2.ajoutCommande(item2);
            com2.creerTicket();

            System.out.println(com1.creerFacture("Vanderbauwede", "Thomas", new Adresse("Lens", 11101, "rue ok", 27), 445887453).genererTexte());
            em.persist(com2);
            em.persist(com1);

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
