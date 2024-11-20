package Test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import modele.Item;
import modele.Menu;

public class TestMenu {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("RestaurantPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();

        try {
            et.begin();
            Menu menu1 = new Menu("Menu1");

            Item item1 = new Item(12.5, 0.055, "Saucisses", "Plat", true);
            Item item2 = new Item(3, 0.2, "Coca", "Boisson", true);
            Item item3 = new Item(2, 0.15, "Café", "Autre", true);

            System.out.println(menu1);

            menu1.ajouterItem(item1);
            System.out.println(menu1);

            menu1.ajouterItem(item2);
            menu1.ajouterItem(item3);
            System.out.println(menu1);

            item2.setTauxTVA(0.02);
            System.out.println(menu1);

            menu1.supprimerItem(item3);
            System.out.println(menu1);

            em.persist(menu1);
            et.commit();
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
