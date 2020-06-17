package objects;

import com.google.gson.JsonArray;

public class UtenteResponsabile extends Utente {
    private String matricola;
    private RuoloResponsabile ruolo;

    public UtenteResponsabile() {
    }

    public UtenteResponsabile(String id, String nome, String cognome, Indirizzo indirizzo, String telefono, String email, String password, String matricola, RuoloResponsabile ruolo) {
        super(id, nome, cognome, indirizzo, telefono, email, password);
        this.matricola = matricola;
        this.ruolo = ruolo;
    }

    public String getMatricola() {
        return matricola;
    }

    public void setMatricola(String matricola) {
        this.matricola = matricola;
    }

    public RuoloResponsabile getRuolo() {
        return ruolo;
    }

    public void setRuolo(RuoloResponsabile ruolo) {
        this.ruolo = ruolo;
    }

    public static UtenteResponsabile getInstanceFromArray(JsonArray jsonArray) {
        UtenteResponsabile utente = new UtenteResponsabile();

        JsonArray utenteinfo = jsonArray.get(0).getAsJsonArray();
        JsonArray indirizzoinfo = jsonArray.get(1).getAsJsonArray();

        utente.setId(utenteinfo.get(0).getAsString());
        utente.setNome(utenteinfo.get(1).getAsString());
        utente.setCognome(utenteinfo.get(2).getAsString());
        utente.setTelefono(utenteinfo.get(3).getAsString());
        utente.setEmail(utenteinfo.get(6).getAsString());
        utente.setPassword(utenteinfo.get(7).getAsString());
        utente.setMatricola(utenteinfo.get(8).getAsString());
        utente.setRuolo(RuoloResponsabile.valueOf(utenteinfo.get(10).getAsString()));

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
