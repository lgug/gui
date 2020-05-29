package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class PopupCart extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemClassLoader().getResource("PopupCart.fxml"));
        AnchorPane pane = loader.load();
        ((PopupCartController) loader.getController()).setPrimaryStage(primaryStage);
        Scene scene = new Scene(pane);
        primaryStage.setTitle("Carrello");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}





