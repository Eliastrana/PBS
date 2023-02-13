package edu.ntnu.idatt1002;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
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

import static edu.ntnu.idatt1002.testdata.*;

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

        //MENUBAR
        MenuBar menuBar = new MenuBar();
        borderPane.setTop(menuBar);
        HBox topMenu = new HBox();
        topMenu.setAlignment(Pos.TOP_LEFT);



        //FIRST BUTTON
        Button overviewButton = new Button("Overview");
        overviewButton.setOnAction(event -> {
            System.out.println("open overview window");
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


            HBox currentAccountStatusTextFormat = new HBox();
            currentAccountStatusTextFormat.setAlignment(Pos.CENTER);

            Text currentAccountStatusText = new Text("Current account status");
            currentAccountStatusText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 40));
            currentAccountStatusTextFormat.getChildren().add(currentAccountStatusText);




            VBox vbox = new VBox(text,text2, hboxPieLayout,emptySpace, currentAccountStatusTextFormat);


            scrollPane.setContent(vbox);
            borderPane.setCenter(scrollPane);

            stage.setScene(scene);
            stage.show();
        });
        topMenu.getChildren().add(overviewButton);
        borderPane.setTop(topMenu);
        overviewButton.setStyle("-fx-font-size: 20px; -fx-min-width: 100px; -fx-min-height: 50px;");


        //SECOND BUTTON
        Button transferButton = new Button("Transfer");
        transferButton.setOnAction(event -> {
            System.out.println("open transfer window");
            VBox transferVBox = new VBox();
            transferVBox.getChildren().add(new Label("This is the transfer page"));
            Scene transferScene = new Scene(transferVBox, 800, 600);
            borderPane.setCenter(transferScene.getRoot());

            Text transferBetweenAccounts = new Text("Transfer between accounts:");
            transferBetweenAccounts.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 60));
            borderPane.setAlignment(transferBetweenAccounts, Pos.CENTER_LEFT);
            transferVBox.getChildren().add(transferBetweenAccounts);

            HBox transferBewteenAccounts = new HBox();
            transferVBox.getChildren().add(transferBewteenAccounts);
            transferBewteenAccounts.setAlignment(Pos.CENTER);


            Text transferfrom = new Text("Transfer from:");
            transferfrom.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 25));
            transferBewteenAccounts.getChildren().add(transferfrom);



            ObservableList<String> firstChoice =
                    FXCollections.observableArrayList(
                            "Card",
                            "Savings",
                            "Checking"
                    );

            final ComboBox leftTransfer = new ComboBox(firstChoice);
            transferBewteenAccounts.getChildren().add(leftTransfer);
            leftTransfer.setStyle("-fx-font-size: 20px; -fx-min-width: 100px; -fx-min-height: 50px;");


            Text transferto = new Text(" to: ");
            transferto.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 25));
            transferBewteenAccounts.getChildren().add(transferto);


            ObservableList<String> secondChoice =
                    FXCollections.observableArrayList(
                            "Card",
                            "Savings",
                            "Checking"
                    );

            final ComboBox rightTransfer = new ComboBox(secondChoice);
            transferBewteenAccounts.getChildren().add(rightTransfer);
            rightTransfer.setStyle("-fx-font-size: 20px; -fx-min-width: 100px; -fx-min-height: 50px;");


            HBox transferBewteenAccountsAmount = new HBox();
            transferVBox.getChildren().add(transferBewteenAccountsAmount);

            transferBewteenAccountsAmount.setAlignment(Pos.CENTER);

            Text selectAmount = new Text("Select transfer amount: ");
            selectAmount.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 25));
            transferBewteenAccountsAmount.getChildren().add(selectAmount);
            transferBewteenAccountsAmount.setAlignment(Pos.CENTER);


            TextField priceEntry = new TextField();
            priceEntry.setPromptText("Enter price");
            transferBewteenAccountsAmount.getChildren().add(priceEntry);

            Button confirmTransfer = new Button("Confirm");
            transferBewteenAccountsAmount.getChildren().add(confirmTransfer);
            confirmTransfer.setStyle("-fx-font-size: 20px; -fx-min-width: 100px; -fx-min-height: 25px;");



        });
        topMenu.getChildren().add(transferButton);
        borderPane.setTop(topMenu);
        transferButton.setStyle("-fx-font-size: 20px; -fx-min-width: 100px; -fx-min-height: 50px;");


        //THIRD BUTTON
        Button payButton = new Button("Pay");
        payButton.setOnAction(event -> {
            //Pay.payWindow();
            System.out.println("opening pay window");
            VBox payVBox = new VBox();
            payVBox.getChildren().add(new Label(Pay.payText()));
            Scene transferScene = new Scene(payVBox, 800, 600);
            borderPane.setCenter(transferScene.getRoot());

        });
        topMenu.getChildren().add(payButton);
        borderPane.setTop(topMenu);
        payButton.setStyle("-fx-font-size: 20px; -fx-min-width: 100px; -fx-min-height: 50px;");



        //FOURTH BUTTON
        Button addExpense = new Button("Add expense");
        addExpense.setOnAction(event -> {
            System.out.println("open expense window");
            VBox addExpenseVBox = new VBox();
            addExpenseVBox.getChildren().add(new Label("This is the addExpense page"));
            Scene transferScene = new Scene(addExpenseVBox, 800, 600);
            borderPane.setCenter(transferScene.getRoot());

            HBox newExpenseTitle = new HBox(2);
            Text text3 = new Text("Add new expense");
            text3.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 45));
            newExpenseTitle.setAlignment(Pos.CENTER);
            newExpenseTitle.getChildren().add(text3);
            addExpenseVBox.getChildren().add(newExpenseTitle);



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
            addExpenseVBox.getChildren().add(hboxAddExpenseCategory);


            HBox hboxAddExpensePrice = new HBox(2);
            hboxAddExpensePrice.setAlignment(Pos.CENTER);


            Text pickPrice = new Text("Pick a price: ");
            pickPrice.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
            hboxAddExpensePrice.getChildren().add(pickPrice);

            TextField prices = new TextField();
            prices.setPromptText("Enter price");
            hboxAddExpensePrice.getChildren().add(prices);

            addExpenseVBox.getChildren().add(hboxAddExpensePrice);

            HBox hboxAddExpenseName = new HBox(2);
            hboxAddExpenseName.setAlignment(Pos.CENTER);

            Text pickName = new Text("Pick a name: ");
            pickName.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
            hboxAddExpenseName.getChildren().add(pickName);

            TextField names = new TextField();
            names.setPromptText("Enter name");
            hboxAddExpenseName.getChildren().add(names);

            addExpenseVBox.getChildren().add(hboxAddExpenseName);

            HBox hboxConfirmExpense = new HBox(2);
            Button confirmExpense = new Button("Confirm");
            confirmExpense.setOnAction(e -> {
                System.out.println("Purchase confirmed");
            });
            confirmExpense.setStyle("-fx-font-size: 20px; -fx-min-width: 100px; -fx-min-height: 50px;");
            hboxConfirmExpense.getChildren().add(confirmExpense);
            hboxConfirmExpense.setAlignment(Pos.CENTER);
            addExpenseVBox.getChildren().add(hboxConfirmExpense);


        });
        topMenu.getChildren().add(addExpense);
        borderPane.setTop(topMenu);
        addExpense.setStyle("-fx-font-size: 20px; -fx-min-width: 100px; -fx-min-height: 50px;");



        //FIFTH BUTTON
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




        //HERE STARTS THE OPENING WINDOW
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


        //TABLE
        ObservableList<TableColumn> leftTable = FXCollections.observableArrayList();
        TableColumn leftColumn1 = new TableColumn("Account: ");
        leftColumn1.setMinWidth(100);
        TableColumn leftColumn2 = new TableColumn("Total amount: ");
        leftColumn2.setMinWidth(100);


        TableView table = new TableView();
        table.getColumns().addAll(leftTable);


        leftTable.addAll(leftColumn1, leftColumn2);


        ObservableList<TableColumn> rightTable = FXCollections.observableArrayList();
        TableColumn rightColumn1 = new TableColumn("Purchase: ");
        rightColumn1.setMinWidth(100);
        TableColumn rightColumn2 = new TableColumn("Price: ");
        rightColumn2.setMinWidth(100);

        rightTable.addAll(rightColumn1, rightColumn2);

        TableView leftTableView = new TableView();
        leftTableView.getColumns().addAll(leftTable);

        TableView rightTableView = new TableView();
        rightTableView.getColumns().addAll(rightTable);

        vboxSavings.getChildren().add(leftTableView);
        vboxSpending.getChildren().add(rightTableView);



        //MAIN VBOX
        VBox vbox = new VBox(text,text2, hboxPieLayout,emptySpace);

        scrollPane.setContent(vbox);
        borderPane.setCenter(scrollPane);

        stage.setScene(scene);
        stage.show();

    }

    //THE FIRST PIE
    private ObservableList<PieChart.Data> createData() {
        return FXCollections.observableArrayList(
                new PieChart.Data("Card", 2000),
                new PieChart.Data("Checkings",8000 ),
                new PieChart.Data("Savings", 26000));

    }

    //THE SECOND PIE
    private ObservableList<PieChart.Data> createData2() {
        return FXCollections.observableArrayList(
                new PieChart.Data("Rent" + 1000 + " kr", 13),
                new PieChart.Data(prices.getItem(), 25),
                new PieChart.Data("Transportation", testdata.getValue()),
                new PieChart.Data("Rent", testdata.getTotalExpenses(rent)),
                new PieChart.Data("Cava", 50),
                new PieChart.Data("Transportation", 22));


    }
    //MAIN STRING
    //testdata.itemList();
    public static void main(String[] args) {
        testdata.createTransportation();
        testdata.createRent();
        testdata.addToArrayList(new Expense("awdwad", 15.0, 1), transportation);
        testdata.addToArrayList(new Expense("awdawdsw", 20.0, 1), transportation);
        testdata.addToArrayList(new Expense("awdlkasnem", 100, 1), rent);
        testdata.addToArrayList(new Expense("awdlkasnem", 50, 1), rent);
        testdata.createHashmap();

        launch(args);
    }


}