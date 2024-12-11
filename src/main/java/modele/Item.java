package modele;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.Observable;

@Entity
public class Item extends Commandable {
    private String categorie;

    public Item(double prixHT, double tauxTVA, String nom, String categorie, boolean visibiliteCarte) {
        super(nom);
        this.setPrixHT(prixHT);
        this.setTauxTVA(tauxTVA);
        this.setVisibiliteCarte(visibiliteCarte);
        this.categorie = categorie;
    }

    public Item() {
        super();
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    @Override
    public String toString() {
        return getNom()+":"+getCategorie()+":"+getVisibiliteCarte()
                +":"+getPrixHT()+ ":"+getTauxTVA();
    }
}
