package requete;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import modele.*;

import javax.lang.model.element.QualifiedNameable;
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

    public List<Commande> getCommandes() {
        EntityManager em = emf.createEntityManager();
        String strQuery = "SELECT c FROM Commande c ORDER BY c.dateDebut";
        Query query = em.createQuery(strQuery);
        List<Commande> commandes = query.getResultList();
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
        System.out.println(rr.getCommandables());
        System.out.println(rr.getCommandes());
        //System.out.println(rr.getVentesParCategorie());
    }
}
