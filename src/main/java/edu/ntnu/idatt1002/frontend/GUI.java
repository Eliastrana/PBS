package edu.ntnu.idatt1002.frontend;

import com.itextpdf.text.DocumentException;
import edu.ntnu.idatt1002.backend.LoginObserver;
import edu.ntnu.idatt1002.frontend.menu.*;
import edu.ntnu.idatt1002.frontend.utility.SoundPlayer;
import edu.ntnu.idatt1002.model.ExcelExporter;
import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.beans.property.BooleanProperty;

import javafx.scene.layout.VBox;

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

    private StackPane passwordForgottenWindow = new StackPane();
    private StackPane createUserWindow = new StackPane();
    private StackPane overviewWindow = new StackPane();
    private StackPane transferWindow = new StackPane();
    private StackPane reportWindow = new StackPane();
    private StackPane addExpenseWindow = new StackPane();
    private StackPane settingsWindow = new StackPane();
    private StackPane budgetWindow = new StackPane();
    private StackPane bankStatementWindow = new StackPane();



    private BooleanProperty isLogin = new SimpleBooleanProperty(false);
    private BooleanProperty isCreateAccount = new SimpleBooleanProperty(false);

    private BooleanProperty passwordForgotten = new SimpleBooleanProperty(false);
    private BooleanProperty newPassword = new SimpleBooleanProperty(false);

    private BooleanProperty backToLoginForgot = new SimpleBooleanProperty(false);
    private BooleanProperty backToLoginCreate = new SimpleBooleanProperty(false);

    public static String currentUser;

    Scene sceneCreateUser = new Scene(createUserWindow);

    Scene scenePasswordForgotten = new Scene(passwordForgottenWindow);

    Scene scene = new Scene(loginWindow);


    public GUI() {
    }

    //HERE END THE DIFFERENT PANES AND BEGINS THE START METHOD, UPDATER AND TOPMENU

    @Override
    public void start(Stage primaryStage) throws Exception {
        externalStartMenu(primaryStage);
    }

    public void externalStartMenu(Stage primaryStage) {
        loginWindow.setVisible(true);
        loginWindow.getChildren().add(Login.loginView());
        loginWindow.getStylesheets().add("/LightMode.css");
        loginWindow.setAlignment(Pos.CENTER);
        loginWindow.setPrefSize(1000, 700);
        loginWindow.setMinSize(1000, 700);
        loginWindow.setMaxSize(1000, 700);

        Image icon = new Image("icons/icon.png");
        primaryStage.getIcons().add(icon);

        scene.getStylesheets().add("/Styling.css");

        primaryStage.setScene(scene);
        primaryStage.show();
        Login.addObserver(this);

        isCreateAccount.addListener((observable, oldValue, newValue) -> {
            if (isCreateAccount.get()) {
                launchCreateUser(primaryStage);
            }
        });

        isLogin.addListener((observable, oldValue, newValue) -> {
            if (isLogin.get()) {
                launchApp(primaryStage);
            }
        });

        passwordForgotten.addListener((observable, oldValue, newValue) -> {
            if (passwordForgotten.get()) {
                launchForgotPassword(primaryStage);
            }
        });
    }


    public void launchForgotPassword(Stage primaryStage){
        passwordForgottenWindow.getChildren().add(ForgotPassword.forgottenPasswordView());
        passwordForgottenWindow.getStylesheets().add("/Styling.css");
        passwordForgottenWindow.setStyle("-fx-background-color: #E6E8E6;");
        passwordForgottenWindow.setAlignment(Pos.CENTER);
        passwordForgottenWindow.setPadding(new Insets(10, 10, 10, 10));
        passwordForgottenWindow.setPrefSize(1000, 700);
        passwordForgottenWindow.setMinSize(1000, 700);
        passwordForgottenWindow.setMaxSize(1000, 700);

        Image icon = new Image("icons/icon.png");
        primaryStage.getIcons().add(icon);

        scenePasswordForgotten.getStylesheets().add("/Styling.css");

        primaryStage.setScene(scenePasswordForgotten);
        primaryStage.show();

        ForgotPassword.addObserver(this);

        newPassword.addListener((observable, oldValue, newValue) -> {
            if (newPassword.get()) {
                try {
                    externalStartMenu(primaryStage);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        backToLoginForgot.addListener((observable, oldValue, newValue) -> {
            if (backToLoginForgot.get()) {
                try {
                    backToLoginForgot.set(false);
                    ForgotPassword.setBackToLogin(false);
                    externalStartMenu(primaryStage);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
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
        primaryStage.setWidth(1050);
        primaryStage.setHeight(700);
        overviewWindow.getStylesheets().add("/LightMode.css");

        Image icon = new Image("icons/icon.png");
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

    public void launchCreateUser(Stage primaryStage) {
        createUserWindow.getChildren().add(CreateUser.createUserView());
        loginWindow.setVisible(true);
        loginWindow.getChildren().add(Login.loginView());
        loginWindow.getStylesheets().add("/LightMode.css");
        loginWindow.setStyle("-fx-background-color: #E6E8E6;");
        loginWindow.setAlignment(Pos.CENTER);
        loginWindow.setPrefSize(1000, 700);
        loginWindow.setMinSize(1000, 700);
        loginWindow.setMaxSize(1000, 700);

        sceneCreateUser.getStylesheets().add("/Styling.css");

        Image icon = new Image("icons/icon.png");
        primaryStage.getIcons().add(icon);

        CreateUser.addObserver(this);

        primaryStage.setScene(sceneCreateUser);
        primaryStage.show();

        backToLoginCreate.addListener((observable, oldValue, newValue) -> {
            if (backToLoginCreate.get()) {
                try {
                    backToLoginCreate.set(false);
                    CreateUser.setBackToLogin(false);
                    externalStartMenu(primaryStage);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }


    private void updatePane() {
        // update the contents of the paneToUpdate
        overviewWindow.getChildren().clear();
        try {
            ExcelExporter.exportToExcel();
            ExcelExporter.convertToPdf(ExcelExporter.exportToExcel(), "report");
        } catch ( IOException ex) {
            throw new RuntimeException(ex);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }

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

        ImageView overviewImage = new ImageView(new Image("icons/overview.png"));
        overviewImage.setFitHeight(20);
        overviewImage.setFitWidth(20);

        //BUTTON 1
        Button overviewButton = new Button("Overview", overviewImage);
        overviewButton.setId("topMenuButton");
        overviewButton.setOnAction(event -> {
            try {
                overviewWindow.visibleProperty().addListener((observable, oldValue, newValue) -> {
                    System.out.println(newValue);
                    if (newValue) { //Redundant, but just to be sure
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
                updatePane();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        topMenu.getChildren().add(overviewButton);

        ImageView transferImage = new ImageView(new Image("icons/transfer.png"));
        transferImage.setFitHeight(20);
        transferImage.setFitWidth(20);

        //BUTTON 2
        Button transferButton = new Button("Transfer", transferImage);
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

        ImageView addExpenseImage = new ImageView(new Image("icons/addExpense.png"));
        addExpenseImage.setFitHeight(20);
        addExpenseImage.setFitWidth(20);

        //BUTTON 4
        Button addExpenseButton = new Button("Add Expense", addExpenseImage);
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

        ImageView reportImage = new ImageView(new Image("icons/report.png"));
        reportImage.setFitHeight(20);
        reportImage.setFitWidth(20);

        Button reportButton = new Button("Report", reportImage);
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

        ImageView settingsImage = new ImageView(new Image("icons/settings.png"));
        settingsImage.setFitHeight(20);
        settingsImage.setFitWidth(20);

        Button settingsButton = new Button("Settings", settingsImage);
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

        ImageView budgetImage = new ImageView(new Image("icons/budget.png"));
        budgetImage.setFitHeight(20);
        budgetImage.setFitWidth(20);

        Button budgetButton = new Button("Budget", budgetImage);
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

        ImageView bankStatementImage = new ImageView(new Image("icons/bankStatement.png"));
        bankStatementImage.setFitHeight(20);
        bankStatementImage.setFitWidth(20);

        Button bankStatementButton = new Button("Bank Statement", bankStatementImage);
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

        ImageView logOutImage = new ImageView(new Image("icons/logOut.png"));
        logOutImage.setFitHeight(15);
        logOutImage.setFitWidth(15);

        Button logOutButton = new Button();
        logOutButton.setGraphic(logOutImage);
        logOutButton.setId("squareButton");

        topMenu.getChildren().add(logOutButton);
        logOutButton.setOnAction(event -> {
            SoundPlayer.play("src/main/resources/16bitconfirm.wav");

            try {
                externalStartMenu(primaryStage);  //This does NOT WORK
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            isLogin.setValue(false);
            isCreateAccount.setValue(false);
            passwordForgotten.setValue(false);
            newPassword.setValue(false);
            Login.setLoggedIn(false);
            CreateUser.setCreatedUser(false);
            Login.setForgotPasswordBoolean(false);
            ForgotPassword.setChangedPassword(false);
        });

        topMenu.getStylesheets().add("/Styling.css");

        return topMenu;
    }

    public static String getCurrentUser() {
        return currentUser;
    }

    @Override
    public void update() throws Exception {
        boolean isLoggedIn = Login.isLoggedIn();
        boolean createdUser = CreateUser.isCreatedUser();
        boolean forgotPassword = Login.isForgotPassword();
        boolean changedPassword = ForgotPassword.isChangedPassword();
        boolean isCreateUser = Login.isCreateUser();
        if (isLoggedIn) {
            currentUser = Login.username.getText();
            isLogin.setValue(true);
        }
        if (createdUser) {
            Login.username.clear();
            currentUser = CreateUser.username.getText();
            isLogin.setValue(true);
        }
        if (changedPassword) {
            Login.username.clear();
            newPassword.setValue(true);
        }
        if (forgotPassword) {
            Login.username.clear();
            passwordForgotten.setValue(true);
        }
        if (isCreateUser){
            Login.username.clear();
            isCreateAccount.setValue(true);
        }
       if (ForgotPassword.isBackToLogin()) {
            backToLoginForgot.setValue(true);
        }

        if (CreateUser.isBackToLogin()) {
            backToLoginCreate.setValue(true);
        }


    }
}

