package edu.ntnu.idatt1002;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.util.HashMap;
import javafx.scene.control.Button;

import javax.swing.*;

import static edu.ntnu.idatt1002.testdata.getItem;

public class Main extends Application {

    @Override public void start(Stage stage) {

        //Establish frame
        stage.setTitle("Bank");
        stage.setWidth(1000);
        stage.setHeight(700);

        BorderPane borderPane = new BorderPane();
        ScrollPane scrollPane = new ScrollPane();

        ObservableList<PieChart.Data> pieChartData = createData();
        ObservableList<PieChart.Data> pieChartData2 = createData2();

        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(false);
        Scene scene = new Scene(borderPane, 1000, 2000);

        MenuBar menuBar = new MenuBar();
        borderPane.setTop(menuBar);
        HBox topMenu = new HBox();
        topMenu.setAlignment(Pos.CENTER);


        Button overviewButton = new Button("Overview");
        overviewButton.setOnAction(event -> {
            System.out.println("open overview window");
        });
        topMenu.getChildren().add(overviewButton);
        borderPane.setTop(topMenu);
        overviewButton.setStyle("-fx-font-size: 20px; -fx-min-width: 100px; -fx-min-height: 50px;");



        Button transferButton = new Button("Transfer");
        transferButton.setOnAction(event -> {
            System.out.println("open transfer window");
            VBox transferVBox = new VBox();
            transferVBox.getChildren().add(new Label("This is the transfer page"));
            Scene transferScene = new Scene(transferVBox, 800, 600);
            borderPane.setCenter(transferScene.getRoot());
        });
        topMenu.getChildren().add(transferButton);
        borderPane.setTop(topMenu);
        transferButton.setStyle("-fx-font-size: 20px; -fx-min-width: 100px; -fx-min-height: 50px;");



        Button payButton = new Button("Pay");
        payButton.setOnAction(event -> {
            System.out.println("opening pay window");
            VBox payVBox = new VBox();
            payVBox.getChildren().add(new Label("This is the pay page"));
            Scene transferScene = new Scene(payVBox, 800, 600);
            borderPane.setCenter(transferScene.getRoot());
        });
        topMenu.getChildren().add(payButton);
        borderPane.setTop(topMenu);
        payButton.setStyle("-fx-font-size: 20px; -fx-min-width: 100px; -fx-min-height: 50px;");



        Button moreButton = new Button("More");
        moreButton.setOnAction(event -> {
            System.out.println("opening more window");
            VBox moreVBox = new VBox();
            moreVBox.getChildren().add(new Label("This is the more page"));
            Scene transferScene = new Scene(moreVBox, 800, 600);
            borderPane.setCenter(transferScene.getRoot());
        });
        topMenu.getChildren().add(moreButton);
        borderPane.setTop(topMenu);
        moreButton.setStyle("-fx-font-size: 20px; -fx-min-width: 100px; -fx-min-height: 50px;");





        //Welcome text

        Text text = new Text("Welcome Keira");
        text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 80));
        borderPane.setAlignment(text, Pos.BASELINE_LEFT);

        //Time of day text
        Text text2 = new Text(timeofdaychecker.timeofdaychecker()+"\n");
        text2.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 60));
        text2.setLineSpacing(0);
        text2.setFill(Color.LIGHTGREEN);
        borderPane.setAlignment(text2, Pos.BASELINE_LEFT);



        HBox hbox2 = new HBox(2);

        Text textSavings = new Text("Total savings");
        textSavings.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 40));
        hbox2.getChildren().add(textSavings);

        Text textSpending = new Text("Monthly spending");
        textSpending.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 40));
        hbox2.getChildren().add(textSpending);

        Text emptySpace = new Text("\n");
        emptySpace.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));

        VBox vboxSavings = new VBox(textSavings, new DoughnutChart(pieChartData));
        vboxSavings.setAlignment(Pos.CENTER);

        VBox vboxSpending = new VBox(textSpending, new DoughnutChart(pieChartData2));
        vboxSpending.setAlignment(Pos.CENTER);

        HBox hboxPieLayout = new HBox(vboxSavings, vboxSpending);
        hboxPieLayout.setAlignment(Pos.CENTER);

        HBox newExpenseTitle = new HBox(2);
        Text text3 = new Text("Add new expense");
        text3.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 45));
        newExpenseTitle.setAlignment(Pos.CENTER);

        HBox hboxAddExpenseCategory = new HBox(2);

        Text pickCategory = new Text("Pick a category: ");
        pickCategory.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        hboxAddExpenseCategory.getChildren().add(pickCategory);

        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "Food",
                        "Transportation",
                        "Clothing",
                        "Entertainment",
                        "Other"
                );

        final ComboBox categoryMenu = new ComboBox(options);
        hboxAddExpenseCategory.getChildren().add(categoryMenu);
        hboxAddExpenseCategory.setAlignment(Pos.CENTER);


        HBox hboxAddExpensePrice = new HBox(2);
        hboxAddExpensePrice.setAlignment(Pos.CENTER);


        Text pickPrice = new Text("Pick a price: ");
        pickPrice.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        hboxAddExpensePrice.getChildren().add(pickPrice);

        TextField prices = new TextField();
        prices.setPromptText("Enter price");
        hboxAddExpensePrice.getChildren().add(prices);

        HBox hboxAddExpenseName = new HBox(2);
        hboxAddExpenseName.setAlignment(Pos.CENTER);

        Text pickName = new Text("Pick a name: ");
        pickName.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        hboxAddExpenseName.getChildren().add(pickName);

        TextField names = new TextField();
        names.setPromptText("Enter name");
        hboxAddExpenseName.getChildren().add(names);

        Button confirmExpense = new Button("Confirm");
        confirmExpense.setStyle("-fx-background-color: #62de7c; ");
        confirmExpense.setStyle("-fx-border-width\n: 200; ");
        confirmExpense.setStyle("-fx-border-heigth\n: 50; ");
        confirmExpense.setStyle("-fx-font-size\n: 40; ");
        confirmExpense.setOnAction(e -> {
            System.out.println("Purchase confirmed");
        });


        VBox vbox = new VBox(text,text2, hboxPieLayout,emptySpace, newExpenseTitle, hboxAddExpenseCategory, hboxAddExpensePrice, hboxAddExpenseName, confirmExpense);

        scrollPane.setContent(vbox);
        borderPane.setCenter(scrollPane);

        stage.setScene(scene);
        stage.show();




    }

    private ObservableList<PieChart.Data> createData() {
        return FXCollections.observableArrayList(
                new PieChart.Data("Card", 2000),
                new PieChart.Data("Checkings",8000 ),
                new PieChart.Data("Savings", 26000));

    }

    private ObservableList<PieChart.Data> createData2() {
        return FXCollections.observableArrayList(
                new PieChart.Data("Rent" + 1000 + " kr", 13),
                new PieChart.Data(prices.getItem(), 25),
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