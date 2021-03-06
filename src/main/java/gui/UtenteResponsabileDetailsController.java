package gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import objects.Indirizzo;
import objects.UtenteResponsabile;
import utils.HttpWrapper;
import utils.Manager;

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
        HttpWrapper httpWrapper = new HttpWrapper();
        UtenteResponsabile utente = (UtenteResponsabile) httpWrapper.getUserByID(Manager.getUIDFromFile(), UtenteResponsabile.class);

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
    protected void handleModificaProfiloButtonAction() {
        ModificaProfilo modificaProfilo = new ModificaProfilo();
        try {
            modificaProfilo.start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
