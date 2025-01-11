package modele;

import jakarta.persistence.*;

import java.util.*;

@Entity
public class Menu extends Commandable implements Observer {
    @ManyToMany(cascade = CascadeType.PERSIST)
    //@JoinTable(name="MENU_ITEM")
    private List<Item> listeItems;


    public Menu() {
        super();
        this.listeItems = new ArrayList<>();
    }

    public Menu(String nom) {
        super(nom);
        this.listeItems = new ArrayList<>();
    }

    public void ajouterItem(Item item) {
        this.listeItems.add(item);
        this.setPrixHT(this.getPrixHT() + item.getPrixHT());;

        if (item.getTauxTVA() > this.getTauxTVA()) {
            this.setTauxTVA(item.getTauxTVA());
        }

        item.addObserver(this);
    }

    public boolean supprimerItem(Item item) {
        if (this.listeItems.remove(item)) {
            this.setPrixHT(this.getPrixHT() - item.getPrixHT());

            recalculerTVA();
            item.deleteObserver(this);

            return true;
        }

        return false;
    }

    private void recalculerTVA() {
        this.setTauxTVA(0);

        for (Item item : this.listeItems) {
            if (item.getTauxTVA() > this.getTauxTVA()) {
                this.setTauxTVA(item.getTauxTVA());
            }
        }
    }

    private void recalculerprixHT() {
        this.setPrixHT(0);

        for (Item item : this.listeItems) {
            this.setPrixHT(this.getPrixHT() + item.getPrixHT());
        }
    }


    public List<Item> getListeItems() {
        return listeItems;
    }

    @Override
    public void update(Observable o, Object arg) {
        recalculerTVA();
        recalculerprixHT();
    }

    @Override
    public String toString() {
        return "Menu : " + this.getNom() +
                ", prixHT=" + this.getPrixHT() +
                ", tauxTVA=" + this.getTauxTVA() +
                ", visibilite=" + this.getVisibiliteCarte() +
                ", listeItems=" + this.getListeItems();
    }
}
