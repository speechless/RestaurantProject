package requete;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

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
