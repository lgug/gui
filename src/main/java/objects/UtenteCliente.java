package objects;

public class UtenteCliente extends Utente {

    private String datiDelPagamento;
    private FormaDiPagamento pagamento;
    private TesseraFedelta tesseraFedelta;

    public UtenteCliente() {
    }

    public UtenteCliente(String id, String nome, String cognome, Indirizzo indirizzo, String telefono, String email, String password, String datiDelPagamento, FormaDiPagamento pagamento, TesseraFedelta tesseraFedelta) {
        super(id, nome, cognome, indirizzo, telefono, email, password);
        this.datiDelPagamento = datiDelPagamento;
        this.pagamento = pagamento;
        this.tesseraFedelta = tesseraFedelta;
    }

    public String getDatiDelPagamento() {
        return datiDelPagamento;
    }

    public void setDatiDelPagamento(String datiDelPagamento) {
        this.datiDelPagamento = datiDelPagamento;
    }

    public  FormaDiPagamento getPagamento() {
        return pagamento;
    }

    public void setPagamento(FormaDiPagamento pagamento) {
        this.pagamento = pagamento;
    }

    public TesseraFedelta getTesseraFedelta() {
        return tesseraFedelta;
    }

    public void setTesseraFedelta(TesseraFedelta tesseraFedelta) {
        this.tesseraFedelta = tesseraFedelta;
    }
}

