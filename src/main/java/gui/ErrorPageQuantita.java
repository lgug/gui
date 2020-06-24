package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class ErrorPageQuantita extends Application {
    public ErrorPageQuantitaController  controller;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemClassLoader().getResource("errorPageQuantita.fxml"));
        AnchorPane pane = loader.load();
        controller = loader.getController();
        ((ErrorPageQuantitaController) loader.getController()).setPrimaryStage();
        Scene scene = new Scene(pane);
        primaryStage.setTitle("Errore");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public ErrorPageQuantitaController getController() {
        return controller;
    }
}
