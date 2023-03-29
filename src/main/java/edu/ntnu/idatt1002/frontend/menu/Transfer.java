package edu.ntnu.idatt1002.frontend.menu;

import edu.ntnu.idatt1002.backend.Accounts;
import edu.ntnu.idatt1002.backend.Income;
import edu.ntnu.idatt1002.frontend.GUI;
import edu.ntnu.idatt1002.frontend.utility.SoundPlayer;
import edu.ntnu.idatt1002.model.CSVReader;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;

import static edu.ntnu.idatt1002.backend.Accounts.accounts;
import static edu.ntnu.idatt1002.backend.Accounts.addToAccount;
import static edu.ntnu.idatt1002.backend.Incomes.incomes;

public class Transfer {
  public static VBox transferView() {
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

            TextField priceEntry = new TextField();
            priceEntry.setPromptText("Enter transfer amount");
            priceEntry.setId("textField");

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

      if (amountToAdd > accounts.get(removeFromAccount)) {
        System.out.println("Not enough money");
        SoundPlayer.play("src/main/resources/error.wav");
        leftTransfer.setValue(null);
        rightTransfer.setValue(null);
        priceEntry.setText(null);
        rightTransfer.setDisable(true);
        rightTransfer.getItems().clear();
      }
      else{
      System.out.println("Confirm transfer button pressed");
      SoundPlayer.play("src/main/resources/16bitconfirm.wav");
      try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File("src/main/resources/userfiles/" + GUI.getCurrentUser() + "/", GUI.getCurrentUser() + "transfer.csv"), true))) {
        writer.write(removeFromAccount + "," + (amountToAdd*-1) + "," + LocalDate.now() + "\n");
        writer.write(addToAccount + "," + amountToAdd + "," + LocalDate.now() + "\n");
      } catch (IOException f) {
        System.err.println("Error writing to file: " + f.getMessage());
      }
        leftTransfer.setValue(null);
        rightTransfer.setValue(null);
        priceEntry.setText(null);
        rightTransfer.setDisable(true);
        rightTransfer.getItems().clear();
    }
    });


        Text registerIncome = new Text("Register new income:");
        registerIncome.setId("titleText");


        HBox registerIncomeHBox = new HBox();
        registerIncomeHBox.setSpacing(20);
        registerIncomeHBox.setAlignment(Pos.CENTER);

    Text incomeTo = new Text("Choose the account for registering income:");
    incomeTo.setFont(Font.font("Helvetica", FontWeight.BOLD, FontPosture.REGULAR, 25));
    ComboBox<String> incomeAccount = new ComboBox<>();
    try {
      incomeAccount.setItems(FXCollections.observableArrayList(CSVReader.readCSV().keySet()));
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
    incomeAccount.setId("categoryMenuButton");

            TextField amountIncome = new TextField();
            amountIncome.setPromptText("Enter income amount");
            amountIncome.setId("textField");

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
      incomes.add(new Income(inncomeAccountName, amountToAdd, 1, LocalDate.now()));
      System.out.println("Confirm income button pressed");
      SoundPlayer.play("src/main/resources/16bitconfirm.wav");
      incomeAccount.setValue(null);
      amountIncome.setText(null);

      try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File("src/main/resources/userfiles/" + GUI.getCurrentUser() + "/", GUI.getCurrentUser() + "transfer.csv"), true))) {
        writer.write(inncomeAccountName + "," + amountToAdd + "," + LocalDate.now() + "\n");
      } catch (IOException f) {
        System.err.println("Error writing to file: " + f.getMessage());
      }
    });


            Text addNewAccount = new Text("Add new account:");
            addNewAccount.setId("titleText");

            HBox addNewAccountHBox = new HBox();
            addNewAccountHBox.setSpacing(20);
            addNewAccountHBox.setAlignment(Pos.CENTER);

            TextField newAccountName = new TextField();
            newAccountName.setPromptText("Enter account name");
            newAccountName.setId("textField");

            TextField newAccountBalance = new TextField();
            newAccountBalance.setPromptText("Enter account balance");
            newAccountBalance.setId("textField");

            Button confirmNewAccount = new Button("Confirm");
            confirmNewAccount.setId("actionButton");
            confirmNewAccount.setOnAction(e -> {
              String accountName = newAccountName.getText();
              String tempText = newAccountBalance.getText();
              double accountBalance = Double.parseDouble(tempText);
              Accounts.addAccount(accountName, accountBalance);
              System.out.println("Confirm new account button pressed");
              SoundPlayer.play("src/main/resources/16bitconfirm.wav");
              newAccountName.setText(null);
              newAccountBalance.setText(null);


                try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File("src/main/resources/userfiles/" + GUI.getCurrentUser() + "/", GUI.getCurrentUser() + "transfer.csv"), true))) {
                    writer.write(accountName + "," + accountBalance + "," + LocalDate.now() + "\n");
                } catch (IOException f) {
                    System.err.println("Error writing to file: " + f.getMessage());
                }
            });

            newAccountBalance.setOnKeyPressed(e -> {
              if (e.getCode() == KeyCode.ENTER) {
                confirmNewAccount.fire(); // Simulate a click event on the logIn button
              }
            });




    transferBetweenAccountsHbox.getChildren().addAll(leftTransfer,arrow, rightTransfer,priceEntry, confirmTransfer);

    registerIncomeHBox.getChildren().addAll(incomeAccount, amountIncome, confirmIncome);

    addNewAccountHBox.getChildren().addAll(newAccountName,newAccountBalance, confirmNewAccount);

    transferVBox.getChildren().addAll(transferBetweenAccounts, transferBetweenAccountsHbox,registerIncome, registerIncomeHBox, addNewAccount, addNewAccountHBox);
    transferVBox.setSpacing(40);

    VBox vbox = new VBox(transferVBox);
    vbox.setPadding(new Insets(40, 40, 40, 40));
    return vbox;




  }

}
