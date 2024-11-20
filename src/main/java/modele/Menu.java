package modele;

import jakarta.persistence.*;

import java.util.*;

@Entity
public class Menu implements Observer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false,
            unique = true)
    private String nom;

    @ManyToMany(cascade = CascadeType.PERSIST)
    //@JoinTable(name="MENU_ITEM")
    private List<Item> listeItems;

    private boolean visibiliteCarte;

    private double prixHT;

    private double tauxTVA;

    public Menu() {
        this.nom = "";
        this.listeItems = new ArrayList<>();
        this.visibiliteCarte = false;
        this.tauxTVA = 0;
        this.prixHT = 0;
    }

    public Menu(String nom) {
        this();
        this.nom = nom;
    }

    public void ajouterItem(Item item) {
        this.listeItems.add(item);
        this.prixHT += item.getPrixHT();

        if (item.getTauxTVA() > this.tauxTVA) {
            this.tauxTVA = item.getTauxTVA();
        }

        item.addObserver(this);
    }

    public boolean supprimerItem(Item item) {
        if (this.listeItems.remove(item)) {
            this.prixHT -= item.getPrixHT();

            recalculerTVA();
            item.deleteObserver(this);

            return true;
        }

        return false;
    }

    private void recalculerTVA() {
        this.tauxTVA = 0;

        for (Item item : this.listeItems) {
            if (item.getTauxTVA() > this.tauxTVA) {
                this.tauxTVA = item.getTauxTVA();
            }
        }
    }

    private void recalculerprixHT() {
        this.prixHT = 0;

        for (Item item : this.listeItems) {
            this.prixHT += item.getPrixHT();
        }
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<Item> getListeItems() {
        return listeItems;
    }

    public boolean getVisibiliteCarte() {
        return visibiliteCarte;
    }

    public void setVisibiliteCarte(boolean visibiliteCarte) {
        this.visibiliteCarte = visibiliteCarte;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Menu menu = (Menu) o;
        return Objects.equals(nom, menu.nom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nom);
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", listeItems=" + listeItems +
                ", visibiliteCarte=" + visibiliteCarte +
                ", prixHT=" + prixHT +
                ", tauxTVA=" + tauxTVA +
                '}';
    }

    @Override
    public void update(Observable o, Object arg) {
        recalculerTVA();
        recalculerprixHT();
    }
}
