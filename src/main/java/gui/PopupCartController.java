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
import java.text.SimpleDateFormat;
import java.util.*;


import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import objects.Ordine;
import objects.Prodotto;
import utils.HttpWrapper;
import utils.ProdottoSemplificato;


public class PopupCartController<TextView> implements Initializable {
    public Label tot;
    public Button Continua;
    public Button Indetro;

    public TableView<Prodotto> table;
    public TableColumn<Prodotto,String> col1;
    public TableColumn<Prodotto,String> col2;
    public TableColumn<Prodotto,String> col3;

    public Label caratteristiche;
    public Label nome;
    public Label marca;
    public Label categoria;
    public Label prezzo;
    public ImageView image;
    public Spinner spinner;

    private ObservableList<Prodotto> list = FXCollections.observableArrayList(
            new Prodotto(1,"banana", "chiquita", CaratteristicheProdotto.BIOLOGICO, Categoria.FRUTTA_VERDURA,2,10,1,"bananas.png"),
            new Prodotto(2,"cherry", "fruttissima",CaratteristicheProdotto.VEGAN,Categoria.FRUTTA_VERDURA,10,4,1,"cherries.png"),
            new Prodotto(3,"sushi", "china express", CaratteristicheProdotto.BIOLOGICO, Categoria.FRUTTA_VERDURA,2,7,1,"sushi.png"),
            new Prodotto(1,"banana", "chiquita", CaratteristicheProdotto.BIOLOGICO, Categoria.FRUTTA_VERDURA,2,10,1,"bananas.png")
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
            public void increment(int steps) { prodotto.get(0).setQuantita(this.getValue()); }
        };
        SpinnerValueFactory<Integer> spinnerQuantity = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, prodotto.get(0).getDisponibilita(), prodotto.get(0).getQuantita());
        spinner.setValueFactory(spinnerQuantity);



    }

    public void buy(MouseEvent mouseEvent) {
        Ordine ord = new Ordine();
        ord.setData(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        List<ProdottoSemplificato> listprodsempl = new ArrayList<ProdottoSemplificato>();
        for(Prodotto prodotto :list){
            listprodsempl.add(new ProdottoSemplificato(prodotto.getId(),prodotto.getQuantita()));
        }
        ord.setProdotti(listprodsempl);
        ord.setID("22");
        HttpWrapper http = new HttpWrapper();
        http.addOrdine(ord.getID(), ord);

    }
}