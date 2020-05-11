public class UtenteCliente extends Utente{
    private FormaDiPagamento pagamento;
    private TesseraFedelta tesseraFedelta;

    public UtenteCliente(String nome, String cognome, Indirizzo indirizzo, String telefono, String email, String password, FormaDiPagamento pagamento, TesseraFedelta tesseraFedelta) {
        super(nome, cognome, indirizzo, telefono, email, password);
        this.pagamento = pagamento;
        this.tesseraFedelta = tesseraFedelta;
    }

    public FormaDiPagamento getPagamento() {
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

