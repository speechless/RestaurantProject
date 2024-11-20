package modele;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.Observable;

@Entity
public class Item extends Observable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private double prixHT;

    @Column(nullable = false)
    private double tauxTVA;

    @Column(nullable = false,
            unique = true)
    private String nom;

    private String categorie;

    private boolean visibiliteCarte;

    public Item(double prixHT, double tauxTVA, String nom, String categorie, boolean visibiliteCarte) {
        this.prixHT = prixHT;
        this.tauxTVA = tauxTVA;
        this.nom = nom;
        this.categorie = categorie;
        this.visibiliteCarte = visibiliteCarte;
    }

    public Item() {}

    public double getPrixHT() {
        return prixHT;
    }

    public void setPrixHT(double prixHT) {
        this.prixHT = prixHT;
        setChanged();
        notifyObservers();
    }

    public double getTauxTVA() {
        return tauxTVA;
    }

    public void setTauxTVA(double tauxTVA) {
        this.tauxTVA = tauxTVA;
        setChanged();
        notifyObservers();

    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
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
        Item item = (Item) o;
        return Objects.equals(nom, item.nom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nom);
    }

    @Override
    public String toString() {
        return nom;
    }
}
