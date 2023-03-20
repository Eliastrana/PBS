package edu.ntnu.idatt1002.frontend.menu;

import edu.ntnu.idatt1002.backend.Accounts;
import edu.ntnu.idatt1002.backend.Income;
import edu.ntnu.idatt1002.frontend.utility.SoundPlayer;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.time.LocalDate;

import static edu.ntnu.idatt1002.backend.Accounts.accounts;
import static edu.ntnu.idatt1002.backend.Accounts.addToAccount;
import static edu.ntnu.idatt1002.backend.Incomes.incomes;

public class Transfer {
  public static VBox transferView() {
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
    leftTransfer.setId("categoryMenuButton");

    Text transferto = new Text(" to: ");
    transferto.setFont(Font.font("Helvetica", FontWeight.BOLD, FontPosture.REGULAR, 25));
    transferBewteenAccounts.getChildren().add(transferto);
    ComboBox<String> rightTransfer = new ComboBox<>();
    rightTransfer.setDisable(true);
    rightTransfer.setItems(FXCollections.observableArrayList(accounts.keySet()));
    leftTransfer.setOnAction(e -> {
      rightTransfer.setItems(FXCollections.observableArrayList(accounts.keySet()));
      rightTransfer.setDisable(false);
      rightTransfer.getItems().remove(leftTransfer.getValue());
    });
    transferBewteenAccounts.getChildren().add(rightTransfer);
    rightTransfer.setId("categoryMenuButton");


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
    priceEntry.setId("textField");
    transferBewteenAccountsAmount.getChildren().add(priceEntry);

    Button confirmTransfer = new Button("Confirm");
    confirmTransfer.setId("actionButton");

    priceEntry.setOnKeyPressed(e -> {
      if (e.getCode() == KeyCode.ENTER) {
        confirmTransfer.fire(); // Simulate a click event on the logIn button
      }
    });

    confirmTransfer.setOnAction(e -> {
      String removeFromAccount = leftTransfer.getValue();
      String addToAccount = rightTransfer.getValue();
      String tempText = priceEntry.getText();
      double amountToAdd = Double.parseDouble(tempText);
      Accounts.transferBetweenAccounts(removeFromAccount, addToAccount, amountToAdd);
      System.out.println("Confirm transfer button pressed");
      SoundPlayer.play("src/main/resources/16bitconfirm.wav");
      leftTransfer.setValue(null);
      rightTransfer.setValue(null);
      priceEntry.setText(null);
      rightTransfer.setDisable(true);
      rightTransfer.getItems().clear();
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
    incomeAccount.setId("categoryMenuButton");

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
    amountIncome.setId("textField");
    registerAmount.getChildren().add(amountIncome);



    Button confirmIncome = new Button("Confirm");
    confirmIncome.setId("actionButton");

    amountIncome.setOnKeyPressed(e -> {
      if (e.getCode() == KeyCode.ENTER) {
        confirmIncome.fire(); // Simulate a click event on the logIn button
      }
    });

    confirmIncome.setOnAction(e -> {
      String inncomeAccountName = incomeAccount.getValue();
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
    //transferWindow.getChildren().add(vbox);
    return vbox;




  }

}
