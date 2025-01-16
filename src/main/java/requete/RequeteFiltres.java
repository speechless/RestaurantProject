package requete;

import jakarta.persistence.*;
import modele.Item;
import modele.Menu;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.time.DayOfWeek;
import java.util.Objects;

/**
 * Requêtes pour la page de statistiques de ventes
 */
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

    /**
     * Récupère l'occurence des différentes catégories dans un intervalle de temps
     * @param startingDate date de debut de l'intervalle
     * @param endingDate date de fin de l'intervalle
     * @return L'occurence des différentes catégories dans l'intervalle
     */
    public List<Object[]> getQuantiteVenteProduitByDate(String startingDate,String endingDate) {
        EntityManager em = emf.createEntityManager();

        String jpql;
        TypedQuery<Object[]> query;

        if(startingDate.isEmpty()) {
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
            query.setParameter("startingDate", startingDate);
            query.setParameter("endingDate", endingDate);
        }
        return query.getResultList();
    }

    /**
     * Utilisé pour le diagramme, cette fonction récupère l'occurence des différentes catégories
     * d'item dans un certain intervalle de temps en fonction d'une date et d'un paramètre temporel (Semaine, Mois,etc..)
     * @param givenDate Une date pour la sélection
     * @param dateOption  Savoir si on prend la semaine, le mois, l'année ou tout
     * @param categories   Les catégories d'items/menus qu'on veut comme valeur de quantité
     * @return Un tableau de paire (Nom de la catégorie, quantité)
     */
    public Object[][] getQuantiteVenteCategorie(String givenDate, String dateOption, List<String> categories) {
        if (categories == null)
            return null;

        List<Object[]> RqList;
        //Si global on récupère tout
        if (Objects.equals(dateOption, "Global")) {
            RqList = getQuantiteVenteProduitByDate("", "");
        } else {
            String[] intervale = createIntervale(givenDate, dateOption);
            RqList = getQuantiteVenteProduitByDate(intervale[0], intervale[1]);
        }

        //Créer un tableau avec les catégories
        Object[][] ReturnList = new Object[][]{
                {"Menu", 0},
                {"Entrée", 0},
                {"Poisson", 0},
                {"Boisson", 0},
                {"Viande", 0},
                {"Fromage", 0},
                {"Dessert", 0},
                {"Plat", 0},
                {"Autre", 0},
                {"Aucune", 0}
        };

        for (Object[] x : RqList) {
            System.out.println(Arrays.toString(x));
        }


        //BARRIERE POUR EVITER LES ERREURS DE TYPE
        for (Object[] result : RqList) {
            //Ajouter la quantité dans la valeur du tableau correspondante
            int index;
            if (result[0] instanceof Integer) {
                index = (Integer) result[0];
            } else if (result[0] instanceof Long) {
                index = ((Long) result[0]).intValue();
            } else {
                throw new IllegalArgumentException("RequeteFiltres.getQuantiteVenteCategorie() : " +
                        "Unsupported type for result[0]: " + result[0].getClass().getName());
            }

            //BARRIERE POUR EVITER LES ERREURS DE TYPE
            int valueResult;
            if (result[1] instanceof Integer) {
                assert result[0] instanceof Integer;
                valueResult = (Integer) result[0];
            } else if (result[1] instanceof Long) {
                valueResult = ((Long) result[1]).intValue();
            } else {
                throw new IllegalArgumentException("RequeteFiltres.getQuantiteVenteCategorie() : " +
                        "Unsupported type for result[1]");
            }

            if (categories.contains("Menu")) {
                Menu m = getMenuById(index);
                if (m != null) {
                    incrementValue(ReturnList, "Menu",valueResult);
                }
            }
            Item i = getItemById(index);
            if (i != null) {
                if (categories.contains(i.getCategorie().label)) {
                    incrementValue(ReturnList, i.getCategorie().label, valueResult);
                }
            }

        }

        return ReturnList;
    }

    private static void incrementValue(Object[][] table, String category,int value) {
        for (int i = 0; i < table.length; i++) {
            if (table[i][0].equals(category)) {  // Vérifier si la catégorie correspond
                int currentValue = (int) table[i][1];  // Récupérer la valeur actuelle
                table[i][1] = currentValue + value;  // Incrémenter la valeur
                break;  // Une fois trouvé, on sort de la boucle
            }
        }
    }

    /**
     * Créer l'intervalle de temps en fonction d'une date donnée et d'un paramètre temporel
     * @param date date donnée, si vide on veut tout, pas d'intervalle donc
     * @param dateOption  Savoir si on prend la semaine, le mois, l'année ou tout
     * @return 2 dates qui composent les bornes de l'intervalle souhaité
     */
    private String[] createIntervale(String date, String dateOption){
        String[] dates = new String[2];
        LocalDate localdate = LocalDate.parse(date);
        switch (dateOption){
            case "Semaine":
                // Obtenir le premier jour de la semaine (lundi)
                LocalDate startOfWeek = LocalDate.parse(date).with(DayOfWeek.MONDAY);
                dates[0] = startOfWeek.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                // Ajouter les 6 jours à partir du lundi pour obtenir dimanche
                dates[1] = startOfWeek.plusDays(6)
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                break;
            case "Mois":
                // Premier jour du mois
                dates[0] = localdate.withDayOfMonth(1)
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                // Dernier jour du mois
                dates[1] = localdate.with(TemporalAdjusters.lastDayOfMonth())
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                break;

            case "Annee":

                // Premier jour de l'année
                dates[0] = localdate.withDayOfYear(1)
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                // Dernier jour de l'année
                dates[1] = localdate.with(TemporalAdjusters.lastDayOfYear())
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                break;

        }
        return dates;
    }

    public static void main(String[] args) {
        RequeteFiltres rr = new RequeteFiltres();
        List<String> l = new ArrayList<>();
        l.add("Menu");
        l.add("Boisson");
        l.add("Fromage");
        l.add("Plat");
        l.add("Autre");

        Object[][] y = rr.getQuantiteVenteCategorie("2024-01-16","Mois",l);
        System.out.println(Arrays.deepToString(y));
    }
}
