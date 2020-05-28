package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import objects.Prodotto;

import java.io.IOException;

public class ProductDetails extends Application {

    private final Prodotto prodotto;

    public static void main(String[] args) {
        launch(args);
    }

    public ProductDetails(Prodotto prodotto) {
        this.prodotto = prodotto;
    }

    @Override
    public void start(Stage primaryStage) {
        FXMLLoader fxmlLoader = new FXMLLoader(ClassLoader.getSystemClassLoader().getResource("product_details_popup.fxml"));
        try {
            AnchorPane anchorPane = fxmlLoader.load();
            ProductDetailsController productDetailsController = fxmlLoader.getController();
            productDetailsController.setProdotto(prodotto);
            productDetailsController.initializeProdottoData();

            Scene scene = new Scene(anchorPane);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Dettagli del prodotto");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
