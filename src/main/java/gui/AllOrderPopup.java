package gui;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import objects.Ordine;
import objects.Prodotto;
import utils.HttpWrapper;
import utils.Manager;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

public class AllOrderPopup extends Application {
    @FXML
    public Label statoOrdine;
    public TableView<Ordine> table;
    public TableColumn<Ordine,String>  col1;
    public TableColumn<Ordine,String>   col2;
    public TableColumn<Ordine,String>   col4;
    @FXML
    private TilePane tilePane1;
    @FXML
    private ChoiceBox choiceBox;
    @FXML
    private Button okButton;
    @FXML
    private Label totaleLabel;
    @FXML
    private Label orderID;
    @FXML
    private Label dataConsegna;

    private HashMap<String,Long> dateMap = new HashMap<>();

    public AllOrderPopup() {
    }

    @FXML
    public void handleSelectOrderButtonAction(MouseEvent mouseEvent) {
        ObservableList<Ordine> ordini = table.getSelectionModel().getSelectedItems();
        if (ordini.size()!=0) {
            Ordine ordine = ordini.get(0);
            clearPanel();
            BigDecimal sum = new BigDecimal("0.0");
            HttpWrapper http = new HttpWrapper();
            Ordine ordines = http.getAllProductsByOrder(Manager.getUIDFromFile(), ordini.get(0).getData());
            List<Prodotto> prodottoList = ordine.getProdotto();
            int i = 0;
            Iterator it = prodottoList.iterator();
            while (it.hasNext()) {
                Label lab1 = new Label(prodottoList.get(i).getNome());
                lab1.setFont(Font.font(17));
                lab1.setStyle("-fx-font-weight: bold");

                Label lab2 = new Label(prodottoList.get(i).getMarca());
                lab2.setFont(Font.font(15));

                Label prezzo = new Label(Manager.EURO + String.valueOf(prodottoList.get(i).getPrezzo()));
                prezzo.setFont(Font.font(20));


                Label quantita = new Label("Quantit\u00E0: " + String.valueOf(prodottoList.get(i).getQuantita()));

                Image image = new Image(ClassLoader.getSystemClassLoader().getResourceAsStream(prodottoList.get(i).getImmagine()));
                ImageView img = new ImageView(image);
                img.setFitHeight(80);
                img.setFitWidth(80);

                VBox vbox = new VBox(10, lab1, lab2, quantita, prezzo);
                HBox hbox = new HBox(20, img, vbox);
                tilePane1.getChildren().add(hbox);

                BigDecimal prezzo2 = new BigDecimal(String.valueOf(prodottoList.get(i).getPrezzo()));
                BigDecimal quantita2 = new BigDecimal(prodottoList.get(i).getQuantita());
                sum = sum.add(prezzo2.multiply(quantita2));

                it.next();
                i++;
            }

            orderID.setText("ID ORDINE: " + ordine.getID());
            totaleLabel.setText("Totale: " + sum + Manager.EURO);
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

    @FXML
    private void initialize() throws IOException {
        List<Ordine> ordines = new ArrayList<>();
        HttpWrapper http = new HttpWrapper();
        List<Long> dates = http.getAllOrdersDate(Manager.getUIDFromFile());
        for(Long date: dates) {
            Ordine ordine = http.getAllProductsByOrder(Manager.getUIDFromFile(), date);
            Date data = new Date(ordine.getDataConsegna());
            SimpleDateFormat DateFor = new SimpleDateFormat("dd/MM/yyyy");
            String dataS = DateFor.format(data);
            ordine.setConsegna(dataS);
            data.setTime(ordine.getData());
            dataS = Manager.getDateFormat(data);
            ordine.setAcquistato(dataS);
            ordines.add(ordine);
        }
        ObservableList<Ordine> obsOrdine = FXCollections.observableArrayList(ordines);
        col4.setCellValueFactory(new PropertyValueFactory<>("ID"));
        col1.setCellValueFactory(new PropertyValueFactory<>("acquistato"));
        col2.setCellValueFactory(new PropertyValueFactory<>("consegna"));
        table.setItems((ObservableList) obsOrdine);

    }
    @FXML
    private void clearPanel(){
        tilePane1.getChildren().clear();
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
