package gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import objects.Indirizzo;
import objects.TesseraFedelta;
import objects.UtenteCliente;
import utils.HttpWrapper;
import utils.KeyGenerator;
import utils.Manager;

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
            idFedeltaLabel.setText("ID: " + utente.getTesseraFedelta().getId());
            emissioneFedeltaLabel.setText("Creata il " + Manager.getDateFormat(new Date(utente.getTesseraFedelta().getDataEmissione())));
            puntiFedeltaLabel.setText(utente.getTesseraFedelta().getSaldoPunti() + " punti");

            emissioneFedeltaLabel.visibleProperty().setValue(true);
            puntiFedeltaLabel.visibleProperty().setValue(true);
            emissioneFedeltaLabel.setManaged(true);
            puntiFedeltaLabel.setManaged(true);
            newTesseraButton.visibleProperty().setValue(false);
            newTesseraButton.setManaged(false);
        } else {
            idFedeltaLabel.setText("Nessuna tessera fedeltà associata.");

            emissioneFedeltaLabel.visibleProperty().setValue(false);
            puntiFedeltaLabel.visibleProperty().setValue(false);
            emissioneFedeltaLabel.setManaged(false);
            puntiFedeltaLabel.setManaged(false);
            newTesseraButton.setManaged(true);
        }
    }

    @FXML
    private void aggiungiTesseraFedelta(){
        TesseraFedelta tesseraFedelta;
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
    private void changePassword(){
        ChangepasswordPopup changepasswordPopup = new ChangepasswordPopup();
        try {
            changepasswordPopup.start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void reset(){
        initialize(null, null);
    }


    public void handleModificaProfiloButtonAction() {
        ModificaProfilo modificaProfilo = new ModificaProfilo();
        try {
            modificaProfilo.start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
