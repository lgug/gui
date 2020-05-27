package gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoggedUCLayoutController implements Initializable {

    @FXML
    private Button myProfileButton;
    @FXML
    private Button myOrdersButton;
    @FXML
    private Button optionsButton;

    @FXML
    protected void handleMyProfileButtonEvent(MouseEvent event) {
        UtenteClienteDetails utenteClienteDetails = new UtenteClienteDetails();
        utenteClienteDetails.start(new Stage());
    }

    @FXML
    protected void handleMyOrdersButtonEvent(MouseEvent event) {
        AllOrderPopup allOrderPopup = new AllOrderPopup();
        try {
            allOrderPopup.start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void handleOptionsButtonEvent(MouseEvent event) {
        //TODO
    }

    @FXML
    protected void handleCartButtonAction(MouseEvent mouseEvent) {
        PopupCart popupCart = new PopupCart();
        try {
            popupCart.start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
