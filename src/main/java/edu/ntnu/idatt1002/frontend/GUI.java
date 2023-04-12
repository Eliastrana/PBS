package edu.ntnu.idatt1002.frontend;

import com.itextpdf.text.DocumentException;
import edu.ntnu.idatt1002.backend.ForgotPasswordBackend;
import edu.ntnu.idatt1002.backend.LoginBackend;
import edu.ntnu.idatt1002.backend.LoginObserver;
import edu.ntnu.idatt1002.frontend.controllers.CreateUserController;
import edu.ntnu.idatt1002.frontend.controllers.ForgotPasswordController;
import edu.ntnu.idatt1002.frontend.controllers.LoginController;
import edu.ntnu.idatt1002.frontend.menu.*;
import edu.ntnu.idatt1002.frontend.utility.SoundPlayer;
import edu.ntnu.idatt1002.model.ExcelExporter;
import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
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



public class GUI extends Application {

    //Each page has its own method, all the buttons are in the same method.
    //The buttons are then connected to the methods that open the pages.
    //The buttons are added to every single page individually, but they should be a separate entity
    //Each window is a StackPane, and the buttons are added to the StackPane
    //The StackPane is then added to the scene, and the scene is added to the stage


    //This stackPane holds the method of the overview window, this is done so that it is easier to
    //refresh the overview window.
    protected static StackPane overviewWindow = new StackPane();
    protected static StackPane transferWindow = new StackPane();
    protected static StackPane reportWindow = new StackPane();
    protected static StackPane addExpenseWindow = new StackPane();
    protected static StackPane settingsWindow = new StackPane();
    protected static StackPane budgetWindow = new StackPane();
    protected static StackPane bankStatementWindow = new StackPane();

    public static String currentUser;

    private Stage primaryStage;
    private LoginController loginController;
    private Login loginView;

    public GUI() {
    }

    //HERE END THE DIFFERENT PANES AND BEGINS THE START METHOD, UPDATER AND TOPMENU

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        loginController = new LoginController(this);
        loginView = new Login();

        Parent root = loginView.loginView(loginController);
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/Styling.css");

        primaryStage.setTitle("Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void navigateToLogin() {
        loginController = new LoginController(this);
        loginView = new Login();

        Parent root = loginView.loginView(loginController);
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/Styling.css");
        updateScene(scene, root);
    }

    public void navigateToCreateUser() {
        CreateUserController createUserController = new CreateUserController(this);
        CreateUser createUser = new CreateUser();

        Parent root = createUser.createUserView(createUserController);
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/Styling.css");
        updateScene(scene, root);
    }

    public void navigateToForgotPassword() {
        ForgotPasswordController forgotPasswordController = new ForgotPasswordController(this);
        ForgotPassword forgotPasswordView = new ForgotPassword();

        Parent root = forgotPasswordView.forgottenPasswordView(forgotPasswordController);
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/Styling.css");
        updateScene(scene, root);
    }

    public void navigateToMainApp() {
        if (LoginBackend.getCurrentUser() != null) {
            setCurrentUser(LoginBackend.getCurrentUser());
        } else if (CreateUser.getCurrentUser() != null) {
            setCurrentUser(CreateUser.getCurrentUser());
        } else {
            setCurrentUser("User");
        }

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

    public static void setCurrentUser(String currentUser) {
        GUI.currentUser = currentUser;
    }

    private void updateScene(Scene scene, Parent root) {
        Stage stage = (Stage) primaryStage.getScene().getWindow();
        stage.setScene(scene);
    }
}
