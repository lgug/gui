package objects;

public enum CaratteristicheProdotto {
    NO_GLUTINE("senza glutine"),
    VEGAN("vegano"),
    NO_LATTE("no latte"),
    BIOLOGICO("biologico"),
    NEUTRO("neutro"),
    VEGETARIANO("vegetariano");

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