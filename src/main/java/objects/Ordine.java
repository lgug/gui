package objects;

public class Ordine {
    private long data;
    private Prodotto prodotti;
    private UtenteCliente utente;

    public Ordine(long data, Prodotto prodotti, UtenteCliente utente) {
        this.data = data;
        this.prodotti = prodotti;
        this.utente = utente;
    }

    public long getData() {
        return data;
    }

    public void setData(long data) {
        this.data = data;
    }

    public Prodotto getProdotti() {
        return prodotti;
    }

    public void setProdotti(Prodotto prodotti) {
        this.prodotti = prodotti;
    }

    public UtenteCliente getUtente() {
        return utente;
    }

    public void setUtente(UtenteCliente utente) {
        this.utente = utente;
    }
}
