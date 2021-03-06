package gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import objects.Prodotto;
import utils.Manager;
import utils.StringsUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ProductDetailsController implements Initializable {

    private Prodotto prodotto;

    @FXML
    private ImageView productImage;
    @FXML
    private Label productName;
    @FXML
    private Label productBrand;
    @FXML
    private Label productCategory;
    @FXML
    private Label productTag;
    @FXML
    private Label productQuantity;
    @FXML
    private Label productAvailability;
    @FXML
    private Label productPrice;
    @FXML
    private Button addToCartButton;

    public void setProdotto(Prodotto prodotto) {
        this.prodotto = prodotto;
    }

    public void initializeProdottoData() {
        if (prodotto != null) {
            productImage.setImage(Manager.decodeImage(prodotto.getImmagine()));
            productName.setText(prodotto.getNome());
            productBrand.setText(prodotto.getMarca());
            productCategory.setText(prodotto.getCategoria().toString());
            productTag.setText(prodotto.getCaratteristiche().toString());
            productQuantity.setText(prodotto.getQuantita() + " pezzi");
            productAvailability.setText("Ancora " + prodotto.getDisponibilita());
            productPrice.setText(StringsUtils.getPriceString(prodotto.getPrezzo()));
            if (!Manager.isUserCliente())
                addToCartButton.setManaged(false);
        }
    }

    @FXML
    protected void handleAddToCartButtonEvent() {
        ArrayList<Prodotto> array = MainWindow.getArray();
        array.add(prodotto);
        MainWindow.setArray(array);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
