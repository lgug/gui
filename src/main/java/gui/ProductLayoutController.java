package gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import objects.Prodotto;
import utils.Manager;
import utils.StringsUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ProductLayoutController implements Initializable {

    private Prodotto prodotto;

    @FXML
    public Button product_add_button;
    @FXML
    private ImageView productAddButtonSymbol;
    @FXML
    private HBox productWrapper;
    @FXML
    private ImageView prodottoImage;
    @FXML
    private Label titoloProdotto;
    @FXML
    private Label marcaProdotto;
    @FXML
    private Label categoriaProdotto;
    @FXML
    private Label disponibilitaProdotto;
    @FXML
    private Label prezzoProdotto;

    public void createOneProductLayout() {
        createOneProductLayout(this.prodotto);
    }

    public void createOneProductLayout(Prodotto prodotto) {
        this.prodotto=prodotto;
        prodottoImage.setImage(Manager.decodeImage(prodotto.getImmagine()));
        titoloProdotto.setText(prodotto.getNome());
        marcaProdotto.setText(prodotto.getMarca());
        categoriaProdotto.setText(prodotto.getCategoria().toString());
        setDisponibilitaProperties(prodotto.getDisponibilita());
        prezzoProdotto.setText(StringsUtils.getPriceString(prodotto.getPrezzo()));
    }

    private void setDisponibilitaProperties(int disp) {
        if (disp <= 0) {
            disponibilitaProdotto.setText("Attualmente non disponibili");
            disponibilitaProdotto.setTextFill(Color.RED);
            product_add_button.setVisible(false);

        } else if (disp <= 10) {
            disponibilitaProdotto.setText("Ancora " + disp + " disponibili!");
            disponibilitaProdotto.setTextFill(Color.GOLDENROD);
            product_add_button.setVisible(true);
        } else {
            disponibilitaProdotto.setText("Attualmente disponibili: " + disp);
            disponibilitaProdotto.setTextFill(Color.GREEN);
            product_add_button.setVisible(true);
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (!Manager.isUserCliente()) {
            product_add_button.setManaged(false);
            productAddButtonSymbol.setManaged(false);
        }
        productWrapper.setBorder(new Border(
                new BorderStroke(Color.LIGHTGREY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderStroke.THIN)));
    }

    @FXML
    protected void handleDetailsButtonEvent() {
        ProductDetails productDetails = new ProductDetails(prodotto);
        productDetails.start(new Stage());
    }

    @FXML
    public void handleAddButtonAction() {
        ArrayList<Prodotto> array = MainWindow.getArray();
        int cont = 0;
        for (Prodotto prodottoar : array){
            cont = 0;
            if (prodottoar == prodotto)
                cont++;
        }
        if(prodotto.getDisponibilita()<=cont){
            Alert alert = new Alert(Alert.AlertType.WARNING,
                    "Prodotto non disponibile!", ButtonType.OK);
            alert.show();

        }
        else{
            array.add(prodotto);
            MainWindow.setArray(array);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Prodotto aggiunto al carrello!", ButtonType.OK);
            alert.show();
        }

    }

    public Prodotto getProdotto() {
        return prodotto;
    }

    public void setProdotto(Prodotto prodotto) {
        this.prodotto = prodotto;
    }
}
