package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import objects.CaratteristicheProdotto;
import objects.Categoria;

import java.io.File;
import java.net.URL;
import java.util.*;


import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import objects.Prodotto;


public class PopupCartController<TextView> implements Initializable {
    @FXML public Label tot;
    public Button Continua;
    public Button Indetro;

    @FXML public TableView<Prodotto> table;
    @FXML public TableColumn<Prodotto,String> col1;
    @FXML public TableColumn<Prodotto,String> col2;
    @FXML public TableColumn<Prodotto,String> col3;

    public Label caratteristiche;
    public Label nome;
    public Label marca;
    public Label categoria;
    public Label prezzo;
    public ImageView image;
    public Spinner spinner;

    private ObservableList<Prodotto> list = FXCollections.observableArrayList(
            new Prodotto(1,"banana", "chiquita", CaratteristicheProdotto.BIOLOGICO, Categoria.FRUTTA_VERDURA,2,1,10,"bananas.png"),
            new Prodotto(2,"cherry", "fruttissima",CaratteristicheProdotto.VEGAN,Categoria.FRUTTA_VERDURA,10,1,4,"cherries.png"),
            new Prodotto(3,"sushi", "china express", CaratteristicheProdotto.BIOLOGICO, Categoria.FRUTTA_VERDURA,2,1,10,"sushi.png"),
            new Prodotto(1,"banana", "chiquita", CaratteristicheProdotto.BIOLOGICO, Categoria.FRUTTA_VERDURA,2,1,10,"bananas.png")
            );
    TreeSet<Prodotto> ts1 = new TreeSet<Prodotto>();
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        int sum=0;
        for(Prodotto prodotto:list){
            sum+= prodotto.getPrezzo();
            if(ts1==null)

                ts1.add(prodotto);

            if(ts1.contains(prodotto))
                ts1.floor(prodotto).setQuantita(prodotto.getQuantita()+1);

            else
                ts1.add(prodotto);
        }
        list.removeAll(list);
        list.addAll(ts1);
        System.out.println(ts1);
        tot.setText(String.valueOf(sum));
        col1.setCellValueFactory(new PropertyValueFactory<>("id"));
        col2.setCellValueFactory(new PropertyValueFactory<>("nome"));
        col3.setCellValueFactory(new PropertyValueFactory<>("prezzo"));
        table.setItems(list);
    }

    public void Action(MouseEvent mouseEvent) {
        ObservableList<Prodotto> prodotto = table.getSelectionModel().getSelectedItems();

        nome.setText(prodotto.get(0).getNome());
        marca.setText(prodotto.get(0).getMarca());
        caratteristiche.setText(prodotto.get(0).getCaratteristiche().toString());
        categoria.setText(prodotto.get(0).getCategoria().toString());
        prezzo.setText(String.valueOf(prodotto.get(0).getPrezzo()));
        image.setImage(new Image (prodotto.get(0).getImmagine()));
        new SpinnerValueFactory<Integer>() {
            @Override
            public void decrement(int steps) { prodotto.get(0).setQuantita(this.getValue()); }

            @Override
            public void increment(int steps) { prodotto.get(0).setQuantita(this.getValue());
                System.out.println(prodotto.get(0).getQuantita());
            }
        };
        SpinnerValueFactory<Integer> spinnerQuantity = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, prodotto.get(0).getDisponibilita(), prodotto.get(0).getQuantita());
        spinner.setValueFactory(spinnerQuantity);



    }


}