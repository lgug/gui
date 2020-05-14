package objects;

public class TesseraFedelta {
    private String id;
    private long dataEmissione;
    private int saldoPunti;

    public TesseraFedelta() {
    }

    public TesseraFedelta(String id, long dataEmissione, int saldoPunti) {
        this.id = id;
        this.dataEmissione = dataEmissione;
        this.saldoPunti = saldoPunti;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getDataEmissione() {
        return dataEmissione;
    }

    public void setDataEmissione(long dataEmissione) {
        this.dataEmissione = dataEmissione;
    }

    public int getSaldoPunti() {
        return saldoPunti;
    }

    public void setSaldoPunti(int saldoPunti) {
        this.saldoPunti = saldoPunti;
    }
}
