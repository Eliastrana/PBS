package edu.ntnu.idatt1002.frontend.menu;

import edu.ntnu.idatt1002.backend.budgeting.Accounts;
import edu.ntnu.idatt1002.backend.budgeting.Income;
import edu.ntnu.idatt1002.frontend.GUI;
import edu.ntnu.idatt1002.frontend.utility.FileUtil;
import edu.ntnu.idatt1002.frontend.utility.SoundPlayer;
import edu.ntnu.idatt1002.model.CSVReader;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

import static edu.ntnu.idatt1002.backend.budgeting.Accounts.accounts;
import static edu.ntnu.idatt1002.backend.budgeting.Incomes.incomes;
import static edu.ntnu.idatt1002.frontend.utility.AlertWindow.showAlert;

/**
 * A class that creates the transfer view.
 *
 * @author Emil J., Vegard J., Sander S. and Elias T.
 * @version 0.5 - 19.04.2023
 */
public class Transfer {
  /**
   * A method that creates the transfer view.
   * The method is used by the GUI class.
   *
   * @return the vertical box
   */
  public static VBox transferView() {
    VBox vbox = null;
    try {
      System.out.println("open transfer window");
      VBox transferVBox = new VBox();


      Text transferBetweenAccounts = new Text("Transfer between accounts:");
      transferBetweenAccounts.setId("titleText");


      HBox transferBetweenAccountsHbox = new HBox();
      transferBetweenAccountsHbox.setSpacing(20);
      transferBetweenAccountsHbox.setAlignment(Pos.CENTER);

      ComboBox<String> leftTransfer = new ComboBox<>();
      leftTransfer.setPromptText("Select Account");
      leftTransfer.setItems(FXCollections.observableArrayList(accounts.keySet()));
      leftTransfer.setFocusTraversable(true);
      leftTransfer.setId("categoryMenuButton");


      ImageView arrow = new ImageView(new Image("icons/fromTo.png"));
      arrow.setFitHeight(20);
      arrow.setFitWidth(20);


      ComboBox<String> rightTransfer = new ComboBox<>();
      rightTransfer.setPromptText("Select Account");

      rightTransfer.setDisable(true);
      rightTransfer.setItems(FXCollections.observableArrayList(accounts.keySet()));
      leftTransfer.setOnAction(e -> {
        rightTransfer.setItems(FXCollections.observableArrayList(accounts.keySet()));
        rightTransfer.setDisable(false);
        rightTransfer.getItems().remove(leftTransfer.getValue());
      });
      rightTransfer.setId("categoryMenuButton");
      rightTransfer.setFocusTraversable(true);

      TextField priceEntry = new TextField();
      priceEntry.setFocusTraversable(true);
      priceEntry.setPromptText("Enter transfer amount");
      priceEntry.setId("textField");

      Button confirmTransfer = new Button("Confirm");
      confirmTransfer.setId("actionButton");

      Label confirmTransferLabel = new Label("The transfer has been confirmed");
      confirmTransferLabel.setVisible(false);
      confirmTransferLabel.setId("confirmLabel");
      confirmTransferLabel.setAlignment(Pos.CENTER);

      HBox confirmTransferHbox = new HBox(confirmTransferLabel);
      confirmTransferHbox.setAlignment(Pos.CENTER);

      priceEntry.setOnKeyPressed(e -> {
        if (e.getCode() == KeyCode.ENTER) {
          confirmTransfer.fire(); // Simulate a click event on the logIn button
        }
      });

      confirmTransfer.setOnKeyPressed(event -> {
        if (event.getCode() == KeyCode.ENTER) {
          confirmTransfer.fire();
        }
      });

      confirmTransfer.setFocusTraversable(true);
      confirmTransfer.setOnAction(e -> {


        String removeFromAccount = leftTransfer.getValue();
        String addToAccount = rightTransfer.getValue();
        String tempText = priceEntry.getText();
        if (removeFromAccount == null || addToAccount == null || tempText.isEmpty()) {
          SoundPlayer.play(FileUtil.getResourceFilePath("error.wav"));
          showAlert("Please fill in all fields.");
          throw new IllegalArgumentException("Please fill in all fields.");
        }
        double amountToAdd;
        amountToAdd = Double.parseDouble(tempText);
        if (amountToAdd < 0) {
          SoundPlayer.play(FileUtil.getResourceFilePath("error.wav"));
          showAlert("You cannot transfer a negative amount of money.");
          throw new IllegalArgumentException("You cannot transfer a negative amount of money.");
        }
        if (amountToAdd > accounts.get(removeFromAccount)) {
          SoundPlayer.play(FileUtil.getResourceFilePath("error.wav"));
          showAlert("You do not have enough money in the " + removeFromAccount + " account to transfer " + amountToAdd + " to the " + addToAccount + " account");
          throw new IllegalArgumentException("You do not have enough money in the " + removeFromAccount + " account to transfer " + amountToAdd + " to the " + addToAccount + " account");
        }

        confirmTransferLabel.setVisible(true);

        FadeTransition ftTransfer = new FadeTransition(javafx.util.Duration.millis(1750), confirmTransferLabel);
        ftTransfer.setFromValue(1.0);
        ftTransfer.setToValue(0.0);

        System.out.println("Confirm transfer button pressed");
        ftTransfer.play();
        SoundPlayer.play(FileUtil.getResourceFilePath("16bitconfirm.wav"));
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File("src/main/resources/userfiles/" + GUI.getCurrentUser() + "/", GUI.getCurrentUser() + "transfer.csv"), true))) {
          writer.write(removeFromAccount + "," + (amountToAdd * -1) + "," + LocalDate.now() + "," + 'B' + "\n");
          writer.write(addToAccount + "," + amountToAdd + "," + LocalDate.now() + "," + 'B' + "\n");
        } catch (IOException f) {
          showAlert("An error occurred while trying to write to the file.");
        }
        leftTransfer.setValue(null);
        rightTransfer.setValue(null);
        priceEntry.setText(null);
        rightTransfer.setDisable(true);
        rightTransfer.getItems().clear();
      });


      Text registerIncome = new Text("Register new income:");
      registerIncome.setId("titleText");


      HBox registerIncomeHBox = new HBox();
      registerIncomeHBox.setSpacing(20);
      registerIncomeHBox.setAlignment(Pos.CENTER);


      ComboBox<String> incomeAccount = new ComboBox<>();
      incomeAccount.setPromptText("Select Account");

      try {
        CSVReader CSVReaderInstance = CSVReader.getInstance();

        incomeAccount.setItems(FXCollections.observableArrayList(CSVReaderInstance.readCSV().keySet()));
      } catch (IOException e) {
        showAlert("An error occurred while trying to read the file.");
      }
      incomeAccount.setId("categoryMenuButton");
      incomeAccount.setFocusTraversable(true);

      TextField amountIncome = new TextField();
      amountIncome.setPromptText("Enter income amount");
      amountIncome.setId("textField");

      Button confirmIncome = new Button("Confirm");
      confirmIncome.setId("actionButton");

      Label confirmIncomeLabel = new Label("The income has been confirmed");
      confirmIncomeLabel.setVisible(false);
      confirmIncomeLabel.setId("confirmLabel");
      confirmIncomeLabel.setAlignment(Pos.CENTER);

      HBox confirmIncomeHbox = new HBox(confirmIncomeLabel);
      confirmIncomeHbox.setAlignment(Pos.CENTER);

      amountIncome.setOnKeyPressed(e -> {
        if (e.getCode() == KeyCode.ENTER) {
          confirmIncome.fire(); // Simulate a click event on the logIn button
        }
      });

      confirmIncome.setOnKeyPressed(event -> {
        if (event.getCode() == KeyCode.ENTER) {
          confirmIncome.fire();
        }
      });

      confirmIncome.setFocusTraversable(true);
      confirmIncome.setOnAction(e -> {


        String inncomeAccountName = incomeAccount.getValue();
        String tempText = amountIncome.getText();
        if (inncomeAccountName == null || tempText.isEmpty()) {
          SoundPlayer.play(FileUtil.getResourceFilePath("error.wav"));
          showAlert("Please fill in all fields.");
          throw new IllegalArgumentException("Please fill in all fields.");
        }

        double amountToAdd;
        amountToAdd = Double.parseDouble(tempText);
        if (amountToAdd < 0) {
          SoundPlayer.play(FileUtil.getResourceFilePath("error.wav"));
          showAlert("You cannot transfer a negative amount of money.");
          throw new IllegalArgumentException("You cannot transfer a negative amount of money.");
        }

        confirmIncomeLabel.setVisible(true);
        FadeTransition ftIncome = new FadeTransition(javafx.util.Duration.millis(1750), confirmIncomeLabel);
        ftIncome.setFromValue(1.0);
        ftIncome.setToValue(0.0);

        incomes.add(new Income(inncomeAccountName, amountToAdd, 1, LocalDate.now()));
        System.out.println("Confirm income button pressed");
        ftIncome.play();
        SoundPlayer.play(FileUtil.getResourceFilePath("16bitconfirm.wav"));
        incomeAccount.setValue(null);
        amountIncome.setText(null);

        Accounts accounts = Accounts.getInstance();

        leftTransfer.getItems().clear();
        leftTransfer.getItems().addAll(accounts.getAccounts().keySet());

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File("src/main/resources/userfiles/" + GUI.getCurrentUser() + "/", GUI.getCurrentUser() + "transfer.csv"), true))) {
          writer.write(inncomeAccountName + "," + amountToAdd + "," + LocalDate.now() + "," + 'A' + "\n");
        } catch (IOException f) {
          showAlert("An error occurred while trying to write to the file.");
        }
      });
      Text addNewAccount = new Text("Add new account:");
      addNewAccount.setId("titleText");
      addNewAccount.setFocusTraversable(true);

      HBox addNewAccountHBox = new HBox();
      addNewAccountHBox.setSpacing(20);
      addNewAccountHBox.setAlignment(Pos.CENTER);

      TextField newAccountName = new TextField();
      newAccountName.setFocusTraversable(true);
      newAccountName.setPromptText("Enter account name");
      newAccountName.setId("textField");

      TextField newAccountBalance = new TextField();
      newAccountBalance.setFocusTraversable(true);
      newAccountBalance.setPromptText("Enter account balance");
      newAccountBalance.setId("textField");

      Label confirmNewAccountLabel = new Label("The account has been confirmed");
      confirmNewAccountLabel.setVisible(false);
      confirmNewAccountLabel.setId("confirmLabel");
      confirmNewAccountLabel.setAlignment(Pos.CENTER);

      HBox confirmNewAccountHbox = new HBox(confirmNewAccountLabel);
      confirmNewAccountHbox.setAlignment(Pos.CENTER);

      Button confirmNewAccount = new Button("Confirm");
      confirmNewAccount.setFocusTraversable(true);
      confirmNewAccount.setId("actionButton");

      newAccountBalance.setOnKeyPressed(event -> {
        if (event.getCode() == KeyCode.ENTER) {
          confirmNewAccount.fire();
        }
      });
      confirmNewAccount.setOnKeyPressed(event -> {
        if (event.getCode() == KeyCode.ENTER) {
          confirmNewAccount.fire();
        }
      });
      confirmNewAccount.setOnAction(e -> {


        String accountName = newAccountName.getText();
        String tempText = newAccountBalance.getText();
        if (accountName == null || tempText.isEmpty()) {
          SoundPlayer.play(FileUtil.getResourceFilePath("error.wav"));
          showAlert("Please fill in all fields.");
          throw new IllegalArgumentException("Please fill in all fields.");
        }

        double accountBalance;
        accountBalance = Double.parseDouble(tempText);
        if (accountBalance < 0) {
          SoundPlayer.play(FileUtil.getResourceFilePath("error.wav"));
          showAlert("You cannot transfer a negative amount of money.");
          throw new IllegalArgumentException("You cannot transfer a negative amount of money.");
        }

        Accounts accounts = Accounts.getInstance();

        confirmNewAccountLabel.setVisible(true);
        FadeTransition ftNewAccount = new FadeTransition(javafx.util.Duration.millis(1750), confirmNewAccountLabel);
        ftNewAccount.setFromValue(1.0);
        ftNewAccount.setToValue(0.0);

        accounts.addAccount(accountName, accountBalance);
        System.out.println("Confirm new account button pressed");
        ftNewAccount.play();
        SoundPlayer.play(FileUtil.getResourceFilePath("16bitconfirm.wav"));
        newAccountName.setText(null);
        newAccountBalance.setText(null);

        leftTransfer.getItems().clear();
        leftTransfer.getItems().addAll(accounts.getAccounts().keySet());

        incomeAccount.getItems().clear();
        incomeAccount.getItems().addAll(accounts.getAccounts().keySet());


        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File("src/main/resources/userfiles/" + GUI.getCurrentUser() + "/", GUI.getCurrentUser() + "transfer.csv"), true))) {
          writer.write(accountName + "," + accountBalance + "," + LocalDate.now() + "," + 'A' + "\n");
        } catch (IOException f) {
          showAlert("An error occurred while trying to write to the file.");
        }
      });


      transferBetweenAccountsHbox.getChildren().addAll(leftTransfer, arrow, rightTransfer, priceEntry, confirmTransfer);

      registerIncomeHBox.getChildren().addAll(incomeAccount, amountIncome, confirmIncome);

      addNewAccountHBox.getChildren().addAll(newAccountName, newAccountBalance, confirmNewAccount);

      transferVBox.getChildren().addAll(transferBetweenAccounts, transferBetweenAccountsHbox, confirmTransferHbox, registerIncome, registerIncomeHBox, confirmIncomeHbox, addNewAccount, addNewAccountHBox, confirmNewAccountHbox);
      transferVBox.setSpacing(25);

      vbox = new VBox(transferVBox);
      vbox.setPadding(new Insets(40, 40, 40, 40));
    } catch (Exception e) {
      SoundPlayer.play(FileUtil.getResourceFilePath("error.wav"));
      showAlert(e.getMessage());
    }
    return vbox;
  }
}