import vue.pages.*;
public class Main {
    public static void main(String[] args) {
        PageManager pageManager = PageManager.getInstance();

        // Afficher la page principale au démarrage
        pageManager.showPage(new MainPage());

        // Lancer l'application
        pageManager.start();
    }
}
