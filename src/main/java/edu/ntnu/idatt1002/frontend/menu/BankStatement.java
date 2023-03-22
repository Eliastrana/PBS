//BANK STATEMENT

package edu.ntnu.idatt1002.frontend.menu;

import edu.ntnu.idatt1002.backend.Expense;
import edu.ntnu.idatt1002.model.ExcelExporter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import java.time.LocalDate;
import java.util.Set;

import static edu.ntnu.idatt1002.backend.Accounts.accounts;

public class BankStatement {
  public static VBox bankStatementView() {


    System.out.println("opening more window");
    VBox bankStatementVbox = new VBox();
    bankStatementVbox.setSpacing(40);

    Text viewBankStatement = new Text("View bank statement");
    viewBankStatement.setId("titleText");


    HBox selectAccountHbox = new HBox();
    selectAccountHbox.setAlignment(Pos.CENTER);


    Set<String> keySet = accounts.keySet();
    ObservableList<String> options2 = FXCollections.observableArrayList(keySet);
    final ComboBox accountMenu = new ComboBox(options2);
    accountMenu.setId("categoryMenuButton");
    accountMenu.setPromptText("Select account");
    selectAccountHbox.getChildren().addAll(accountMenu);



    HBox selectCategoryHbox = new HBox();
    selectCategoryHbox.setAlignment(Pos.CENTER);


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
    categoryMenu.setId("categoryMenuButton");
    selectCategoryHbox.getChildren().addAll(categoryMenu);




    HBox calenderIntervalHbox = new HBox();
    calenderIntervalHbox.setAlignment(Pos.CENTER);

    //this should probably have its own hbox
    Text calenderIntervalText = new Text("Select timeframe: ");
    calenderIntervalText.setId("bodyText");


    Text fromText = new Text("From: ");
    fromText.setId("bodyText");

    DatePicker datePickerFrom = new DatePicker();
    datePickerFrom.setValue(LocalDate.now());
    datePickerFrom.setShowWeekNumbers(true);

    Text toText = new Text("To: ");
    toText.setId("bodyText");

    DatePicker datePickerTo = new DatePicker();
    datePickerTo.setValue(LocalDate.now());
    datePickerTo.setShowWeekNumbers(true);

    calenderIntervalHbox.getChildren().addAll(fromText, datePickerFrom, toText, datePickerTo);
    calenderIntervalHbox.setSpacing(20);

    HBox tableHbox = new HBox();
    tableHbox.setAlignment(Pos.CENTER);

    TableView<Expense> bankStatementTable = new TableView<>();
    TableColumn<Expense, String> rightColumn1 = new TableColumn<>("Name: ");
    rightColumn1.setCellValueFactory(new PropertyValueFactory<>("name"));

    TableColumn<Expense, Double> rightColumn2 = new TableColumn<>("Price: ");
    rightColumn2.setCellValueFactory(new PropertyValueFactory<>("price"));

    TableColumn<Expense, LocalDate> rightColumn3 = new TableColumn<>("Date: ");
    rightColumn3.setCellValueFactory(new PropertyValueFactory<>("date"));

    TableColumn<Expense, String> rightColumn4 = new TableColumn<>("Category: ");
    rightColumn4.setCellValueFactory(new PropertyValueFactory<>("category"));

    TableColumn<Expense, String> rightColumn5 = new TableColumn<>("Account: ");
    rightColumn5.setCellValueFactory(new PropertyValueFactory<>("account"));

    bankStatementTable.getColumns().addAll(rightColumn1, rightColumn2, rightColumn3, rightColumn4, rightColumn5);

    bankStatementTable.getItems().addAll(ExcelExporter.getExpensesForMonth());

    bankStatementTable.setMinWidth(600);

    tableHbox.getChildren().add(bankStatementTable);

    Button exportToExcel = new Button("Export to Excel");
    exportToExcel.setId("actionButton");

    bankStatementVbox.getChildren().addAll(viewBankStatement, selectAccountHbox, selectCategoryHbox,calenderIntervalText, calenderIntervalHbox, tableHbox, exportToExcel);

    bankStatementVbox.setAlignment(Pos.TOP_CENTER);
    return bankStatementVbox;

  }
}
