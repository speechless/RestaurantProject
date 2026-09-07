import requete.RequeteRestaurant;
import vue.pages.*;
import vue.pages.admin.RestaurantInfoPage;
import vue.utils.Commons;

public class Main {

    public static void main(String[] args) {
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
