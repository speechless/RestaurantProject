package modele;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class CompteurManager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private static int currentNumTransactionTicket;
    private static int currentNumFacture;

    public static int getNextNumTransactionTicket() {
        CompteurManager.currentNumTransactionTicket++;
        return CompteurManager.currentNumTransactionTicket;
    }

    public static int getNextNumFacture() {
        CompteurManager.currentNumFacture++;
        return CompteurManager.currentNumFacture;
    }
}
