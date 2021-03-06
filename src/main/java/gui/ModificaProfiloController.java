package gui;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import objects.Indirizzo;
import objects.Utente;
import objects.UtenteCliente;
import objects.UtenteResponsabile;
import utils.FieldChecker;
import utils.HttpWrapper;
import utils.Manager;

import java.net.URL;
import java.util.ResourceBundle;

public class ModificaProfiloController implements Initializable {

    private Class<? extends Utente> userClass;
    private Utente utente;
    private Stage primaryStage;

    @FXML
    private TextField nome;
    @FXML
    private TextField cognome;
    @FXML
    private TextField telefono;
    @FXML
    private TextField via;
    @FXML
    private TextField civico;
    @FXML
    private TextField cap;
    @FXML
    private TextField citta;
    @FXML
    private TextField provinicia;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void fillFields() {
        HttpWrapper http = new HttpWrapper();
        if (userClass.equals(UtenteCliente.class)) {
            utente = http.getUserByID(Manager.getUIDFromFile(), UtenteCliente.class);
        } else {
            utente = http.getUserByID(Manager.getUIDFromFile(), UtenteResponsabile.class);
        }

        nome.setText(utente.getNome());
        cognome.setText(utente.getCognome());
        telefono.setText((utente.getTelefono()));
        via.setText(utente.getIndirizzo().getVia());
        civico.setText(utente.getIndirizzo().getCivico());
        cap.setText((utente.getIndirizzo().getCap()));
        citta.setText((utente.getIndirizzo().getLocalita()));
        provinicia.setText(utente.getIndirizzo().getProvincia());
    }

    @FXML
    public void aggiornaDatiHandle(){
        HttpWrapper http = new HttpWrapper();
        if (FieldChecker.validateNonEmptyString(nome.getText()) &&
                FieldChecker.validateNonEmptyString(cognome.getText()) &&
                FieldChecker.validateNonEmptyString(telefono.getText()) &&
                FieldChecker.validateNonEmptyString(cap.getText()) &&
                FieldChecker.validateNonEmptyString(civico.getText()) &&
                FieldChecker.validateNonEmptyString(via.getText()) &&
                FieldChecker.validateNonEmptyString(provinicia.getText()) &&
                FieldChecker.validateNonEmptyString(citta.getText())) {
            utente.setNome(nome.getText());
            utente.setCognome(cognome.getText());
            utente.setTelefono(telefono.getText());
            Indirizzo indirizzo = new Indirizzo();
            indirizzo.setCap(cap.getText());
            indirizzo.setCivico(civico.getText());
            indirizzo.setVia(via.getText());
            indirizzo.setProvincia(provinicia.getText());
            indirizzo.setLocalita(citta.getText());
            utente.setIndirizzo(indirizzo);
            http.updateUserInfo(Manager.getUIDFromFile(), utente);
            primaryStage.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING,
                    "I campi non possono essere lasciati vuoti", ButtonType.OK);
            alert.show();
        }
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setUserClass(Class<? extends Utente> userClass) {
        this.userClass = userClass;
    }

}
