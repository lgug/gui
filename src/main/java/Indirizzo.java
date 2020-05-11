public class Indirizzo {
    private String via;
    private String civico;
    private String cap;
    private String localita;
    private String provincia;
    private String paese;

    public Indirizzo(String via, String civico, String cap, String localita, String provincia, String paese) {
        this.via = via;
        this.civico = civico;
        this.cap = cap;
        this.localita = localita;
        this.provincia = provincia;
        this.paese = paese;
    }

    public String getVia() {
        return via;
    }

    public void setVia(String via) {
        this.via = via;
    }

    public String getCivico() {
        return civico;
    }

    public void setCivico(String civico) {
        this.civico = civico;
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public String getLocalita() {
        return localita;
    }

    public void setLocalita(String localita) {
        this.localita = localita;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getPaese() {
        return paese;
    }

    public void setPaese(String paese) {
        this.paese = paese;
    }
}
