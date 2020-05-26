package gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import objects.CaratteristicheProdotto;
import objects.Categoria;
import objects.Prodotto;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class MainWindow extends Application implements Initializable {

    private Stage stage;
    private Map<ProductLayoutController, Pane> productLayoutControllerMap;

    @FXML
    private TextField searchField;
    @FXML
    private Button searchButton;
    @FXML
    private Button accediButton;
    @FXML
    private Button cartButton;
    @FXML
    private Label productsListTitle;
    @FXML
    private GridPane productsGridPane;

    @FXML
    protected void handleAccediButtonAction(ActionEvent event) {
        LoginPopup loginPopup = new LoginPopup();
        try {
            loginPopup.start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void handleCercaButtonAction(ActionEvent event) {
        productLayoutControllerMap = new HashMap<>();
        List<Prodotto> prodottoList = get10mostAvailableProducts();

        for (int i = 0; i < prodottoList.size(); i++) {
            FXMLLoader fxmlLoader = new FXMLLoader(ClassLoader.getSystemClassLoader().getResource("products_layout.fxml"));
            HBox hBox;
            try {
                hBox = fxmlLoader.<HBox>load();
                ProductLayoutController productLayoutController = fxmlLoader.getController();
                productLayoutControllerMap.put(productLayoutController, hBox);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        int pos = 0;
        int row = 0;
        int col = 0;
        for (ProductLayoutController plc: productLayoutControllerMap.keySet()) {
            plc.createOneProductLayout(prodottoList.get(pos));
            pos++;
            productsGridPane.add(productLayoutControllerMap.get(plc), col, row);
            if (col == 1) {
                col = 0;
                row++;
            } else {
                col++;
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        stage = primaryStage;
        FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemClassLoader().getResource("main_window.fxml"));
        AnchorPane pane = loader.load();
        Scene scene = new Scene(pane);
        primaryStage.setTitle("ShopOnline");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public List<Prodotto> get10mostAvailableProducts() {
        //TODO Call to Flask

        //TODO only for test: TO REMOVE!!!
        Prodotto prodotto1 = new Prodotto(41, "Sushi", "Xi-Sushi", CaratteristicheProdotto.BIOLOGICO, Categoria.PESCE, 15.99f, 20, 1, "sushi.png");
        Prodotto prodotto2 = new Prodotto(42, "Ciliege", "FruttaFresca", CaratteristicheProdotto.BIOLOGICO, Categoria.FRUTTA_VERDURA, 6.49f, 20, 1, "cherries.png");
        Prodotto prodotto3 = new Prodotto(43, "Banane", "FruttaFresca", CaratteristicheProdotto.BIOLOGICO, Categoria.FRUTTA_VERDURA, 5.99f, 20, 1, "bananas.png");
        Prodotto prodotto4 = new Prodotto(44, "Cioccolatini", "ChocoLove", CaratteristicheProdotto.BIOLOGICO, Categoria.DOLCI, 2.99f, 20, 1, "chocolate_box.png");
        Prodotto prodotto5 = new Prodotto(45, "Costolette", "MuccaPazza S.r.l", CaratteristicheProdotto.BIOLOGICO, Categoria.CARNE, 13.99f, 20, 1,"spare_ribs.png");
        Prodotto prodotto6 = new Prodotto(46, "Formaggio svizzero", "Cheddar", CaratteristicheProdotto.BIOLOGICO, Categoria.LATTICINI, 21.99f, 20, 1, "swiss_cheese.png");
        Prodotto prodotto7 = new Prodotto(47, "Tonno fresco", "Il pescatore allegro", CaratteristicheProdotto.BIOLOGICO, Categoria.PESCE, 7.99f, 20, 1, "tuna.png");
        Prodotto prodotto8 = new Prodotto(48, "Honey", "Apindustria", CaratteristicheProdotto.BIOLOGICO, Categoria.DOLCI, 3.99f, 20, 1, "honey.png");
        Prodotto prodotto9 = new Prodotto(49, "Sushi", "Xi-Sushi", CaratteristicheProdotto.BIOLOGICO, Categoria.PESCE, 15.99f, 20, 1, "sushi.png");
        Prodotto prodotto10 = new Prodotto(50, "Sushi", "Xi-Sushi", CaratteristicheProdotto.BIOLOGICO, Categoria.PESCE, 15.99f, 20, 1, "sushi.png");
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
        prodottoList.add(prodotto10);

        return prodottoList;
    }
}
