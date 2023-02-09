package edu.ntnu.idatt1002;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.util.HashMap;

public class Main extends Application {

    @Override public void start(Stage stage) {
        stage.setTitle("Bank");
        stage.setWidth(1000);
        stage.setHeight(700);

        BorderPane borderPane = new BorderPane();
        ScrollPane scrollPane = new ScrollPane();

        ObservableList<PieChart.Data> pieChartData = createData();
        ObservableList<PieChart.Data> pieChartData2 = createData2();

        final DoughnutChart chart = new DoughnutChart(pieChartData);
        final DoughnutChart chart2 = new DoughnutChart(pieChartData2);

        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(false);

        Text text = new Text("Welcome Keira");
        text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 80));
        borderPane.setAlignment(text, Pos.BASELINE_LEFT);

        Text text2 = new Text(timeofdaychecker.timeofdaychecker()+"\n");
        text2.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 60));
        text2.setLineSpacing(0);
        text2.setFill(Color.LIGHTGREEN);
        borderPane.setAlignment(text2, Pos.BASELINE_LEFT);


        HBox hbox2 = new HBox(2);

        Text textSavings = new Text("Total savings");
        textSavings.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 40));
        borderPane.setAlignment(textSavings, Pos.CENTER);
        hbox2.getChildren().add(textSavings);

        Text textSpending = new Text("Monthly spending");
        textSpending.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 40));
        borderPane.setAlignment(textSpending, Pos.CENTER);
        hbox2.getChildren().add(textSpending);

        Text text3 = new Text("Scrolling funker");
        text3.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 80));
        borderPane.setAlignment(text3, Pos.CENTER);
        hbox2.getChildren().add(text3);

        VBox vboxSavings = new VBox(textSavings, new DoughnutChart(pieChartData));
        VBox vboxSpending = new VBox(textSpending, new DoughnutChart(pieChartData2));
        HBox hboxPieLayout = new HBox(vboxSavings, vboxSpending);

        VBox vbox = new VBox(text,text2, hboxPieLayout, text3);

        scrollPane.setContent(vbox);
        borderPane.setCenter(scrollPane);

        Scene scene = new Scene(borderPane, 1000, 2000);
        stage.setScene(scene);
        stage.show();
    }

    private ObservableList<PieChart.Data> createData() {
        return FXCollections.observableArrayList(
                new PieChart.Data("Card", 13),
                new PieChart.Data("Checkings", 25),
                new PieChart.Data("Savings", 80),
                new PieChart.Data("abc", 10));

    }

    private ObservableList<PieChart.Data> createData2() {
        return FXCollections.observableArrayList(
                new PieChart.Data("Rent" + 1000 + " kr", 13),
                new PieChart.Data("Food", 25),
                new PieChart.Data("Shopping", 10),
                new PieChart.Data("Cava", 50),
                new PieChart.Data("Transportation", 22));


    }
    //testdata.itemList();
    public static void main(String[] args) {
        HashMap<String, Double> items = new HashMap<>();

        launch(args);
    }

}