package gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import objects.Prodotto;
import utils.Manager;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductLayoutOrderController implements Initializable {

    private Prodotto prodotto;
    private int quantity;

    @FXML
    private HBox productWrapper;
    @FXML
    private ImageView productImage;
    @FXML
    private Label productName;
    @FXML
    private Label productBrand;
    @FXML
    private Label productQuantity;
    @FXML
    private Label productPrice;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        productWrapper.setBorder(new Border(
                new BorderStroke(Color.LIGHTGREY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderStroke.THIN)));
    }

    public void generate() {
        if (prodotto != null) {
            productImage.setImage(Manager.decodeImage(prodotto.getImmagine()));
            productName.setText(prodotto.getNome());
            productBrand.setText("Marca: " + prodotto.getMarca());
            productQuantity.setText("Quantità: " + quantity);
            productPrice.setText(Manager.EURO + prodotto.getPrezzo());
        }
    }

    public Prodotto getProdotto() {
        return prodotto;
    }

    public void setProdotto(Prodotto prodotto) {
        this.prodotto = prodotto;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
