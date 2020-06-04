package gui;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import objects.Ordine;
import objects.Prodotto;
import utils.HttpWrapper;
import utils.Manager;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class AllOrderUtenteResponsabile extends Application {
    @FXML
    private ListView userListView;
    @FXML
    private ListView productsListView;
    @FXML
    private ListView dateListView;
    @FXML
    private Label orderidLabel;
    @FXML
    private Label datadiconsegnaLabel;
    private ObservableList<String> userlist = FXCollections.observableArrayList();
    private ObservableList<String> datelist = FXCollections.observableArrayList();
    private ObservableList<String> utente = FXCollections.observableArrayList();
    private ObservableList<String> prodotti = FXCollections.observableArrayList();
    private HashMap<String,Long> dateMap = new HashMap<>();

    @FXML
    private void cercaDate(){
        dateListView.getItems().clear();
        utente = userListView.getSelectionModel().getSelectedItems();
        if(utente.size() != 0){
            HttpWrapper http = new HttpWrapper();
            List<Long> dateList = http.getAllOrdersDate(utente.get(0));
            for (Long i : dateList){
                Date data = new Date(i);
                String dataS = Manager.getDateFormat(data);
                dateMap.put(dataS,i);
                datelist.add(dataS);
            }
            dateListView.setItems(datelist);
        }

    }

    @FXML
    private void cercaProdotti(){
        productsListView.getItems().clear();
        ObservableList<String> data = dateListView.getSelectionModel().getSelectedItems();
        if(data.size() != 0){
            HttpWrapper http = new HttpWrapper();
            Ordine ordine = http.getAllProductsByOrder(utente.get(0),dateMap.get(data.get(0)));
            List<Prodotto> prodottoList = ordine.getProdotto();
            for(Prodotto i:prodottoList){
                String s = i.getId()+" ---> "+ i.getNome();
                prodotti.add(s);
            }
            productsListView.setItems(prodotti);
            orderidLabel.setText(ordine.getID());
            datadiconsegnaLabel.setText(Manager.getDateFormat(new Date(ordine.getDataConsegna())));
        }
    }

    @FXML
    private void initialize() throws IOException {
        HttpWrapper http = new HttpWrapper();
        List<String> uderid = http.getAllUserID(Manager.getUIDFromFile());
        for (String i:uderid){
            userlist.add(i);
        }
        userListView.setItems(userlist);

    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemClassLoader().getResource("allOrderUtenteResponsabile.fxml"));
        AnchorPane pane = loader.load();
        Scene scene = new Scene(pane);
        primaryStage.setTitle("Tutti gli Ordini");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
