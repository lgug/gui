package gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import utils.HttpWrapper;
import utils.Manager;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginPopupController implements Initializable {

    private Stage primaryStage;

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    @FXML
    protected void handleAccediButtonEvent(MouseEvent event) {
        HttpWrapper httpWrapper = new HttpWrapper();
        String result = httpWrapper.login(usernameField.getText(), passwordField.getText());
        if (!(result.startsWith("UC") || result.startsWith("UR"))) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, result, ButtonType.OK);
            alert.show();
        } else {
            primaryStage.close();
            Manager.createIDFile(result);
            MainWindow.getInstance().setUserTypeLayout(result);
            MainWindow.getInstance().resetWindow();
        }
    }

    @FXML
    protected void handleRegistratiButtonEvent(MouseEvent event) {
        SignUpPopup signUpPopup = new SignUpPopup();
        try {
            signUpPopup.start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void handleForgotPasswordLabelEvent(MouseEvent event) {
        //TODO
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
