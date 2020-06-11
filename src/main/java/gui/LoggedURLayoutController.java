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

public class LoggedURLayoutController implements Initializable {

    @FXML
    private Button myProfileButton;
    @FXML
    private Button myHistoryButton;
    @FXML
    private MenuButton optionsButton;
    @FXML
    private Button addButton;

    @FXML
    protected void handleMyProfileButtonEvent(MouseEvent event) {
        UtenteResponsabileDetails utenteResponsabileDetails = new UtenteResponsabileDetails();
        utenteResponsabileDetails.start(new Stage());
    }

    @FXML
    protected void handleMyHistoryButtonEvent(MouseEvent event) throws Exception {
        AllOrderUtenteResponsabile allorderPopup = new AllOrderUtenteResponsabile();
        allorderPopup.start(new Stage());
    }

    @FXML
    protected void handleOptionsButtonEvent(MouseEvent event) {
        //TODO
    }

    @FXML
    protected void handleEsciMenuEvent(ActionEvent event) {
        Manager.createIDFile("");
        MainWindow.getInstance().setUserTypeLayout(Manager.getUIDFromFile());
        MainWindow.getInstance().resetWindow();
    }

    @FXML
    protected void handleAddButtonAction(MouseEvent event) {
        ManageProducts manageProducts = new ManageProducts();
        manageProducts.start(new Stage());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
