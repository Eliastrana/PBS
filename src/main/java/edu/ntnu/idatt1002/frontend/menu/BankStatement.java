//BANK STATEMENT

package edu.ntnu.idatt1002.frontend.menu;

import com.itextpdf.text.DocumentException;
import edu.ntnu.idatt1002.backend.Expense;
import edu.ntnu.idatt1002.backend.Income;
import edu.ntnu.idatt1002.frontend.GUI;
import edu.ntnu.idatt1002.frontend.Login;
import edu.ntnu.idatt1002.frontend.utility.SoundPlayer;
import edu.ntnu.idatt1002.model.CSVReader;
import edu.ntnu.idatt1002.model.ExcelExporter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Set;

import static edu.ntnu.idatt1002.backend.Accounts.accounts;
import static edu.ntnu.idatt1002.backend.Incomes.getIncomes;
import static edu.ntnu.idatt1002.frontend.utility.AlertWindow.showAlert;
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


    ObservableList<String> options2 = null;
    try {
      options2 = FXCollections.observableArrayList(CSVReader.readCSV().keySet());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
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



    DatePicker datePickerFrom = new DatePicker();
    datePickerFrom.setValue(LocalDate.now());
    datePickerFrom.setShowWeekNumbers(true);

    ImageView arrow = new ImageView(new Image("icons/fromTo.png"));
    arrow.setFitHeight(20);
    arrow.setFitWidth(20);


    DatePicker datePickerTo = new DatePicker();
    datePickerTo.setValue(LocalDate.now());
    datePickerTo.setShowWeekNumbers(true);

    calenderIntervalHbox.getChildren().addAll(datePickerFrom, arrow, datePickerTo);
    calenderIntervalHbox.setSpacing(20);

    Button export = new Button("Confirm");
    export.setId("actionButton");

    export.setOnAction(e -> {

          if (categoryMenu.getValue() == null) {
            SoundPlayer.play("src/main/resources/error.wav");
            String customMessage = "Please select a category";
            showAlert(customMessage);

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
                      File myFile =
                          new File("src/main/resources/userfiles/" + GUI.getCurrentUser() + "/" + GUI.getCurrentUser() + "bankstatement.pdf");
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

    calenderIntervalHbox.setOnKeyPressed(event -> {
      if (event.getCode() == KeyCode.ENTER) {
        exportToExcel.fire();
      }
    });

    bankStatementVbox.getChildren().addAll(viewBankStatement, selectAccountHbox, selectCategoryHbox,calenderIntervalText, calenderIntervalHbox, tableHbox, export);

    bankStatementVbox.setAlignment(Pos.TOP_CENTER);
    return bankStatementVbox;

  }
}
