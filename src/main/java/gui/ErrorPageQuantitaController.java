package gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class ErrorPageQuantitaController {
    @FXML private Button button;
    @FXML private ImageView warning;
    @FXML private Label textError;

    public void initialize () {
        warning.setImage(new Image("wrong.png"));
        textError.setText("Quantita non disonibile");
    }

    public void handleOkButtonAction() {
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();
    }

    public Label getTextError() {
        return textError;
    }

    public void setPrimaryStage() {
    }

}
