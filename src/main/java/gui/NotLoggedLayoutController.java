package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class NotLoggedLayoutController implements Initializable {

    @FXML
    protected void handleAccediButtonAction(ActionEvent event) {
        LoginPopup loginPopup = new LoginPopup();
        try {
            loginPopup.start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
