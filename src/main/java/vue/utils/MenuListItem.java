package vue.utils;

import javax.swing.*;
import java.util.List;

public class MenuListItem {
    private String title;
    private ImageIcon image; // Nouvelle propriété pour l'image
    private double priceHT; // Prix HT
    private double priceTTC; // Prix TTC
    private boolean isVisible; // Visibilité
    private boolean isItemInMenu;
    public MenuListItem(String title, ImageIcon image, double priceHT, double priceTTC,
                        boolean isVisible,boolean isItemInMenu) {
        this.title = title;
        this.image = image;
        this.priceHT = priceHT;
        this.priceTTC = priceTTC;
        this.isVisible = isVisible;
        this.isItemInMenu = isItemInMenu;
    }

    public String getTitle() {
        return title;
    }


    public ImageIcon getImage() {
        return image;
    }

    public double getPriceHT() {
        return priceHT;
    }

    public double getPriceTTC() {
        return priceTTC;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public boolean isItemInMenu() {return isItemInMenu;}
}
