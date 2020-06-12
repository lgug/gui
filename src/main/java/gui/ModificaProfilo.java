package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import objects.UtenteCliente;
import objects.UtenteResponsabile;
import utils.Manager;

public class ModificaProfilo extends Application {

        public static void main(String[] args) {
            launch(args);
        }

        @Override
        public void start(Stage primaryStage) throws Exception {
            FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemClassLoader().getResource("modificaProfilo.fxml"));
            AnchorPane pane = loader.load();
            ModificaProfiloController modificaProfiloController = loader.getController();
            modificaProfiloController.setPrimaryStage(primaryStage);
            if (Manager.isUserCliente())
                modificaProfiloController.setUserClass(UtenteCliente.class);
            else if (Manager.isUserResponsabile())
                modificaProfiloController.setUserClass(UtenteResponsabile.class);
            modificaProfiloController.fillFields();

            Scene scene = new Scene(pane);
            primaryStage.setTitle("Modifica");
            primaryStage.setScene(scene);
            primaryStage.show();
        }
}

