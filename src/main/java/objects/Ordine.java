package objects;

import java.util.List;

public class Ordine {
    private String data;
    private List<Prodotto> prodotti;
    private UtenteCliente utente;

    public Ordine(String data, List<Prodotto> prodotti, UtenteCliente utente) {
        this.data = data;
        this.prodotti = prodotti;
        this.utente = utente;
    }
    public Ordine(){ }
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public List<Prodotto> getProdotti() {
        return prodotti;
    }

    public void setProdotti(List<Prodotto> prodotti) {
        this.prodotti = prodotti;
    }

    public UtenteCliente getUtente() {
        return utente;
    }

    public void setUtente(UtenteCliente utente) {
        this.utente = utente;
    }
}
