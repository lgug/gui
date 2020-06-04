package gui;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
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

    public void initialize(){
        HttpWrapper http = new HttpWrapper();
        UtenteCliente utente = (UtenteCliente) http.getUserByID(Manager.getUIDFromFile(), UtenteCliente.class);

        nome.setText(utente.getNome());
        cognome.setText(utente.getCognome());
        telefono.setText((utente.getTelefono()));
        via.setText(utente.getIndirizzo().getVia());
        civico.setText(utente.getIndirizzo().getCivico());
        cap.setText((utente.getIndirizzo().getCap()));
        citta.setText((utente.getIndirizzo().getPaese()));
        provinicia.setText(utente.getIndirizzo().getProvincia());

    }
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}
