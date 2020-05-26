package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginPopup extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        FXMLLoader fxmlLoader = new FXMLLoader(ClassLoader.getSystemClassLoader().getResource("login_popup.fxml"));
        try {
            AnchorPane anchorPane = fxmlLoader.load();
            ((LoginPopupController) fxmlLoader.getController()).setPrimaryStage(primaryStage);
            Scene scene = new Scene(anchorPane);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Accedi");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
