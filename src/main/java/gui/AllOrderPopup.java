package gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import objects.CaratteristicheProdotto;
import objects.Categoria;
import objects.Ordine;
import objects.Prodotto;
import utils.HttpWrapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AllOrderPopup extends Application {
    @FXML
    private TilePane tilePane1;
    @FXML
    private ChoiceBox choiceBox;
    @FXML
    private Button okButton;

    @FXML
    private void handleOkButtonAction(ActionEvent Event){
        clearPanel();
        if (choiceBox.getValue() != null) {
            String date = (String) choiceBox.getValue();
            HttpWrapper http = new HttpWrapper();
            Ordine ordine = http.getAllProductsByOrder("1",date);
            List<Prodotto> prodottoList = ordine.getProdotti();

            /*Prodotto prodotto1 = new Prodotto(41, "Sushi", "Xi-Sushi", CaratteristicheProdotto.BIOLOGICO, Categoria.PESCE, 15.99f, 20, 10, "sushi.png");
            Prodotto prodotto2 = new Prodotto(42, "Ciliege", "FruttaFresca", CaratteristicheProdotto.BIOLOGICO, Categoria.FRUTTA_VERDURA, 6.49f, 20, 10, "cherries.png");
            Prodotto prodotto3 = new Prodotto(43, "Banane", "FruttaFresca", CaratteristicheProdotto.BIOLOGICO, Categoria.FRUTTA_VERDURA, 5.99f, 20, 10, "bananas.png");
            Prodotto prodotto4 = new Prodotto(44, "Cioccolatini", "ChocoLove", CaratteristicheProdotto.BIOLOGICO, Categoria.DOLCI, 2.99f, 20, 10, "chocolate_box.png");
            Prodotto prodotto5 = new Prodotto(45, "Costolette", "MuccaPazza S.r.l", CaratteristicheProdotto.BIOLOGICO, Categoria.CARNE, 13.99f, 20, 10, "spare_ribs.png");
            Prodotto prodotto6 = new Prodotto(46, "Formaggio svizzero", "Cheddar", CaratteristicheProdotto.BIOLOGICO, Categoria.LATTICINI, 21.99f, 20, 10, "swiss_cheese.png");
            Prodotto prodotto7 = new Prodotto(47, "Tonno fresco", "Il pescatore allegro", CaratteristicheProdotto.BIOLOGICO, Categoria.PESCE, 7.99f, 20, 10, "tuna.png");
            Prodotto prodotto8 = new Prodotto(48, "Honey", "Apindustria", CaratteristicheProdotto.BIOLOGICO, Categoria.DOLCI, 3.99f, 20, 10, "honey.png");
            Prodotto prodotto9 = new Prodotto(49, "Sushi", "Xi-Sushi", CaratteristicheProdotto.BIOLOGICO, Categoria.PESCE, 15.99f, 20, 10, "sushi.png");
            Prodotto prodotto10 = new Prodotto(50, "Sushi", "Xi-Sushi", CaratteristicheProdotto.BIOLOGICO, Categoria.PESCE, 15.99f, 20, 10, "sushi.png");
            List<Prodotto> prodottoList = new ArrayList<>();
            prodottoList.add(prodotto1);
            prodottoList.add(prodotto2);
            prodottoList.add(prodotto3);
            prodottoList.add(prodotto4);
            prodottoList.add(prodotto5);
            prodottoList.add(prodotto6);
            prodottoList.add(prodotto7);
            prodottoList.add(prodotto8);
            prodottoList.add(prodotto9);
            prodottoList.add(prodotto10);*/


            int i = 0;
            Iterator it = prodottoList.iterator();
            while (it.hasNext()) {
                Label lab1 = new Label(prodottoList.get(i).getNome());
                lab1.setFont(Font.font(17));
                lab1.setStyle("-fx-font-weight: bold");

                Label lab2 = new Label(prodottoList.get(i).getMarca());
                lab2.setFont(Font.font(15));

                Label prezzo = new Label("$" + String.valueOf(prodottoList.get(i).getPrezzo()));
                prezzo.setFont(Font.font(20));

                Image image = new Image(ClassLoader.getSystemClassLoader().getResourceAsStream(prodottoList.get(i).getImmagine()));
                ImageView img = new ImageView(image);
                img.setFitHeight(80);
                img.setFitWidth(80);

                VBox vbox = new VBox(10, lab1, lab2, prezzo);
                HBox hbox = new HBox(20, img, vbox);
                tilePane1.getChildren().add(hbox);

                it.next();
                i++;
            }
        }
    }

    @FXML
    private void initialize() throws IOException {
        HttpWrapper http = new HttpWrapper();
        List<String> date = http.getAllOrdersDate("1");
        if (date.isEmpty()){
            choiceBox.setDisable(true);
        }
        else {
            for (String i : date) {
                choiceBox.getItems().add(i);
            }
        }
    }
    @FXML
    private void clearPanel(){
        tilePane1.getChildren().clear();
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
