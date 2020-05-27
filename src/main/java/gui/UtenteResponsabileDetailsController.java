package gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import objects.Indirizzo;
import objects.RuoloResponsabile;
import objects.UtenteResponsabile;

import java.net.URL;
import java.util.ResourceBundle;

public class UtenteResponsabileDetailsController implements Initializable {

    @FXML
    private Label nomeLabel;
    @FXML
    private Label cognomeLabel;
    @FXML
    private Label indirizzoLabel;
    @FXML
    private Label telefonoLabel;
    @FXML
    private Label ruoloLabel;
    @FXML
    private Label matricolaLabel;
    @FXML
    private Label emailLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        UtenteResponsabile utente = metodoDiProva(); //TODO get user by file
        nomeLabel.setText(utente.getNome());
        cognomeLabel.setText(utente.getCognome());
        Indirizzo indirizzo = utente.getIndirizzo();
        indirizzoLabel.setText("Via " + indirizzo.getVia() + " " + indirizzo.getCivico() + ", " + indirizzo.getCap() + " " +
                indirizzo.getLocalita() + " (" + indirizzo.getProvincia() + "), " + indirizzo.getPaese());
        telefonoLabel.setText(utente.getTelefono());
        emailLabel.setText(utente.getEmail());
        matricolaLabel.setText(utente.getMatricola());
        ruoloLabel.setText(utente.getRuolo().toString());
    }


    //TODO to delete
    private UtenteResponsabile metodoDiProva() {
        return new UtenteResponsabile("UR-234567890", "Mario", "Rossi",
                new Indirizzo("Verdi", "46b", "37120", "Verona", "Verona", "Italia"),
                "+39467873773", "mario.rossi@gmail.com", "password12345", "1235742", RuoloResponsabile.ESERCENTE);
    }
}
