package edu.ntnu.idatt1002.frontend.menu;

import edu.ntnu.idatt1002.backend.Expense;
import edu.ntnu.idatt1002.backend.Expenses;
import edu.ntnu.idatt1002.frontend.utility.SoundPlayer;
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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

import static edu.ntnu.idatt1002.backend.Expenses.*;
import static edu.ntnu.idatt1002.backend.Expenses.rent;
import static edu.ntnu.idatt1002.frontend.utility.AlertWindow.emptyFieldAlert;

public class AddExpense {
  public static VBox expanseView() {
    System.out.println("open expense window");
    VBox addExpenseVBox = new VBox();
    addExpenseVBox.setAlignment(Pos.CENTER);
    addExpenseVBox.getChildren().add(new Label("This is the addExpense page"));

    Text text3 = new Text("Add new expense");
    text3.setStyle("-fx-fill: #3F403F");
    text3.setFont(Font.font("helvetica", FontWeight.BOLD, FontPosture.REGULAR, 60));

    DatePicker datePicker = new DatePicker();
    datePicker.setValue(LocalDate.now());
    datePicker.setShowWeekNumbers(true);
    datePicker.setStyle("-fx-font-size: 20px; -fx-min-width: 100px; -fx-min-height: 50px;-fx-background-color: #9FB8AD; -fx-border-width: 2; -fx-padding: 10px; -fx-background-radius: 0.5em;");

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

    String originalPromptText = "Pick a category";
    categoryMenu.setPromptText(originalPromptText);
    categoryMenu.setStyle("-fx-font-size: 20px; -fx-min-width: 100px; -fx-min-height: 50px;-fx-background-color: #9FB8AD; -fx-border-width: 2; -fx-padding: 10px; -fx-background-radius: 0.5em; -fx-prompt-text-fill: #FFFFFF; -fx-text-fill: #FFFFFF;");

    TextField prices = new TextField();
    prices.setPromptText("Enter price");
    prices.setStyle("-fx-font-size: 20px; -fx-min-width: 100px; -fx-min-height: 50px;-fx-background-color: #9FB8AD; -fx-border-width: 2; -fx-padding: 10px; -fx-background-radius: 0.5em; -fx-prompt-text-fill: #FFFFFF; -fx-text-fill: #FFFFFF;");

    TextField names = new TextField();
    names.setPromptText("Enter name");
    names.setStyle("-fx-font-size: 20px; -fx-min-width: 100px; -fx-min-height: 50px;-fx-background-color: #9FB8AD; -fx-border-width: 2; -fx-padding: 10px; -fx-background-radius: 0.5em; -fx-prompt-text-fill: #FFFFFF; -fx-text-fill: #FFFFFF;");

    Button confirmExpense = new Button("Confirm");
    confirmExpense.setStyle("-fx-font-size: 30px; -fx-min-width: 100px; -fx-min-height: 50px;-fx-background-color: #9FB8AD; -fx-border-width: 2; -fx-padding: 10px; -fx-background-radius: 0.5em;");

    confirmExpense.setOnAction(e -> {

      if (categoryMenu.getValue() == null) {
        SoundPlayer.play("src/main/resources/error.wav");
        emptyFieldAlert();
        System.out.println("No category selected");
      } else {
        String selectedOption = (String) categoryMenu.getValue();
        String name = names.getText();
        String tempText = prices.getText();

        LocalDate date = datePicker.getValue();
        System.out.println("Selected date: " + date);

        double price = Double.parseDouble(tempText); //det er en error her

        switch (selectedOption) {
          case "Entertainment" ->
                  Expenses.addToArrayList(new Expense(name, price, 1, datePicker.getValue()), entertainment);
          case "Food" -> Expenses.addToArrayList(new Expense(name, price, 1, datePicker.getValue()), food);
          case "Transportation" ->
                  Expenses.addToArrayList(new Expense(name, price, 1, datePicker.getValue()), transportation);
          case "Clothing" ->
                  Expenses.addToArrayList(new Expense(name, price, 1, datePicker.getValue()), clothing);
          case "Other" -> Expenses.addToArrayList(new Expense(name, price, 1, datePicker.getValue()), other);
          case "Rent" -> Expenses.addToArrayList(new Expense(name, price, 1, datePicker.getValue()), rent);
          default -> System.out.println("Error");
        }

        System.out.println("Purchase confirmed");
        System.out.println("Category: " + selectedOption);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File("src/main/resources", "logger.txt"), true))) {
          writer.write(selectedOption + "," + name + "," + date + "," + price + "\n");
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


    VBox dateAndInputAndConfirm = new VBox(title, dateAndInput, confirmExpense);
    dateAndInputAndConfirm.setAlignment(Pos.CENTER);



    VBox vbox = new VBox(dateAndInputAndConfirm);
    //addExpenseWindow.getChildren().add(vbox);
    return vbox;

  }





}
