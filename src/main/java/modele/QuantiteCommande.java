package modele;

import jakarta.persistence.*;

@Entity
public class QuantiteCommande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Commande commandeSource;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Commandable produit;
    private int quantite;

    public QuantiteCommande() {}

    public QuantiteCommande(Commande commandeSource, Commandable produit) {
        this.commandeSource = commandeSource;
        this.produit = produit;
        this.quantite = 0;
    }

    public QuantiteCommande(Commande commandeSource, Commandable produit, int quantite) {
        this.commandeSource = commandeSource;
        this.produit = produit;
        this.quantite = quantite;
    }

    public Commandable getProduit() {
        return produit;
    }

    public int getQuantite() {
        return quantite;
    }

    public void add() {
        this.quantite++;
    }

    public void subtract() {
        this.quantite--;
    }

    @Override
    public String toString() {
        return produit.getNom() + " x" + quantite;
    }
}
