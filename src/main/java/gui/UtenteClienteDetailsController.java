package gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import objects.Indirizzo;
import objects.UtenteCliente;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class UtenteClienteDetailsController implements Initializable {

    @FXML
    private Label nomeLabel;
    @FXML
    private Label cognomeLabel;
    @FXML
    private Label indirizzoLabel;
    @FXML
    private Label telefonoLabel;
    @FXML
    private Label formaPagamentoLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label idFedeltaLabel;
    @FXML
    private Label emissioneFedeltaLabel;
    @FXML
    private Label puntiFedeltaLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        UtenteCliente utente = null; //TODO get user from database with id
        nomeLabel.setText(utente.getNome());
        cognomeLabel.setText(utente.getCognome());
        Indirizzo indirizzo = utente.getIndirizzo();
        indirizzoLabel.setText(indirizzo.getVia() + " " + indirizzo.getCivico() + ", " + indirizzo.getLocalita() + " (" + indirizzo.getProvincia() + "), " + indirizzo.getPaese());
        telefonoLabel.setText(utente.getTelefono());
        emailLabel.setText(utente.getEmail());
        formaPagamentoLabel.setText(utente.getPagamento().toString());

        idFedeltaLabel.setText("ID: " + utente.getTesseraFedelta().getId());
        emissioneFedeltaLabel.setText("Creata il " + new Date(utente.getTesseraFedelta().getDataEmissione()).toString());
        puntiFedeltaLabel.setText(utente.getTesseraFedelta().getSaldoPunti() + " punti");
    }
}
