package edu.ntnu.idatt1002;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.time.LocalDate;

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
        Text textSavings = new Text("Total savings");
        textSavings.setStyle("-fx-fill: #3F403F");
        textSavings.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 40));
        hbox2.getChildren().add(textSavings);

        Text textSpending = new Text("Monthly spending");
        textSpending.setStyle("-fx-fill: #3F403F");
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


        //LeftTable
        TableView<Expense> leftTable = new TableView<>();
        TableColumn<Expense, String> leftColumn1 = new TableColumn<>("Name: ");
        leftColumn1.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Expense, Double> leftColumn2 = new TableColumn<>("Price: ");
        leftColumn2.setCellValueFactory(new PropertyValueFactory<>("price"));

        leftTable.getColumns().addAll(leftColumn1, leftColumn2);

        leftTable.getItems().addAll(transportation);

        vboxSavings.getChildren().add(leftTable);

        //TODO vboxSpending.getChildren().add(rightTable);

        //RightTable
        TableView<Expense> rightTable = new TableView<>();
        TableColumn<Expense, String> rightColumn1 = new TableColumn<>("Name: ");
        rightColumn1.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Expense, Double> rightColumn2 = new TableColumn<>("Price: ");
        rightColumn2.setCellValueFactory(new PropertyValueFactory<>("price"));

        rightTable.getColumns().addAll(rightColumn1, rightColumn2);

        rightTable.getItems().addAll(rent);

        vboxSpending.getChildren().add(rightTable);


        Text currentAccountStatusText = new Text("Current account status");
        currentAccountStatusText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 40));
        currentAccountStatusTextFormat.getChildren().add(currentAccountStatusText);

        //barChart.start();


        //topMenu(primaryStage);

        VBox vbox = new VBox(text, text2, hboxPieLayout, emptySpace, currentAccountStatusTextFormat);

        overviewWindowStackPane.getChildren().add(vbox);

    }

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

        HBox newExpenseTitle = new HBox(2);
        Text text3 = new Text("Add new expense");
        text3.setStyle("-fx-fill: #3F403F");
        text3.setFont(Font.font("helvetica", FontWeight.BOLD, FontPosture.REGULAR, 60));

        DatePicker datePicker = new DatePicker();
        datePicker.setValue(LocalDate.now());
        datePicker.setShowWeekNumbers(true);
        datePicker.setStyle("-fx-font-size: 20px; -fx-min-width: 100px; -fx-min-height: 50px;-fx-background-color: #9FB8AD; -fx-border-width: 2; -fx-padding: 10px; -fx-background-radius: 0.5em;");

        HBox hboxAddExpenseCategory = new HBox(2);

//        Text pickCategory = new Text("Pick a category: ");
//        pickCategory.setStyle("-fx-fill: #3F403F");
//        pickCategory.setFont(Font.font("helvetica", FontWeight.BOLD, FontPosture.REGULAR, 20));


        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "rent",
                        "Food",
                        "Transportation",
                        "Clothing",
                        "Entertainment",
                        "Other"
                );


        final ComboBox categoryMenu = new ComboBox(options);
        categoryMenu.setPromptText("Pick a category");
        categoryMenu.setStyle("-fx-font-size: 20px; -fx-min-width: 100px; -fx-min-height: 50px;-fx-background-color: #9FB8AD; -fx-border-width: 2; -fx-padding: 10px; -fx-background-radius: 0.5em; -fx-prompt-text-fill: #FFFFFF; -fx-text-fill: #FFFFFF;");


//        Text pickPrice = new Text("Pick a price: ");
//        pickPrice.setFont(Font.font("Helvetica", FontWeight.BOLD, FontPosture.REGULAR, 20));
//        pickPrice.setStyle("-fx-fill: #3F403F");
//        pickPrice.setStyle("-fx-padding: 20px;");


        TextField prices = new TextField();
        prices.setPromptText("Enter price");
        prices.setStyle("-fx-font-size: 20px; -fx-min-width: 100px; -fx-min-height: 50px;-fx-background-color: #9FB8AD; -fx-border-width: 2; -fx-padding: 10px; -fx-background-radius: 0.5em; -fx-prompt-text-fill: #FFFFFF; -fx-text-fill: #FFFFFF;");



//        Text pickName = new Text("Pick a name: ");
//        pickName.setFont(Font.font("Helvetica", FontWeight.BOLD, FontPosture.REGULAR, 20));
//        pickName.setStyle("-fx-fill: #3F403F");
//        pickName.setStyle("-fx-padding: 20px;");

        TextField names = new TextField();
        names.setPromptText("Enter name");
        names.setStyle("-fx-font-size: 20px; -fx-min-width: 100px; -fx-min-height: 50px;-fx-background-color: #9FB8AD; -fx-border-width: 2; -fx-padding: 10px; -fx-background-radius: 0.5em; -fx-prompt-text-fill: #FFFFFF; -fx-text-fill: #FFFFFF;");





        Button confirmExpense = new Button("Confirm");
        confirmExpense.setStyle("-fx-font-size: 30px; -fx-min-width: 100px; -fx-min-height: 50px;-fx-background-color: #9FB8AD; -fx-border-width: 2; -fx-padding: 10px; -fx-background-radius: 0.5em;");


        confirmExpense.setOnAction(e -> {
            String selectedOption = (String) categoryMenu.getValue();
            String name = String.valueOf(names); //endret denne fra pickName til names
            String tempText = prices.getText();
            double price = Double.parseDouble(tempText); //det er en error her

            if (selectedOption.equals("Entertainment")) {
                Expenses.addToArrayList(new Expense(name, price, 1), entertainment);
            } else if (selectedOption.equals("Food")) {
                Expenses.addToArrayList(new Expense(name, price, 1), food);
            } else if (selectedOption.equals("Transportation")) {
                Expenses.addToArrayList(new Expense(name, price, 1), transportation);
            } else if (selectedOption.equals("Clothing")) {
                Expenses.addToArrayList(new Expense(name, price, 1), clothing);
            } else if (selectedOption.equals("Other")) {
                Expenses.addToArrayList(new Expense(name, price, 1), other);
            } else if (selectedOption.equals("Rent")) {
                Expenses.addToArrayList(new Expense(name, price, 1), rent);
            } else {
                System.out.println("Error");
            }

            System.out.println("Purchase confirmed");
            System.out.println("Category: " + selectedOption);

            categoryMenu.setValue(null);
            names.setText(null);
            prices.setText(null);

            SoundPlayer.play("src/main/resources/16bitconfirm.wav");
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
