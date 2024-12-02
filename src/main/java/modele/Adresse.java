package modele;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Adresse {
    private int numero;

    @Column(nullable = false)
    private String rue;
    @Column(nullable = false)
    private String ville;
    private int codePostal;

    public Adresse() {}
    public Adresse(String ville, int codePostal, String rue, int numero) {
        this.ville = ville;
        this.codePostal = codePostal;
        this.rue = rue;
        this.numero = numero;
    }

    @Override
    public String toString() {
        return numero + " " + rue + ", " + codePostal + " " + ville;
    }
}
