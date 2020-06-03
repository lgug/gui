package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class InsertNewProduct extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        FXMLLoader fxmlLoader = new FXMLLoader(ClassLoader.getSystemClassLoader().getResource("insert_new_product.fxml"));
        try {
            AnchorPane anchorPane = fxmlLoader.load();
            ((InsertNewProductController) fxmlLoader.getController()).setStage(primaryStage);
            Scene scene = new Scene(anchorPane);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Inserisci un nuovo prodotto");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
