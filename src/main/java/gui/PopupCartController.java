package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import objects.FormaDiPagamento;
import objects.Ordine;
import objects.Prodotto;
import objects.UtenteCliente;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.*;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import utils.HttpWrapper;
import utils.Manager;
import utils.ProdottoSemplificato;


public class PopupCartController{
    private final ObservableList<String> st =FXCollections.observableArrayList( FormaDiPagamento.CARTA_CREDITO.toString(), FormaDiPagamento.CONSEGNA.toString(),FormaDiPagamento.PAYPAL.toString());
    private final ObservableList<Prodotto> list = MainWindow.getList();
    private final TreeSet<Prodotto> ts1 = new TreeSet<>();
    public DatePicker dataConsegna;
    private Stage primaryStage;
    public Label tot,caratteristiche,nome,marca,categoria,prezzo;
    public TableView<Prodotto> table;
    public TableColumn<Prodotto,String> col1,col2,col3,col4;
    public Button Continua;
    public ImageView image;
    public Spinner<Integer> spinner;
    public ChoiceBox<String> choicePagamento;
    private BigDecimal sum= new BigDecimal("0.0");
    ErrorPageQuantitaController controller;
    public UtenteCliente utente;

    public void initialize() {
        HttpWrapper http = new HttpWrapper();
        this.utente = (UtenteCliente) http.getUserByID(Manager.getUIDFromFile(), UtenteCliente.class);

        choicePagamento.setItems(st);
        choicePagamento.setValue(utente.getPagamento().toString());

        for(Prodotto prodotto:list){
            if(ts1.contains(prodotto))
                Objects.requireNonNull(ts1.floor(prodotto)).setQuantita(prodotto.getQuantita()+1);

            else
                ts1.add(prodotto);
        }
        list.clear();
        list.addAll(ts1);
        for(Prodotto prodotto:list){

                BigDecimal prezzo = new BigDecimal(String.valueOf(prodotto.getPrezzo()));
                BigDecimal quantita = new BigDecimal(prodotto.getQuantita());
                sum =sum.add(prezzo.multiply(quantita));

        }
        ts1.clear();
        System.out.println(ts1);
        tot.setText(String.valueOf(sum));
        col1.setCellValueFactory(new PropertyValueFactory<>("id"));
        col2.setCellValueFactory(new PropertyValueFactory<>("nome"));
        col3.setCellValueFactory(new PropertyValueFactory<>("prezzo"));
        col4.setCellValueFactory(new PropertyValueFactory<>("quantita"));
        table.setItems(list);
    }

    public void handleSelectProductButtonAction() {
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
                        BigDecimal totale = new BigDecimal(tot.getText());
                        totale = totale.add(new BigDecimal(String.valueOf(-prodotto.get(0).getPrezzo())));
                        tot.setText(String.valueOf(totale));
                    }
                    this.setValue(this.getValue() - 1);
                    prodotto.get(0).setQuantita(this.getValue());
                    table.refresh();
                    if(prodotto.get(0).getQuantita()==0) {
                        list.remove(prodotto.get(0));

                        handleSelectProductButtonAction();
                    }
                }

                @Override
                public void increment(int steps) {
                    if (prodotto.get(0).getQuantita() < prodotto.get(0).getDisponibilita()) {
                        BigDecimal totale = new BigDecimal(tot.getText());
                        totale = totale.add(new BigDecimal(String.valueOf(prodotto.get(0).getPrezzo())));
                        tot.setText(String.valueOf(totale));
                    }
                    this.setValue(this.getValue() + 1);
                    prodotto.get(0).setQuantita(this.getValue());
                    table.refresh();
                }
            };
            spinner.setValueFactory(spinnerQuantity);
        }
    }

    public void handleBuyButtonAction() throws Exception {
        boolean check = true;
        for (Prodotto prodotto : list)
            if (prodotto.getQuantita() > prodotto.getDisponibilita()) {
                ErrorPageQuantitaController errorPageQuantitaController = new ErrorPageQuantitaController();
                errorPageQuantitaController.textError.setText("quantita");
                list.remove(prodotto);
                initialize();
                check = false;
                break;
            }
        if (check) {
            if (!dataConsegna.hasProperties()) {

                ErrorPageQuantita errorPageQuantita = new ErrorPageQuantita();
                errorPageQuantita.start(new Stage());
                controller = errorPageQuantita.getController();
                controller.getTextError().setText("Selezionare la data");

            } else {
                Calendar cal = Calendar.getInstance();
                Ordine ord = new Ordine();
                cal.set(Calendar.DAY_OF_MONTH, dataConsegna.getValue().getDayOfMonth());
                cal.set(Calendar.MONTH, dataConsegna.getValue().getMonthValue());
                cal.set(Calendar.YEAR, dataConsegna.getValue().getYear());
                ord.setDataConsegna(cal.getTimeInMillis());
                ord.setData(new Date().getTime());
                List<ProdottoSemplificato> listprodsempl = new ArrayList<>();
                for (Prodotto prodotto : list) {
                    listprodsempl.add(new ProdottoSemplificato(prodotto.getId(), prodotto.getQuantita()));
                }
                ord.setProdotti(listprodsempl);
                Random rand = new Random();
                ord.setID(String.valueOf(rand.nextInt()));
                HttpWrapper http = new HttpWrapper();
                String response = http.addOrdine(Manager.getUIDFromFile(), ord);
                if (response.equalsIgnoreCase("OK")) {
                    BigDecimal totale = new BigDecimal(tot.getText());
                    int punti = totale.round(new MathContext(1)).intValueExact();
                    int puntis = utente.getTesseraFedelta().getSaldoPunti() + punti;
                    //String id = utente.getTesseraFedelta().getId();
                    http.addTesseraPoint(utente.getTesseraFedelta().getId(), puntis);

                    list.clear();
                    MainWindow.resetWindow();
                    primaryStage.close();

                }
            }
        }
    }
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

}