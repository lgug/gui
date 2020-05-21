package gui;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import objects.Prodotto;
import utils.HttpWrapper;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class AllOrderPopup extends Application {
    @FXML
    private TilePane tilePane1;

    @FXML
    private void initialize() throws IOException {
        HttpWrapper http = new HttpWrapper();
        List<Prodotto> prodList = http.getAllOrders("1");
        int i = 0;
        Iterator it = prodList.iterator();
        while (it.hasNext()) {
            Label lab1 = new Label(prodList.get(i).getNome());
            lab1.setFont(Font.font(17));
            lab1.setStyle("-fx-font-weight: bold");

            Label lab2 = new Label(prodList.get(i).getMarca());
            lab2.setFont(Font.font(15));

            Label prezzo = new Label("$"+String.valueOf(prodList.get(i).getPrezzo()));
            prezzo.setFont(Font.font(20));

            Image image = new Image(ClassLoader.getSystemClassLoader().getResourceAsStream(prodList.get(i).getImmagine()));
            ImageView img = new ImageView(image);
            img.setFitHeight(80);
            img.setFitWidth(80);

            VBox vbox = new VBox(10,lab1,lab2,prezzo);
            HBox hbox = new HBox(20,img,vbox);
            tilePane1.getChildren().add(hbox);

            it.next();
            i++;
        }
    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemClassLoader().getResource("popupOrder.fxml"));
        AnchorPane pane = loader.load();
        Scene scene = new Scene(pane);
        primaryStage.setTitle("Tutti gli Ordini");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

}
