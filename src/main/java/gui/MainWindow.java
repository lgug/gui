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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import objects.CaratteristicheProdotto;
import objects.Categoria;
import objects.Prodotto;
import utils.HttpWrapper;

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
    protected void handleCercaButtonAction(ActionEvent event) throws IOException {
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

    public List<Prodotto> get10mostAvailableProducts() throws IOException {
        HttpWrapper http = new HttpWrapper();
        List<Prodotto> prodottoList = http.getFirst10Prod();
        return prodottoList;
    }

    public void handleCartButtonAction(MouseEvent mouseEvent) {
        PopupCart popupCart = new PopupCart();
        try {
            popupCart.start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
