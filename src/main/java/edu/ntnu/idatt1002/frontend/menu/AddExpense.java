package edu.ntnu.idatt1002.frontend.menu;

import edu.ntnu.idatt1002.backend.Accounts;
import edu.ntnu.idatt1002.backend.Expense;
import edu.ntnu.idatt1002.backend.Expenses;
import edu.ntnu.idatt1002.frontend.GUI;
import edu.ntnu.idatt1002.frontend.Login;
import edu.ntnu.idatt1002.frontend.utility.SoundPlayer;
import edu.ntnu.idatt1002.model.ExcelExporter;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.w3c.dom.Node;

import javax.xml.crypto.dsig.keyinfo.KeyValue;
import java.awt.*;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import static edu.ntnu.idatt1002.backend.Accounts.accounts;
import static edu.ntnu.idatt1002.backend.Accounts.getTotalOfAccount;
import static edu.ntnu.idatt1002.backend.Expenses.*;
import static edu.ntnu.idatt1002.backend.Expenses.rent;
import static edu.ntnu.idatt1002.frontend.utility.AlertWindow.emptyFieldAlert;

public class AddExpense {
  public static VBox expenseView() {
    System.out.println("open expense window");
    VBox addExpenseVBox = new VBox();
    addExpenseVBox.setAlignment(Pos.CENTER);
    addExpenseVBox.getChildren().add(new Label("This is the addExpense page"));

    Text text3 = new Text("Add new expense");
    text3.setId("titleText");
    text3.setLineSpacing(10);

    DatePicker datePicker = new DatePicker();
    datePicker.getStyleClass().add("date-picker");

    datePicker.setValue(LocalDate.now());
    datePicker.setShowWeekNumbers(true);

    ObservableList<String> options =
            FXCollections.observableArrayList(
                    "Rent",
                    "Food",
                    "Transportation",
                    "Clothing",
                    "Entertainment",
                    "Other"
            );



    final ComboBox categoryMenu = new ComboBox(options);


    Set<String> keySet = accounts.keySet();
    ObservableList<String> options2 = FXCollections.observableArrayList(keySet);
    final ComboBox accountMenu = new ComboBox(options2);
    accountMenu.setPromptText("Pick an account");
    accountMenu.setId("categoryMenuButton");


    String originalPromptText = "Pick a category";
    categoryMenu.setPromptText(originalPromptText);
    categoryMenu.setId("categoryMenuButton");

    TextField prices = new TextField();
    prices.setPromptText("Enter price");
    prices.setId("textField");

    TextField names = new TextField();
    names.setPromptText("Enter name");
    names.setId("textField");

    Button confirmExpense = new Button("Confirm");
    confirmExpense.setId("actionButton");

    names.setOnKeyPressed(e -> {
      if (e.getCode() == KeyCode.ENTER) {
        confirmExpense.fire(); // Simulate a click event on the logIn button
      }
    });

    confirmExpense.setOnAction(e -> {

              if (categoryMenu.getValue() == null) {
                SoundPlayer.play("src/main/resources/error.wav");
                emptyFieldAlert();
                System.out.println("No category selected");
              } else if (accounts.get((String) accountMenu.getValue()) - (Double.parseDouble(prices.getText())) < 0) {
                System.out.println("Not enough money in account");
                emptyFieldAlert();
                categoryMenu.setValue(null);
                categoryMenu.setPromptText(originalPromptText);

                names.setText(null);
                prices.setText(null);
              } else {
                String selectedOption = (String) categoryMenu.getValue();
                String name = names.getText();
                String tempText = prices.getText();
                String accountName = (String) accountMenu.getValue();  //placeholder for knappen som skal velge konto

                LocalDate date = datePicker.getValue();
                System.out.println("Selected date: " + date);

                double price = Double.parseDouble(tempText); //det er en error her

                switch (selectedOption) {
                  case "Entertainment" -> Expenses.addToArrayList(new Expense(name, price, 1, datePicker.getValue()), entertainment);
                  case "Food" -> Expenses.addToArrayList(new Expense(name, price, 2, datePicker.getValue()), food);
                  case "Transportation" -> Expenses.addToArrayList(new Expense(name, price, 3, datePicker.getValue()), transportation);
                  case "Clothing" -> Expenses.addToArrayList(new Expense(name, price, 4, datePicker.getValue()), clothing);
                  case "Other" -> Expenses.addToArrayList(new Expense(name, price, 5, datePicker.getValue()), other);
                  case "Rent" -> Expenses.addToArrayList(new Expense(name, price, 6, datePicker.getValue()), rent);
                  default -> System.out.println("Error");
                }

                Accounts.addExpenseToAccount(new Expense(name, price, 1, datePicker.getValue()),
                        accountName);


                System.out.println("Purchase confirmed");
                System.out.println("Category: " + selectedOption);

                try {
                  ExcelExporter.exportToExcel();
                } catch (IOException ioException) {
                  ioException.printStackTrace();
                }
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File("src/main/resources/userfiles/" + GUI.getCurrentUser() + "/", GUI.getCurrentUser() + ".csv"), true))) {
                  writer.write(selectedOption + "," + name + "," + date + "," + price + "," + accountName + "\n");
                } catch (IOException f) {
                  System.err.println("Error writing to file: " + f.getMessage());
                }


        //This clears the textfields and plays the confirmation sound
        //categoryMenu.setValue(null);
        //categoryMenu.setPromptText("Pick a category");
        //categoryMenu.getSelectionModel().clearSelection();


        categoryMenu.setValue(null);
        categoryMenu.setPromptText(originalPromptText);

        names.setText(null);
        prices.setText(null);
        SoundPlayer.play("src/main/resources/16bitconfirm.wav");

      }
    });



    HBox title = new HBox(text3);
    title.setAlignment(Pos.CENTER);
    title.setSpacing(40);

    VBox categoryNamePrice = new VBox(//pickCategory,
            categoryMenu,
            //pickPrice,
            prices,
            //pickName,
            names);


    categoryNamePrice.setPadding(new Insets(25));
    categoryNamePrice.setSpacing(20);
    categoryNamePrice.setAlignment(Pos.TOP_LEFT);

    VBox calendar = new VBox(datePicker);

    calendar.setAlignment(Pos.TOP_LEFT);
    calendar.setSpacing(20);
    calendar.setPadding(new Insets(25));




    HBox dateAndInput = new HBox(categoryNamePrice, calendar);
    dateAndInput.setAlignment(Pos.CENTER);
    dateAndInput.setPadding(new Insets(15));


    VBox dateAndInputAndConfirm = new VBox(title,accountMenu, dateAndInput, confirmExpense);
    dateAndInputAndConfirm.setAlignment(Pos.CENTER);
    dateAndInputAndConfirm.setSpacing(20);



    VBox vbox = new VBox(dateAndInputAndConfirm);
    //addExpenseWindow.getChildren().add(vbox);
    return vbox;

  }





}
