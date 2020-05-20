package objects;

public enum Categoria {
    FRUTTA_VERDURA("Frutta e verdura"),
    CARNE("Carne"),
    DOLCI("Dolci e dessert"),
    PESCE("Pesce"),
    LEGUMI("Legumi e cereali"),
    LATTICINI("Latticini");

    private String enumString;

    Categoria(String enumString) {
        this.enumString = enumString;
    }

    @Override
    public String toString() {
        return enumString;
    }
}
