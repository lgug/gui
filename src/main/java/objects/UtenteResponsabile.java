package objects;

public class UtenteResponsabile extends Utente {
    private String matricola;
    private RuoloResponsabile ruolo;

    public UtenteResponsabile() {
    }

    public UtenteResponsabile(String nome, String cognome, Indirizzo indirizzo, String telefono, String email, String password, String matricola, RuoloResponsabile ruolo) {
        super(nome, cognome, indirizzo, telefono, email, password);
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
}
