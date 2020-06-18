package objects;

import com.sun.javafx.collections.ElementObservableListDecorator;
import utils.Manager;
import utils.ProdottoSemplificato;

import java.util.Date;
import java.util.List;

public class Ordine {
    private List<Prodotto> prodotto;
    private Long data;
    private List<ProdottoSemplificato> prodotti;
    private String ID;
    private long dataConsegna;
    private String consegna;
    private String acquistato;
    private String ora;
    private String metodoPagamento;
    private String datiPagamento;


    public Ordine(Long data, List<ProdottoSemplificato> prodotti, String ID, long dataConsegna) {
        this.data = data;
        this.prodotti = prodotti;
        this.dataConsegna = dataConsegna;
        this.ID = ID;

    }
    public Ordine(){ }
    public Long getData() { return data; }

    public void setData(Long data) { this.data = data; }

    public List<ProdottoSemplificato> getProdotti() { return prodotti; }

    public void setProdotti(List<ProdottoSemplificato> prodotti) { this.prodotti = prodotti; }

    public List<Prodotto> getProdotto() { return prodotto; }

    public String getID() { return ID; }

    public void setID(String ID) { this.ID = ID; }

    public long getDataConsegna() { return dataConsegna; }

    public void setDataConsegna(long dataConsegna) { this.dataConsegna = dataConsegna; }

    public void setProdotto(List<Prodotto> prodotto) { this.prodotto = prodotto; }

    public void setConsegna(String consegna) { this.consegna = consegna; }

    public void setAcquistato(String acquistato) { this.acquistato = acquistato; }

    public void setOra(String ora) { this.ora = ora; }

    public String getOra() { return ora; }

    public String getConsegna() { return consegna; }

    public String getAcquistato() { return acquistato; }
}
