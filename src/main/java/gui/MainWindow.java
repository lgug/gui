package gui;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import objects.CaratteristicheProdotto;
import objects.Categoria;
import objects.Categoria;
import objects.Prodotto;
import utils.HttpWrapper;
import utils.Manager;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class MainWindow extends Application implements Initializable {


    public static ArrayList<Prodotto> array = new ArrayList<>();
    public static  ObservableList<Prodotto> list = FXCollections.observableArrayList();
    private Stage stage;
    private Map<ProductLayoutController, Pane> productLayoutControllerMap;

    private static HBox staticUserTypeButtonWrapper;
    private NotLoggedLayoutController notLoggedLayoutController;
    private static HBox notLoggedHBox;
    private LoggedUCLayoutController loggedUCLayoutController;
    private static HBox loggedUCHBox;
    private LoggedURLayoutController loggedURLayoutController;
    private static HBox loggedURHBox;

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
    private RadioButton nomeButton;
    @FXML
    private RadioButton categoriaButton;
    @FXML
    private RadioButton caratteristicaButton;
    @FXML
    private HBox userTypeButtonWrapper;

    @FXML
    protected void handleCercaButtonAction(ActionEvent event) throws IOException {
        List<Prodotto> prodottoList = new ArrayList<>();
        clearPanel();
        productLayoutControllerMap = new HashMap<>();
        if(nomeButton.isSelected()){
             prodottoList = getProdByName(searchField.getText());
        }
        else if (categoriaButton.isSelected()){
            Categoria cat = null;
            if(searchField.getText().equalsIgnoreCase("frutta")){
                cat = Categoria.valueOf("FRUTTA_VERDURA");
            }
            else if(searchField.getText().equalsIgnoreCase("verdura")){
                cat = Categoria.valueOf("FRUTTA_VERDURA");
            }
            else{
                cat = Categoria.valueOf(searchField.getText().toUpperCase());
            }

            prodottoList = getProdByCat(cat);
        }
        else if(caratteristicaButton.isSelected()){
            CaratteristicheProdotto tag = null;
            tag = CaratteristicheProdotto.valueOf(searchField.getText().toUpperCase());
            prodottoList = getProdByTag(tag);
        }

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

    @FXML
    private void start10Prod() throws IOException {
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

    private void clearPanel(){
        productsGridPane.getChildren().clear();
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
        staticUserTypeButtonWrapper = userTypeButtonWrapper;
        try {
            FXMLLoader notLogged = new FXMLLoader(ClassLoader.getSystemClassLoader().getResource("not_logged_layout.fxml"));
            FXMLLoader loggedUC = new FXMLLoader(ClassLoader.getSystemClassLoader().getResource("logged_uc_layout.fxml"));
            FXMLLoader loggedUR = new FXMLLoader(ClassLoader.getSystemClassLoader().getResource("logged_ur_layout.fxml"));
            notLoggedHBox = notLogged.load();
            loggedUCHBox = loggedUC.load();
            loggedURHBox = loggedUR.load();

            notLoggedLayoutController = notLogged.getController();
            loggedUCLayoutController = loggedUC.getController();
            loggedURLayoutController = loggedUR.getController();

            String uid = Manager.getUIDFromFile();
            setUserTypeLayout(uid);

            start10Prod();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Prodotto> get10mostAvailableProducts() throws IOException {
        HttpWrapper http = new HttpWrapper();
        List<Prodotto> prodottoList = http.getFirst10Prod();
        return prodottoList;
    }

    private List<Prodotto> getProdByName(String prodName) throws IOException {
        HttpWrapper http = new HttpWrapper();
        return http.getProductsPerName(prodName);
    }
    private List<Prodotto> getProdByCat(Categoria... category) throws IOException {
        HttpWrapper http = new HttpWrapper();
        return http.getProductByCategory(Manager.getUIDFromFile(),category);
    }

    private List<Prodotto> getProdByTag(CaratteristicheProdotto... tag) throws IOException {
        HttpWrapper http = new HttpWrapper();
        return http.tag(Manager.getUIDFromFile(),tag);
    }

    public void handleCartButtonAction(MouseEvent mouseEvent) {
        list= FXCollections.observableArrayList(array);
        PopupCart popupCart = new PopupCart();
        try {
            popupCart.start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setUserTypeLayout(String uid) {
        staticUserTypeButtonWrapper.getChildren().clear();
        if (uid != null) {
            if (uid.matches("UC-\\d+")) {
                staticUserTypeButtonWrapper.getChildren().add(loggedUCHBox);
            } else if (uid.matches("UR-\\d+")) {
                staticUserTypeButtonWrapper.getChildren().add(loggedURHBox);
            } else if (uid.trim().isEmpty() || uid.trim().equals("ERROR")) {
                staticUserTypeButtonWrapper.getChildren().add(notLoggedHBox);
            }
        }
    }

    public static ArrayList<Prodotto> getArray() {
        return array;
    }

    public static void setArray(ArrayList<Prodotto> array) {
        MainWindow.array = array;
    }

    public static ObservableList<Prodotto> getList() {
        return list;
    }

    public static void setList(ObservableList<Prodotto> list) {
        MainWindow.list = list;
    }
    
}
