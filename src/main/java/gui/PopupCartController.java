package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import objects.FormaDiPagamento;
import objects.Ordine;
import objects.Prodotto;
import objects.UtenteCliente;
import utils.HttpWrapper;
import utils.Manager;
import utils.ProdottoSemplificato;
import utils.StringsUtils;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.*;
import java.util.*;


public class PopupCartController {

    private final ObservableList<FormaDiPagamento> st =FXCollections.observableArrayList(FormaDiPagamento.CARTA_CREDITO, FormaDiPagamento.CONSEGNA, FormaDiPagamento.PAYPAL);
    private final ObservableList<Prodotto> list = MainWindow.getList();
    private final ObservableList<ProdottoEsteso> listext = FXCollections.observableArrayList();
    private final TreeSet<Prodotto> ts1 = new TreeSet<>();
    private final TreeSet<ProdottoEsteso> ts2 = new TreeSet<>();
    private final ObservableList<String> ore =FXCollections.observableArrayList("08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00");
    private final List<String> orerest= Arrays.asList("08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00");
    @FXML private DatePicker dataConsegna;
    @FXML private ChoiceBox<String> choiceOra;
    @FXML private Stage primaryStage;
    @FXML private Label tot,caratteristiche,nome,marca,categoria,prezzo,pezzi;
    @FXML private TableView<ProdottoEsteso> table;
    @FXML private TableColumn<Prodotto,String> col1,col2,col3,col4;
    @FXML private ImageView image;
    @FXML private Spinner<Integer> spinner;
    @FXML private ChoiceBox<FormaDiPagamento> choicePagamento;
    private BigDecimal sum= new BigDecimal("0.0");
    private PaymentDataController paymentDataController;
    ErrorPageQuantita errorPageQuantita = new ErrorPageQuantita();
    private final ArrayList<ProdottoSemplificato> listSempl= new ArrayList<>();
    public UtenteCliente utente;
    private BigDecimal totale = new BigDecimal(0);

    public static class ProdottoEsteso extends Prodotto{
        public int numeroProdotti;
        public ProdottoEsteso(Prodotto prodotto, int numeroProdotti) {
            super(prodotto.getId(), prodotto.getNome(), prodotto.getMarca(), prodotto.getTag(), prodotto.getCategoria(),prodotto.getPrezzo(),prodotto.getDisponibilita(),prodotto.getQuantita(),prodotto.getImmagine());
            this.numeroProdotti = numeroProdotti;
        }
        public int getNumeroProdotti() { return numeroProdotti; }

        public void setNumeroProdotti(int numeroProdotti) { this.numeroProdotti = numeroProdotti; }
    }

    public void initialize() {
        choiceOra.setDisable(true);
        HttpWrapper http = new HttpWrapper();
        this.utente = (UtenteCliente) http.getUserByID(Manager.getUIDFromFile(), UtenteCliente.class);
        choiceOra.setItems(ore);
        choicePagamento.setItems(st);
        choicePagamento.setValue(utente.getPagamento());
        choicePagamento.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> checkPagamento());

        for(Prodotto prodotto:list){
            if(ts1.contains(prodotto)) {
                for(ProdottoSemplificato prodottoSemplificato: listSempl){
                    if(prodottoSemplificato.getId()==prodotto.getId())
                        prodottoSemplificato.setQuantita(prodottoSemplificato.getQuantita()+1);
                }
            }
            else{
                ts1.add(prodotto);
                listSempl.add(new ProdottoSemplificato(prodotto.getId(),1));
            }
        }
        list.clear();
        list.addAll(ts1);
        for(Prodotto prodotto: list){
            for(ProdottoSemplificato prodottoSemplificato:listSempl){
                if(prodotto.getId()==prodottoSemplificato.getId()){
                    ts2.add(new ProdottoEsteso(prodotto,prodottoSemplificato.getQuantita()));
                }
            }
        }
        listext.addAll(ts2);
        for(ProdottoEsteso prodottoEsteso:listext){
            BigDecimal prezzo = new BigDecimal(String.valueOf(prodottoEsteso.getPrezzo()));
            BigDecimal quantita = new BigDecimal(prodottoEsteso.getNumeroProdotti());
            sum =sum.add(prezzo.multiply(quantita));
            totale=sum;
        }
        ts1.clear();
        ts2.clear();
        tot.setText(StringsUtils.getPriceString(totale));
        col1.setCellValueFactory(new PropertyValueFactory<>("id"));
        col2.setCellValueFactory(new PropertyValueFactory<>("nome"));
        col3.setCellValueFactory(new PropertyValueFactory<>("prezzo"));
        col4.setCellValueFactory(new PropertyValueFactory<>("numeroProdotti"));
        table.setItems(listext);
        Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item.isBefore(LocalDate.now())) { setStyle("-fx-background-color: #f08080;");setDisable(true);setTextFill(Color.web("#ff0000")); }
                        if (item.isAfter(LocalDate.now())) { setStyle("-fx-background-color: #90ee90;"); setTextFill(Color.web("#008080"));}
                        if (item.equals(LocalDate.now())) { setStyle("-fx-background-color: #ff9999;");setDisable(true);setTextFill(Color.web("#ff0000")); }
                        if (item.equals(LocalDate.now().plusDays(1))) { setStyle("-fx-background-color: #ff9999;");setDisable(true);setTextFill(Color.web("#ff0000")); }
                        if (item.equals(LocalDate.now().plusDays(2))) { setStyle("-fx-background-color: #ff9999;");setDisable(true);setTextFill(Color.web("#ff0000")); }

                        for(Long date: http.getAllDeliveryDate()) {
                            if (MonthDay.from(item).equals(MonthDay.from(LocalDateTime.ofInstant(Instant.ofEpochMilli(date), ZoneId.systemDefault())))&& Year.from(item).equals(Year.from(LocalDateTime.ofInstant(Instant.ofEpochMilli(date), ZoneId.systemDefault())))) {
                                if(ore.size() == 0){
                                    setStyle("-fx-background-color: #f08080;");setDisable(true);setTextFill(Color.web("#ff0000"));
                                }
                                else{
                                    setStyle("-fx-background-color: #ffff99;");setTextFill(Color.web("#ff0000"));
                                }
                            }
                        }
                    }
                };
            }
        };
        dataConsegna.setDayCellFactory(dayCellFactory);
        dataConsegna.setShowWeekNumbers(true);

    }

    public void reset(){
        nome.setText("");
        marca.setText("");
        caratteristiche.setText("");
        categoria.setText("");
        prezzo.setText("");
        image.setImage(null);
        pezzi.setText("");
        spinner.setDisable(true);
    }

    public void handleSelectProductButtonAction() {
        spinner.setDisable(false);
        ObservableList<ProdottoEsteso> prodotto = table.getSelectionModel().getSelectedItems();
        if(prodotto.size() !=0){
            nome.setText(prodotto.get(0).getNome());
            marca.setText(prodotto.get(0).getMarca());
            caratteristiche.setText(prodotto.get(0).getCaratteristiche().toString());
            categoria.setText(prodotto.get(0).getCategoria().toString());
            prezzo.setText(StringsUtils.getPriceString(prodotto.get(0).getPrezzo()));
            image.setImage(Manager.decodeImage(prodotto.get(0).getImmagine()));
            pezzi.setText(String.valueOf(prodotto.get(0).getQuantita()));
            SpinnerValueFactory<Integer> spinnerQuantity = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, prodotto.get(0).getDisponibilita(), prodotto.get(0).getNumeroProdotti()) {
                @Override
                public void decrement(int steps) {
                    if ((prodotto.get(0).getNumeroProdotti() > 0) && (this.getValue() != 0)) {
                        totale = totale.add(new BigDecimal(String.valueOf(-prodotto.get(0).getPrezzo())));
                        tot.setText(StringsUtils.getPriceString(totale));
                    }
                    this.setValue(this.getValue() - 1);
                    prodotto.get(0).setNumeroProdotti(this.getValue());
                    table.refresh();
                    if(this.getValue()==0) {
                        list.remove(prodotto.get(0));
                        listext.remove(prodotto.get(0));

                        table.refresh();
                        if(listext.isEmpty())
                            reset();
                        else
                            handleSelectProductButtonAction();
                    }
                }

                @Override
                public void increment(int steps) {
                    if (prodotto.get(0).getNumeroProdotti() < prodotto.get(0).getDisponibilita()) {
                        totale = totale.add(new BigDecimal(String.valueOf(prodotto.get(0).getPrezzo())));
                        tot.setText(StringsUtils.getPriceString(totale));
                    }
                    this.setValue(this.getValue() + 1);
                    prodotto.get(0).setNumeroProdotti(this.getValue());
                    table.refresh();
                }
            };

            if(listext.isEmpty())
                spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,1,0));
            else
                spinner.setValueFactory(spinnerQuantity);

        }
    }

    public void handleBuyButtonAction() throws Exception {
        if (!choicePagamento.getValue().equals(utente.getPagamento()) && paymentDataController.getPaymentString() == null) {
            choicePagamento.setValue(utente.getPagamento());
            Alert alert = new Alert(Alert.AlertType.WARNING, "Mancano dei dati di pagamento...");
            alert.show();
            return;
        }

        boolean check = true;
        HttpWrapper http = new HttpWrapper();
        for (ProdottoEsteso prodotto : listext)
            if (prodotto.getNumeroProdotti() > http.getProductsPerId(String.valueOf(prodotto.getId())).getDisponibilita()) {
                errorPageQuantita.start(new Stage());
                ErrorPageQuantitaController controller=errorPageQuantita.getController();
                controller.getTextError().setText("Quantit\u00E0 non disponibile");
                list.remove(prodotto);
                listext.remove(prodotto);
                reset();
                initialize();
                check = false;
                break;
            }
        if (choiceOra.getValue() == null || choiceOra.getValue().equals("--:--")){
            errorPageQuantita.start(new Stage());
            ErrorPageQuantitaController controller = errorPageQuantita.getController();
            controller.getTextError().setText("Inserire orario di consegna");
            check = false;
        }
        if (dataConsegna.getValue()==null) {
            errorPageQuantita.start(new Stage());
            ErrorPageQuantitaController controller = errorPageQuantita.getController();
            controller.getTextError().setText("Selezionare la data");
            check = false;
        }
        if (check) {
            Ordine ord = new Ordine();
            long millis = (ore.indexOf(choiceOra.getValue())+8)*3600000;
            LocalDate localDate = dataConsegna.getValue();
            Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
            Date date = Date.from(instant);
            long dataora = date.getTime() + millis;
            ord.setDataConsegna(dataora);
            ord.setData(new Date().getTime());
            List<ProdottoSemplificato> listprodsempl = new ArrayList<>();
            for (ProdottoEsteso prodotto : listext) {
                listprodsempl.add(new ProdottoSemplificato(prodotto.getId(), prodotto.getNumeroProdotti()));
            }
            ord.setProdotti(listprodsempl);
            ord.setMetodoPagamento(choicePagamento.getValue());
            if (!choicePagamento.getValue().equals(utente.getPagamento()))
                ord.setDatiPagamento(paymentDataController.getPaymentString());
            else
                ord.setDatiPagamento(utente.getDatiDelPagamento());
            Random rand = new Random();
            ord.setID(String.valueOf(rand.nextInt()));
            String response = http.addOrdine(Manager.getUIDFromFile(), ord);
            if (response.equalsIgnoreCase("OK")) {
                if(utente.getTesseraFedelta()!=null) {
                    http.addTesseraPoint(utente.getTesseraFedelta().getId(), totale.intValue());
                }
            }
            list.clear();
            MainWindow.getInstance().resetWindow();
            primaryStage.close();
        }
    }

    private void checkPagamento() {
        if(!choicePagamento.getValue().equals(utente.getPagamento())){
            FXMLLoader fxmlLoader3 = new FXMLLoader(ClassLoader.getSystemClassLoader().getResource("dati_pagamento_popup.fxml"));

            try {
                Scene paymentScene = new Scene(fxmlLoader3.load());
                Stage paymentStage = new Stage();
                paymentStage.setTitle("Forma di pagamento");
                paymentStage.setScene(paymentScene);
                paymentStage.initModality(Modality.WINDOW_MODAL);
                paymentStage.initOwner(primaryStage);

                paymentDataController = fxmlLoader3.getController();
                paymentDataController.setPaymentStage(paymentStage);

                paymentDataController.selectScreen(choicePagamento.getValue());
                paymentDataController.setPaymentDataStatus(null);
                paymentDataController.setConfermaButton(null);
                paymentStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    public void updateDelivery() {
        choiceOra.setDisable(false);

        ore.setAll(orerest);
        choiceOra.setItems(ore);
        HttpWrapper http = new HttpWrapper();
        for (Long date : http.getAllDeliveryDate()) {
            LocalDate localDate = dataConsegna.getValue();
            Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
            Date datepick = Date.from(instant);
            for (String ora : ore) {
                long datepickl = datepick.getTime() + (ore.indexOf(ora) + 8) * 3600000;
                if (date == datepickl) {
                    choiceOra.getItems().set(ore.indexOf(ora),"--:--");
                }
            }
        }

    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

}