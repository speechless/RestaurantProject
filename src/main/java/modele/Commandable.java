package modele;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Observable;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Commandable extends Observable {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private int id;

    @Column(nullable = false,
            unique = true)
    private String nom;
    private double prixHT;

    private double tauxTVA;

    private boolean visibiliteCarte;

    public Commandable() {
        this.nom = "";
        this.visibiliteCarte = false;
        this.tauxTVA = 0;
        this.prixHT = 0;
    }

    public Commandable(String nom) {
        this();
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getId() {
        return id;
    }

    public boolean isVisibiliteCarte() {
        return visibiliteCarte;
    }

    public boolean getVisibiliteCarte() {
        return visibiliteCarte;
    }

    public void setVisibiliteCarte(boolean visibiliteCarte) {
        this.visibiliteCarte = visibiliteCarte;
    }

    public double getPrixHT() {
        return prixHT;
    }

    public double getTauxTVA() {
        return tauxTVA;
    }

    public void setPrixHT(double prixHT) {
        this.prixHT = prixHT;

        setChanged();
        notifyObservers();
    }

    public void setTauxTVA(double tauxTVA) {
        this.tauxTVA = tauxTVA;

        setChanged();
        notifyObservers();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Commandable that = (Commandable) o;
        return Objects.equals(nom, that.nom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nom);
    }

    @Override
    public String toString() {
        return "Commandable : " + nom +
                ", prixHT=" + prixHT +
                ", tauxTVA=" + tauxTVA +
                ", visibilite=" + visibiliteCarte;
    }
}
