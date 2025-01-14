package modele;

import jakarta.persistence.*;

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
        this.categorie = CategorieItem.AUCUNE;
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
