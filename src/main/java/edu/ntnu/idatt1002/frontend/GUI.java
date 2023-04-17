package edu.ntnu.idatt1002.frontend;

import com.itextpdf.text.DocumentException;
import edu.ntnu.idatt1002.backend.ForgotPasswordBackend;
import edu.ntnu.idatt1002.backend.LoginBackend;
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


/**
 * The type Gui.
 */
public class GUI extends Application {

    //Each page has its own method, all the buttons are in the same method.
    //The buttons are then connected to the methods that open the pages.
    //The buttons are added to every single page individually, but they should be a separate entity
    //Each window is a StackPane, and the buttons are added to the StackPane
    //The StackPane is then added to the scene, and the scene is added to the stage


    /**
     * The constant overviewWindow.
     */
//This stackPane holds the method of the overview window, this is done so that it is easier to
    //refresh the overview window.
    protected static StackPane overviewWindow = new StackPane();
    /**
     * The constant transferWindow.
     */
    protected static StackPane transferWindow = new StackPane();
    /**
     * The constant reportWindow.
     */
    protected static StackPane reportWindow = new StackPane();
    /**
     * The constant addExpenseWindow.
     */
    protected static StackPane addExpenseWindow = new StackPane();
    /**
     * The constant settingsWindow.
     */
    protected static StackPane settingsWindow = new StackPane();
    /**
     * The constant budgetWindow.
     */
    protected static StackPane budgetWindow = new StackPane();
    /**
     * The constant bankStatementWindow.
     */
    protected static StackPane bankStatementWindow = new StackPane();

    /**
     * The constant currentUser.
     */
    public static String currentUser;

    private Stage primaryStage;
    private LoginController loginController;
    private Login loginView;
    private static String stylesheet = "/Styling.css";

    /**
     * Instantiates a new Gui.
     */
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


        scene.getStylesheets().add(stylesheet);

        primaryStage.setTitle("Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Navigate to login.
     */
    public void navigateToLogin() {
        loginController = new LoginController(this);
        loginView = new Login();

        Parent root = loginView.loginView(loginController);
        Scene scene = new Scene(root);
        scene.getStylesheets().add(stylesheet);
        updateScene(scene, root);
    }

    /**
     * Navigate to create user.
     */
    public void navigateToCreateUser() {
        CreateUserController createUserController = new CreateUserController(this);
        CreateUser createUser = new CreateUser();

        Parent root = createUser.createUserView(createUserController);
        Scene scene = new Scene(root);
        scene.getStylesheets().add(stylesheet);
        updateScene(scene, root);
    }

    /**
     * Navigate to forgot password.
     */
    public void navigateToForgotPassword() {
        ForgotPasswordController forgotPasswordController = new ForgotPasswordController(this);
        ForgotPassword forgotPasswordView = new ForgotPassword();

        Parent root = forgotPasswordView.forgottenPasswordView(forgotPasswordController);
        Scene scene = new Scene(root);
        scene.getStylesheets().add(stylesheet);
        updateScene(scene, root);
    }

    /**
     * Navigate to main app.
     *
     * @throws IOException the io exception
     */
    public void navigateToMainApp() throws IOException {
        if (LoginBackend.getCurrentUser() != null) {
            setCurrentUser(LoginBackend.getCurrentUser());
        } else if (CreateUser.getCurrentUser() != null) {
            setCurrentUser(CreateUser.getCurrentUser());
        } else {
            setCurrentUser("User");
        }

        overviewWindow.getChildren().add(Overview.overviewView());
        overviewWindow.getStylesheets().add(stylesheet);

        transferWindow.getChildren().add(Transfer.transferView());
        transferWindow.getStylesheets().add(stylesheet);

        reportWindow.getChildren().add(Report.reportView());
        reportWindow.getStylesheets().add(stylesheet);

        addExpenseWindow.getChildren().add(AddExpense.expenseView());
        addExpenseWindow.getStylesheets().add(stylesheet);

        settingsWindow.getChildren().add(Settings.settingsView());
        settingsWindow.getStylesheets().add(stylesheet);

        budgetWindow.getChildren().add(Budget.budgetView());
        budgetWindow.getStylesheets().add(stylesheet);

        bankStatementWindow.getChildren().add(BankStatement.bankStatementView());
        bankStatementWindow.getStylesheets().add(stylesheet);



        primaryStage.setTitle("Bank");
        primaryStage.setWidth(1050);
        primaryStage.setHeight(700);
        overviewWindow.getStylesheets().add(stylesheet);

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


    /**
     * Update pane.
     */
    public static void updatePane() {
        // update the contents of the paneToUpdate

        try {
            ExcelExporter.exportToExcel();
            ExcelExporter.convertToPdf(ExcelExporter.exportToExcel(), "report");
        } catch (IOException | DocumentException ex) {
            throw new RuntimeException(ex);
        }

        //THIS CODE IS BAD AND MAKES THE WHOLE PROGRAM SLOW
        overviewWindow.getChildren().clear();
        overviewWindow.getChildren().add(Overview.overviewView());
        transferWindow.getChildren().clear();
        transferWindow.getChildren().add(Transfer.transferView());
        addExpenseWindow.getChildren().clear();
        addExpenseWindow.getChildren().add(AddExpense.expenseView());
        reportWindow.getChildren().clear();
        reportWindow.getChildren().add(Report.reportView());
        budgetWindow.getChildren().clear();
        budgetWindow.getChildren().add(Budget.budgetView());
        settingsWindow.getChildren().clear();
        settingsWindow.getChildren().add(Settings.settingsView());


    }

    /**
     * Gets current user.
     *
     * @return the current user
     */
    public static String getCurrentUser() {
        return currentUser;
    }

    /**
     * Sets current user.
     *
     * @param currentUser the current user
     */
    public static void setCurrentUser(String currentUser) {
        GUI.currentUser = currentUser;
    }
    public static void setStylesheet(String stylesheet) {
        GUI.stylesheet = stylesheet;
    }
    public static String getStylesheet() {
        return stylesheet;
    }
    public static void setStyle(String style) {
        StackPane[] stackPanes = new StackPane[]{overviewWindow, transferWindow, addExpenseWindow, reportWindow, budgetWindow, settingsWindow};

        for (StackPane stackPane : stackPanes) {
            stackPane.getStylesheets().clear();
            stackPane.getStylesheets().add("/" + style + ".css");
        }
    }

    private void updateScene(Scene scene, Parent root) {
        Stage stage = (Stage) primaryStage.getScene().getWindow();
        stage.setScene(scene);
        stage.setMinHeight(700);
        stage.setMinWidth(1000);
    }
}
