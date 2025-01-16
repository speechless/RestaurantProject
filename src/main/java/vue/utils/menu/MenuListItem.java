package vue.utils.menu;

import javax.swing.*;

public class MenuListItem {
    private String title;
    private ImageIcon image; // Nouvelle propriété pour l'image
    private double priceHT; // Prix HT
    private double priceTTC; // Prix TTC
    private boolean isVisible; // Visibilité
    private boolean isItem; // Correspond à une classe item (sinon menu)
    private boolean isItemInMenu;
    private int id;
    public MenuListItem(String title, ImageIcon image, double priceHT, double priceTTC,
                        boolean isVisible,boolean isItemInMenu, boolean isItem, int id) {
        this.title = title;
        this.image = image;
        this.priceHT = priceHT;
        this.priceTTC = priceTTC;
        this.isVisible = isVisible;
        this.isItemInMenu = isItemInMenu;
        this.id = id;
        this.isItem = isItem;
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

    public boolean isItem() {
        return isItem;
    }

    public int getId() {
        return id;
    }
}
