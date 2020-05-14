package objects;

public enum RuoloResponsabile {
    DIPENDENTE("Dipendente"),
    AMMINISTRATORE("Amministratore"),
    ESERCENTE("Esercente");

    private final String enumString;

    RuoloResponsabile(String string) {
        enumString = string;
    }

    @Override
    public String toString() {
        return enumString;
    }
}
