package objects;

public enum CaratteristicheProdotto {
    NO_GLUTINE("senza glutine"),
    VEGAN("vegano"),
    NO_LATTE("no latte"),
    BIOLOGICO("biologico"),
    VEGETARIANO("vegetariano");

    private String enumString;

    CaratteristicheProdotto(String enumString) {
        this.enumString = enumString;
    }

    @Override
    public String toString() {
        return enumString;
    }
}