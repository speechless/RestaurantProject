package modele;

import jakarta.persistence.*;

import java.util.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Recu {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private int id;

    @ManyToOne
    private Commande commandeSource;

    private Date dateCreation;

    public Recu() {}

    public Recu(Commande commandeSource) {
        this.commandeSource = commandeSource;
        this.dateCreation = new Date();
    }

    public Commande getCommandeSource() {
        return commandeSource;
    }

    public Date getDateCreation() {
        return dateCreation;
    }


}
