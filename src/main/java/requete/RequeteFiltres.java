package requete;

import jakarta.persistence.*;
import modele.Item;
import modele.Menu;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class RequeteFiltres {

    private static RequeteFiltres instance;

    private EntityManagerFactory emf;

    private RequeteFiltres() {
        this.emf = Requete.getInstance().getEmf();
    }

    public static RequeteFiltres getInstance() {
        if (instance == null) {
            instance = new RequeteFiltres();
        }
        return instance;
    }

    public Item getItemById(int id){
        EntityManager em = emf.createEntityManager();
        try {

            String strQuery = "SELECT i FROM Item i WHERE i.id = :id";
            Query query = em.createQuery(strQuery);
            query.setParameter("id", id);

            return (Item) query.getSingleResult();
        }catch (NoResultException e){
            return null;
        }
    }

    public Menu getMenuById(int id){
        try {
            EntityManager em = emf.createEntityManager();
            String strQuery = "SELECT m FROM Menu m WHERE m.id = :id";
            Query query = em.createQuery(strQuery);
            query.setParameter("id", id);

            return (Menu) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Object[]> getQuantiteVenteProduit(LocalDate startingDate, LocalDate endingDate,
            List<String> categories, int limite) {
        if (categories == null)
            return null;

        List<Object[]> RqList = getQuantiteVenteProduitByDate(startingDate, endingDate);
        List<Object[]> ReturnList = new ArrayList<>();

        //Voir si le commandable est un menu ou un item
        //Si menu voir catégorie menu
        //Si item renvoyer la catégorie
        for (Object[] result : RqList) {
            if (ReturnList.size() >= limite) {
                return ReturnList;
            } else {
                if (categories.contains("Menu")) {
                    Menu m = getMenuById((Integer) result[0]);
                    if (m != null) {
                        ReturnList.add(new Object[] { m.getNom(), result[1] });
                        System.out.println("Menu," + m.getNom() + " " +
                                result[1]);
                        continue;
                    }

                }
                Item i = getItemById((Integer) result[0]);
                if (i != null) {
                    if (categories.contains(i.getCategorie())) {
                        ReturnList.add(new Object[] { i.getNom(), result[1] });
                        System.out.println(i.getCategorie() + " " + i.getNom() + " " +
                                result[1]);
                    }
                }
            }
        }
        return ReturnList;
    }

    public List<Object[]> getQuantiteVenteProduitByDate(LocalDate startingDate,LocalDate endingDate) {
        EntityManager em = emf.createEntityManager();

        //LocalDate currentDate = LocalDate.now();
        String startingDateAsString = startingDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String endingDateAsString = endingDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        if(endingDate.isBefore(startingDate) && !endingDate.equals(LocalDate.now())){
            System.err.println("\nERROR : La date de fin est avant la date de début.\n");
            return null;
        }

        String jpql;
        TypedQuery<Object[]> query;

        if(endingDate.equals(LocalDate.now())) {
            jpql = "SELECT q.produit.id, SUM(q.quantite) AS totalQuantite " +
                    "FROM QuantiteCommande q " +
                    "GROUP BY q.produit.id " +
                    "ORDER BY totalQuantite DESC";
            query = em.createQuery(jpql, Object[].class);
        }else{
            jpql = "SELECT q.produit.id, SUM(q.quantite) AS totalQuantite " +
                    "FROM QuantiteCommande q "+
                    "WHERE q.commandeSource.dateDebut >= :startingDate " +
                    "AND q.commandeSource.dateDebut <= :endingDate " +
                    "GROUP BY q.produit.id " +
                    "ORDER BY totalQuantite DESC";
            query = em.createQuery(jpql, Object[].class);
            query.setParameter("startingDate", startingDateAsString);
            query.setParameter("endingDate", endingDateAsString);
        }
        return query.getResultList();
    }
/*
    public Object[][] getQuantiteVenteCategorie(LocalDate startingDate, LocalDate endingDate,
                                                  List<String> categories) {
        if (categories == null)
            return null;

        //Créer un tableau avec les catégories
        List<Object[]> RqList = getQuantiteVenteProduitByDate(startingDate, endingDate);
        Object[][] ReturnList = new Object[][] {
                {"Plat", 0},
                {"Boisson", 0},
                {"Menu", 0},
                {"Autre", 0}
        };


        //Ajouter la quantité dans la valeur du tableau correspondante
        for (Object[] result : RqList) {
                if (categories.contains("Menu")) {
                    Menu m = getMenuById((Integer) result[0]);
                    if (m != null) {
                        incrementValue(ReturnList,"Menu");
                        continue;
                    }

                }
                Item i = getItemById((Integer) result[0]);
                if (i != null) {
                    if (categories.contains(i.getCategorie())) {
                        incrementValue(ReturnList,i.getCategorie());
                    }
                }
            }

        return ReturnList;
    }
*/
    public static void incrementValue(Object[][] table, String category) {
        for (int i = 0; i < table.length; i++) {
            if (table[i][0].equals(category)) {  // Vérifier si la catégorie correspond
                int currentValue = (Integer) table[i][1];  // Récupérer la valeur actuelle
                table[i][1] = currentValue + 1;  // Incrémenter la valeur
                break;  // Une fois trouvé, on sort de la boucle
            }
        }
    }

    public static void main(String[] args) {
        RequeteFiltres rr = new RequeteFiltres();
        /*LocalDate d1 = LocalDate.of(2025,1,1);
        LocalDate d2 = LocalDate.of(2024,10,18);

        List<String> l = new ArrayList<>();
        l.add("Autre");
        l.add("Plat");
        Object[][] y = rr.getQuantiteVenteCategorie(d1,LocalDate.now(),l);
        System.out.println(Arrays.deepToString(y));
        List<Object[]> x = rr.getQuantiteVenteProduit(d2,LocalDate.now(),l,3);
        if(x == null){
            System.out.println("Rien a signaler");
        }else{
            for(Object[] a : x){
                System.out.println("--");
                System.out.println(a[0]);
                System.out.println(a[1]);

            }

        }*/
    }
}
