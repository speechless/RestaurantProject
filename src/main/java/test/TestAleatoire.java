package test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import modele.CategorieItem;
import modele.Commande;
import modele.Item;
import modele.Menu;
import requete.RequeteRestaurant;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Test à exécuter afin de remplir la base de données de commandes (à l'année 2024), de menus et d'item
 */
public class TestAleatoire {

    private final static int INTERVAL_JOURS = 3;
    private final static int INTERVAL_MINUTES = 20;

    private final static int NB_PRODUITS_COMMANDES_MIN = 2;
    private final static int NB_PRODUITS_COMMANDES_MAX = 5;
    private final static int NB_ITEMS_PAR_MENU_MIN = 1;
    private final static int NB_ITEMS_PAR_MENU_MAX = 4;

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("RestaurantPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();

        try {

            System.out.println("Génération des items");

            et.begin();
            List<Item> items = genererItems();
            for (int i = 0; i < items.size(); i++) {
                Item item = items.get(i);
                items.set(i, RequeteRestaurant.getInstance().saveItem(item));
            }
            et.commit();

            System.out.println("Génération des menus");

            et.begin();
            List<Menu> menus = genererMenus(items);
            for (int i = 0; i < menus.size(); i++) {
                Menu menu = menus.get(i);
                menus.set(i, RequeteRestaurant.getInstance().saveMenu(menu)) ;
            }
            et.commit();

            System.out.println("Génération des commandes");

            et.begin();
            List<Commande> commandes = genererCommandes(items, menus);
            System.out.println(commandes.size() + " commandes à sauvegarder (environ 1 min pour 1000)");
            for (Commande commande : commandes) {
                RequeteRestaurant.getInstance().saveCommande(commande);
            }
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

    private static List<Commande> genererCommandes(List<Item> items, List<Menu> menus) {
        Random random = new Random();

        List<Commande> commandes = new ArrayList<>();

        int annee = 2024;
        for (int mois = 1; mois <= 12; mois++) {
            for (int jour = 1; jour <= 28; jour += INTERVAL_JOURS) {
                for (int heure = 11; heure <= 15; heure++) {
                    for (int minute = 0; minute <= 59; minute += INTERVAL_MINUTES) {
                        LocalDateTime date = LocalDateTime.of(annee, mois, jour, heure, minute);
                        Commande commande = new Commande(random.nextInt(11), date);

                        int nbItems = random.nextInt(NB_PRODUITS_COMMANDES_MAX - NB_PRODUITS_COMMANDES_MIN + 1);
                        nbItems += NB_PRODUITS_COMMANDES_MIN;
                        for (int i = 0; i < nbItems; i++) {
                            if (random.nextBoolean()) {
                                int indice = random.nextInt(menus.size());
                                commande.ajoutCommande(menus.get(indice));
                            }
                            else {
                                int indice = random.nextInt(items.size());
                                commande.ajoutCommande(items.get(indice));
                            }
                        }

                        commande.finaliserCommande();
                        commandes.add(commande);
                    }
                }
            }
        }

        return commandes;
    }

    private static List<Menu> genererMenus(List<Item> items) {
        Random random = new Random();

        List<Menu> menus = new ArrayList<>();
        menus.add(new Menu("Menu1"));
        menus.add(new Menu("Menu2"));
        menus.add(new Menu("Menu3"));

        for (Menu menu : menus) {
            int nbItems = random.nextInt(NB_ITEMS_PAR_MENU_MAX - NB_ITEMS_PAR_MENU_MIN + 1);
            nbItems += NB_ITEMS_PAR_MENU_MIN;
            for (int i = 0; i < nbItems; i++) {
                int indiceItem = random.nextInt(items.size());
                menu.ajouterItem(items.get(indiceItem));
            }
        }

        return menus;
    }

    private static List<Item> genererItems() {
        List<Item> items = new ArrayList<>();
        items.add(new Item(10.5, 0.055, "Pates au beurre", CategorieItem.PLAT, true));
        items.add(new Item(4, 0.2, "Coca cola cherry", CategorieItem.BOISSON, true));
        items.add(new Item(2, 0.1, "Chocolat chaud", CategorieItem.AUTRE, true));
        items.add(new Item(15.5, 0.055, "Pâtes bolo", CategorieItem.PLAT, true));
        items.add(new Item(4, 0.2, "7up", CategorieItem.BOISSON, true));

        return items;
    }
}
