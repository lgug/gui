package objects;

import utils.ProdottoSemplificato;

import java.util.List;

public class Ordine {
    private List<Prodotto> prodotto;
    private String data;
    private List<ProdottoSemplificato> prodotti;
    private String ID;

    public Ordine(String data, List<ProdottoSemplificato> prodotti, String idu) {
        this.data = data;
        this.prodotti = prodotti;
        this.ID = ID;

    }
    public Ordine(){ }
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public List<ProdottoSemplificato> getProdotti() {
        return prodotti;
    }

    public void setProdotti(List<ProdottoSemplificato> prodotti) {
        this.prodotti = prodotti;
    }

    public List<Prodotto> getProdotto() {
        return prodotto;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }


    public void setProdotto(List<Prodotto> prodotto) {
        this.prodotto = prodotto;
    }
}
