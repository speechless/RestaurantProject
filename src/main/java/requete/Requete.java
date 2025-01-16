package requete;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Classe mère des classes de requêtes. Elle gère l'instance du dialogue avec la BDD et la persistance
 */
public class Requete {
    protected final EntityManagerFactory emf;
    private static Requete instance;

    private Requete() {
        this.emf = Persistence.createEntityManagerFactory("RestaurantPU");
    }

    public static Requete getInstance() {
        if (instance == null) {
            instance = new Requete();
        }
        return instance;
    }

    public EntityManagerFactory getEmf() {
        return emf;
    }
}
