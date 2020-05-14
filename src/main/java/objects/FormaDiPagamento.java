package objects;

public enum FormaDiPagamento {
    CONSEGNA("Alla consegna"),
    PAYPAL("PayPal"),
    CARTA_CREDITO("Carta di credito");

    private final String enumString;

    FormaDiPagamento(String string) {
        this.enumString = string;
    }

    @Override
    public String toString() {
        return enumString;
    }
}
