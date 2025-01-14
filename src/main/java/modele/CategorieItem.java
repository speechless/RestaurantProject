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

    public static String[] toStringArray() {
        String[] array = new String[CategorieItem.values().length];

        int i = 0;
        for (CategorieItem item : CategorieItem.values()) {
            array[i] = item.label;
            i++;
        }

        return array;
    }
}
