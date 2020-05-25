package utils;

public class ProdottoSemplificato {
    private int id;
    private int quantita;

    public ProdottoSemplificato(int id, int quantita) {
        this.id = id;
        this.quantita = quantita;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
