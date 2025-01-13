package modele;

import jakarta.persistence.*;
import vue.pages.admin.CategorieItem;

import java.util.Objects;
import java.util.Observable;

@Entity
public class Item extends Commandable {
    private CategorieItem categorie;

    public Item(double prixHT, double tauxTVA, String nom, CategorieItem categorie, boolean visibiliteCarte) {
        super(nom);
        this.setPrixHT(prixHT);
        this.setTauxTVA(tauxTVA);
        this.setVisibiliteCarte(visibiliteCarte);
        this.categorie = categorie;
    }

    public Item() {
        super();
    }

    public CategorieItem getCategorie() {
        return categorie;
    }

    public void setCategorie(CategorieItem categorie) {
        this.categorie = categorie;
    }

    @Override
    public String toString() {
        return getNom()+":"+getCategorie()+":"+getVisibiliteCarte()
                +":"+getPrixHT()+ ":"+getTauxTVA();
    }
}
