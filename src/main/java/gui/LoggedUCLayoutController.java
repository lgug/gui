package gui;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import utils.Manager;

import java.net.URL;
import java.util.ResourceBundle;

public class LoggedUCLayoutController implements Initializable {


    @FXML protected void handleMyProfileButtonEvent() {
        UtenteClienteDetails utenteClienteDetails = new UtenteClienteDetails();
        utenteClienteDetails.start(new Stage());
    }

    @FXML protected void handleMyOrdersButtonEvent() {
        AllOrderPopup allOrderPopup = new AllOrderPopup();
        try {
            allOrderPopup.start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML protected void handleOptionsButtonEvent() {
        //TODO
    }

    @FXML protected void handleEsciMenuEvent() {
        Manager.createIDFile("");
        MainWindow.getInstance().setUserTypeLayout(Manager.getUIDFromFile());
        MainWindow.getInstance().resetWindow();
    }

    @FXML protected void handleCartButtonAction() {
        MainWindow.getList().addAll(MainWindow.getArray());

        PopupCart popupCart = new PopupCart();
        try {
            popupCart.start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        MainWindow.getArray().clear();
    }

    @Override public void initialize(URL location, ResourceBundle resources) {

    }
}
