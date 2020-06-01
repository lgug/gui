package gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ErrorPageQuantitaController {
    private Stage primaryStage;
    @FXML public Button button;
    @FXML public ImageView warning;
    @FXML public Label textError;
    public String errore;

    public void initialize () {
        warning.setImage(new Image("wrong.png"));
        textError.setText("Quantita non disonibile");
    }

    public void handleOkButtonAction(MouseEvent mouseEvent) {
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();
    }

    public void setErrore(String errore) {
        this.errore = errore;
    }

    public void setTextError(Label textError) {
        this.textError = textError;
    }

    public Label getTextError() {
        return textError;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

}
