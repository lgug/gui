package gui;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import objects.*;
import utils.FieldChecker;
import utils.HttpWrapper;
import utils.KeyGenerator;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignUpPopup extends Application implements Initializable {

    private static Stage primaryStage;
    private static Stage paymentStage;
    private Parent root;
    private Pane utenteClientePane;
    private Pane utenteResponsabilePane;
    private UtenteClienteController utenteClienteController;
    private UtenteResponsabileController utenteResponsabileController;
    private PaymentDataController paymentDataController;

    private static String nome;
    private static String cognome;
    private static Indirizzo indirizzo;
    private static String telefono;
    private static String email;
    private static String password;

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
    protected void handleContinuaButtonAction(MouseEvent event) {

        if (FieldChecker.validateNonEmptyString(nomeField.getText())) {
            nome = nomeField.getText();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION,
                    String.format(FieldChecker.emptyFieldMsg, "NOME"), ButtonType.OK);
            alert.show();
            return;
        }
        if (FieldChecker.validateNonEmptyString(cognomeField.getText())) {
            cognome = cognomeField.getText();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION,
                    String.format(FieldChecker.emptyFieldMsg, "COGNOME"), ButtonType.OK);
            alert.show();
            return;
        }
        if (FieldChecker.validateNonEmptyString(telefonoField.getText())) {
            telefono = telefonoField.getText();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION,
                    String.format(FieldChecker.emptyFieldMsg, "TELEFONO"), ButtonType.OK);
            alert.show();
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
                            Alert alert = new Alert(Alert.AlertType.INFORMATION,
                                    String.format(FieldChecker.emptyFieldMsg, "PROVINCIA"), ButtonType.OK);
                            alert.show();
                            return;
                        }
                    } else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION,
                                String.format(FieldChecker.emptyFieldMsg, "CITTÀ"), ButtonType.OK);
                        alert.show();
                        return;
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION,
                            String.format(FieldChecker.emptyFieldMsg, "CAP"), ButtonType.OK);
                    alert.show();
                    return;
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION,
                        String.format(FieldChecker.emptyFieldMsg, "CIVICO"), ButtonType.OK);
                alert.show();
                return;
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION,
                    String.format(FieldChecker.emptyFieldMsg, "VIA"), ButtonType.OK);
            alert.show();
            return;
        }
        if (FieldChecker.validateEmail(emailField.getText())) {
            email = emailField.getText();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION,
                    "L'email che hai inserito non è in un formato valido!", ButtonType.OK);
            alert.show();
            return;
        }
        if (FieldChecker.validatePassword(passwordField.getText())) {
            password = passwordField.getText();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, FieldChecker.passwordRequirements, ButtonType.OK);
            alert.show();
            return;
        }
        FXMLLoader fxmlLoader = new FXMLLoader(ClassLoader.getSystemClassLoader().getResource("signup_popup_2.fxml"));
        try {
            root = fxmlLoader.load();
            primaryStage.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void handleUtenteClienteToggleButtonEvent(MouseEvent event) throws IOException {
        tipoUtentePane.setCenter(utenteClientePane);
        confermaButton.setDisable(utenteClienteController.getFormaDiPagamento() == null ||
                (!utenteClienteController.getFormaDiPagamento().equals(FormaDiPagamento.CONSEGNA) &&
                paymentDataController.getPaymentString() == null));
    }

    @FXML
    protected void handleUtenteResponsabileToggleButtonEvent(MouseEvent event) throws IOException {
        tipoUtentePane.setCenter(utenteResponsabilePane);
        confermaButton.setDisable(false);
    }

    @FXML protected void handleConfermaButtonAction(MouseEvent event) {
        Utente utente;
        if (utenteClienteButton.isSelected()) {
            TesseraFedelta tesseraFedelta = null;
            if (utenteClienteController.isCartaFedelta() != null && utenteClienteController.isCartaFedelta()) {
                tesseraFedelta = new TesseraFedelta();
                tesseraFedelta.setId(KeyGenerator.generateFidelityCardKey());
                tesseraFedelta.setDataEmissione(System.currentTimeMillis());
                tesseraFedelta.setSaldoPunti(0);
            }
            UtenteCliente utenteCliente = new UtenteCliente();
            utenteCliente.setId(KeyGenerator.generateUserKey(UtenteCliente.class));
            utenteCliente.setNome(nome);
            utenteCliente.setCognome(cognome);
            utenteCliente.setIndirizzo(indirizzo);
            utenteCliente.setTelefono(telefono);
            utenteCliente.setEmail(email);
            utenteCliente.setPassword(password);
            utenteCliente.setDatiDelPagamento(paymentDataController.getPaymentString());
            utenteCliente.setPagamento(utenteClienteController.getFormaDiPagamento());
            utenteCliente.setTesseraFedelta(tesseraFedelta);
            utente = utenteCliente;
        } else if (utenteResponsabileButton.isSelected()) {
            if (FieldChecker.validateNonEmptyString(utenteResponsabileController.getMatricola())) {
                UtenteResponsabile utenteResponsabile = new UtenteResponsabile();
                utenteResponsabile.setId(KeyGenerator.generateUserKey(UtenteResponsabile.class));
                utenteResponsabile.setNome(nome);
                utenteResponsabile.setCognome(cognome);
                utenteResponsabile.setIndirizzo(indirizzo);
                utenteResponsabile.setTelefono(telefono);
                utenteResponsabile.setEmail(email);
                utenteResponsabile.setPassword(password);
                utenteResponsabile.setRuolo(utenteResponsabileController.getRuoloResponsabile());
                utenteResponsabile.setMatricola(utenteResponsabileController.getMatricola());
                utente = utenteResponsabile;
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION,
                        String.format(FieldChecker.emptyFieldMsg, "MATRICOLA"), ButtonType.OK);
                alert.show();
                return;
            }
        } else return;

        HttpWrapper httpWrapper = new HttpWrapper();
        boolean operationResult = httpWrapper.sendNewUser(utente);
        if (operationResult)
            primaryStage.close();
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "Errore durante la creazione dell'utente!", ButtonType.OK);
            alert.show();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        SignUpPopup.primaryStage = primaryStage;
        FXMLLoader fxmlLoader = new FXMLLoader(ClassLoader.getSystemClassLoader().getResource("signup_popup.fxml"));
        root = fxmlLoader.load();
        Scene mainScene = new Scene(root);
        SignUpPopup.primaryStage.setTitle("Registrati");
        SignUpPopup.primaryStage.setScene(mainScene);
        SignUpPopup.primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FXMLLoader fxmlLoader1 = new FXMLLoader(ClassLoader.getSystemClassLoader().getResource("utente_cliente_layout.fxml"));
        FXMLLoader fxmlLoader2 = new FXMLLoader(ClassLoader.getSystemClassLoader().getResource("utente_responsabile_layout.fxml"));
        FXMLLoader fxmlLoader3 = new FXMLLoader(ClassLoader.getSystemClassLoader().getResource("dati_pagamento_popup.fxml"));
        try {
            utenteClientePane = fxmlLoader1.load();
            utenteResponsabilePane = fxmlLoader2.load();
            utenteClienteController = fxmlLoader1.getController();
            utenteResponsabileController = fxmlLoader2.getController();

            utenteClienteController.setConfermaButton(confermaButton);
            utenteClienteController.setPrimaryStage(primaryStage);

            utenteClienteController.setPaymentDataPane(fxmlLoader3.load());
            paymentDataController = fxmlLoader3.getController();
            paymentDataController.setConfermaButton(confermaButton);
            utenteClienteController.setPaymentDataController(paymentDataController);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
