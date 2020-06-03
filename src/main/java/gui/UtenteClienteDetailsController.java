package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import objects.FormaDiPagamento;
import objects.Indirizzo;
import objects.TesseraFedelta;
import objects.UtenteCliente;
import utils.HttpWrapper;
import utils.KeyGenerator;
import utils.Manager;

import javax.swing.*;
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
    private Label datiPagamentoLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label idFedeltaLabel;
    @FXML
    private Label emissioneFedeltaLabel;
    @FXML
    private Label puntiFedeltaLabel;
    @FXML
    private Button newTesseraButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        HttpWrapper http = new HttpWrapper();
        UtenteCliente utente = (UtenteCliente) http.getUserByID(Manager.getUIDFromFile(), UtenteCliente.class);

        nomeLabel.setText(utente.getNome());
        cognomeLabel.setText(utente.getCognome());
        Indirizzo indirizzo = utente.getIndirizzo();
        indirizzoLabel.setText("Via " + indirizzo.getVia() + " " + indirizzo.getCivico() + ", " + indirizzo.getCap() + " " +
                indirizzo.getLocalita() + " (" + indirizzo.getProvincia() + "), " + indirizzo.getPaese());
        telefonoLabel.setText(utente.getTelefono());
        emailLabel.setText(utente.getEmail());
        formaPagamentoLabel.setText(utente.getPagamento().toString());
        switch (utente.getPagamento()) {
            case CARTA_CREDITO:
                String s1 = utente.getDatiDelPagamento().split(";")[0];
                String s2 = utente.getDatiDelPagamento().split(";")[1];
                datiPagamentoLabel.setText("xxx-" + s1.substring(s1.length() - 4) + ", scade il " + s2);
                break;
            case PAYPAL:
                String s = utente.getDatiDelPagamento().split(";")[0];
                datiPagamentoLabel.setText(s + " (password: ********)");
                break;
            case CONSEGNA:
                datiPagamentoLabel.setText("Nessuno");
        }

        if (utente.getTesseraFedelta() != null) {
            newTesseraButton.visibleProperty().setValue(false);
            idFedeltaLabel.setText("ID: " + utente.getTesseraFedelta().getId());
            emissioneFedeltaLabel.setText("Creata il " + Manager.getDateFormat(new Date(utente.getTesseraFedelta().getDataEmissione())));
            puntiFedeltaLabel.setText(utente.getTesseraFedelta().getSaldoPunti() + " punti");
        } else {
            idFedeltaLabel.setText("Nessuna tessera fedeltà associata.");
            emissioneFedeltaLabel.visibleProperty().setValue(false);
            puntiFedeltaLabel.visibleProperty().setValue(false);
            newTesseraButton.visibleProperty().setValue(true);
        }
    }

    @FXML
    private void aggiungiTesseraFedelta(ActionEvent event){
        TesseraFedelta tesseraFedelta = null;
        tesseraFedelta = new TesseraFedelta();
        tesseraFedelta.setId(KeyGenerator.generateFidelityCardKey());
        tesseraFedelta.setDataEmissione(System.currentTimeMillis());
        tesseraFedelta.setSaldoPunti(0);
        HttpWrapper http = new HttpWrapper();
        String response = http.addTessera(tesseraFedelta,Manager.getUIDFromFile());

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Esito dell'Operazione");
        alert.setHeaderText(null);
        alert.setContentText(response);

        alert.showAndWait();
        reset();

    }
    @FXML
    private void changePassword(ActionEvent event){
        ChangepasswordPopup changepasswordPopup = new ChangepasswordPopup();
        try {
            changepasswordPopup.start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void reset(){
        HttpWrapper http = new HttpWrapper();
        UtenteCliente utente = (UtenteCliente) http.getUserByID(Manager.getUIDFromFile(), UtenteCliente.class);

        nomeLabel.setText(utente.getNome());
        cognomeLabel.setText(utente.getCognome());
        Indirizzo indirizzo = utente.getIndirizzo();
        indirizzoLabel.setText("Via " + indirizzo.getVia() + " " + indirizzo.getCivico() + ", " + indirizzo.getCap() + " " +
                indirizzo.getLocalita() + " (" + indirizzo.getProvincia() + "), " + indirizzo.getPaese());
        telefonoLabel.setText(utente.getTelefono());
        emailLabel.setText(utente.getEmail());
        formaPagamentoLabel.setText(utente.getPagamento().toString());
        switch (utente.getPagamento()) {
            case CARTA_CREDITO:
                String s1 = utente.getDatiDelPagamento().split(";")[0];
                String s2 = utente.getDatiDelPagamento().split(";")[1];
                datiPagamentoLabel.setText("xxx-" + s1.substring(s1.length() - 4) + ", scade il " + s2);
                break;
            case PAYPAL:
                String s = utente.getDatiDelPagamento().split(";")[0];
                datiPagamentoLabel.setText(s + " (password: ********)");
                break;
            case CONSEGNA:
                datiPagamentoLabel.setText("Nessuno");
        }

        if (utente.getTesseraFedelta() != null) {
            emissioneFedeltaLabel.visibleProperty().setValue(true);
            puntiFedeltaLabel.visibleProperty().setValue(true);
            newTesseraButton.visibleProperty().setValue(false);
            idFedeltaLabel.setText("ID: " + utente.getTesseraFedelta().getId());
            emissioneFedeltaLabel.setText("Creata il " + Manager.getDateFormat(new Date(utente.getTesseraFedelta().getDataEmissione())));
            puntiFedeltaLabel.setText(utente.getTesseraFedelta().getSaldoPunti() + " punti");
        } else {
            idFedeltaLabel.setText("Nessuna tessera fedeltà associata.");
            emissioneFedeltaLabel.visibleProperty().setValue(false);
            puntiFedeltaLabel.visibleProperty().setValue(false);
            newTesseraButton.visibleProperty().setValue(true);
        }
    }


    //TODO to delete
    private UtenteCliente metodoDiProva() {
        return new UtenteCliente("UC-234567890", "Mario", "Rossi",
                new Indirizzo("Verdi", "46b", "37120", "Verona", "Verona", "Italia"),
                "+39467873773", "mario.rossi@gmail.com", "password12345", "11234567842;03/22",
                FormaDiPagamento.CARTA_CREDITO, new TesseraFedelta("23456789034567", new Date().getTime(), 46));
    }
}
