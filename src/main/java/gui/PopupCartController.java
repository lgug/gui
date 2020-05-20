package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import objects.Prodotto;

import java.util.ArrayList;

public class PopupCartController {
    @FXML public Label tot;
    public Button Continua;
    public Button Indetro;
   @FXML private ListView<String> ProductList;
    public Label caratteristiche;
    public Label nome;
    public Label marca;
    public Label categoria;
    public Label prezzo;

    ObservableList list= FXCollections.observableArrayList();
    ArrayList<Prodotto> prodotti = new ArrayList<Prodotto>();

    @FXML
    private void initialize(/*ArrayList<Prodotto> prodotti*/) {
        int totale= 10+2;
        tot.setText(String.valueOf(totale));
        loadProduct(/*prodotti*/);
    }
    private void loadProduct(/*ArrayList<Prodotto> prodotti*/ ){
        list.removeAll(list);
        /*
        for(int i=0; i<prodotti.size();i++){
            list.add(prodotti.get(i).getNome());
        }
         */
        String a = "pane";
        String b = "marmellata";
        list.addAll(a,b);
        ProductList.getItems().addAll(list);

    }

    public void displaySelected(MouseEvent mouseEvent) {
        ObservableList<String> prodotto = ProductList.getSelectionModel().getSelectedItems();
        nome.setText(prodotto.toString());

    }
}