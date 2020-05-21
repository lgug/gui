package gui;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import objects.*;
import utils.FieldChecker;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Random;

public class SignUpPopup extends Application {

    private static Stage primaryStage;
    private static Stage paymentStage;

    private String nome;
    private String cognome;
    private Indirizzo indirizzo;
    private String telefono;
    private String email;
    private String password;
    private FormaDiPagamento formaDiPagamento;
    private TesseraFedelta tesseraFedelta;
    private String datiDiPagamento;
    private RuoloResponsabile ruoloResponsabile;
    private String matricola;

    @FXML
    private TextField nomeField;
    @FXML
    private TextField cognomeField;
    @FXML
    private TextField telefonoField;
    @FXML
    private TextField viaField;
    @FXML
    private TextField civicoField;
    @FXML
    private TextField capField;
    @FXML
    private TextField cittaField;
    @FXML
    private TextField provinciaField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField passwordField;
    @FXML
    private Button continuaButton;
    @FXML
    private ToggleButton utenteClienteButton;
    @FXML
    private ToggleButton utenteResponsabileButton;
    @FXML
    private BorderPane tipoUtentePane;
    @FXML
    private Button confermaButton;
    @FXML
    private RadioButton siCartaFedelta;
    @FXML
    private RadioButton noCartaFedelta;
    @FXML
    private ChoiceBox<FormaDiPagamento> formaDiPagamentoBox;
    @FXML
    private Button datiPagamentoButton;
    @FXML
    private TextField matricolaField;
    @FXML
    private ChoiceBox<RuoloResponsabile> ruoloResponsabileBox;
    @FXML
    private Label label1;
    @FXML
    private Label label2;
    @FXML
    private TextField textField1;
    @FXML
    private TextField textField2;
    @FXML
    private Button confermaDatiButton;
    @FXML
    private Button annullaDatiButton;
    @FXML
    private ImageView iconDatiPagamento;

    @FXML
    protected void handleContinuaButtonAction(MouseEvent event) {
        if (FieldChecker.validateNonEmptyString(nomeField.getText())) {
            nome = nomeField.getText();
        } else {
            //TODO error message: Il campo e' obbligatorio
            return;
        }
        if (FieldChecker.validateNonEmptyString(cognomeField.getText())) {
            cognome = cognomeField.getText();
        } else {
            //TODO error message: Il campo e' obbligatorio
            return;
        }
        if (FieldChecker.validateNonEmptyString(telefonoField.getText())) {
            telefono = telefonoField.getText();
        } else {
            //TODO error message: Il campo e' obbligatorio
            return;
        }
        if (FieldChecker.validateNonEmptyString(viaField.getText())) {
            Indirizzo ind = new Indirizzo();
            ind.setVia(viaField.getText());
            if (FieldChecker.validateNonEmptyString(civicoField.getText())) {
                ind.setCivico(civicoField.getText());
                if (FieldChecker.validateNonEmptyString(capField.getText())) {
                    ind.setCap(capField.getText());
                    if (FieldChecker.validateNonEmptyString(cittaField.getText())) {
                        ind.setLocalita(cittaField.getText());
                        if (FieldChecker.validateNonEmptyString(provinciaField.getText())) {
                            ind.setProvincia(provinciaField.getText());
                            ind.setPaese("Italia");
                            indirizzo = ind;
                        } else {
                            //TODO error message: Il campo e' obbligatorio
                            return;
                        }
                    } else {
                        //TODO error message: Il campo e' obbligatorio
                        return;
                    }
                } else {
                    //TODO error message: Il campo e' obbligatorio
                    return;
                }
            } else {
                //TODO error message: Il campo e' obbligatorio
                return;
            }
        } else {
            //TODO error message: Il campo e' obbligatorio
            return;
        }
        if (FieldChecker.validateEmail(emailField.getText())) {
            email = emailField.getText();
        } else {
            //TODO error message: email non valida
            return;
        }
        if (FieldChecker.validatePassword(passwordField.getText())) {
            password = passwordField.getText();
        } else {
            //TODO error message: la password deve contenere almeno 8 caratteri tra cui
            // una lettera maiuscola, minuscola, un numero e niente spazi.
            return;
        }
        FXMLLoader fxmlLoader = new FXMLLoader(ClassLoader.getSystemClassLoader().getResource("signup_popup_2.fxml"));
        try {
            AnchorPane anchorPane = fxmlLoader.load();
            Scene secondScene = new Scene(anchorPane);
            primaryStage.setScene(secondScene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void handleUtenteClienteToggleButtonEvent(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ClassLoader.getSystemClassLoader().getResource("utente_cliente_layout.fxml"));
        tipoUtentePane.setCenter(fxmlLoader.<VBox>load());
        formaDiPagamentoBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            confermaButton.setDisable((newValue != FormaDiPagamento.CONSEGNA));
            datiPagamentoButton.setDisable(newValue != FormaDiPagamento.CARTA_CREDITO && newValue != FormaDiPagamento.PAYPAL);
            formaDiPagamento = newValue;
            datiDiPagamento = null;
        });
    }

    @FXML
    protected void handleUtenteResponsabileToggleButtonEvent(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ClassLoader.getSystemClassLoader().getResource("utente_responsabile_layout.fxml"));
        tipoUtentePane.setCenter(fxmlLoader.<VBox>load());
        confermaButton.setDisable(false);
        ruoloResponsabileBox.getItems().addAll(RuoloResponsabile.values());
        ruoloResponsabileBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
                ruoloResponsabile = newValue);
    }

    @FXML
    protected void handleInserisciDatiPagamentoButtonEvent(MouseEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(ClassLoader.getSystemClassLoader().getResource("dati_pagamento_popup.fxml"));
        try {
            AnchorPane anchorPane = fxmlLoader.load();
            InputStream is = null;
            switch (formaDiPagamento) {
                case PAYPAL:
                    is = ClassLoader.getSystemClassLoader().getResourceAsStream("paypal_logo.jpg");
                    label1.setText("Username:");
                    label2.setText("Password:");
                    break;
                case CARTA_CREDITO:
                    is = ClassLoader.getSystemClassLoader().getResourceAsStream("credit_card.jpg");
                    label1.setText("N. della carta:");
                    label2.setText("Data di scadenza:");
                    break;
            }
            if (is != null) {
                iconDatiPagamento.setImage(new Image(is));
            }
            Scene paymentScene = new Scene(anchorPane);
            paymentStage = new Stage();
            paymentStage.setTitle("Forma di pagamento");
            paymentStage.setScene(paymentScene);
            paymentStage.initModality(Modality.WINDOW_MODAL);
            paymentStage.initOwner(primaryStage);
            paymentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void handleConfermaDatiButtonEvent(MouseEvent event) {
        //TODO to verify data
        datiDiPagamento = textField1.getText() + ";" + textField2.getText();
        confermaButton.setDisable(false);
        if (paymentStage != null) paymentStage.close();
    }

    @FXML
    protected void handleAnnullaDatiButtonEvent(MouseEvent event) {
        if (paymentStage != null) paymentStage.close();
    }

    @FXML protected void handleConfermaButtonAction(MouseEvent event) {
        Random random = new Random();
        String id = String.valueOf(random.nextLong());
        if (utenteClienteButton.isSelected()) {
            TesseraFedelta tesseraFedelta = null;
            if (siCartaFedelta != null && siCartaFedelta.isSelected()) {
                tesseraFedelta = new TesseraFedelta();
                tesseraFedelta.setId(String.valueOf(random.nextLong()));
                tesseraFedelta.setDataEmissione(System.currentTimeMillis());
                tesseraFedelta.setSaldoPunti(0);
            }
            UtenteCliente utenteCliente = new UtenteCliente();
            utenteCliente.setId(id);
            utenteCliente.setNome(nome);
            utenteCliente.setCognome(cognome);
            utenteCliente.setIndirizzo(indirizzo);
            utenteCliente.setTelefono(telefono);
            utenteCliente.setEmail(email);
            utenteCliente.setPassword(password);
            utenteCliente.setDatiDelPagamento(datiDiPagamento);
            utenteCliente.setPagamento(formaDiPagamento);
            utenteCliente.setTesseraFedelta(tesseraFedelta);
            //TODO to send to Flask
        } else if (utenteResponsabileButton.isSelected()) {
            matricola = matricolaField.getText();
            ruoloResponsabile = ruoloResponsabileBox.getValue();
            UtenteResponsabile utenteResponsabile = new UtenteResponsabile();
            utenteResponsabile.setId(id);
            utenteResponsabile.setNome(nome);
            utenteResponsabile.setCognome(cognome);
            utenteResponsabile.setIndirizzo(indirizzo);
            utenteResponsabile.setTelefono(telefono);
            utenteResponsabile.setEmail(email);
            utenteResponsabile.setPassword(password);
            utenteResponsabile.setRuolo(ruoloResponsabile);
            utenteResponsabile.setMatricola(matricola);
            //TODO to send to Flask
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        SignUpPopup.primaryStage = primaryStage;
        FXMLLoader fxmlLoader = new FXMLLoader(ClassLoader.getSystemClassLoader().getResource("signup_popup.fxml"));
        AnchorPane anchorPane = fxmlLoader.load();
        Scene mainScene = new Scene(anchorPane);
        SignUpPopup.primaryStage.setTitle("Registrati");
        SignUpPopup.primaryStage.setScene(mainScene);
        SignUpPopup.primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
