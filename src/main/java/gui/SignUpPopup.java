package gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class SignUpPopup extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Layout declarations
        BorderPane mainBorderPane = new BorderPane();
        VBox vBox = new VBox();
//        TilePane address1TilePane = new TilePane();
//        TilePane address2TilePane = new TilePane();
        GridPane address1GridPane = new GridPane();
        GridPane address2GridPane = new GridPane();


        // Main border pane
        mainBorderPane.setPrefHeight(500);
        mainBorderPane.setPrefWidth(600);

        // Title
        VBox titleVBox = new VBox();
        titleVBox.setAlignment(Pos.CENTER);
        Text title = new Text();
        title.setText("Registrati");
        title.setStyle("-fx-font-weight: bold;");
        title.setFont(new Font("Verdana Bold", 20));
        title.setTextAlignment(TextAlignment.CENTER);

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
        nameTextField.setText("Inserisci il tuo nome");

        TextField surnameTextField = new TextField();
        surnameTextField.setText("Inserisci il tuo cognome");

        TextField streetTextField = new TextField();
        streetTextField.setText("Inserisci la via");

        TextField civicTextField = new TextField();
        civicTextField.setText("Num.");

        TextField capTextField = new TextField();
        capTextField.setText("CAP");

        TextField cityTextField = new TextField();
        cityTextField.setText("Inserisci la città");

        TextField provinceTextField = new TextField();
        provinceTextField.setText("Prov.");

        TextField phoneTextField = new TextField();
        phoneTextField.setText("Inserisci il tuo telefono");

        TextField emailTextField = new TextField();
        emailTextField.setText("Inserisci la tua email");

        TextField passwordTextField = new TextField();
        passwordTextField.setText("Inserisci una password");

        Button continueButton = new Button();
        continueButton.setText("Continue");
        continueButton.setPadding(new Insets(5));
        continueButton.setAlignment(Pos.CENTER);

        address1GridPane.setHgap(20);
        address1GridPane.setVgap(4);
        address1GridPane.setScaleShape(true);
        address2GridPane.setHgap(20);
        address2GridPane.setVgap(4);
        address2GridPane.setScaleShape(true);


        // Layout embedding
        address1GridPane.add(streetLabel, 0, 0);
        address1GridPane.add(civicLabel, 1, 0);
        address1GridPane.add(capLabel, 2, 0);
        address1GridPane.add(streetTextField, 0, 1);
        address1GridPane.add(civicTextField, 1, 1);
        address1GridPane.add(capTextField, 2, 1);
        address2GridPane.add(cityLabel, 0, 0);
        address2GridPane.add(provinceLabel, 1, 0);
        address2GridPane.add(cityTextField, 0, 1);
        address2GridPane.add(provinceTextField, 1, 1);
        titleVBox.getChildren().addAll(title);
        vBox.getChildren().addAll(
                new VBox(nameLabel, nameTextField),
                new VBox(surnameLabel, surnameTextField),
                new VBox(5, addressLabel, address1GridPane, address2GridPane),
                new VBox(emailLabel, emailTextField),
                new VBox(passwordLabel, passwordTextField),
                continueButton
        );
        mainBorderPane.setTop(title);
        mainBorderPane.setCenter(vBox);

        final Scene scene = new Scene(mainBorderPane, Color.LIGHTBLUE);
        primaryStage.setTitle("Registrati");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
