//BANK STATEMENT

package edu.ntnu.idatt1002.frontend.menu;

import com.itextpdf.text.DocumentException;
import edu.ntnu.idatt1002.backend.Income;
import edu.ntnu.idatt1002.frontend.utility.SoundPlayer;
import edu.ntnu.idatt1002.model.ExcelExporter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Set;

import static edu.ntnu.idatt1002.backend.Accounts.accounts;
import static edu.ntnu.idatt1002.backend.Incomes.getIncomes;
import static javafx.scene.text.Font.font;

public class BankStatement {
  public static VBox bankStatementView() {


    System.out.println("opening more window");
    VBox bankStatementVbox = new VBox();
    bankStatementVbox.setSpacing(40);


    Text viewBankStatement = new Text("View bank statement");
    viewBankStatement.setStyle("-fx-fill: #3F403F");
    viewBankStatement.setFont(font("helvetica", FontWeight.BOLD, FontPosture.REGULAR, 60));
    viewBankStatement.setTextAlignment(TextAlignment.LEFT);



    HBox selectAccountHbox = new HBox();
    selectAccountHbox.setAlignment(Pos.CENTER_LEFT);

    Text selectAccountText = new Text("Select account: ");
    selectAccountText.setStyle("-fx-fill: #3F403F");
    selectAccountText.setFont(font("helvetica", FontWeight.BOLD, FontPosture.REGULAR, 30));

    Set<String> keySet = accounts.keySet();
    ObservableList<String> options2 = FXCollections.observableArrayList(keySet);
    final ComboBox accountMenu = new ComboBox(options2);
    accountMenu.setPromptText("Pick an account");
    accountMenu.setStyle("-fx-font-size: 20px; -fx-min-width: 100px; -fx-min-height: 50px;-fx-background-color: #9FB8AD; -fx-border-width: 2; -fx-padding: 10px; -fx-background-radius: 0.5em; -fx-prompt-text-fill: #FFFFFF; -fx-text-fill: #FFFFFF;");
    selectAccountHbox.getChildren().addAll(selectAccountText, accountMenu);


    HBox selectCategoryHbox = new HBox();
    selectCategoryHbox.setAlignment(Pos.CENTER_LEFT);

    Text selectCategoryText = new Text("Select category: ");
    selectCategoryText.setStyle("-fx-fill: #3F403F");
    selectCategoryText.setFont(font("helvetica", FontWeight.BOLD, FontPosture.REGULAR, 30));

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
    categoryMenu.setPromptText("Pick a category");
    categoryMenu.setStyle("-fx-font-size: 20px; -fx-min-width: 100px; -fx-min-height: 50px;-fx-background-color: #9FB8AD; -fx-border-width: 2; -fx-padding: 10px; -fx-background-radius: 0.5em; -fx-prompt-text-fill: #FFFFFF; -fx-text-fill: #FFFFFF;");
    selectCategoryHbox.getChildren().addAll(selectCategoryText, categoryMenu);


    HBox calenderIntervalHbox = new HBox();
    calenderIntervalHbox.setAlignment(Pos.CENTER_LEFT);

    //this should probably have its own hbox
    Text calenderIntervalText = new Text("Select timeframe: ");
    calenderIntervalText.setStyle("-fx-fill: #3F403F");
    calenderIntervalText.setFont(font("helvetica", FontWeight.BOLD, FontPosture.REGULAR, 30));


    Text fromText = new Text("From: ");
    fromText.setStyle("-fx-fill: #3F403F");
    fromText.setFont(font("helvetica", FontWeight.BOLD, FontPosture.REGULAR, 30));

    DatePicker datePickerFrom = new DatePicker();
    datePickerFrom.setValue(LocalDate.now());
    datePickerFrom.setShowWeekNumbers(true);
    datePickerFrom.setStyle("-fx-font-size: 20px; -fx-min-width: 100px; -fx-min-height: 50px;-fx-background-color: #9FB8AD; -fx-border-width: 2; -fx-padding: 10px; -fx-background-radius: 0.5em;");

    Text toText = new Text("To: ");
    toText.setStyle("-fx-fill: #3F403F");
    toText.setFont(font("helvetica", FontWeight.BOLD, FontPosture.REGULAR, 30));

    DatePicker datePickerTo = new DatePicker();
    datePickerTo.setValue(LocalDate.now());
    datePickerTo.setShowWeekNumbers(true);
    datePickerTo.setStyle("-fx-font-size: 20px; -fx-min-width: 100px; -fx-min-height: 50px;-fx-background-color: #9FB8AD; -fx-border-width: 2; -fx-padding: 10px; -fx-background-radius: 0.5em;");

    calenderIntervalHbox.getChildren().addAll(fromText, datePickerFrom, toText, datePickerTo);


    HBox tableHbox = new HBox();
    tableHbox.setAlignment(Pos.CENTER);

    TableView<Income> bankStatementTable = new TableView<>();
    TableColumn<Income, String> budgetTableColumn1 = new TableColumn<>("Name: ");
    budgetTableColumn1.setCellValueFactory(new PropertyValueFactory<>("name"));

    TableColumn<Income, Double> budgetTableColumn2 = new TableColumn<>("Price: ");
    budgetTableColumn2.setCellValueFactory(new PropertyValueFactory<>("price"));

    TableColumn<Income, LocalDate> budgetTableColumn3 = new TableColumn<>("Date: ");
    budgetTableColumn3.setCellValueFactory(new PropertyValueFactory<>("date"));

    bankStatementTable.getColumns().addAll(budgetTableColumn1, budgetTableColumn2, budgetTableColumn3);
    bankStatementTable.getItems().addAll(getIncomes());
    bankStatementTable.setMinWidth(600);

    tableHbox.getChildren().add(bankStatementTable);



    bankStatementVbox.getChildren().addAll(viewBankStatement, selectAccountHbox, selectCategoryHbox,calenderIntervalText, calenderIntervalHbox, tableHbox);

    bankStatementVbox.setAlignment(Pos.TOP_CENTER);
    return bankStatementVbox;

  }
}
