package modele;

public enum CategorieItem {
    ENTREE("Entrée"),
    POISSON("Poisson"),
    BOISSON("Boisson"),
    VIANDE("Viande"),
    FROMAGE("Fromage"),
    DESSERT("Dessert"),
    PLAT("Plat"),
    AUTRE("Autre"),
    AUCUNE("Aucune");

    public final String label;

    private CategorieItem(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
