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
    public ErrorPageQuantitaController  controller;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemClassLoader().getResource("errorPageQuantita.fxml"));
        AnchorPane pane = loader.load();
        controller = ((ErrorPageQuantitaController) loader.getController());
        ((ErrorPageQuantitaController) loader.getController()).setPrimaryStage(primaryStage);
        Scene scene = new Scene(pane);
        primaryStage.setTitle("Errore");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public ErrorPageQuantitaController getController() {
        return controller;
    }
}
