package gui;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ErrorPageQuantita extends Application {

    @FXML public Button button;
    @FXML public ImageView warning;
    @FXML public Label textError;
    public String errore;
    public void initialize () {
        warning.setImage(new Image("wrong.png"));
        textError.setText("Quantita non disonibile");
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemClassLoader().getResource("errorPageQuantita.fxml"));
        AnchorPane pane = loader.load();
        Scene scene = new Scene(pane);

        primaryStage.setTitle("Errore");
        primaryStage.setScene(scene);
        primaryStage.show();


    }

    public void handleOkButtonAction(MouseEvent mouseEvent) {
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();
    }

    public void setErrore(String errore) {
        this.errore = errore;
    }
}