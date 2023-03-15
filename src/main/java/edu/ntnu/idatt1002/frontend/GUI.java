package edu.ntnu.idatt1002.frontend;

<<<<<<< HEAD
import com.itextpdf.text.DocumentException;
=======
import edu.ntnu.idatt1002.backend.LoginObserver;
>>>>>>> f8d7858cea4c4de1b4f1e6b4a1bcbcd797dd9d3c
import edu.ntnu.idatt1002.frontend.menu.*;
import edu.ntnu.idatt1002.model.ExcelExporter;
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

import java.io.FileNotFoundException;
import java.io.IOException;


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

    public GUI() {
    }

    //HERE END THE DIFFERENT PANES AND BEGINS THE START METHOD, UPDATER AND TOPMENU

    @Override
<<<<<<< HEAD
    public void start(Stage primaryStage) {
=======
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
        transferWindow.getChildren().add(Transfer.transferView());
        reportWindow.getChildren().add(Report.reportView());
        addExpenseWindow.getChildren().add(AddExpense.expenseView());
        settingsWindow.getChildren().add(Settings.settingsView());
        budgetWindow.getChildren().add(Budget.budgetView());
        bankStatementWindow.getChildren().add(BankStatement.bankStatementView());

>>>>>>> f8d7858cea4c4de1b4f1e6b4a1bcbcd797dd9d3c
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
<<<<<<< HEAD
        overviewWindowStackPane.getChildren().clear();
        try {
            ExcelExporter.exportToExcel();
            ExcelExporter.convertToPdf();
        } catch (DocumentException | IOException ex) {
            throw new RuntimeException(ex);
        }
        overviewWindowStackPane.getChildren().add(Overview.overviewView());
=======
        overviewWindow.getChildren().clear();
        overviewWindow.getChildren().add(Overview.overviewView());

>>>>>>> f8d7858cea4c4de1b4f1e6b4a1bcbcd797dd9d3c
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

        topMenu.setSpacing(20);
        topMenu.setPadding(new Insets(20, 20, 20, 20));


        String buttonStyle= "-fx-font-size: 15px; -fx-min-width: 115px; -fx-min-height: 40px; -fx-background-color: #9FB8AD; -fx-border-width: 2; -fx-background-radius: 5em;";

        //overviewWindowStackPane, transferWindow, addExpenseWindow, reportWindow, settingsWindow, budgetWindow, bankStatementWindow

        //BUTTON 1
        Button overviewButton = new Button("Overview");
        overviewButton.setOnAction(event -> {
            try {
<<<<<<< HEAD
                overviewWindowStackPane.visibleProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue) {
                        updatePane();
                        }
                    });
                overviewWindowStackPane.setVisible(true);
=======

                overviewWindow.visibleProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue) {
                        updatePane();
                    }});

                overviewWindow.setVisible(true);
>>>>>>> f8d7858cea4c4de1b4f1e6b4a1bcbcd797dd9d3c
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
        overviewButton.setStyle(buttonStyle);
        topMenu.getChildren().add(overviewButton);


        //BUTTON 2
        Button transferButton = new Button("Transfer");
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
        transferButton.setStyle(buttonStyle);
        topMenu.getChildren().add(transferButton);


        //BUTTON 4
        Button addExpenseButton = new Button("Add Expense");
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
        addExpenseButton.setStyle(buttonStyle);
        topMenu.getChildren().add(addExpenseButton);

        //BUTTON 3


        Button reportButton = new Button("Report");
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
        reportButton.setStyle(buttonStyle);
        topMenu.getChildren().add(reportButton);


        Button settingsButton = new Button("Settings");
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
        settingsButton.setStyle(buttonStyle);
        topMenu.getChildren().add(settingsButton);



        Button budgetButton = new Button("Budget");
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
        budgetButton.setStyle(buttonStyle);
        topMenu.getChildren().add(budgetButton);

        Button bankStatementButton = new Button("Bank Statement");
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
        bankStatementButton.setStyle(buttonStyle);
        topMenu.getChildren().add(bankStatementButton);

        topMenu.getStylesheets().add("/style.css");

        return topMenu;
    }

    @Override
    public void update() {
        isLogin.setValue(true);
    }
}

