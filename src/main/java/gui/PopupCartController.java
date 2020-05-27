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
import javafx.stage.Stage;
import objects.*;

import java.io.File;
import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;


import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import utils.HttpWrapper;
import utils.ProdottoSemplificato;


public class PopupCartController<TextView> {
    public Label tot;
    public Button Continua;

    public TableView<Prodotto> table;
    public TableColumn<Prodotto,String> col1;
    public TableColumn<Prodotto,String> col2;
    public TableColumn<Prodotto,String> col3;

    public ObservableList<String> st =FXCollections.observableArrayList( "PayPal", "Alla consegna", "Carta di credito" );
    public Label caratteristiche;
    public Label nome;
    public Label marca;
    public Label categoria;
    public Label prezzo;
    public ImageView image;
    public Spinner spinner;
    public ChoiceBox<String> choicePagamento;
    private UtenteCliente utenteCliente;

    private ObservableList<Prodotto> list = MainWindow.getList();

    TreeSet<Prodotto> ts1 = new TreeSet<Prodotto>();

    public void initialize() {

        choicePagamento.setItems(st);
        if(utenteCliente!= null)
            choicePagamento.setValue(utenteCliente.getPagamento().toString());

        double sum=0.0;

        for(Prodotto prodotto:list){
            if(ts1==null)
                ts1.add(prodotto);

            if(ts1.contains(prodotto))
                ts1.floor(prodotto).setQuantita(prodotto.getQuantita()+1);

            else
                ts1.add(prodotto);
        }
        list.removeAll(list);
        list.addAll(ts1);
        for(Prodotto prodotto:list){
            if(prodotto.getQuantita()<= prodotto.getDisponibilita()){
                sum += prodotto.getPrezzo();
            }
        }
        ts1.removeAll(ts1);
        System.out.println(ts1);
        tot.setText(String.valueOf(new BigDecimal(sum).setScale(2 , BigDecimal.ROUND_UP).doubleValue()));
        col1.setCellValueFactory(new PropertyValueFactory<>("id"));
        col2.setCellValueFactory(new PropertyValueFactory<>("nome"));
        col3.setCellValueFactory(new PropertyValueFactory<>("prezzo"));
        table.setItems(list);
    }

    public void handleSelectProductButtonAction(MouseEvent mouseEvent) {
        ObservableList<Prodotto> prodotto = table.getSelectionModel().getSelectedItems();
        if(prodotto.size() !=0){
            nome.setText(prodotto.get(0).getNome());
            marca.setText(prodotto.get(0).getMarca());
            caratteristiche.setText(prodotto.get(0).getCaratteristiche().toString());
            categoria.setText(prodotto.get(0).getCategoria().toString());
            prezzo.setText(String.valueOf(prodotto.get(0).getPrezzo()));
            image.setImage(new Image(prodotto.get(0).getImmagine()));

            SpinnerValueFactory<Integer> spinnerQuantity = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, prodotto.get(0).getDisponibilita(), prodotto.get(0).getQuantita()) {
                @Override
                public void decrement(int steps) {
                    if ((prodotto.get(0).getQuantita() > 0) && (this.getValue() != 0)) {
                        double totale = Double.parseDouble(tot.getText());
                        totale -= prodotto.get(0).getPrezzo();
                        tot.setText(String.valueOf(new BigDecimal(totale).setScale(2 , BigDecimal.ROUND_UP).doubleValue()));
                    }
                    this.setValue(this.getValue() - 1);
                    prodotto.get(0).setQuantita(this.getValue());
                }

                @Override
                public void increment(int steps) {
                    if (prodotto.get(0).getQuantita() < prodotto.get(0).getDisponibilita()) {
                        double totale = Double.parseDouble(tot.getText());
                        totale += prodotto.get(0).getPrezzo();
                        tot.setText(String.valueOf(new BigDecimal(totale).setScale(2 , BigDecimal.ROUND_UP).doubleValue()));
                    }
                    this.setValue(this.getValue() + 1);
                    prodotto.get(0).setQuantita(this.getValue());
                }
            };
            spinner.setValueFactory(spinnerQuantity);
        }
    }

    public void handleBuyButtonAction(MouseEvent mouseEvent) throws Exception {
        boolean check= true;
        for (Prodotto prodotto : list)
            if (prodotto.getQuantita() > prodotto.getDisponibilita()) {
                ErrorPageQuantita errorPageQuantita = new ErrorPageQuantita();
                errorPageQuantita.start(new Stage());
                list.remove(prodotto);
                initialize();
                check = false;
            }
        if (check) {
            Ordine ord = new Ordine();
            ord.setData(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
            List<ProdottoSemplificato> listprodsempl = new ArrayList<ProdottoSemplificato>();
            for (Prodotto prodotto : list) {
                listprodsempl.add(new ProdottoSemplificato(prodotto.getId(), prodotto.getQuantita()));
            }
            ord.setProdotti(listprodsempl);
            Random rand = new Random();
            ord.setID(String.valueOf(rand.nextInt()));
            HttpWrapper http = new HttpWrapper();
            http.addOrdine("2", ord);
        }
    }
}