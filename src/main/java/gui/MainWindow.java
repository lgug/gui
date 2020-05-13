package gui;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Reflection;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class MainWindow extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Layout declarations
        BorderPane mainBorderPane = new BorderPane();
        HBox topBarHBox = new HBox();
        BorderPane productsBorderPane = new BorderPane();
        GridPane productsGridPane = new GridPane();


        // Main border pane
        mainBorderPane.setBackground(new Background(new BackgroundFill(Color.WHITESMOKE, null, null)));
        mainBorderPane.setPrefWidth(1000);
        mainBorderPane.setPrefHeight(600);


        // Top HBox's nodes
        Button searchButton = new Button();
        searchButton.setText("Cerca");
        searchButton.setAlignment(Pos.CENTER_LEFT);

        Button loginButton = new Button();
        loginButton.setText("Accedi");
        loginButton.setAlignment(Pos.CENTER_RIGHT);

        Rectangle cartRectangle = new Rectangle(40, 40);
        cartRectangle.setArcHeight(15);
        cartRectangle.setArcWidth(15);
        cartRectangle.setFill(Color.VIOLET);


        // Products Panes' nodes
        Text text = new Text();
        text.setText("Lista dei prodotti");
        text.setTextAlignment(TextAlignment.CENTER);
        text.setFont(new Font("Verdana Bold", 18));

        // TODO call to Flask and ask the 10 most available products
//        Rectangle rectangle = new Rectangle(200, 200, Color.DARKORCHID);
//        rectangle.setArcWidth(30);
//        rectangle.setArcHeight(30);
//        rectangle.setEffect(new DropShadow(10, 5, 5, Color.GRAY));


        // Layout embedding
        mainBorderPane.setTop(topBarHBox);
        mainBorderPane.setCenter(productsBorderPane);
//        borderPane.setBottom();
//        borderPane.setLeft();
//        borderPane.setRight();
        productsBorderPane.setTop(text);
        productsBorderPane.setCenter(productsGridPane);
        topBarHBox.getChildren().addAll(searchButton, loginButton, cartRectangle);

        // Scene starting
        final Scene scene = new Scene(mainBorderPane);
        primaryStage.setTitle("ShopOnline");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
