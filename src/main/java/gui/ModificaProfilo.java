package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ModificaProfilo extends Application {

        public static void main(String[] args) {
            launch(args);
        }

        @Override
        public void start(Stage primaryStage) throws Exception {
            FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemClassLoader().getResource("ModificaProfilo.fxml"));
            AnchorPane pane = loader.load();
            ((ModificaProfiloController) loader.getController()).setPrimaryStage(primaryStage);
            Scene scene = new Scene(pane);
            primaryStage.setTitle("Modifica");
            primaryStage.setScene(scene);
            primaryStage.show();
        }
}

