package objects;

import utils.ProdottoSemplificato;

import java.util.List;

public class Ordine {
    private List<Prodotto> prodotto;
    private Long data;
    private List<ProdottoSemplificato> prodotti;
    private String ID;
    private long dataConsegna;


    public Ordine(Long data, List<ProdottoSemplificato> prodotti, String idu, long dataConsegna) {
        this.data = data;
        this.prodotti = prodotti;
        this.dataConsegna = dataConsegna;
        this.ID = ID;

    }
    public Ordine(){ }
    public Long getData() {
        return data;
    }

    public void setData(Long data) {
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

    public long getDataConsegna() { return dataConsegna; }

    public void setDataConsegna(long dataConsegna) { this.dataConsegna = dataConsegna; }

    public void setProdotto(List<Prodotto> prodotto) {
        this.prodotto = prodotto;
    }
}
