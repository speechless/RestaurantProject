import modele.Restaurant;
import requete.RequeteFiltres;
import requete.RequeteRestaurant;
import vue.pages.*;
import vue.pages.admin.RestaurantInfoPage;
import vue.utils.Commons;

public class Main {

    public static void main(String[] args) {
        /*Restaurant r = new Restaurant("Restau2I","Quelque part",
                "0849234683","456374296352","12345678910");
        RequeteRestaurant rq = RequeteRestaurant.getInstance();
        rq.createRestaurant(r);*/

        PageManager pageManager = PageManager.getInstance();

        Commons.setRestaurant(RequeteRestaurant.getInstance().getRestaurant());

        // Afficher la page de création de restaurant s'il n'y en a pas
        if (Commons.mainGetRestaurant() == null) {
            pageManager.showPage(new RestaurantInfoPage());
        }
        // Sinon afficher la page principale
        else {
            pageManager.showPage(new MainPage());
        }

        // Lancer l'application
        pageManager.start();
    }
}
