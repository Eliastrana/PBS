package edu.ntnu.idatt1002;

import java.util.HashMap;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Group;
import javafx.geometry.Bounds;


import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Main extends Application {

    @Override public void start(Stage stage) {
        stage.setTitle("Bank");
        stage.setWidth(1000);
        stage.setHeight(600);

       // Text text = new Text();

       // text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 50));
        //text.setX(50);
       // text.setY(130);
        //text.setText("Welcome Keira");

        //stage.show();

        //stage.setTitle("Expenses");
        //stage.setWidth(500);
        //stage.setHeight(500);

        //Nammenamm det funker!

        Pane pane = new StackPane();
        GridPane grid = new GridPane();
        Pane mainPane = new StackPane();





        ObservableList<PieChart.Data> pieChartData = createData();
        ObservableList<PieChart.Data> pieChartData2 = createData2();


        final DoughnutChart chart = new DoughnutChart(pieChartData);
        final DoughnutChart chart2 = new DoughnutChart(pieChartData2);


        stage.setTitle("Total balance");
        stage.show();

        stage.setTitle("Expenses");
        stage.show();




        HBox hbox = new HBox(2);
        for (int i = 1; i<3; i++) {
            ObservableList pieData;
            if (i == 1) {
                pieData = pieChartData;
            } else {
                pieData = pieChartData2;
            }
            hbox.getChildren().add(new DoughnutChart(pieData));
        }
        pane.getChildren().add(hbox);

        //stage.setScene(scene);
        //stage.show();

        //Scene scene = new Scene(new StackPane(chart));
        //stage.setScene(scene);
        //stage.show();

        //Scene scene2 = new Scene(new StackPane(chart2));
        //stage.setScene(scene2);
        //stage.show();


        /**
         * Her er test av font
         */
        //Creating a Text object
        Text text = new Text();

        grid.setAlignment(Pos.TOP_LEFT);


        //Setting font to the text
        text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 100));

        //setting the position of the text
        text.setWrappingWidth(1000);

        //text.setX(20);
        //text.setY(700);


        //Setting the text to be added.
        text.setText("Welcome Keira");



        //Creating a Group object

        //Creating a scene object
        grid.getChildren().add(text);
        //Setting title to the Stage
        stage.setTitle("Setting Font to the text");

        mainPane.getChildren().addAll(grid, pane);

        Scene scene = new Scene(mainPane, 1000, 700);


        //Adding scene to the stage
        stage.setScene(scene);

        //Displaying the contents of the stage
        stage.show();

        /**
         * Her stopper test av font
         */

    }


    private ObservableList<PieChart.Data> createData() {
        return FXCollections.observableArrayList(
                new PieChart.Data("Card", 13),
                new PieChart.Data("Checkings", 25),
                new PieChart.Data("Savings", 10),
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