package edu.ntnu.idatt1002;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.chart.XYChart;

import static edu.ntnu.idatt1002.AlertWindow.emptyFieldAlert;
import static edu.ntnu.idatt1002.Incomes.*;
import static edu.ntnu.idatt1002.PieChart.createData;
import static edu.ntnu.idatt1002.Expenses.*;
import static edu.ntnu.idatt1002.Accounts.*;


public class GUI extends Application {

    //Each page has its own method, all the buttons are in the same method.
    //The buttons are then connected to the methods that open the pages.
    //The buttons are added to every single page individually, but they should be a separate entity
    //Each window is a StackPane, and the buttons are added to the StackPane
    //The StackPane is then added to the scene, and the scene is added to the stage

    private void overviewWindow(){
        ObservableList<PieChart.Data> pieChartData = createData();
        ObservableList<PieChart.Data> pieChartData2 = edu.ntnu.idatt1002.PieChart.createData2();

        //BarChartSample barChart = new BarChartSample();


        System.out.println("open overview window");
        Text text = new Text("Welcome Keira");
        text.setFont(Font.font("Helvetica", FontWeight.BOLD, FontPosture.REGULAR, 80));
        text.setStyle("-fx-fill: #3F403F");

        //Time of day text
        Text text2 = new Text(timeofdaychecker.timeofdaychecker() + "\n");
        text2.setFont(Font.font("helvetica", FontWeight.BOLD, FontPosture.REGULAR, 60));
        text2.setLineSpacing(0);
        text2.setFill(Color.LIGHTGREEN);
        text2.setStyle("-fx-fill: #9FB8AD");


        HBox hbox2 = new HBox(2);
        Text textSavings = new Text("Total savings: " +"\n"+ getTotalOfAllAccounts());
        textSavings.setTextAlignment(TextAlignment.CENTER);
        textSavings.setStyle("-fx-fill: #3F403F");
        textSavings.setFont(Font.font("Helvetica", FontWeight.BOLD, FontPosture.REGULAR, 30));
        hbox2.getChildren().add(textSavings);

        Text textSpending = new Text("Monthly spending: " +"\n"+ getExpensesOfAllCategories());
        textSpending.setTextAlignment(TextAlignment.CENTER);

        textSpending.setStyle("-fx-fill: #3F403F");
        textSpending.setFont(Font.font("Helvetica", FontWeight.BOLD, FontPosture.REGULAR, 30));
        hbox2.getChildren().add(textSpending);

        Text emptySpace = new Text("\n");
        emptySpace.setFont(Font.font("Helvetica", FontWeight.BOLD, FontPosture.REGULAR, 20));

        VBox vboxSavings = new VBox(textSavings, new DoughnutChart(pieChartData));
        vboxSavings.setAlignment(Pos.CENTER);

        VBox vboxSpending = new VBox(textSpending, new DoughnutChart(pieChartData2));
        vboxSpending.setAlignment(Pos.CENTER);

        HBox hboxPieLayout = new HBox(vboxSavings, vboxSpending);
        hboxPieLayout.setAlignment(Pos.CENTER);
        hboxPieLayout.setSpacing(50);

        HBox currentAccountStatusTextFormat = new HBox();
        currentAccountStatusTextFormat.setAlignment(Pos.CENTER);


        //LeftTable
        TableView<Income> leftTable = new TableView<>();
        TableColumn<Income, String> leftColumn1 = new TableColumn<>("Name: ");
        leftColumn1.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Income, Double> leftColumn2 = new TableColumn<>("Price: ");
        leftColumn2.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Income, LocalDate> leftColumn3 = new TableColumn<>("Date: ");
        leftColumn3.setCellValueFactory(new PropertyValueFactory<>("date"));

        leftTable.getColumns().addAll(leftColumn1, leftColumn2, leftColumn3);

        leftTable.getItems().addAll(Incomes.createAllIncomes());

        vboxSavings.getChildren().add(leftTable);

        //TODO vboxSpending.getChildren().add(rightTable);

        //RightTable
        TableView<Expense> rightTable = new TableView<>();
        TableColumn<Expense, String> rightColumn1 = new TableColumn<>("Name: ");
        rightColumn1.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Expense, Double> rightColumn2 = new TableColumn<>("Price: ");
        rightColumn2.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Expense, LocalDate> rightColumn3 = new TableColumn<>("Date: ");
        rightColumn3.setCellValueFactory(new PropertyValueFactory<>("date"));

        rightTable.getColumns().addAll(rightColumn1, rightColumn2, rightColumn3);

        rightTable.getItems().addAll(Expenses.createAllExpenses());

        vboxSpending.getChildren().add(rightTable);


        Text currentAccountStatusText = new Text("Current account status");
        currentAccountStatusText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 40));
        currentAccountStatusTextFormat.getChildren().add(currentAccountStatusText);


        //TESTING BARCHART

//        HBox barcharts = new HBox();
//
//        CategoryAxis xAxis = new CategoryAxis();
//        NumberAxis yAxis = new NumberAxis();
//        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
//        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
//        series1.setName("Series 1");
//        series1.getData().add(new XYChart.Data<>("Category 1", 10));
//        series1.getData().add(new XYChart.Data<>("Category 2", 20));
//        series1.getData().add(new XYChart.Data<>("Category 3", 30));
//        barChart.getData().addAll(series1);
//        barChart.setTitle("Bar Chart Example");
//        xAxis.setLabel("Categories");
//        yAxis.setLabel("Values");
//        barChart.setStyle("-fx-background-color: #F4F4F4");
//        series1.getNode().setStyle("-fx-bar-fill: #0099FF");
//
//        barcharts.getChildren().add(barChart);


        //END OF TEST OF BARCHART

        //topMenu(primaryStage);

        VBox vbox = new VBox(text, text2, hboxPieLayout, emptySpace, currentAccountStatusTextFormat);

        overviewWindowStackPane.getChildren().add(vbox);

    }

    //This stackPane holds the method of the overview window, this is done so that it is easier to
    //refresh the overview window.
    private StackPane overviewWindowStackPane = new StackPane(); {
        overviewWindow();
    }

    private StackPane transferWindow = new StackPane(); {

        System.out.println("open transfer window");
        VBox transferVBox = new VBox();

        Text transferBetweenAccounts = new Text("Transfer between accounts:");
        transferBetweenAccounts.setFont(Font.font("Helvetica", FontWeight.BOLD, FontPosture.REGULAR, 60));
        transferBetweenAccounts.setStyle("-fx-fill: #3F403F");
        transferVBox.getChildren().add(transferBetweenAccounts);

        HBox transferBewteenAccounts = new HBox();
        transferBewteenAccounts.setSpacing(20);
        transferVBox.getChildren().add(transferBewteenAccounts);
        transferBewteenAccounts.setAlignment(Pos.CENTER);


        Text transferfrom = new Text("Transfer from:");
        transferfrom.setFont(Font.font("Helvetica", FontWeight.BOLD, FontPosture.REGULAR, 25));
        transferBewteenAccounts.getChildren().add(transferfrom);
        ComboBox<String> leftTransfer = new ComboBox<>();
        leftTransfer.setItems(FXCollections.observableArrayList(accounts.keySet()));
        transferBewteenAccounts.getChildren().add(leftTransfer);
        leftTransfer.setStyle("-fx-font-size: 20px; -fx-min-width: 100px; -fx-min-height: 50px; -fx-background-color: #9FB8AD; -fx-border-width: 2; -fx-background-radius: 5em;");


        Text transferto = new Text(" to: ");
        transferto.setFont(Font.font("Helvetica", FontWeight.BOLD, FontPosture.REGULAR, 25));
        transferBewteenAccounts.getChildren().add(transferto);
        ComboBox<String> rightTransfer = new ComboBox<>();
        rightTransfer.setItems(FXCollections.observableArrayList(accounts.keySet()));
        transferBewteenAccounts.getChildren().add(rightTransfer);
        rightTransfer.setStyle("-fx-font-size: 20px; -fx-min-width: 100px; -fx-min-height: 50px;-fx-background-color: #9FB8AD; -fx-border-width: 2; -fx-background-radius: 2em;");


        HBox transferBewteenAccountsAmount = new HBox();
        transferBewteenAccountsAmount.setSpacing(20);
        transferBewteenAccountsAmount.setPadding(new Insets(40, 40, 40, 40));
        transferVBox.getChildren().add(transferBewteenAccountsAmount);
        transferBewteenAccountsAmount.setAlignment(Pos.CENTER);

        Text selectAmount = new Text("Select transfer amount: ");
        selectAmount.setFont(Font.font("Helvetica", FontWeight.BOLD, FontPosture.REGULAR, 25));
        transferBewteenAccountsAmount.getChildren().add(selectAmount);
        transferBewteenAccountsAmount.setAlignment(Pos.CENTER);


        TextField priceEntry = new TextField();
        priceEntry.setPromptText("Enter price");
        transferBewteenAccountsAmount.getChildren().add(priceEntry);

        Button confirmTransfer = new Button("Confirm");
        confirmTransfer.setStyle("-fx-font-size: 20px; -fx-min-width: 100px; -fx-min-height: 50px;-fx-background-color: #9FB8AD; -fx-border-width: 2; -fx-padding: 10px; -fx-background-radius: 0.5em;");


        confirmTransfer.setOnAction(e -> {
            String removeFromAccount = (String) leftTransfer.getValue();
            String addToAccount = (String) rightTransfer.getValue();
            String tempText = priceEntry.getText();
            double amountToAdd = Double.parseDouble(tempText);
            Accounts.transferBetweenAccounts(removeFromAccount, addToAccount, amountToAdd);
            System.out.println("Confirm transfer button pressed");
            SoundPlayer.play("src/main/resources/16bitconfirm.wav");
            leftTransfer.setValue(null);
            rightTransfer.setValue(null);
            priceEntry.setText(null);
        });

        transferBewteenAccountsAmount.getChildren().add(confirmTransfer);

        Text registerIncome = new Text("Register new income:");
        registerIncome.setFont(Font.font("Helvetica", FontWeight.BOLD, FontPosture.REGULAR, 60));
        registerIncome.setStyle("-fx-fill: #3F403F");
        transferVBox.getChildren().add(registerIncome);

        HBox registerIncomeHBox = new HBox();
        registerIncomeHBox.setSpacing(20);
        transferVBox.getChildren().add(registerIncomeHBox);
        registerIncomeHBox.setAlignment(Pos.CENTER);

        Text incomeTo = new Text("Choose the account for registering income:");
        incomeTo.setFont(Font.font("Helvetica", FontWeight.BOLD, FontPosture.REGULAR, 25));
        registerIncomeHBox.getChildren().add(incomeTo);
        ComboBox<String> incomeAccount = new ComboBox<>();
        incomeAccount.setItems(FXCollections.observableArrayList(accounts.keySet()));
        registerIncomeHBox.getChildren().add(incomeAccount);
        incomeAccount.setStyle("-fx-font-size: 20px; -fx-min-width: 100px; -fx-min-height: 50px; -fx-background-color: #9FB8AD; -fx-border-width: 2; -fx-background-radius: 5em;");

        HBox registerAmount = new HBox();
        registerAmount.setSpacing(20);
        registerAmount.setPadding(new Insets(40, 40, 40, 40));
        transferVBox.getChildren().add(registerAmount);
        registerAmount.setAlignment(Pos.CENTER);

        Text selectAmountIncome = new Text("Select transfer amount: ");
        selectAmountIncome.setFont(Font.font("Helvetica", FontWeight.BOLD, FontPosture.REGULAR, 25));
        registerAmount.getChildren().add(selectAmountIncome);
        registerAmount.setAlignment(Pos.CENTER);

        TextField amountIncome = new TextField();
        amountIncome.setPromptText("Enter price");
        registerAmount.getChildren().add(amountIncome);

        Button confirmIncome = new Button("Confirm");
        confirmIncome.setStyle("-fx-font-size: 20px; -fx-min-width: 100px; -fx-min-height: 50px;-fx-background-color: #9FB8AD; -fx-border-width: 2; -fx-padding: 10px; -fx-background-radius: 0.5em;");


        confirmIncome.setOnAction(e -> {
            String inncomeAccountName = (String) incomeAccount.getValue();
            String tempText = amountIncome.getText();
            double amountToAdd = Double.parseDouble(tempText);
            addToAccount(inncomeAccountName, amountToAdd);
            incomes.add(new Income(inncomeAccountName, amountToAdd, 1, LocalDate.now()));
            System.out.println("Confirm income button pressed");
            SoundPlayer.play("src/main/resources/16bitconfirm.wav");
            incomeAccount.setValue(null);
            amountIncome.setText(null);
        });

        registerAmount.getChildren().add(confirmIncome);
        VBox vbox = new VBox(transferVBox);
        vbox.setPadding(new Insets(40, 40, 40, 40));
        transferWindow.getChildren().add(vbox);





    };
    private StackPane payWindow = new StackPane();{


        System.out.println("opening pay window");
        VBox payVBox = new VBox();
        payVBox.getChildren().add(new Label(Pay.payText()));

        VBox vbox = new VBox(payVBox);
        payWindow.getChildren().add(vbox);


    }
    private StackPane addExpenseWindow = new StackPane();{



        System.out.println("open expense window");
        VBox addExpenseVBox = new VBox();
        addExpenseVBox.setAlignment(Pos.CENTER);
        addExpenseVBox.getChildren().add(new Label("This is the addExpense page"));

        Text text3 = new Text("Add new expense");
        text3.setStyle("-fx-fill: #3F403F");
        text3.setFont(Font.font("helvetica", FontWeight.BOLD, FontPosture.REGULAR, 60));

        DatePicker datePicker = new DatePicker();
        datePicker.setValue(LocalDate.now());
        datePicker.setShowWeekNumbers(true);
        datePicker.setStyle("-fx-font-size: 20px; -fx-min-width: 100px; -fx-min-height: 50px;-fx-background-color: #9FB8AD; -fx-border-width: 2; -fx-padding: 10px; -fx-background-radius: 0.5em;");

        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "Rent",
                        "Food",
                        "Transportation",
                        "Clothing",
                        "Entertainment",
                        "Other"
                );



        final ComboBox categoryMenu = new ComboBox(options);

        String originalPromptText = "Pick a category";
        categoryMenu.setPromptText(originalPromptText);
        categoryMenu.setStyle("-fx-font-size: 20px; -fx-min-width: 100px; -fx-min-height: 50px;-fx-background-color: #9FB8AD; -fx-border-width: 2; -fx-padding: 10px; -fx-background-radius: 0.5em; -fx-prompt-text-fill: #FFFFFF; -fx-text-fill: #FFFFFF;");

        TextField prices = new TextField();
        prices.setPromptText("Enter price");
        prices.setStyle("-fx-font-size: 20px; -fx-min-width: 100px; -fx-min-height: 50px;-fx-background-color: #9FB8AD; -fx-border-width: 2; -fx-padding: 10px; -fx-background-radius: 0.5em; -fx-prompt-text-fill: #FFFFFF; -fx-text-fill: #FFFFFF;");

        TextField names = new TextField();
        names.setPromptText("Enter name");
        names.setStyle("-fx-font-size: 20px; -fx-min-width: 100px; -fx-min-height: 50px;-fx-background-color: #9FB8AD; -fx-border-width: 2; -fx-padding: 10px; -fx-background-radius: 0.5em; -fx-prompt-text-fill: #FFFFFF; -fx-text-fill: #FFFFFF;");


        Button confirmExpense = new Button("Confirm");
        confirmExpense.setStyle("-fx-font-size: 30px; -fx-min-width: 100px; -fx-min-height: 50px;-fx-background-color: #9FB8AD; -fx-border-width: 2; -fx-padding: 10px; -fx-background-radius: 0.5em;");


        confirmExpense.setOnAction(e -> {

            if (categoryMenu.getValue() == null) {
                emptyFieldAlert();
                SoundPlayer.play("src/main/resources/error.wav");
                System.out.println("No category selected");
            } else {
                String selectedOption = (String) categoryMenu.getValue();
                String name = names.getText();
                String tempText = prices.getText();

                LocalDate date = datePicker.getValue();
                System.out.println("Selected date: " + date);

                double price = Double.parseDouble(tempText); //det er en error her

                switch (selectedOption) {
                    case "Entertainment" ->
                            Expenses.addToArrayList(new Expense(name, price, 1, datePicker.getValue()), entertainment);
                    case "Food" -> Expenses.addToArrayList(new Expense(name, price, 1, datePicker.getValue()), food);
                    case "Transportation" ->
                            Expenses.addToArrayList(new Expense(name, price, 1, datePicker.getValue()), transportation);
                    case "Clothing" ->
                            Expenses.addToArrayList(new Expense(name, price, 1, datePicker.getValue()), clothing);
                    case "Other" -> Expenses.addToArrayList(new Expense(name, price, 1, datePicker.getValue()), other);
                    case "Rent" -> Expenses.addToArrayList(new Expense(name, price, 1, datePicker.getValue()), rent);
                    default -> System.out.println("Error");
                }

                System.out.println("Purchase confirmed");
                System.out.println("Category: " + selectedOption);

                try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File("src/main/resources", "logger.txt"), true))) {
                    writer.write(selectedOption + "," + name + "," + date + "," + price + "\n");
                } catch (IOException f) {
                    System.err.println("Error writing to file: " + f.getMessage());
                }

                //This clears the textfields and plays the confirmation sound
                //categoryMenu.setValue(null);
                //categoryMenu.setPromptText("Pick a category");
                //categoryMenu.getSelectionModel().clearSelection();


                categoryMenu.setValue(null);
                categoryMenu.setPromptText(originalPromptText);

                names.setText(null);
                prices.setText(null);
                SoundPlayer.play("src/main/resources/16bitconfirm.wav");
            }
        });


        HBox title = new HBox(text3);
        title.setAlignment(Pos.CENTER);
        title.setSpacing(40);

        VBox categoryNamePrice = new VBox(//pickCategory,
                categoryMenu,
                //pickPrice,
                prices,
                //pickName,
                names);


        categoryNamePrice.setPadding(new Insets(25));
        categoryNamePrice.setSpacing(20);
        categoryNamePrice.setAlignment(Pos.TOP_LEFT);

        VBox calendar = new VBox(datePicker);

        calendar.setAlignment(Pos.TOP_LEFT);
        calendar.setSpacing(20);
        calendar.setPadding(new Insets(25));




        HBox dateAndInput = new HBox(categoryNamePrice, calendar);
        dateAndInput.setAlignment(Pos.CENTER);
        dateAndInput.setPadding(new Insets(15));


        VBox dateAndInputAndConfirm = new VBox(title, dateAndInput, confirmExpense);
        dateAndInputAndConfirm.setAlignment(Pos.CENTER);



        VBox vbox = new VBox(dateAndInputAndConfirm);
        addExpenseWindow.getChildren().add(vbox);

    }
    private StackPane moreWindow = new StackPane();{


        System.out.println("opening more window");
        VBox moreVBox = new VBox();
        moreVBox.getChildren().add(new Label("This is the more page"));

        VBox vbox = new VBox(moreVBox);
        moreWindow.getChildren().add(vbox);



    }


    //HERE END THE DIFFERENT PANES AND BEGINS THE START METHOD, UPDATER AND TOPMENU

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Bank");
        primaryStage.setWidth(1000);
        primaryStage.setHeight(700);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(false);
        BorderPane borderPane = new BorderPane();

        borderPane.setStyle("-fx-background-color: #E6E8E6;");
        scrollPane.setStyle("-fx-background-color: #E6E8E6;");
        scrollPane.setContent(borderPane);

        Scene scene = new Scene(scrollPane);
        primaryStage.setScene(scene);
        primaryStage.show();

        overviewWindowStackPane.setVisible(true);
        transferWindow.setVisible(false);
        payWindow.setVisible(false);
        addExpenseWindow.setVisible(false);
        moreWindow.setVisible(false);

        StackPane root = new StackPane();

        root.getChildren().addAll(overviewWindowStackPane, transferWindow, payWindow, addExpenseWindow, moreWindow);
        borderPane.setTop(topMenu(primaryStage));
        borderPane.setCenter(root);


    }

    private void updatePane() {
        // update the contents of the paneToUpdate
            overviewWindowStackPane.getChildren().clear();

            overviewWindow();

    }


    //TOP MENU
    public HBox topMenu(Stage primaryStage) {

        BorderPane borderPane = new BorderPane();
        MenuBar menuBar = new MenuBar();
        borderPane.setTop(menuBar);
        HBox topMenu = new HBox();
        topMenu.setAlignment(Pos.TOP_CENTER);
        primaryStage.show();

        topMenu.setSpacing(20);
        topMenu.setPadding(new Insets(20, 20, 20, 20));


        //BUTTON 1
        Button overviewButton = new Button("Overview");
        overviewButton.setOnAction(event -> {
            try {

                overviewWindowStackPane.visibleProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue) {
                        updatePane();
                    }});

                overviewWindowStackPane.setVisible(true);
                transferWindow.setVisible(false);
                payWindow.setVisible(false);
                addExpenseWindow.setVisible(false);
                moreWindow.setVisible(false);
                System.out.println("overview button pressed");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        overviewButton.setStyle("-fx-font-size: 25px; -fx-min-width: 155px; -fx-min-height: 50px; -fx-background-color: #9FB8AD; -fx-border-width: 2; -fx-background-radius: 5em;");
        topMenu.getChildren().add(overviewButton);


        //BUTTON 2
        Button transferButton = new Button("Transfer");
        transferButton.setOnAction(event -> {
            try {
                overviewWindowStackPane.setVisible(false);
                transferWindow.setVisible(true);
                payWindow.setVisible(false);
                addExpenseWindow.setVisible(false);
                moreWindow.setVisible(false);

                System.out.println("transfer button pressed");

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        transferButton.setStyle("-fx-font-size: 25px; -fx-min-width: 155px; -fx-min-height: 50px; -fx-background-color: #9FB8AD; -fx-border-width: 2; -fx-background-radius: 5em;");
        topMenu.getChildren().add(transferButton);

        //BUTTON 3
        Button payButton = new Button("Pay");
        payButton.setOnAction(event -> {
            try {
                overviewWindowStackPane.setVisible(false);
                transferWindow.setVisible(false);
                payWindow.setVisible(true);
                addExpenseWindow.setVisible(false);
                moreWindow.setVisible(false);


                System.out.println("Pay button pressed");

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        payButton.setStyle("-fx-font-size: 25px; -fx-min-width: 155px; -fx-min-height: 50px; -fx-background-color: #9FB8AD; -fx-border-width: 2; -fx-background-radius: 5em;");
        topMenu.getChildren().add(payButton);

        //BUTTON 4
        Button addExpenseButton = new Button("Add Expense");
        addExpenseButton.setOnAction(event -> {
            try {

                overviewWindowStackPane.setVisible(false);
                transferWindow.setVisible(false);
                payWindow.setVisible(false);
                addExpenseWindow.setVisible(true);
                moreWindow.setVisible(false);


                System.out.println("add expense button pressed");

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        addExpenseButton.setStyle("-fx-font-size: 25px; -fx-min-width: 155px; -fx-min-height: 50px; -fx-background-color: #9FB8AD; -fx-border-width: 2; -fx-background-radius: 5em;");
        topMenu.getChildren().add(addExpenseButton);

        //BUTTON 5
        Button moreButton = new Button("More");
        moreButton.setOnAction(event -> {
            try {

                overviewWindowStackPane.setVisible(false);
                transferWindow.setVisible(false);
                payWindow.setVisible(false);
                addExpenseWindow.setVisible(false);
                moreWindow.setVisible(true);

                System.out.println("more button pressed");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        moreButton.setStyle("-fx-font-size: 25px; -fx-min-width: 155px; -fx-min-height: 50px; -fx-background-color: #9FB8AD; -fx-border-width: 2; -fx-background-radius: 5em;");
        topMenu.getChildren().add(moreButton);

        return topMenu;
    }


// register a ChangeListener with the StackPane's visibleProperty

}
