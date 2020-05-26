package gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import objects.FormaDiPagamento;

import java.net.URL;
import java.util.ResourceBundle;

public class UtenteClienteController implements Initializable {

    private Button confermaButton;
    private FormaDiPagamento formaDiPagamento;
    private String datiDiPagamento;
    private Stage primaryStage;
    private Stage paymentStage;
    private PaymentDataController paymentDataController;
    private Pane paymentDataPane;

    @FXML
    private RadioButton siCartaFedelta;
    @FXML
    private RadioButton noCartaFedelta;
    @FXML
    private ChoiceBox<FormaDiPagamento> formaDiPagamentoBox;
    @FXML
    private HBox pagamentoHBox;
    @FXML
    private Button datiPagamentoButton;
    @FXML
    private ImageView paymentDataStatus;

    public void setPaymentDataPane(Pane paymentDataPane) {
        this.paymentDataPane = paymentDataPane;

        Scene paymentScene = new Scene(paymentDataPane);
        paymentStage = new Stage();
        paymentStage.setTitle("Forma di pagamento");
        paymentStage.setScene(paymentScene);
        paymentStage.initModality(Modality.WINDOW_MODAL);
        paymentStage.initOwner(primaryStage);
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setPaymentStage(Stage paymentStage) {
        this.paymentStage = paymentStage;
    }

    public void setPaymentDataController(PaymentDataController paymentDataController) {
        this.paymentDataController = paymentDataController;
    }

    public Boolean isCartaFedelta() {
        if (siCartaFedelta != null && noCartaFedelta != null)
            return siCartaFedelta.isSelected();
        else return null;
    }

    public void changeStatusPaymentButton(boolean ok) {
        Image image;
        if (ok) {
            image = new Image(ClassLoader.getSystemClassLoader().getResourceAsStream("correct.png"));
        } else {
            image = new Image(ClassLoader.getSystemClassLoader().getResourceAsStream("wrong.png"));
        }
        paymentDataStatus.setImage(image);
    }

    public FormaDiPagamento getFormaDiPagamento() {
        if (formaDiPagamentoBox != null)
            return formaDiPagamentoBox.getValue();
        else return null;
    }

    public void setConfermaButton(Button button) {
        confermaButton = button;
    }

    @FXML
    protected void handleInserisciDatiPagamentoButtonEvent(MouseEvent event) {
        paymentDataController.setPaymentStage(paymentStage);
        paymentDataController.selectScreen(formaDiPagamento);
        paymentDataController.setPaymentDataStatus(paymentDataStatus);
        paymentStage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        formaDiPagamentoBox = new ChoiceBox<>();
        formaDiPagamentoBox.setPrefWidth(150);
        formaDiPagamentoBox.getItems().addAll(FormaDiPagamento.values());
        pagamentoHBox.getChildren().add(formaDiPagamentoBox);
        formaDiPagamentoBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            confermaButton.setDisable((newValue != FormaDiPagamento.CONSEGNA));
            changeStatusPaymentButton(newValue == FormaDiPagamento.CONSEGNA);
            datiPagamentoButton.setDisable(newValue != FormaDiPagamento.CARTA_CREDITO && newValue != FormaDiPagamento.PAYPAL);
            formaDiPagamento = newValue;
            datiDiPagamento = null;
        });
    }
}
