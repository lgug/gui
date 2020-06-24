package gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import objects.RuoloResponsabile;

import java.net.URL;
import java.util.ResourceBundle;

public class    UtenteResponsabileController implements Initializable {

    private RuoloResponsabile ruoloResponsabile;

    @FXML
    private TextField matricolaField;
    @FXML
    private ChoiceBox<RuoloResponsabile> ruoloResponsabileBox;

    public String getMatricola() {
        if (matricolaField != null)
            return matricolaField.getText();
        else return null;
    }

    public RuoloResponsabile getRuoloResponsabile() {
        if (ruoloResponsabileBox != null)
            return ruoloResponsabile;
        else return null;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ruoloResponsabileBox.getItems().addAll(RuoloResponsabile.values());
        ruoloResponsabileBox.setValue(RuoloResponsabile.DIPENDENTE);
        ruoloResponsabile = ruoloResponsabileBox.getValue();
        ruoloResponsabileBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
                ruoloResponsabile = newValue);
    }
}
