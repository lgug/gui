package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import utils.Manager;

import java.net.URL;
import java.util.ResourceBundle;

public class LoggedUCLayoutController implements Initializable {

    @FXML
    private Button myProfileButton;
    @FXML
    private Button myOrdersButton;
    @FXML
    private MenuButton optionsButton;

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
    protected void handleEsciMenuEvent(ActionEvent event) {
        Manager.createIDFile("");
        MainWindow.setUserTypeLayout(Manager.getUIDFromFile());
    }

    @FXML
    protected void handleCartButtonAction(MouseEvent mouseEvent) {
        MainWindow.getList().addAll(MainWindow.getArray());

        PopupCart popupCart = new PopupCart();
        try {
            popupCart.start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        MainWindow.getArray().clear();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
