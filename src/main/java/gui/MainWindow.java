package gui;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import objects.CaratteristicheProdotto;
import objects.Categoria;
import objects.Prodotto;
import objects.SortingType;
import utils.HttpWrapper;
import utils.Manager;
import utils.StringsUtils;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class MainWindow extends Application implements Initializable {


    public static ArrayList<Prodotto> array = new ArrayList<>();
    public static  ObservableList<Prodotto> list = FXCollections.observableArrayList();
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
    private Label productsListTitle;
    @FXML
    private Label numberOfResults;
    @FXML
    private GridPane productsGridPane;
    @FXML
    private RadioButton nomeButton;
    @FXML
    private RadioButton categoriaButton;
    @FXML
    private RadioButton caratteristicaButton;
    @FXML
    private RadioButton marcaButton;
    @FXML
    private HBox userTypeButtonWrapper;
    @FXML
    private ChoiceBox<Categoria> catChoiceBox;
    @FXML
    private ChoiceBox<CaratteristicheProdotto> tagChoiceBox;
    @FXML
    private ChoiceBox<SortingType> sortTypeChoiceBox;

    static GridPane statiProdGridPane;

    @FXML
    protected void handleCercaButtonAction(ActionEvent event) {
        productsListTitle.setText("Risultati della ricerca");
        List<Prodotto> prodottoList = new ArrayList<>();
        clearPanel();
        productLayoutControllerMap = new HashMap<>();
        if(nomeButton.isSelected()){
             prodottoList = getProdByName(searchField.getText());
        }
        else if (categoriaButton.isSelected()){
            Categoria cat = catChoiceBox.getValue();
            prodottoList = getProdByCat(cat);
        }
        else if(caratteristicaButton.isSelected()){
            CaratteristicheProdotto tag = tagChoiceBox.getValue();
            prodottoList = getProdByTag(tag);
        }

        else if(marcaButton.isSelected()){
            prodottoList = getProdByBrand(searchField.getText());
        }

        if (prodottoList != null && prodottoList.size() > 0) {
            for (Prodotto prodotto : prodottoList) {
                FXMLLoader fxmlLoader = new FXMLLoader(ClassLoader.getSystemClassLoader().getResource("products_layout.fxml"));
                HBox hBox;
                try {
                    hBox = fxmlLoader.<HBox>load();
                    ProductLayoutController productLayoutController = fxmlLoader.getController();
                    productLayoutController.setProdotto(prodotto);
                    productLayoutControllerMap.put(productLayoutController, hBox);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            numberOfResults.setText(StringsUtils.foundProduct(productLayoutControllerMap.size()));
        }

        sortProductResult();
    }

    @FXML
    private void start10Prod() {
        productsListTitle.setText("Prodotti suggeriti");
        productLayoutControllerMap = new HashMap<>();
        List<Prodotto> prodottoList = get10mostAvailableProducts();
        clearPanel();
        if (prodottoList != null && prodottoList.size() > 0) {
            for (Prodotto prodotto : prodottoList) {
                FXMLLoader fxmlLoader = new FXMLLoader(ClassLoader.getSystemClassLoader().getResource("products_layout.fxml"));
                HBox hBox;
                try {
                    hBox = fxmlLoader.<HBox>load();
                    ProductLayoutController productLayoutController = fxmlLoader.getController();
                    productLayoutController.setProdotto(prodotto);
                    productLayoutControllerMap.put(productLayoutController, hBox);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            numberOfResults.setText(StringsUtils.foundProduct(productLayoutControllerMap.size()));
        }

        sortProductResult();
    }

    private void clearPanel(){
        productsGridPane.getChildren().clear();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemClassLoader().getResource("main_window.fxml"));
        AnchorPane pane = loader.load();
        Scene scene = new Scene(pane);
        primaryStage.setTitle("ShopOnline");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        statiProdGridPane = productsGridPane;
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

            catChoiceBox.getItems().addAll(Categoria.values());
            tagChoiceBox.getItems().addAll(CaratteristicheProdotto.values());
            sortTypeChoiceBox.getItems().setAll(SortingType.values());
            sortTypeChoiceBox.setValue(SortingType.NAME_ASCENDING);
            sortTypeChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                clearPanel();
                sortProductResult();
            });

            String uid = Manager.getUIDFromFile();
            setUserTypeLayout(uid);

            start10Prod();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Prodotto> get10mostAvailableProducts() {
        HttpWrapper http = new HttpWrapper();
        return http.getFirst10Prod();
    }

    private List<Prodotto> getProdByName(String prodName) {
        HttpWrapper http = new HttpWrapper();
        return http.getProductsPerName(prodName);
    }

    private List<Prodotto> getProdByBrand(String brandName) {
        HttpWrapper http = new HttpWrapper();
        return http.getProductsPerBrand(brandName,Manager.getUIDFromFile());
    }
    private List<Prodotto> getProdByCat(Categoria... category) {
        HttpWrapper http = new HttpWrapper();
        return http.getProductByCategory(Manager.getUIDFromFile(),category);
    }

    private List<Prodotto> getProdByTag(CaratteristicheProdotto... tag) {
        HttpWrapper http = new HttpWrapper();
        return http.tag(Manager.getUIDFromFile(),tag);
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

    //TODO redundant method: to review
    public static void resetWindow() {
        HashMap<ProductLayoutController, Pane> productLayoutControllerMap = new HashMap<>();
        List<Prodotto> prodottoList = get10mostAvailableProducts();

        statiProdGridPane.getChildren().clear();
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
            statiProdGridPane.add(productLayoutControllerMap.get(plc), col, row);
            if (col == 1) {
                col = 0;
                row++;
            } else {
                col++;
            }
        }
    }

    private void sortProductResult() {
        if (sortTypeChoiceBox.getValue() != null) {
            List<ProductLayoutController> sortedList = new ArrayList<>(productLayoutControllerMap.keySet());
            sortedList.sort(new ProductComparator(sortTypeChoiceBox.getValue()));
            int row = 0;
            int col = 0;
            for (ProductLayoutController plc: sortedList) {
                plc.createOneProductLayout();
                productsGridPane.add(productLayoutControllerMap.get(plc), col, row);
                if (col == 1) {
                    col = 0;
                    row++;
                } else {
                    col++;
                }
            }
        }
    }


    private static class ProductComparator implements Comparator<ProductLayoutController> {

        private final SortingType sortingType;

        public ProductComparator(SortingType sortingType) {
            this.sortingType = sortingType;
        }

        @Override
        public int compare(ProductLayoutController o1, ProductLayoutController o2) {
            switch (sortingType) {
                case NAME_ASCENDING:
                    return o1.getProdotto().getNome().compareTo(o2.getProdotto().getNome());
                case NAME_DESCENDING:
                    return o2.getProdotto().getNome().compareTo(o1.getProdotto().getNome());
                case BRAND_ASCENDING:
                    int c1 = o1.getProdotto().getMarca().compareTo(o2.getProdotto().getMarca());
                    if (c1 == 0)
                        return o1.getProdotto().getNome().compareTo(o2.getProdotto().getNome());
                    return c1;
                case BRAND_DESCENDING:
                    int c2 = o2.getProdotto().getMarca().compareTo(o1.getProdotto().getMarca());
                    if (c2 == 0)
                        return o2.getProdotto().getNome().compareTo(o1.getProdotto().getNome());
                    return c2;
                case PRICE_ASCENDING:
                    int c3 = Float.compare(o1.getProdotto().getPrezzo(), o2.getProdotto().getPrezzo());
                    if (c3 == 0)
                        return o1.getProdotto().getNome().compareTo(o2.getProdotto().getNome());
                    return c3;
                case PRICE_DESCENDING:
                    int c4 = Float.compare(o2.getProdotto().getPrezzo(), o1.getProdotto().getPrezzo());
                    if (c4 == 0)
                        return o2.getProdotto().getNome().compareTo(o1.getProdotto().getNome());
                    return c4;
            }
            return 0;
        }
    }
    
}
