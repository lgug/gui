package gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import objects.CaratteristicheProdotto;
import objects.Categoria;
import objects.Prodotto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainWindow extends Application {

    private Stage stage;

    @FXML
    private TextField searchField;

    @FXML
    private Button searchButton;

    @FXML
    private Button accediButton;

    @FXML
    private Button cartButton;

    @FXML
    private Label productsListTitle;

    @FXML
    private TilePane productsTilePane;

    @FXML
    protected void handleAccediButtonAction(ActionEvent event) {
//        Stage accessoStage = new Stage();
//        accessoStage.setScene(new Scene());
    }

    @FXML
    protected void handleCercaButtonAction(ActionEvent event) {
        if (searchField.getText().isEmpty()) {
            get10mostAvailableProducts();
            FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemClassLoader().getResource("products_layout.fxml"));
            try {
                HBox productHBox = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        stage = primaryStage;
        FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemClassLoader().getResource("main_window.fxml"));
        AnchorPane pane = loader.load();
        //loader.load();
        Scene scene = new Scene(pane);
        primaryStage.setTitle("ShopOnline");
        primaryStage.setScene(scene);
        primaryStage.show();

//        // Layout declarations
//        BorderPane mainBorderPane = new BorderPane();
//        VBox topBarVBox = new VBox();
//        BorderPane productsBorderPane = new BorderPane();
//        TilePane productsTilePane = new TilePane();
//
//
//        // Main border pane
//        mainBorderPane.setBackground(new Background(new BackgroundFill(Color.WHITESMOKE, null, null)));
//        mainBorderPane.setPrefWidth(1000);
//        mainBorderPane.setPrefHeight(600);
//
//
//        // Top HBox's nodes
//        topBarVBox.setFillWidth(true);
//        GridPane topBarGridPane = new GridPane();
//        topBarGridPane.setScaleShape(true);
//        topBarGridPane.setHgap(10);
//        topBarGridPane.setAlignment(Pos.BASELINE_CENTER);
//
//        TextField searchTextField =  new TextField();
//        searchTextField.setPromptText("Cerca i tuoi prodotti qui");
//        searchTextField.setPrefWidth(400);
//
//        Button searchButton = new Button();
//        searchButton.setText("Cerca");
//        searchButton.setAlignment(Pos.CENTER_LEFT);
//
//        Button loginButton = new Button();
//        loginButton.setText("Accedi");
//        loginButton.setAlignment(Pos.CENTER_RIGHT);
//
//        Image cartIcon = new Image(ClassLoader.getSystemClassLoader().getResourceAsStream("shopping_basket.png"));
//        ImageView cartImageView = new ImageView(cartIcon);
//        cartImageView.setPreserveRatio(true);
//        cartImageView.setFitWidth(40);
//        cartImageView.setFitHeight(40);
//        Button cartButton = new Button("", cartImageView);
//
//        topBarGridPane.add(searchTextField, 0, 0, 1, 1);
//        topBarGridPane.add(searchButton, 1, 0, 1, 1);
//        topBarGridPane.add(loginButton, 20, 0, 1, 1);
//        topBarGridPane.add(cartButton, 21, 0, 1, 1);
//
//        // Products Panes' nodes
//        productsBorderPane.setPadding(new Insets(40));
//        productsBorderPane.setBorder(new Border(new BorderStroke(null, BorderStrokeStyle.SOLID, null, BorderStroke.THIN)));
//        Text text = new Text();
//        text.setText("Lista dei prodotti");
//        text.setTextAlignment(TextAlignment.CENTER);
//        text.setFont(new Font("Verdana Bold", 25));
//
//
//        // Grid pane of products
//        productsTilePane.setScaleShape(true);
//        productsTilePane.setAlignment(Pos.BASELINE_CENTER);
//        productsTilePane.setVgap(20);
//        productsTilePane.setHgap(30);
//        List<Prodotto> firstProducts = get10mostAvailableProducts();
//        for (Prodotto p: firstProducts) {
//            Image productIcon = new Image(ClassLoader.getSystemClassLoader().getResourceAsStream(p.getImmagine()));
//            ImageView imageView = new ImageView(productIcon);
//            imageView.setFitHeight(130);
//            imageView.setFitWidth(130);
//
//            VBox descriptionVBox = new VBox();
//            descriptionVBox.setMaxWidth(200);
//            descriptionVBox.setPadding(new Insets(20));
//            descriptionVBox.setSpacing(3);
//            descriptionVBox.setAlignment(Pos.CENTER_LEFT);
//
//            Text descriptionTitleText = new Text(p.getNome());
//            descriptionTitleText.setFont(new Font(18));
//            descriptionTitleText.setStyle("-fx-font-weight: bold");
//
//            Text descriptionBrandText = new Text("Prodotto da " + p.getMarca());
//            descriptionBrandText.setFont(new Font(12));
//
//            Text descriptionCategoryText = new Text("Prodotto da " + p.getCategoria().toString());
//            descriptionCategoryText.setFont(new Font(12));
//
//            Text descriptionAvailabilityText = new Text("Disponibilità: " + p.getDisponibilita());
//            descriptionAvailabilityText.setFont(new Font(12));
//
//            Text descriptionPriceText = new Text("€" + p.getPrezzo());
//            descriptionPriceText.setFont(new Font("Comic Sans MS", 20));
//
//            ImageView addButtonLogo = new ImageView(new Image(ClassLoader.getSystemClassLoader().getResourceAsStream("plus.png")));
//            addButtonLogo.setPreserveRatio(true);
//            addButtonLogo.setFitWidth(15);
//            Button addToCartButton = new Button("ADD", addButtonLogo);
//
//            descriptionVBox.getChildren().addAll(descriptionTitleText, descriptionBrandText,
//                    descriptionCategoryText, descriptionAvailabilityText, descriptionPriceText);
//            HBox hBox = new HBox(imageView, descriptionVBox, addToCartButton);
//            hBox.setAlignment(Pos.CENTER);
//            hBox.setPadding(new Insets(20));
//            hBox.setBorder(new Border(new BorderStroke(null, BorderStrokeStyle.SOLID, null, BorderStroke.DEFAULT_WIDTHS)));
//            productsTilePane.getChildren().add(hBox);
//        }
//
//        // Layout embedding
//        topBarVBox.getChildren().addAll(topBarGridPane);
//        productsBorderPane.setTop(text);
//        productsBorderPane.setCenter(productsTilePane);
//        mainBorderPane.setTop(topBarVBox);
//        mainBorderPane.setCenter(productsBorderPane);
//
//        // Scene starting
//        final Scene scene = new Scene(mainBorderPane);
//        primaryStage.setTitle("ShopOnline");
//        primaryStage.setScene(scene);
//        primaryStage.show();
    }

    public List<Prodotto> get10mostAvailableProducts() {
        //TODO Call to Flask

        //TODO only for test: TO REMOVE!!!
        Prodotto prodotto1 = new Prodotto(41, "Sushi", "Xi-Sushi", CaratteristicheProdotto.BIOLOGICO, Categoria.PESCE, 15.99f, 20,10, "sushi.png");
        Prodotto prodotto2 = new Prodotto(42, "Ciliege", "FruttaFresca", CaratteristicheProdotto.BIOLOGICO, Categoria.FRUTTA_VERDURA, 6.49f, 20,10, "cherries.png");
        Prodotto prodotto3 = new Prodotto(43, "Banane", "FruttaFresca", CaratteristicheProdotto.BIOLOGICO, Categoria.FRUTTA_VERDURA, 5.99f, 20,10, "bananas.png");
        Prodotto prodotto4 = new Prodotto(44, "Cioccolatini", "ChocoLove", CaratteristicheProdotto.BIOLOGICO, Categoria.DOLCI, 2.99f, 20,10, "chocolate_box.png");
        Prodotto prodotto5 = new Prodotto(45, "Costolette", "MuccaPazza S.r.l", CaratteristicheProdotto.BIOLOGICO, Categoria.CARNE, 13.99f, 20,10, "spare_ribs.png");
        Prodotto prodotto6 = new Prodotto(46, "Formaggio svizzero", "Cheddar", CaratteristicheProdotto.BIOLOGICO, Categoria.LATTICINI, 21.99f, 20,10, "swiss_cheese.png");
        Prodotto prodotto7 = new Prodotto(47, "Tonno fresco", "Il pescatore allegro", CaratteristicheProdotto.BIOLOGICO, Categoria.PESCE, 7.99f, 20,10, "tuna.png");
        Prodotto prodotto8 = new Prodotto(48, "Honey", "Apindustria", CaratteristicheProdotto.BIOLOGICO, Categoria.DOLCI, 3.99f, 20,10, "honey.png");
        Prodotto prodotto9 = new Prodotto(49, "Sushi", "Xi-Sushi", CaratteristicheProdotto.BIOLOGICO, Categoria.PESCE, 15.99f, 20,10, "sushi.png");
        Prodotto prodotto10 = new Prodotto(50, "Sushi", "Xi-Sushi", CaratteristicheProdotto.BIOLOGICO, Categoria.PESCE, 15.99f, 20,10, "sushi.png");
        List<Prodotto> prodottoList = new ArrayList<>();
        prodottoList.add(prodotto1);
        prodottoList.add(prodotto2);
        prodottoList.add(prodotto3);
        prodottoList.add(prodotto4);
        prodottoList.add(prodotto5);
        prodottoList.add(prodotto6);
        prodottoList.add(prodotto7);
        prodottoList.add(prodotto8);
        prodottoList.add(prodotto9);
        prodottoList.add(prodotto10);

        return prodottoList;
    }

}
