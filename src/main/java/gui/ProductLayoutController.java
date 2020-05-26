package gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import objects.Prodotto;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductLayoutController implements Initializable {

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

    public void createOneProductLayout(Prodotto prodotto) {
        Image image = new Image(ClassLoader.getSystemClassLoader().getResourceAsStream(prodotto.getImmagine()));
        prodottoImage.setImage(image);
        titoloProdotto.setText(prodotto.getNome());
        marcaProdotto.setText(prodotto.getMarca());
        categoriaProdotto.setText(prodotto.getCategoria().toString());
        setDisponibilitaProperties(prodotto.getDisponibilita());
        prezzoProdotto.setText("€" + prodotto.getPrezzo());
    }

    private void setDisponibilitaProperties(int disp) {
        if (disp <= 0) {
            disponibilitaProdotto.setText("Attualmente non disponibili!");
            disponibilitaProdotto.setTextFill(Color.RED);
        } else if (disp <= 10) {
            disponibilitaProdotto.setText("Ancora " + disp + "disponibili!!!");
            disponibilitaProdotto.setTextFill(Color.GOLD);
        } else {
            disponibilitaProdotto.setText("Attualmente disponibili: " + disp);
            disponibilitaProdotto.setTextFill(Color.GREEN);
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        productWrapper.setBorder(new Border(
                new BorderStroke(Color.LIGHTGREY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderStroke.THIN)));
    }

}
