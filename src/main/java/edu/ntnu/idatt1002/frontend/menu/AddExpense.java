package edu.ntnu.idatt1002.frontend.menu;

import edu.ntnu.idatt1002.backend.budgeting.Expense;
import edu.ntnu.idatt1002.backend.budgeting.Expenses;
import edu.ntnu.idatt1002.frontend.GUI;
import edu.ntnu.idatt1002.frontend.utility.SoundPlayer;
import edu.ntnu.idatt1002.model.CSVReader;
import edu.ntnu.idatt1002.model.ExcelExporter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import java.io.*;
import java.time.LocalDate;

import static edu.ntnu.idatt1002.backend.budgeting.Accounts.accounts;
import static edu.ntnu.idatt1002.backend.budgeting.Expenses.*;
import static edu.ntnu.idatt1002.backend.budgeting.Expenses.rent;
import static edu.ntnu.idatt1002.frontend.utility.AlertWindow.showAlert;

/**
 * A class that creates the add expense view.
 *
 * @author Emil J., Vegard J., Sander S. and Elias T.
 * @version 0.5 - 19.04.2023
 */
public class AddExpense {
  /**
   * A method that creates the add expense view.
   * The method is used by the GUI class.
   *
   * @return the vertical box
   */
  public static VBox expenseView() {

    System.out.println("open expense window");
    VBox addExpenseVBox = new VBox();
    addExpenseVBox.setAlignment(Pos.CENTER);
    addExpenseVBox.getChildren().add(new Label("This is the addExpense page"));

    Text text3 = new Text("Add new expense");
    text3.setId("titleText");
    text3.setLineSpacing(10);



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
    categoryMenu.setFocusTraversable(true);


    DatePicker datePicker = new DatePicker();
    datePicker.getStyleClass().add("date-picker");
    datePicker.setValue(LocalDate.now());
    datePicker.setShowWeekNumbers(true);
    datePicker.setFocusTraversable(true);

    ObservableList<String> options2 = null;
    try {
      CSVReader CSVReaderInstance = CSVReader.getInstance();

      options2 = FXCollections.observableArrayList(CSVReaderInstance.readCSV().keySet());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    final ComboBox accountMenu = new ComboBox(options2);
    accountMenu.setPromptText("Pick an account");
    accountMenu.setId("categoryMenuButton");
    accountMenu.setFocusTraversable(true);
    String originalPromptText = "Pick a category";
    categoryMenu.setPromptText(originalPromptText);
    categoryMenu.setId("categoryMenuButton");

    TextField prices = new TextField();
    prices.setPromptText("Enter price");
    prices.setId("textField");
    prices.setFocusTraversable(true);

    // Replace the TextField object with an instance of the OutlinedTextField class
    TextField names = new TextField();
    names.setPromptText("Enter name");
    names.setId("textField");
    names.setFocusTraversable(true);

    Button confirmExpense = new Button("Confirm");
    confirmExpense.setId("actionButton");
    confirmExpense.setFocusTraversable(true);

    names.setOnKeyPressed(e -> {
      if (e.getCode() == KeyCode.ENTER) {
        confirmExpense.fire(); // Simulate a click event on the logIn button
      }
    });

    confirmExpense.setOnKeyPressed(event -> {
      if (event.getCode() == KeyCode.ENTER) {
        confirmExpense.fire();
      }
    });
    confirmExpense.setOnAction(e -> {




      if (categoryMenu.getValue() == null) {
        SoundPlayer.play("/src/main/resources/error.wav");
        String customMessage = "Please select a category.";
        showAlert(customMessage);
        System.out.println("No category selected");

      } else if (prices.getText().isEmpty()) {
        SoundPlayer.play("/src/main/resources/error.wav");

      } else if (names.getText().isEmpty()) {
        SoundPlayer.play("/src/main/resources/error.wav");


      } else if (accounts.get((String) accountMenu.getValue()) - (Double.parseDouble(prices.getText())) < 0) {
        System.out.println("Not enough money in account");
        String customMessage = "Not enough money in account.";
        showAlert(customMessage);

        categoryMenu.setValue(null);
        categoryMenu.setPromptText(originalPromptText);

        names.setText(null);
        prices.setText(null);
      } else {
        String selectedOption = (String) categoryMenu.getValue();
        String name = ('|' + names.getText() + '|');
        String tempText = prices.getText();
        String accountName = (String) accountMenu.getValue();  //placeholder for knappen som skal velge konto

        LocalDate date = datePicker.getValue();
        System.out.println("Selected date: " + date);

        double price = Double.parseDouble(tempText); //det er en error her

        Expenses expenseInstance = Expenses.getInstance();

        switch (selectedOption) {
          case "Entertainment" -> expenseInstance.addToArrayList(new Expense(name, price, 1, datePicker.getValue()), entertainment);
          case "Food" -> expenseInstance.addToArrayList(new Expense(name, price, 2, datePicker.getValue()), food);
          case "Transportation" -> expenseInstance.addToArrayList(new Expense(name, price, 3, datePicker.getValue()), transportation);
          case "Clothing" -> expenseInstance.addToArrayList(new Expense(name, price, 4, datePicker.getValue()), clothing);
          case "Other" -> expenseInstance.addToArrayList(new Expense(name, price, 5, datePicker.getValue()), other);
          case "Rent" -> expenseInstance.addToArrayList(new Expense(name, price, 6, datePicker.getValue()), rent);
          default -> System.out.println("Error");
        }

//                Accounts.addExpenseToAccount(new Expense(name, price, 1, datePicker.getValue()),
//                        accountName);


        System.out.println("Purchase confirmed");
        System.out.println("Category: " + selectedOption);

        try {
          ExcelExporter instance = ExcelExporter.getInstance();

          instance.exportToExcel();
        } catch (IOException ioException) {
          ioException.printStackTrace();
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File("src/main/resources/userfiles/" + GUI.getCurrentUser() + "/", GUI.getCurrentUser() + ".csv"), true))) {
          writer.write(selectedOption + "," + name + "," + date + "," + price + "," + accountName + "\n");
        } catch (IOException f) {
          System.err.println("Error writing to file: " + f.getMessage());
        }

        names.setText(null);
        prices.setText(null);
        SoundPlayer.play("src/main/resources/16bitconfirm.wav");

      }
    });



    HBox title = new HBox(text3);
    title.setAlignment(Pos.CENTER);
    title.setSpacing(40);

    VBox categoryNamePrice = new VBox(accountMenu, categoryMenu, prices, names);


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


    VBox dateAndInputAndConfirm = new VBox(title, dateAndInput, confirmExpense);
    dateAndInputAndConfirm.setAlignment(Pos.CENTER);
    dateAndInputAndConfirm.setSpacing(20);



    VBox vbox = new VBox(dateAndInputAndConfirm);
    //addExpenseWindow.getChildren().add(vbox);
    return vbox;

  }
}
