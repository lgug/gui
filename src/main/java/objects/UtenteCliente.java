package objects;

import com.google.gson.JsonArray;

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

    public static UtenteCliente getInstanceFromArray(JsonArray jsonArray) {
        UtenteCliente utente = new UtenteCliente();

        JsonArray utenteinfo = jsonArray.get(0).getAsJsonArray();
        JsonArray indirizzoinfo = jsonArray.get(1).getAsJsonArray();

        if (jsonArray.size() == 3) {
            JsonArray tesserainfo = jsonArray.get(2).getAsJsonArray();
            TesseraFedelta tessera = new TesseraFedelta();
            tessera.setId(tesserainfo.get(0).getAsString());
            tessera.setDataEmissione(tesserainfo.get(1).getAsLong());
            tessera.setSaldoPunti(tesserainfo.get(2).getAsInt());
            utente.setTesseraFedelta(tessera);
        }

        utente.setId(utenteinfo.get(0).getAsString());
        utente.setNome(utenteinfo.get(1).getAsString());
        utente.setCognome(utenteinfo.get(2).getAsString());
        utente.setTelefono(utenteinfo.get(3).getAsString());
        utente.setPagamento(FormaDiPagamento.valueOf(utenteinfo.get(4).getAsString()));
        utente.setDatiDelPagamento(utenteinfo.get(5).getAsString());
        utente.setEmail(utenteinfo.get(6).getAsString());
        utente.setPassword(utenteinfo.get(7).getAsString());

        Indirizzo indirizzo = new Indirizzo();
        indirizzo.setVia(indirizzoinfo.get(1).getAsString());
        indirizzo.setCap(indirizzoinfo.get(2).getAsString());
        indirizzo.setLocalita(indirizzoinfo.get(3).getAsString());
        indirizzo.setProvincia(indirizzoinfo.get(4).getAsString());
        indirizzo.setPaese(indirizzoinfo.get(5).getAsString());
        indirizzo.setCivico(indirizzoinfo.get(6).getAsString());
        utente.setIndirizzo(indirizzo);
        return utente;
    }
}

