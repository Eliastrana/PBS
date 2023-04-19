package edu.ntnu.idatt1002.frontend;

import edu.ntnu.idatt1002.backend.LoginBackend;
import edu.ntnu.idatt1002.frontend.utility.SoundPlayer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class TopMenu {

  private static GUI gui;
  public TopMenu(GUI gui) {
    this.gui = gui;
  }

  public static HBox topMenu(Stage primaryStage) {
    BorderPane borderPane = new BorderPane();
    MenuBar menuBar = new MenuBar();
    borderPane.setTop(menuBar);
    HBox topMenu = new HBox();
    topMenu.setAlignment(Pos.TOP_CENTER);
    primaryStage.show();


    topMenu.setSpacing(20);
    topMenu.setPadding(new Insets(20, 20, 20, 20));

    ImageView overviewImage = new ImageView(new Image("/icons/overview.png"));
    overviewImage.setFitHeight(20);
    overviewImage.setFitWidth(20);

    //BUTTON 1
    Button overviewButton = new Button("Overview", overviewImage);
    overviewButton.setId("topMenuButton");
    overviewButton.setOnKeyPressed(event -> {
      if (event.getCode() == KeyCode.ENTER) {
        overviewButton.fire();
        event.consume();
      }
    });
    overviewButton.setOnAction(event -> {
      try {
        GUI.overviewWindow.visibleProperty().addListener((observable, oldValue, newValue) -> {
          System.out.println(newValue);
          if (newValue) { //Redundant, but just to be sure
            GUI.updatePane();
          }});

        GUI.overviewWindow.setVisible(true);
        GUI.transferWindow.setVisible(false);
        GUI.addExpenseWindow.setVisible(false);
        GUI.reportWindow.setVisible(false);
        GUI.settingsWindow.setVisible(false);
        GUI.budgetWindow.setVisible(false);
        GUI.bankStatementWindow.setVisible(false);


        System.out.println("overview button pressed");
        GUI.updatePane();
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    });
    topMenu.getChildren().add(overviewButton);

    ImageView transferImage = new ImageView(new Image("/icons/transfer.png"));
    transferImage.setFitHeight(20);
    transferImage.setFitWidth(20);

    //BUTTON 2
    Button transferButton = new Button("Transfer", transferImage);
    transferButton.setId("topMenuButton");
    transferButton.setOnKeyPressed(event -> {
      if (event.getCode() == KeyCode.ENTER) {
        transferButton.fire();
        event.consume();
      }
    });
    transferButton.setOnAction(event -> {
      try {

        GUI.overviewWindow.setVisible(false);
        GUI.transferWindow.setVisible(true);
        GUI.addExpenseWindow.setVisible(false);
        GUI.reportWindow.setVisible(false);
        GUI.settingsWindow.setVisible(false);
        GUI.budgetWindow.setVisible(false);
        GUI.bankStatementWindow.setVisible(false);


        System.out.println("transfer button pressed");

      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    });
    topMenu.getChildren().add(transferButton);

    ImageView addExpenseImage = new ImageView(new Image("/icons/addExpense.png"));
    addExpenseImage.setFitHeight(20);
    addExpenseImage.setFitWidth(20);

    //BUTTON 4
    Button addExpenseButton = new Button("Add Expense", addExpenseImage);
    addExpenseButton.setId("topMenuButton");
    addExpenseButton.setOnKeyPressed(event -> {
      if (event.getCode() == KeyCode.ENTER) {
        addExpenseButton.fire();
        event.consume();
      }
    });
    addExpenseButton.setOnAction(event -> {
      try {

        GUI.addExpenseWindow.visibleProperty().addListener((observable, oldValue, newValue) -> {
          System.out.println(newValue);
          if (newValue) { //Redundant, but just to be sure
            GUI.updatePane();
          }});

//        FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), GUI.addExpenseWindow);
//        fadeTransition.setFromValue(0);
//        fadeTransition.setToValue(1);
//        fadeTransition.play();

        GUI.addExpenseWindow.setVisible(true);
        GUI.overviewWindow.setVisible(false);
        GUI.transferWindow.setVisible(false);
        GUI.reportWindow.setVisible(false);
        GUI.settingsWindow.setVisible(false);
        GUI.budgetWindow.setVisible(false);
        GUI.bankStatementWindow.setVisible(false);


        System.out.println("add expense button pressed");

      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    });
    topMenu.getChildren().add(addExpenseButton);

    //BUTTON 3

    ImageView reportImage = new ImageView(new Image("/icons/report.png"));
    reportImage.setFitHeight(20);
    reportImage.setFitWidth(20);

    Button reportButton = new Button("Report" ,reportImage);
    reportButton.setId("topMenuButton");
    reportButton.setOnKeyPressed(event -> {
      if (event.getCode() == KeyCode.ENTER) {
        reportButton.fire();
        event.consume();
      }
    });
    reportButton.setOnAction(event -> {
      try {

        GUI.overviewWindow.setVisible(false);
        GUI.transferWindow.setVisible(false);
        GUI.addExpenseWindow.setVisible(false);
        GUI.reportWindow.setVisible(true);
        GUI.settingsWindow.setVisible(false);
        GUI.budgetWindow.setVisible(false);
        GUI.bankStatementWindow.setVisible(false);


        System.out.println("add expense button pressed");

      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    });
    topMenu.getChildren().add(reportButton);

    ImageView settingsImage = new ImageView(new Image("/icons/settings.png"));
    settingsImage.setFitHeight(20);
    settingsImage.setFitWidth(20);



    ImageView budgetImage = new ImageView(new Image("/icons/budget.png"));
    budgetImage.setFitHeight(20);
    budgetImage.setFitWidth(20);

    Button budgetButton = new Button("Budget", budgetImage);
    budgetButton.setId("topMenuButton");
    budgetButton.setOnKeyPressed(event -> {
      if (event.getCode() == KeyCode.ENTER) {
        budgetButton.fire();
        event.consume();
      }
    });
    budgetButton.setOnAction(event -> {
      try {

        GUI.budgetWindow.visibleProperty().addListener((observable, oldValue, newValue) -> {
          System.out.println(newValue);
          if (newValue) { //Redundant, but just to be sure
            GUI.updatePane();
          }});
        GUI.overviewWindow.setVisible(false);
        GUI.transferWindow.setVisible(false);
        GUI.addExpenseWindow.setVisible(false);
        GUI.reportWindow.setVisible(false);
        GUI.settingsWindow.setVisible(false);
        GUI.budgetWindow.setVisible(true);
        GUI.bankStatementWindow.setVisible(false);

        System.out.println("settings button pressed");
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    });
    topMenu.getChildren().add(budgetButton);

    ImageView bankStatementImage = new ImageView(new Image("/icons/bankStatement.png"));
    bankStatementImage.setFitHeight(20);
    bankStatementImage.setFitWidth(20);

    Button bankStatementButton = new Button("Bank Statement", bankStatementImage);
    bankStatementButton.setId("topMenuButton");
    bankStatementButton.setOnKeyPressed(event -> {
      if (event.getCode() == KeyCode.ENTER) {
        bankStatementButton.fire();
        event.consume();
      }
    });
    bankStatementButton.setOnAction(event -> {
      try {

        GUI.bankStatementWindow.visibleProperty().addListener((observable, oldValue, newValue) -> {
          System.out.println(newValue);
          if (newValue) { //Redundant, but just to be sure
            GUI.updatePane();
          }});

        GUI.overviewWindow.setVisible(false);
        GUI.transferWindow.setVisible(false);
        GUI.addExpenseWindow.setVisible(false);
        GUI.reportWindow.setVisible(false);
        GUI.settingsWindow.setVisible(false);
        GUI.budgetWindow.setVisible(false);
        GUI.bankStatementWindow.setVisible(true);

        System.out.println("settings button pressed");
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    });
    topMenu.getChildren().add(bankStatementButton);


    ImageView settingsIcon = new ImageView(new Image("/icons/settings.png"));
    settingsIcon.setFitHeight(15);
    settingsIcon.setFitWidth(15);

    Button settingsButton = new Button();
    settingsButton.setGraphic(settingsIcon);
    settingsButton.setId("squareButton");
    settingsButton.setOnKeyPressed(event -> {
      if (event.getCode() == KeyCode.ENTER) {
        settingsButton.fire();
        event.consume();
      }
    });
    settingsButton.setOnAction(event -> {
      try {
        GUI.overviewWindow.setVisible(false);
        GUI.transferWindow.setVisible(false);
        GUI.addExpenseWindow.setVisible(false);
        GUI.reportWindow.setVisible(false);
        GUI.settingsWindow.setVisible(true);
        GUI.budgetWindow.setVisible(false);
        GUI.bankStatementWindow.setVisible(false);

        System.out.println("settings button pressed");
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    });
    topMenu.getChildren().add(settingsButton);




    ImageView logOutImage = new ImageView(new Image("/icons/logOut.png"));
    logOutImage.setFitHeight(15);
    logOutImage.setFitWidth(15);

    Button logOutButton = new Button();
    logOutButton.setGraphic(logOutImage);
    logOutButton.setId("squareButton");

    topMenu.getChildren().add(logOutButton);
    logOutButton.setOnKeyPressed(event -> {
      if (event.getCode() == KeyCode.ENTER) {
        logOutButton.fire();
        event.consume();
      }
    });
    logOutButton.setOnAction(event -> {
      SoundPlayer.play("src/main/resources/16bitconfirm.wav");

      try {
        LoginBackend.setCurrentUser(null);
        CreateUser.setCurrentUser(null);
        GUI.settingsWindow.getChildren().clear();
        gui.navigateToLogin();
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    });

    return topMenu;
  }
}
