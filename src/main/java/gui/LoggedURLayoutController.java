package gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import utils.Manager;

import java.net.URL;
import java.util.ResourceBundle;

public class LoggedURLayoutController implements Initializable {



    @FXML
    protected void handleMyProfileButtonEvent() {
        UtenteResponsabileDetails utenteResponsabileDetails = new UtenteResponsabileDetails();
        utenteResponsabileDetails.start(new Stage());
    }

    @FXML
    protected void handleMyHistoryButtonEvent() throws Exception {
        AllOrderUtenteResponsabile allorderPopup = new AllOrderUtenteResponsabile();
        allorderPopup.start(new Stage());
    }

    @FXML
    protected void handleOptionsButtonEvent() {
        //TODO
    }

    @FXML
    protected void handleEsciMenuEvent() {
        Manager.createIDFile("");
        MainWindow.getInstance().setUserTypeLayout(Manager.getUIDFromFile());
        MainWindow.getInstance().resetWindow();
    }

    @FXML
    protected void handleAddButtonAction() {
        ManageProducts manageProducts = new ManageProducts();
        manageProducts.start(new Stage());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
