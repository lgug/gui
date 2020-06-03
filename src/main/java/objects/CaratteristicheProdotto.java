package objects;

public enum CaratteristicheProdotto {
    NO_GLUTINE("Senza glutine"),
    VEGAN("Vegano"),
    NO_LATTE("Senza latte"),
    BIOLOGICO("Biologico"),
    NEUTRO("Generico"),
    VEGETARIANO("Vegetariano");

    private String enumString;

    CaratteristicheProdotto(String enumString) {
        this.enumString = enumString;
    }

    @Override
    public String toString() {
        return enumString;
    }

    public String realToString(){return super.toString();}
}