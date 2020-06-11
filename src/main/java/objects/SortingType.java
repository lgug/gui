package objects;

public enum SortingType {
    PRICE_ASCENDING("Prezzo (crescente)"),
    PRICE_DESCENDING("Prezzo (decrescente)"),
    NAME_ASCENDING("Nome (crescente)"),
    NAME_DESCENDING("Nome (decrescente)"),
    BRAND_ASCENDING("Marca (crescente)"),
    BRAND_DESCENDING("Marca (decrescente)");

    private final String enumString;

    SortingType(String enumString) {
        this.enumString = enumString;
    }

    @Override
    public String toString() {
        return enumString;
    }
}
