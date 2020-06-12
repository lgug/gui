package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import objects.Indirizzo;
import objects.UtenteCliente;
import utils.HttpWrapper;
import utils.Manager;

public class ModificaProfiloController {
    public TextField nome;
    public TextField cognome;
    public TextField telefono;
    public TextField via;
    public TextField civico;
    public TextField cap;
    public TextField citta;
    public TextField provinicia;
    public Button aggiornaDati;
    private Stage primaryStage;
    private UtenteCliente utente;

    public void initialize(){
        HttpWrapper http = new HttpWrapper();
        utente = (UtenteCliente) http.getUserByID(Manager.getUIDFromFile(), UtenteCliente.class);

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
    public void aggiornaDatiHandle(ActionEvent action){
        HttpWrapper http = new HttpWrapper();
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
        http.updateUserInfo(Manager.getUIDFromFile(),utente);
    }
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}
