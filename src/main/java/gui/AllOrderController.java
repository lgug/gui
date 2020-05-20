package gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.TilePane;
import objects.Prodotto;
import objects.Utente;
import utils.HttpWrapper;

import java.util.Iterator;
import java.util.List;

public class AllOrderController {
    @FXML
    private TilePane tilePane1;
    @FXML
    private void initialize(){
        HttpWrapper http = new HttpWrapper();
        List<Prodotto> prodList = http.getAllOrders("1");
        int i = 0;
        Iterator it = prodList.iterator();
        while (it.hasNext()) {
            Label lab1 = new Label(prodList.get(i).getNome());
            tilePane1.getChildren().addAll(lab1);
            it.next();
            i++;
        }

    }

}
