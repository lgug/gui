package gui;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import objects.CaratteristicheProdotto;
import objects.Categoria;
import objects.Prodotto;
import utils.FieldChecker;
import utils.HttpWrapper;
import utils.KeyGenerator;
import utils.Manager;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class InsertNewProductController implements Initializable {

    private Stage stage;
    private File pendingFile;

    @FXML
    private Label newImageSelected;
    @FXML
    private TextField newProductName;
    @FXML
    private TextField newProductBrand;
    @FXML
    private TextField newProductPrice;
    @FXML
    private ChoiceBox<Categoria> newProductCategory;
    @FXML
    private ChoiceBox<CaratteristicheProdotto> newProductTag;
    @FXML
    private Spinner<Integer> newProductQuantity;
    @FXML
    private Spinner<Integer> newProductAvailability;

    @FXML
    protected void handleInsertNewProductButtonEvent(MouseEvent mouseEvent) {
        Prodotto prodotto = new Prodotto();
        if (pendingFile != null) {
            prodotto.setId(KeyGenerator.generateProductKey());
            String imageBlob = Manager.encodeImage(pendingFile);
            prodotto.setImmagine(imageBlob);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Nessun immagine inserita", ButtonType.OK);
            alert.show();
            return;
        }

        if (FieldChecker.validateNonEmptyString(newProductName.getText())) {
            prodotto.setNome(newProductName.getText());
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING,
                    String.format(FieldChecker.emptyFieldMsg, "NOME"), ButtonType.OK);
            alert.show();
            return;
        }

        if (FieldChecker.validateNonEmptyString(newProductBrand.getText())) {
            prodotto.setMarca(newProductBrand.getText());
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING,
                    String.format(FieldChecker.emptyFieldMsg, "MARCA"), ButtonType.OK);
            alert.show();
            return;
        }

        if (FieldChecker.validateNonEmptyString(newProductPrice.getText()) &&
                FieldChecker.validateNumerableString(newProductPrice.getText())) {
            prodotto.setPrezzo(Float.valueOf(newProductPrice.getText()));
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING,
                    "Il prezzo inserito non è valido.", ButtonType.OK);
            alert.show();
            return;
        }

        if (newProductCategory.getValue() != null) {
            prodotto.setCategoria(newProductCategory.getValue());
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING,
                    String.format(FieldChecker.emptyFieldMsg, "CATEGORIA"), ButtonType.OK);
            alert.show();
            return;
        }

        if (newProductTag.getValue() != null) {
            prodotto.setCaratteristiche(newProductTag.getValue());
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING,
                    String.format(FieldChecker.emptyFieldMsg, "CARATTERISTICA"), ButtonType.OK);
            alert.show();
            return;
        }

        prodotto.setQuantita(newProductQuantity.getValue());
        prodotto.setDisponibilita(newProductAvailability.getValue());

        String uid = Manager.getUIDFromFile();
        HttpWrapper httpWrapper = new HttpWrapper();
        boolean result = httpWrapper.addProdotto(uid, prodotto);
        if (result) stage.close();
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "Errore durante l'inserimento del prodotto", ButtonType.OK);
            alert.show();
        }
    }

    @FXML
    protected void handleChooseImageButtonEvent(MouseEvent mouseEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Scegli l'immagine per il tuo prodotto");
        pendingFile = fileChooser.showOpenDialog(stage);
        if (pendingFile != null) {
            newImageSelected.setText(pendingFile.getName());
        } else {
            newImageSelected.setText("Immagine inserita non valida!");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        newProductCategory.setItems(FXCollections.observableArrayList(Categoria.values()));
        newProductTag.setItems(FXCollections.observableArrayList(CaratteristicheProdotto.values()));
        newProductQuantity.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE, 1, 1));
        newProductAvailability.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, 0, 1));
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
