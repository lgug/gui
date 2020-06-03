package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import objects.Prodotto;
import utils.HttpWrapper;
import utils.Manager;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ManageProductsController implements Initializable {

    private Map<String, Prodotto> pendingProductsMap;
    private ObservableList<String> observableList;
    private String pendingTextSearch;

    @FXML
    private TextField searchProductField;
    @FXML
    private ListView<String> productsListView;
    @FXML
    private ImageView productDetailImage;
    @FXML
    private Label productDetailTitle;
    @FXML
    private Label productDetailBrand;
    @FXML
    private Label productDetailAvailability;
    @FXML
    private Label productDetailPrice;
    @FXML
    private Button productMoreDetailButton;
    @FXML
    private Button addProductUnitButton;
    @FXML
    private Button removeProductUnitButton;
    @FXML
    private Button removeProductButton;
    @FXML
    private VBox productDetailsWrapper;

    @FXML
    protected void handleSearchProductButtonEvent(MouseEvent mouseEvent) {
        pendingTextSearch = searchProductField.getText();
        HttpWrapper httpWrapper = new HttpWrapper();
        try {
            List<Prodotto> products = httpWrapper.getProductsPerName(pendingTextSearch);
            List<String> stringList = new ArrayList<>();
            products.forEach(prodotto -> {
                String s = prodotto.getId() + "  ->  " + prodotto.getNome() + ", " + prodotto.getMarca();
                pendingProductsMap.put(s, prodotto);
                stringList.add(s);
            });
            observableList = FXCollections.observableList(stringList);
            productsListView.setItems(observableList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void handleListItemEvent(MouseEvent mouseEvent) {
        Prodotto prodotto = getPendingProduct();
        productDetailImage.setImage(new Image(ClassLoader.getSystemClassLoader().getResourceAsStream(prodotto.getImmagine())));
        productDetailTitle.setText(prodotto.getNome());
        productDetailBrand.setText(prodotto.getMarca());
        productDetailAvailability.setText("Disponibilità: " + prodotto.getDisponibilita());
        productDetailPrice.setText(Manager.EURO + prodotto.getPrezzo());

        addProductUnitButton.setDisable(false);
        removeProductUnitButton.setDisable(false);
        removeProductButton.setDisable(false);
        productMoreDetailButton.setDisable(false);
        productDetailsWrapper.setVisible(true);
    }

    @FXML
    protected void handleMoreDetailButtonEvent(MouseEvent mouseEvent) {
        Prodotto prodotto = getPendingProduct();
        ProductDetails productDetails = new ProductDetails(prodotto);
        productDetails.start(new Stage());
    }

    @FXML
    protected void handleAddUnitButtonEvent(MouseEvent mouseEvent) {
        String uid = Manager.getUIDFromFile();
        HttpWrapper httpWrapper = new HttpWrapper();
        if (getPendingProduct() != null) {
            httpWrapper.addUnitOfProdotto(uid, getPendingProduct().getId());
            handleSearchProductButtonEvent(mouseEvent);
            handleListItemEvent(mouseEvent);
        }
    }

    @FXML
    protected void handleRemoveUnitButtonEvent(MouseEvent mouseEvent) {
        if (getPendingProduct() != null) {
            if (getPendingProduct().getDisponibilita() > 0) {
                String uid = Manager.getUIDFromFile();
                HttpWrapper httpWrapper = new HttpWrapper();
                httpWrapper.removeUnitOfProdotto(uid, getPendingProduct().getId());
                handleSearchProductButtonEvent(mouseEvent);
                handleListItemEvent(mouseEvent);
            }
        }
    }

    @FXML
    protected void handleRemoveProductButtonEvent(MouseEvent mouseEvent) {
        String uid = Manager.getUIDFromFile();
        HttpWrapper httpWrapper = new HttpWrapper();
        if (getPendingProduct() != null) {
            httpWrapper.remove(uid, getPendingProduct().getId());
        }
        productDetailsWrapper.setVisible(false);
        addProductUnitButton.setDisable(true);
        removeProductUnitButton.setDisable(true);
        removeProductButton.setDisable(true);
        handleSearchProductButtonEvent(mouseEvent);
    }

    @FXML
    protected void handleAddNewProductButtonEvent(MouseEvent mouseEvent) {
        InsertNewProduct insertNewProduct = new InsertNewProduct();
        insertNewProduct.start(new Stage());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pendingProductsMap = new HashMap<>();
        addProductUnitButton.setDisable(true);
        removeProductUnitButton.setDisable(true);
        removeProductButton.setDisable(true);
        productMoreDetailButton.setDisable(true);
        productDetailsWrapper.setVisible(false);
    }

    private Prodotto getPendingProduct() {
        return pendingProductsMap.get(productsListView.getSelectionModel().getSelectedItem());
    }

    private void resetDetailsButton() {

    }
}
