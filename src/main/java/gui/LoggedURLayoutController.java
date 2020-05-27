package gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoggedURLayoutController implements Initializable {

    @FXML
    private Button myProfileButton;
    @FXML
    private Button myHistoryButton;
    @FXML
    private Button optionsButton;
    @FXML
    private Button addButton;

    @FXML
    protected void handleMyProfileButtonEvent(MouseEvent event) {
        UtenteResponsabileDetails utenteResponsabileDetails = new UtenteResponsabileDetails();
        utenteResponsabileDetails.start(new Stage());
    }

    @FXML
    protected void handleMyHistoryButtonEvent(MouseEvent event) {
        //TODO
    }

    @FXML
    protected void handleOptionsButtonEvent(MouseEvent event) {
        //TODO
    }

    @FXML
    protected void handleAddButtonAction(MouseEvent event) {
        //TODO
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
