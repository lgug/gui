package gui;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import objects.Ordine;
import objects.Prodotto;
import utils.HttpWrapper;
import utils.Manager;
import utils.ProdottoSemplificato;
import utils.StringsUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

public class AllOrderPopup extends Application implements Initializable {

    private Map<ProductLayoutOrderController, HBox> controllerMap;

    @FXML
    public Label statoOrdine;
    public TableView<Ordine> table;
    public TableColumn<Ordine,String>  col1;
    public TableColumn<Ordine,String>   col2;
    public TableColumn<Ordine,String>   col4;
    @FXML
    private GridPane contentGridPane;
    @FXML
    private Label totaleLabel;
    @FXML
    private Label orderID;



    public AllOrderPopup() {
    }

    @FXML
    public void handleSelectOrderButtonAction() {
        controllerMap = new HashMap<>();
        ObservableList<Ordine> ordini = table.getSelectionModel().getSelectedItems();
        if (ordini.size()!=0) {
            Ordine ordine = ordini.get(0);
            clearPanel();
            BigDecimal sum = new BigDecimal("0.0");
            List<ProdottoSemplificato> prodottoSemplificatoList = ordine.getProdotti();
            List<Prodotto> prodottoList = ordine.getProdotto();
            for (Prodotto prodotto : prodottoList) {
                FXMLLoader fxmlLoader = new FXMLLoader(ClassLoader.getSystemClassLoader().getResource("product_layout_order.fxml"));
                try {
                    HBox hBox = fxmlLoader.load();
                    ProductLayoutOrderController productLayoutOrderController = fxmlLoader.getController();
                    productLayoutOrderController.setProdotto(prodotto);
                    productLayoutOrderController.setQuantity(prodotto.getQuantita()); //TODO quantita is the wrong datum
                    controllerMap.put(productLayoutOrderController, hBox);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                BigDecimal prezzo2 = new BigDecimal(String.valueOf(prodotto.getPrezzo()));
                BigDecimal quantita2 = new BigDecimal(prodotto.getQuantita()); //TODO quantita is the wrong datum
                sum = sum.add(prezzo2.multiply(quantita2));
            }

            int row = 0;
            int col = 0;
            for (ProductLayoutOrderController ploc: controllerMap.keySet()) {
                ploc.generate();
                contentGridPane.add(controllerMap.get(ploc), col, row);
                if (col == 1) {
                    col = 0;
                    row++;
                } else {
                    col++;
                }
            }

            orderID.setText("ID ORDINE: " + ordine.getID());
            totaleLabel.setText("Totale: " + StringsUtils.getPriceString(sum));
            Date dates = new Date();
            if (ordine.getDataConsegna() > dates.getTime()) {
                statoOrdine.setText("In preparazione");
                statoOrdine.setTextFill(Color.web("#FF2D00"));
            }
            if (ordine.getDataConsegna() - 172800000 < dates.getTime()) {
                statoOrdine.setText("In consegna");
                statoOrdine.setTextFill(Color.web("#B3B900"));
            }
            if (ordine.getDataConsegna() < dates.getTime()) {
                statoOrdine.setText("Consegnato");
                statoOrdine.setTextFill(Color.web("#00B908"));
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        controllerMap = new HashMap<>();
        List<Ordine> ordines = new ArrayList<>();
        HttpWrapper http = new HttpWrapper();
        List<Long> dates = http.getAllOrdersDate(Manager.getUIDFromFile());
        for(Long date: dates) {
            Ordine ordine = http.getAllProductsByOrder(Manager.getUIDFromFile(), date);
            Date data = new Date(ordine.getDataConsegna());
            SimpleDateFormat DateFor = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            String dataS = DateFor.format(data);
            ordine.setConsegna(dataS);
            data.setTime(ordine.getData());
            dataS = DateFor.format(data);
            ordine.setAcquistato(dataS);
            ordines.add(ordine);
        }
        ObservableList<Ordine> obsOrdine = FXCollections.observableArrayList(ordines);
        col4.setCellValueFactory(new PropertyValueFactory<>("ID"));
        col1.setCellValueFactory(new PropertyValueFactory<>("acquistato"));
        col2.setCellValueFactory(new PropertyValueFactory<>("consegna"));
        table.setItems(obsOrdine);
    }

    @FXML
    private void clearPanel(){
        contentGridPane.getChildren().clear();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemClassLoader().getResource("popupOrder.fxml"));
        AnchorPane pane = loader.load();
        Scene scene = new Scene(pane);
        primaryStage.setTitle("Tutti gli Ordini");
        primaryStage.setScene(scene);
        primaryStage.show();

    }


}
