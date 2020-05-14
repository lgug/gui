package gui;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import java.io.InputStream;
import java.util.Date;
import java.util.Random;

public class SignUpPopup extends Application {

    private EventHandler<MouseEvent> continueEvent;
    private Stage paymentStage;
    private Stage primaryStage;
    private Button createUserButton;

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

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        Scene mainScene = getPersonalDataPane(this.primaryStage);
        this.primaryStage.setTitle("Registrati");
        this.primaryStage.setScene(mainScene);
        this.primaryStage.show();
    }

    private Scene getPersonalDataPane(Stage primaryStage) {
        // Layout declarations
        BorderPane mainBorderPane = new BorderPane();
        VBox vBox = new VBox();
        VBox titleVBox = new VBox();
        VBox buttonVBox = new VBox();
        GridPane address1GridPane = new GridPane();
        GridPane address2GridPane = new GridPane();


        // Main border pane
        mainBorderPane.setPrefHeight(500);
        mainBorderPane.setPrefWidth(600);


        // Title
        titleVBox.setAlignment(Pos.CENTER);
        titleVBox.setPadding(new Insets(10));
        Text title = new Text();
        title.setText("REGISTRATI");
        title.setStyle("-fx-font-weight: bold;");
        title.setFont(new Font("Verdana Bold", 25));


        // VBox's nodes
        vBox.setPadding(new Insets(30, 30, 30, 30));
        vBox.setSpacing(20);

        Label nameLabel = new Label();
        nameLabel.setText("Nome:");
        nameLabel.setStyle("-fx-font-weight: bold");

        Label surnameLabel = new Label();
        surnameLabel.setText("Cognome:");
        surnameLabel.setStyle("-fx-font-weight: bold");

        Label addressLabel = new Label();
        addressLabel.setText("Indirizzo:");
        addressLabel.setStyle("-fx-font-weight: bold");

        Label streetLabel = new Label();
        streetLabel.setText("Via:");
        streetLabel.setStyle("-fx-font-weight: bold");

        Label civicLabel = new Label();
        civicLabel.setText("N. civico:");
        civicLabel.setStyle("-fx-font-weight: bold");

        Label capLabel = new Label();
        capLabel.setText("CAP:");
        capLabel.setStyle("-fx-font-weight: bold");

        Label cityLabel = new Label();
        cityLabel.setText("Città:");
        cityLabel.setStyle("-fx-font-weight: bold");

        Label provinceLabel = new Label();
        provinceLabel.setText("Provincia:");
        provinceLabel.setStyle("-fx-font-weight: bold");

        Label phoneLabel = new Label();
        phoneLabel.setText("Telefono:");
        phoneLabel.setStyle("-fx-font-weight: bold");

        Label emailLabel = new Label();
        emailLabel.setText("Email:");
        emailLabel.setStyle("-fx-font-weight: bold");

        Label passwordLabel = new Label();
        passwordLabel.setText("Password:");
        passwordLabel.setStyle("-fx-font-weight: bold");

        TextField nameTextField = new TextField();
        nameTextField.setPromptText("Inserisci il tuo nome");
        nameTextField.setText("Mario"); //TODO only for test: to remove!!!

        TextField surnameTextField = new TextField();
        surnameTextField.setPromptText("Inserisci il tuo cognome");
        surnameTextField.setText("Rossi"); //TODO only for test: to remove!!!

        TextField streetTextField = new TextField();
        streetTextField.setPromptText("Inserisci la via");
        streetTextField.setText("Verdi"); //TODO only for test: to remove!!!

        TextField civicTextField = new TextField();
        civicTextField.setPromptText("Num.");
        civicTextField.setText("9065"); //TODO only for test: to remove!!!

        TextField capTextField = new TextField();
        capTextField.setPromptText("CAP");
        capTextField.setText("37120"); //TODO only for test: to remove!!!

        TextField cityTextField = new TextField();
        cityTextField.setPromptText("Inserisci la città");
        cityTextField.setText("Verona"); //TODO only for test: to remove!!!

        TextField provinceTextField = new TextField();
        provinceTextField.setPromptText("Prov.");
        provinceTextField.setText("Verona"); //TODO only for test: to remove!!!

        TextField phoneTextField = new TextField();
        phoneTextField.setPromptText("Inserisci il tuo telefono");
        phoneTextField.setText("+39 123456789"); //TODO only for test: to remove!!!

        TextField emailTextField = new TextField();
        emailTextField.setPromptText("Inserisci la tua email");
        emailTextField.setText("mariorossi@mail.com"); //TODO only for test: to remove!!!

        TextField passwordTextField = new TextField();
        passwordTextField.setPromptText("Inserisci una password");
        passwordTextField.setText("PaSSw0rd12345"); //TODO only for test: to remove!!!

        buttonVBox.setAlignment(Pos.CENTER);
        Button continueButton = new Button();
        continueButton.setText("Continue");
        continueButton.setPadding(new Insets(5));

        address1GridPane.setHgap(20);
        address1GridPane.setVgap(4);
        address1GridPane.setScaleShape(true);
        address2GridPane.setHgap(20);
        address2GridPane.setVgap(4);
        address2GridPane.setScaleShape(true);

        continueEvent = event -> {
            if (FieldChecker.validateNonEmptyString(nameTextField.getText())) {
                nome = nameTextField.getText();
            } else {
                //TODO error message: Il campo e' obbligatorio
                return;
            }
            if (FieldChecker.validateNonEmptyString(surnameTextField.getText())) {
                cognome = surnameTextField.getText();
            } else {
                //TODO error message: Il campo e' obbligatorio
                return;
            }
            if (FieldChecker.validateNonEmptyString(streetTextField.getText())) {
                Indirizzo ind = new Indirizzo();
                ind.setVia(streetTextField.getText());
                if (FieldChecker.validateNonEmptyString(civicTextField.getText())) {
                    ind.setCivico(civicTextField.getText());
                    if (FieldChecker.validateNonEmptyString(capTextField.getText())) {
                        ind.setCap(capTextField.getText());
                        if (FieldChecker.validateNonEmptyString(cityTextField.getText())) {
                            ind.setLocalita(cityTextField.getText());
                            if (FieldChecker.validateNonEmptyString(provinceTextField.getText())) {
                                ind.setProvincia(provinceTextField.getText());
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
            if (FieldChecker.validateNonEmptyString(phoneTextField.getText())) {
                telefono = phoneTextField.getText();
            } else {
                //TODO error message: Il campo e' obbligatorio
                return;
            }
            if (FieldChecker.validateEmail(emailTextField.getText())) {
                email = emailTextField.getText();
            } else {
                //TODO error message: email non valida
                return;
            }
            if (FieldChecker.validatePassword(passwordTextField.getText())) {
                password = passwordTextField.getText();
            } else {
                //TODO error message: la password deve contenere almeno 8 caratteri tra cui
                // una lettera maiuscola, minuscola, un numero e niente spazi.
                return;
            }
            Scene secondScene = getAdvancedDataPane(primaryStage);
            primaryStage.setScene(secondScene);
            primaryStage.show();
        };
        continueButton.addEventHandler(MouseEvent.MOUSE_CLICKED, continueEvent);


        // Layout embedding
        address1GridPane.add(streetLabel, 0, 0, 3, 1);
        address1GridPane.add(civicLabel, 3, 0, 1, 1);
        address1GridPane.add(streetTextField, 0, 1, 3, 1);
        address1GridPane.add(civicTextField, 3, 1, 1, 1);
        address2GridPane.add(capLabel, 0, 0, 1, 1);
        address2GridPane.add(cityLabel, 1, 0, 3, 1);
        address2GridPane.add(provinceLabel, 5, 0, 1, 1);
        address2GridPane.add(capTextField, 0, 1, 1, 1);
        address2GridPane.add(cityTextField, 1, 1, 3, 1);
        address2GridPane.add(provinceTextField, 5, 1, 1, 1);
        titleVBox.getChildren().addAll(title);
        buttonVBox.getChildren().addAll(continueButton);
        vBox.getChildren().addAll(
                new VBox(nameLabel, nameTextField),
                new VBox(surnameLabel, surnameTextField),
                new VBox(phoneLabel, phoneTextField),
                new VBox(5, addressLabel, address1GridPane, address2GridPane),
                new VBox(emailLabel, emailTextField),
                new VBox(passwordLabel, passwordTextField),
                buttonVBox
        );
        mainBorderPane.setTop(titleVBox);
        mainBorderPane.setCenter(vBox);
        return new Scene(mainBorderPane);
    }


    private Scene getAdvancedDataPane(Stage primaryStage) {
        //Layout declarations
        BorderPane mainBorderPane = new BorderPane();
        VBox clientVBox = new VBox();
        VBox employeeVBox = new VBox();
        VBox titleVBox = new VBox();
        VBox contentVBox = new VBox();
        HBox toggleHBox = new HBox();
        HBox paymentHBox = new HBox();
        HBox buttonHBox = new HBox();

        ToggleGroup toggleGroup = new ToggleGroup();
        ToggleButton clientToggleButton = new ToggleButton();
        ToggleButton employeeToggleButton = new ToggleButton();

        //Main border pane
        mainBorderPane.setPrefWidth(600);
        mainBorderPane.setPrefHeight(500);

        //Title
        titleVBox.setAlignment(Pos.CENTER);
        titleVBox.setPadding(new Insets(10));
        Text title = new Text();
        title.setText("Come vuoi registrarti?");
        title.setStyle("-fx-font-weight: bold;");
        title.setFont(new Font("Verdana Bold", 25));

        // Toggle button
        clientToggleButton.setToggleGroup(toggleGroup);
        clientToggleButton.setText("REGISTRAMI COME\nCLIENTE");
        clientToggleButton.setTextAlignment(TextAlignment.CENTER);
        clientToggleButton.setPadding(new Insets(15, 30, 15, 30));
        clientToggleButton.setSelected(true);
        clientToggleButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                createUserButton.setDisable(formaDiPagamento != FormaDiPagamento.CONSEGNA && datiDiPagamento == null);
                contentVBox.getChildren().remove(employeeVBox);
                contentVBox.getChildren().add(clientVBox);
            }
        });
        employeeToggleButton.setToggleGroup(toggleGroup);
        employeeToggleButton.setText("REGISTRAMI COME\nRESPONSABILE");
        employeeToggleButton.setTextAlignment(TextAlignment.CENTER);
        employeeToggleButton.setPadding(new Insets(15, 30, 15, 30));
        employeeToggleButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                createUserButton.setDisable(false);
                contentVBox.getChildren().remove(clientVBox);
                contentVBox.getChildren().add(employeeVBox);
            }
        });
        toggleHBox.setAlignment(Pos.CENTER);
        toggleHBox.getChildren().addAll(clientToggleButton, employeeToggleButton);


        // Client layout
        Label fidelityCardLabel = new Label();
        fidelityCardLabel.setText("Tessera Fedeltà:");
        fidelityCardLabel.setStyle("-fx-font-weight: bold");
        fidelityCardLabel.setTextAlignment(TextAlignment.LEFT);

        ToggleGroup radioButtonGroup = new ToggleGroup();
        RadioButton yesFCRadioButton = new RadioButton();
        RadioButton noFCRadioButton = new RadioButton();
        yesFCRadioButton.setText("Si, desidero creare una tessera Fedeltà associata al mio account.");
        yesFCRadioButton.setToggleGroup(radioButtonGroup);
        yesFCRadioButton.setSelected(true);
        noFCRadioButton.setText("No, preferisco crearla in un secondo momento.");
        noFCRadioButton.setToggleGroup(radioButtonGroup);

        Label paymentFormLabel = new Label();
        paymentFormLabel.setText("Forma di pagamento:");
        paymentFormLabel.setStyle("-fx-font-weight: bold");
        paymentFormLabel.setTextAlignment(TextAlignment.LEFT);
        Label paymentFieldDescriptionLabel = new Label();
        paymentFieldDescriptionLabel.setText("Seleziona il metodo che preferisci per pagare la tua spesa:");
        paymentFieldDescriptionLabel.setAlignment(Pos.CENTER_LEFT);
        ChoiceBox<FormaDiPagamento> paymentChoiceBox = new ChoiceBox<>();
        paymentChoiceBox.getItems().addAll(FormaDiPagamento.values());

        paymentHBox.setAlignment(Pos.CENTER);
        Button paymentDataButton = new Button("Inserisci dati di pagamento");
        paymentDataButton.setDisable(true);
        paymentDataButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            Scene paymentScene = getPaymentScene(paymentChoiceBox.getValue());
            paymentStage = new Stage();
            paymentStage.setTitle("Forma di pagamento");
            paymentStage.setScene(paymentScene);
            paymentStage.initModality(Modality.WINDOW_MODAL);
            paymentStage.initOwner(primaryStage);
            paymentStage.show();
        });
        paymentChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            createUserButton.setDisable(newValue != FormaDiPagamento.CONSEGNA);
            paymentDataButton.setDisable(newValue != FormaDiPagamento.CARTA_CREDITO && newValue != FormaDiPagamento.PAYPAL);
            formaDiPagamento = newValue;
            datiDiPagamento = null;
        });
        clientVBox.setPadding(new Insets(30));
        clientVBox.setSpacing(10);


        // Employee layout
        Label matricolaLabel = new Label();
        matricolaLabel.setText("Matricola:");
        matricolaLabel.setStyle("-fx-font-weight: bold");

        Label roleLabel = new Label();
        roleLabel.setText("Ruolo:");
        roleLabel.setStyle("-fx-font-weight: bold");
        Label roleDescriptionLabel = new Label();
        roleDescriptionLabel.setText("Seleziona il tuo ruolo:");

        TextField matricolaTextField = new TextField();
        matricolaTextField.setPromptText("Inserisci il tuo numero di matricola");
        ChoiceBox<RuoloResponsabile> roleChoiceBox = new ChoiceBox<>();
        roleChoiceBox.getItems().addAll(RuoloResponsabile.values());
        roleChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
                ruoloResponsabile = newValue);

        employeeVBox.setPadding(new Insets(30));
        employeeVBox.setSpacing(10);

        // Creation of user
        createUserButton = new Button("Crea nuovo utente");
        createUserButton.setDisable(true);
        createUserButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (clientToggleButton.isSelected()) {
                TesseraFedelta tesseraFedelta = null;
                if (yesFCRadioButton.isSelected()) {
                    Random random = new Random();
                    tesseraFedelta = new TesseraFedelta();
                    tesseraFedelta.setId(String.valueOf(random.nextLong()));
                    tesseraFedelta.setDataEmissione(System.currentTimeMillis());
                    tesseraFedelta.setSaldoPunti(0);
                }
                UtenteCliente utenteCliente = new UtenteCliente();
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
            } else if (employeeToggleButton.isSelected()) {
                matricola = matricolaTextField.getText();
                ruoloResponsabile = roleChoiceBox.getValue();
                UtenteResponsabile utenteResponsabile = new UtenteResponsabile();
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
        });
        buttonHBox.setSpacing(40);
        buttonHBox.setPadding(new Insets(20));
        buttonHBox.setAlignment(Pos.CENTER);

        // Layout embedding
        paymentHBox.getChildren().addAll(paymentDataButton);
        buttonHBox.getChildren().addAll(createUserButton);
        clientVBox.getChildren().addAll(fidelityCardLabel,
                new VBox(5, yesFCRadioButton, noFCRadioButton), paymentFormLabel,
                new HBox(10, paymentFieldDescriptionLabel, paymentChoiceBox),
                paymentHBox);
        employeeVBox.getChildren().addAll(
                new VBox(matricolaLabel, matricolaTextField),
                new VBox(roleLabel, new HBox(10, roleDescriptionLabel, roleChoiceBox)));
        titleVBox.getChildren().addAll(title);
        contentVBox.getChildren().addAll(toggleHBox, clientVBox);
        mainBorderPane.setTop(titleVBox);
        mainBorderPane.setCenter(contentVBox);
        mainBorderPane.setBottom(buttonHBox);

        return new Scene(mainBorderPane);
    }

    private Scene getPaymentScene(FormaDiPagamento paymentChosen) {
        // Layout declarations
        BorderPane borderPane = new BorderPane();
        ImageView paymentImageView = new ImageView();
        VBox contentVBox = new VBox();
        HBox titleHBox = new HBox();
        HBox buttonsHBox = new HBox();
        GridPane gridPane = new GridPane();

        // Main border pane
        borderPane.setPadding(new Insets(10));
        borderPane.setPrefHeight(200);
        borderPane.setPrefWidth(450);
        contentVBox.setPadding(new Insets(30));
        contentVBox.setAlignment(Pos.CENTER);

        // Title
        titleHBox.setAlignment(Pos.CENTER);
        Text titleText = new Text("Inserisci i dati di pagamento");
        titleText.setStyle("-fx-font-weight: bold;");
        titleText.setFont(new Font("Verdana Bold", 20));

        // Content's nodes
        Label label1 = new Label();
        Label label2 = new Label();

        TextField textField1 = new TextField();
        TextField textField2 = new TextField();

        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(20, 0, 0, 0));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        InputStream is = null;
        switch (paymentChosen) {
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
            Image image = new Image(is);
            paymentImageView.setImage(image);
            paymentImageView.setPreserveRatio(true);
            paymentImageView.setFitHeight(150);
        }

        // Buttons
        buttonsHBox.setPadding(new Insets(50, 10, 10, 10));
        buttonsHBox.setAlignment(Pos.CENTER);
        buttonsHBox.setSpacing(30);
        Button okButton = new Button("Conferma");
        okButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            //TODO to verify data
            datiDiPagamento = textField1.getText() + ";" + textField2.getText();
            formaDiPagamento = paymentChosen;
            createUserButton.setDisable(false);
            if (paymentStage != null) paymentStage.close();
        });
        Button cancelButton = new Button("Annulla");
        cancelButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (paymentStage != null) paymentStage.close();
        });

        //Layout embedding
        gridPane.add(label1, 0, 0);
        gridPane.add(textField1, 1, 0);
        gridPane.add(label2, 0, 1);
        gridPane.add(textField2, 1, 1);
        titleHBox.getChildren().addAll(titleText);
        buttonsHBox.getChildren().addAll(cancelButton, okButton);
        contentVBox.getChildren().addAll(paymentImageView, gridPane, buttonsHBox);
        borderPane.setTop(titleHBox);
        borderPane.setCenter(contentVBox);

        return new Scene(borderPane);
    }

}
