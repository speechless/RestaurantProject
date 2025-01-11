import modele.Restaurant;
import requete.RequeteFiltres;
import requete.RequeteRestaurant;
import vue.pages.*;
public class Main {

    public static void main(String[] args) {
        /*Restaurant r = new Restaurant("Restau2I","Quelque part",
                "0849234683","456374296352","12345678910");
        RequeteRestaurant rq = RequeteRestaurant.getInstance();
        rq.createRestaurant(r);*/

        PageManager pageManager = PageManager.getInstance();

        // Afficher la page principale au démarrage
        pageManager.showPage(new MainPage());

        // Lancer l'application
        pageManager.start();
    }
}
