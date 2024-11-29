package vue.utils;

import javax.swing.*;

public class MenuListItem {
    private String title;
    private String description;
    private ImageIcon image; // Nouvelle propriété pour l'image
    private double priceHT; // Prix HT
    private double priceTTC; // Prix TTC
    private boolean isVisible; // Visibilité

    public MenuListItem(String title, String description, ImageIcon image, double priceHT, double priceTTC, boolean isVisible) {
        this.title = title;
        this.description = description;
        this.image = image;
        this.priceHT = priceHT;
        this.priceTTC = priceTTC;
        this.isVisible = isVisible;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
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

    public static JList<MenuListItem> createList(){
        DefaultListModel<MenuListItem> listModel = new DefaultListModel<>();
        Templates t = new Templates();
        listModel.addElement(new MenuListItem("Produit 1", "Description produit 1",
                t.loadImage("img/Whiteboard.png"), 10.0, 12.0, true));
        listModel.addElement(new MenuListItem("Produit 2", "Description produit 2",
                new ImageIcon("path/to/image2.png"), 20.0, 24.0, false));
        listModel.addElement(new MenuListItem("Produit 3", "Description produit 3",
                new ImageIcon("path/to/image3.png"), 15.0, 18.0, true));

        JList<MenuListItem> list2 = new JList<>(listModel);
        return list2;
    }
}
