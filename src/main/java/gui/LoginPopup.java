package gui;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class LoginPopup extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Layout declarations
        BorderPane mainBorderPane = new BorderPane();
        StackPane stackPane = new StackPane();
        HBox signOptionsHBox = new HBox();

        // Main border pane
        mainBorderPane.setPrefWidth(400);
        mainBorderPane.setPrefHeight(300);

        // Title
        Text text = new Text("Accedi al sistema");
        text.setFont(new Font("Verdana Bold", 18));
        text.setTextAlignment(TextAlignment.CENTER);

        // Stack pane's nodes

        Text usernameText = new Text("Username:");
        usernameText.setTextAlignment(TextAlignment.LEFT);

        TextField usernameTextField = new TextField("Inserisci la tua email");

        Text passwordText = new Text("Password:");
        passwordText.setTextAlignment(TextAlignment.LEFT);

        TextField passwordTextField = new TextField("Inserisci la tua password");

        Button signInButton  = new Button("Accedi");
        Button signUpButton = new Button("Registrati");


        // Layout embedding
        mainBorderPane.setTop(text);
        signOptionsHBox.getChildren().addAll(signInButton, signUpButton);
        stackPane.getChildren().addAll(usernameText, usernameTextField, passwordText, passwordTextField, signOptionsHBox);
        mainBorderPane.setCenter(stackPane);

        final Scene scene = new Scene(mainBorderPane, Color.LIGHTBLUE);
        primaryStage.setTitle("Accedi");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
