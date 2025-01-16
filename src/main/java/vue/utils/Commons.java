package vue.utils;

import modele.Commandable;
import modele.Restaurant;
import requete.RequeteRestaurant;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class Commons {

    private static Restaurant restaurant = null;

    /**
     * Couleur principale de l'application
     */
    private static final Color MAIN_COLOR = new Color(236, 236, 236);

    /**
     * Couleur secondaire de l'application
     */
    private static final Color SECONDARY_COLOR = new Color(130, 179, 246);

    /**
     * Intervalle maximal en ms pour un double-clic
     */
    public static final int DOUBLE_CLICK_INTERVAL = 300;

    /**
     * Renvoie la couleur principale de l'application
     * @return Color
     */
    public static Color getPrimaryColor() {
        return MAIN_COLOR;
    }

    /**
     * Renvoie la couleur secondaire de l'application
     * @return Color
     */
    public static Color getSecondaryColor() {
        return SECONDARY_COLOR;
    }

    /**
     * Affiche un image en petit
     *
     * @param path Chemin vers l'image
     * @return ImageIcon - Image
     */
    public ImageIcon loadImage(String path) {
        URL imageUrl = getClass().getClassLoader().getResource(path);

        //L'image existe
        if (imageUrl != null) {
            ImageIcon icon = new ImageIcon(imageUrl);

            // Redimensionner l'image
            Image image = icon.getImage();
            Image resizedImage = image.getScaledInstance(50, 50, Image.SCALE_SMOOTH);

            // Retourner l'ImageIcon redimensionnée
            return new ImageIcon(resizedImage);

        } else {
            //L'image n'existe pas
            System.err.println("Icône non trouvée : " + path);
            return null;
        }
    }

    public static void setRestaurant(Restaurant restaurant) {
            if (restaurant != null) {
                Commons.restaurant = restaurant;
            }
    }

    public static Restaurant mainGetRestaurant(){
        return Commons.restaurant;
    }
}
