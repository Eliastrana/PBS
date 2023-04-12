package edu.ntnu.idatt1002.frontend;

import com.itextpdf.text.DocumentException;
import edu.ntnu.idatt1002.backend.ForgotPasswordBackend;
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
    protected static StackPane overviewWindow = new StackPane();
    protected static StackPane transferWindow = new StackPane();
    protected static StackPane reportWindow = new StackPane();
    protected static StackPane addExpenseWindow = new StackPane();
    protected static StackPane settingsWindow = new StackPane();
    protected static StackPane budgetWindow = new StackPane();
    protected static StackPane bankStatementWindow = new StackPane();



    BooleanProperty isLogin = new SimpleBooleanProperty(false);
    BooleanProperty isCreateAccount = new SimpleBooleanProperty(false);

    BooleanProperty passwordForgotten = new SimpleBooleanProperty(false);
    BooleanProperty newPassword = new SimpleBooleanProperty(false);

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
        TopMenu topMenu = new TopMenu(this);
        borderPane.setTop(topMenu.topMenu(primaryStage));
        borderPane.setCenter(root);

        updatePane();
    }


    static void updatePane() {
        // update the contents of the paneToUpdate
        overviewWindow.getChildren().clear();
        try {
            ExcelExporter.exportToExcel();
            ExcelExporter.convertToPdf(ExcelExporter.exportToExcel(), "report");
        } catch (IOException | DocumentException ex) {
            throw new RuntimeException(ex);
        }

        overviewWindow.getChildren().add(Overview.overviewView());

    }

    public static String getCurrentUser() {
        return currentUser;
    }

    @Override
    public void update() throws Exception {
        boolean isLoggedIn = Login.isLoggedIn();
        boolean createdUser = CreateUser.isCreatedUser();
        boolean forgotPassword = Login.isForgotPassword();
        boolean changedPassword = ForgotPasswordBackend.isChangedPassword();
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
