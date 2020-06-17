package gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import objects.Utente;
import utils.FieldChecker;
import utils.HttpWrapper;

import java.net.URL;
import java.util.ResourceBundle;

public class ForgotPasswordPopupController implements Initializable {

    private Stage stage;
    private Utente pendingUtente;

    @FXML
    private Label statusUser;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField newPassword;
    @FXML
    private HBox newPasswordHBox;
    @FXML
    private Button confirmButton;

    @FXML
    protected void handleVerifyUserButtonEvent(MouseEvent mouseEvent) {
        HttpWrapper httpWrapper = new HttpWrapper();
        Utente u = httpWrapper.getUserByEmail(emailField.getText());
        if (u == null) {
            statusUser.setText("Utente non registrato!");
            statusUser.setTextFill(Color.RED);
            newPasswordHBox.setDisable(true);
            confirmButton.setDisable(true);
        } else {
            statusUser.setText("Utente verificato!");
            statusUser.setTextFill(Color.GREEN);
            newPasswordHBox.setDisable(false);
            confirmButton.setDisable(false);
            pendingUtente = u;
        }
        statusUser.setVisible(true);
    }

    @FXML
    protected void handleConfirmUserButtonEvent(MouseEvent mouseEvent) {
        if (FieldChecker.validatePassword(newPassword.getText())) {
            HttpWrapper httpWrapper = new HttpWrapper();
            String s = httpWrapper.changePassword(pendingUtente.getId(), pendingUtente.getPassword(), newPassword.getText());
            if (s.equals("Error")) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Errore interno nel recupero dell'account!");
                alert.show();
            } else {
                stage.close();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, FieldChecker.passwordRequirements, ButtonType.OK);
            alert.show();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        newPasswordHBox.setDisable(true);
        confirmButton.setDisable(true);
        statusUser.setVisible(false);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
