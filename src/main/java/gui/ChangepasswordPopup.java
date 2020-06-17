package gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import utils.FieldChecker;
import utils.HttpWrapper;
import utils.Manager;


public class ChangepasswordPopup extends Application{
    @FXML
    private TextField oldPasswordField;
    @FXML
    private TextField newPasswordField;
    @FXML
    private Button changeButton;
    @FXML
    private Label responseLabel;


    @FXML
    private void changePassword(ActionEvent event){
        if (!(oldPasswordField.getText().isEmpty()  && newPasswordField.getText().isEmpty())){
            HttpWrapper http = new HttpWrapper();
            if(FieldChecker.validatePassword(newPasswordField.getText())) {
                String r = http.changePassword(Manager.getUIDFromFile(), oldPasswordField.getText(), newPasswordField.getText());
                if(r.equalsIgnoreCase("OK")){
                    responseLabel.setText("PASSWORD CAMBIATA CON SUCCESSO");
                    responseLabel.setTextFill(Paint.valueOf("green"));
                } else {
                    responseLabel.setText(r);
                    responseLabel.setTextFill(Paint.valueOf("red"));
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, FieldChecker.passwordRequirements, ButtonType.OK);
                alert.show();
            }
        }
        else{
            responseLabel.setText("INSERISCI I VALORI NEI CAMPI");
            responseLabel.setTextFill(Paint.valueOf("red"));
        }
    }



    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemClassLoader().getResource("changepassword_popup.fxml"));
        AnchorPane pane = loader.load();
        Scene scene = new Scene(pane);
        primaryStage.setTitle("Tutti gli Ordini");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

}
