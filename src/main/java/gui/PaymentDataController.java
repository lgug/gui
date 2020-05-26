package gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import objects.FormaDiPagamento;

import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class PaymentDataController implements Initializable {

    private String paymentString = null;
    private ImageView paymentDataStatus;
    private Button confermaButton;
    private Stage paymentStage;

    @FXML
    private Label label1;
    @FXML
    private Label label2;
    @FXML
    private TextField textField1;
    @FXML
    private TextField textField2;
    @FXML
    private Button confermaDatiButton;
    @FXML
    private Button annullaDatiButton;
    @FXML
    private ImageView iconDatiPagamento;

    public String getPaymentString() {
        return paymentString;
    }

    public void setConfermaButton(Button button) {
        confermaButton = button;
    }

    public void setPaymentStage(Stage stage) {
        paymentStage = stage;
    }

    public void setPaymentDataStatus(ImageView paymentDataStatus) {
        this.paymentDataStatus = paymentDataStatus;
    }

    @FXML
    protected void handleConfermaDatiButtonEvent(MouseEvent event) {
        //TODO to verify data
        paymentString = textField1.getText() + ";" + textField2.getText();
        confermaButton.setDisable(false);
        paymentDataStatus.setImage(new Image(ClassLoader.getSystemClassLoader().getResourceAsStream("correct.png")));
        if (paymentStage != null) paymentStage.close();
    }

    @FXML
    protected void handleAnnullaDatiButtonEvent(MouseEvent event) {
        if (paymentStage != null) paymentStage.close();
    }

    public void selectScreen(FormaDiPagamento formaDiPagamento) {
        InputStream is = null;
        switch (formaDiPagamento) {
            case PAYPAL:
                is = ClassLoader.getSystemClassLoader().getResourceAsStream("paypal_logo.jpg");
                label1.setText("Username:");
                label2.setText("Password:");
                break;
            case CARTA_CREDITO:
                is = ClassLoader.getSystemClassLoader().getResourceAsStream("credit_card.jpg");
                label1.setText("N. della carta:");
                label2.setText("Data di scadenza:");
                break;
        }
        if (is != null) {
            iconDatiPagamento.setImage(new Image(is));
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
