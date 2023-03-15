package edu.ntnu.idatt1002.frontend;

import edu.ntnu.idatt1002.backend.LoginObserver;
import edu.ntnu.idatt1002.frontend.menu.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.beans.property.BooleanProperty;

import javafx.scene.layout.VBox;


public class GUI extends Application implements LoginObserver {

    //Each page has its own method, all the buttons are in the same method.
    //The buttons are then connected to the methods that open the pages.
    //The buttons are added to every single page individually, but they should be a separate entity
    //Each window is a StackPane, and the buttons are added to the StackPane
    //The StackPane is then added to the scene, and the scene is added to the stage


    //This stackPane holds the method of the overview window, this is done so that it is easier to
    //refresh the overview window.
    private StackPane loginWindow = new StackPane(new VBox(Login.loginView()));
    private StackPane overviewWindow = new StackPane();
    private StackPane transferWindow = new StackPane();
    private StackPane reportWindow = new StackPane();
    private StackPane addExpenseWindow = new StackPane();
    private StackPane settingsWindow = new StackPane();
    private StackPane budgetWindow = new StackPane();
    private StackPane bankStatementWindow = new StackPane();

    private BooleanProperty isLogin = new SimpleBooleanProperty(false);

    //HERE END THE DIFFERENT PANES AND BEGINS THE START METHOD, UPDATER AND TOPMENU

    @Override
    public void start(Stage primaryStage) throws Exception {
        loginWindow.setVisible(true);
        loginWindow.getChildren().add(Login.loginView());
        loginWindow.getStylesheets().add("/LightMode.css");
        loginWindow.setStyle("-fx-background-color: #E6E8E6;");
        loginWindow.setAlignment(Pos.CENTER);
        loginWindow.setPadding(new Insets(10, 10, 10, 10));
        loginWindow.setPrefSize(1000, 700);
        loginWindow.setMinSize(1000, 700);
        loginWindow.setMaxSize(1000, 700);

        Scene scene = new Scene(loginWindow);
        scene.getStylesheets().add("/Styling.css");

        primaryStage.setScene(scene);
        primaryStage.show();
        Login.addObserver(this);

        isLogin.addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                launchApp(primaryStage);
            }
        });
    }

    public void launchApp(Stage primaryStage) {
        overviewWindow.getChildren().add(Overview.overviewView());
        overviewWindow.getStylesheets().add("/Styling.css");

        transferWindow.getChildren().add(Transfer.transferView());
        transferWindow.getStylesheets().add("/Styling.css");

        reportWindow.getChildren().add(Report.reportView());
        reportWindow.getStylesheets().add("/Styling.css");

        addExpenseWindow.getChildren().add(AddExpense.expenseView());
        addExpenseWindow.getStylesheets().add("/Styling.css");

        settingsWindow.getChildren().add(Settings.settingsView());
        settingsWindow.getStylesheets().add("/Styling.css");

        budgetWindow.getChildren().add(Budget.budgetView());
        budgetWindow.getStylesheets().add("/Styling.css");

        bankStatementWindow.getChildren().add(BankStatement.bankStatementView());
        bankStatementWindow.getStylesheets().add("/Styling.css");


        primaryStage.setTitle("Bank");
        primaryStage.setWidth(1000);
        primaryStage.setHeight(700);
        overviewWindow.getStylesheets().add("/LightMode.css");

        Image icon = new Image("icon.png");
        primaryStage.getIcons().add(icon);

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

        overviewWindow.setVisible(true);
        transferWindow.setVisible(false);
        addExpenseWindow.setVisible(false);
        reportWindow.setVisible(false);
        settingsWindow.setVisible(false);
        budgetWindow.setVisible(false);
        bankStatementWindow.setVisible(false);


        StackPane root = new StackPane();

        root.getChildren().addAll(overviewWindow, transferWindow, addExpenseWindow, reportWindow, settingsWindow, budgetWindow, bankStatementWindow );
        borderPane.setTop(topMenu(primaryStage));
        borderPane.setCenter(root);

        updatePane();
    }


    private void updatePane() {
        // update the contents of the paneToUpdate
        overviewWindow.getChildren().clear();
        overviewWindow.getChildren().add(Overview.overviewView());

    }


    //TOP MENU
    //TODO Prog 2, Øving 11, Property, ex 2, få knappene til å variere å størrelse ut ifra hvor stort vinduet er
    //TODO Flytte meny ut i TopMenu klassen
    public HBox topMenu(Stage primaryStage) {

        BorderPane borderPane = new BorderPane();
        MenuBar menuBar = new MenuBar();
        borderPane.setTop(menuBar);
        HBox topMenu = new HBox();
        topMenu.setAlignment(Pos.TOP_CENTER);
        primaryStage.show();

        topMenu.getStylesheets().add("/Styling.css");

        topMenu.setSpacing(20);
        topMenu.setPadding(new Insets(20, 20, 20, 20));

        //BUTTON 1
        Button overviewButton = new Button("Overview");
        overviewButton.setId("topMenuButton");
        overviewButton.setOnAction(event -> {
            try {

                overviewWindow.visibleProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue) {
                        updatePane();
                    }});

                overviewWindow.setVisible(true);
                transferWindow.setVisible(false);
                addExpenseWindow.setVisible(false);
                reportWindow.setVisible(false);
                settingsWindow.setVisible(false);
                budgetWindow.setVisible(false);
                bankStatementWindow.setVisible(false);


                System.out.println("overview button pressed");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        topMenu.getChildren().add(overviewButton);


        //BUTTON 2
        Button transferButton = new Button("Transfer");
        transferButton.setId("topMenuButton");
        transferButton.setOnAction(event -> {
            try {
                overviewWindow.setVisible(false);
                transferWindow.setVisible(true);
                addExpenseWindow.setVisible(false);
                reportWindow.setVisible(false);
                settingsWindow.setVisible(false);
                budgetWindow.setVisible(false);
                bankStatementWindow.setVisible(false);


                System.out.println("transfer button pressed");

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        topMenu.getChildren().add(transferButton);


        //BUTTON 4
        Button addExpenseButton = new Button("Add Expense");
        addExpenseButton.setId("topMenuButton");
        addExpenseButton.setOnAction(event -> {
            try {

                overviewWindow.setVisible(false);
                transferWindow.setVisible(false);
                addExpenseWindow.setVisible(true);
                reportWindow.setVisible(false);
                settingsWindow.setVisible(false);
                budgetWindow.setVisible(false);
                bankStatementWindow.setVisible(false);


                System.out.println("add expense button pressed");

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        topMenu.getChildren().add(addExpenseButton);

        //BUTTON 3


        Button reportButton = new Button("Report");
        reportButton.setId("topMenuButton");
        reportButton.setOnAction(event -> {
            try {

                overviewWindow.setVisible(false);
                transferWindow.setVisible(false);
                addExpenseWindow.setVisible(false);
                reportWindow.setVisible(true);
                settingsWindow.setVisible(false);
                budgetWindow.setVisible(false);
                bankStatementWindow.setVisible(false);


                System.out.println("add expense button pressed");

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        topMenu.getChildren().add(reportButton);


        Button settingsButton = new Button("Settings");
        settingsButton.setId("topMenuButton");
        settingsButton.setOnAction(event -> {
            try {
                overviewWindow.setVisible(false);
                transferWindow.setVisible(false);
                addExpenseWindow.setVisible(false);
                reportWindow.setVisible(false);
                settingsWindow.setVisible(true);
                budgetWindow.setVisible(false);
                bankStatementWindow.setVisible(false);

                System.out.println("settings button pressed");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        topMenu.getChildren().add(settingsButton);



        Button budgetButton = new Button("Budget");
        budgetButton.setId("topMenuButton");
        budgetButton.setOnAction(event -> {
            try {
                overviewWindow.setVisible(false);
                transferWindow.setVisible(false);
                addExpenseWindow.setVisible(false);
                reportWindow.setVisible(false);
                settingsWindow.setVisible(false);
                budgetWindow.setVisible(true);
                bankStatementWindow.setVisible(false);

                System.out.println("settings button pressed");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        topMenu.getChildren().add(budgetButton);

        Button bankStatementButton = new Button("Bank Statement");
        bankStatementButton.setId("topMenuButton");
        bankStatementButton.setOnAction(event -> {
            try {
                overviewWindow.setVisible(false);
                transferWindow.setVisible(false);
                addExpenseWindow.setVisible(false);
                reportWindow.setVisible(false);
                settingsWindow.setVisible(false);
                budgetWindow.setVisible(false);
                bankStatementWindow.setVisible(true);

                System.out.println("settings button pressed");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        topMenu.getChildren().add(bankStatementButton);

        topMenu.getStylesheets().add("/style.css");

        return topMenu;
    }

    @Override
    public void update() {
        isLogin.setValue(true);
    }
}

