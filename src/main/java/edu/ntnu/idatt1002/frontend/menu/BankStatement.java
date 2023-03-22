//BANK STATEMENT

package edu.ntnu.idatt1002.frontend.menu;

import edu.ntnu.idatt1002.backend.Expense;
import edu.ntnu.idatt1002.backend.Income;
import edu.ntnu.idatt1002.frontend.Login;
import edu.ntnu.idatt1002.frontend.utility.SoundPlayer;
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
import static edu.ntnu.idatt1002.backend.Incomes.getIncomes;
import static edu.ntnu.idatt1002.frontend.utility.AlertWindow.emptyFieldAlert;
import static javafx.scene.text.Font.font;

public class BankStatement {
  public static VBox bankStatementView() {


    System.out.println("opening more window");
    VBox bankStatementVbox = new VBox();
    bankStatementVbox.setSpacing(40);


    Text viewBankStatement = new Text("View bank statement");
    viewBankStatement.setId("titleText");


    HBox selectAccountHbox = new HBox();
    selectAccountHbox.setAlignment(Pos.CENTER);

    Text selectAccountText = new Text("Select account: ");
    selectAccountText.setStyle("-fx-fill: #3F403F");
    selectAccountText.setFont(font("helvetica", FontWeight.BOLD, FontPosture.REGULAR, 30));

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
    datePickerFrom.setStyle("-fx-font-size: 20px; -fx-min-width: 100px; -fx-min-height: 50px;-fx-background-color: #9FB8AD; -fx-border-width: 2; -fx-padding: 10px; -fx-background-radius: 0.5em;");

    Text toText = new Text("To: ");
    toText.setId("bodyText");

    DatePicker datePickerTo = new DatePicker();
    datePickerTo.setValue(LocalDate.now());
    datePickerTo.setShowWeekNumbers(true);
    datePickerTo.setStyle("-fx-font-size: 20px; -fx-min-width: 100px; -fx-min-height: 50px;-fx-background-color: #9FB8AD; -fx-border-width: 2; -fx-padding: 10px; -fx-background-radius: 0.5em;");

    calenderIntervalHbox.getChildren().addAll(fromText, datePickerFrom, toText, datePickerTo);

    Button export = new Button("Confirm");
    export.setId("actionButton");

    export.setOnAction(e -> {

          if (categoryMenu.getValue() == null) {
            SoundPlayer.play("src/main/resources/error.wav");
            emptyFieldAlert();

          } else {
            String account = (String) accountMenu.getValue();
            String category = (String) categoryMenu.getValue();
            String from = String.valueOf(datePickerFrom.getValue());
            String to = String.valueOf(datePickerTo.getValue());

            try {
                ExcelExporter.convertToPdf(ExcelExporter.createBankStatement(account, category, from, to), "bankstatement");

            } catch (IOException f) {
              throw new RuntimeException(f);
            } catch (DocumentException ex) {
                throw new RuntimeException(ex);
            }
              if (Desktop.isDesktopSupported()) {
                  try {
                      File myFile = new File("src/main/resources/userfiles/" + Login.getCurrentUser() + "/" + Login.getCurrentUser() + "bankstatement.pdf");
                      Desktop.getDesktop().open(myFile);
                  } catch (IOException ex) {
                      // no application registered for PDFs
                  }
              }
          }
        });


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


    bankStatementVbox.getChildren().addAll(viewBankStatement, selectAccountHbox, selectCategoryHbox,calenderIntervalText, calenderIntervalHbox, tableHbox);

    bankStatementVbox.setAlignment(Pos.TOP_CENTER);
    return bankStatementVbox;

  }
}
