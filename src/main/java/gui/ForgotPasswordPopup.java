package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ForgotPasswordPopup extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        FXMLLoader fxmlLoader = new FXMLLoader(ClassLoader.getSystemClassLoader().getResource("forgot_password_popup.fxml"));
        try {
            AnchorPane anchorPane = fxmlLoader.load();
            ForgotPasswordPopupController forgotPasswordPopupController = fxmlLoader.getController();
            forgotPasswordPopupController.setStage(primaryStage);
            Scene scene = new Scene(anchorPane);
            primaryStage.setTitle("Recupera l'account");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
