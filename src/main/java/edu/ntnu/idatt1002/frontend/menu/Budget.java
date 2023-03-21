//BUDGET

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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

import static edu.ntnu.idatt1002.backend.Incomes.getIncomes;
import static javafx.scene.text.Font.font;

public class Budget {
  public static VBox budgetView() {

    VBox budgetLayout = new VBox();
    budgetLayout.setAlignment(Pos.CENTER_LEFT);
    budgetLayout.setSpacing(20);

    Text editMonthBudget = new Text("Edit this months budget");
    editMonthBudget.setId("titleText");



    HBox categorySelectorHbox = new HBox();



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
    categoryMenu.setPromptText("Select category");
    categoryMenu.setId("categoryMenuButton");


    Button confirmCategory = new Button("Confirm");
    confirmCategory.setId("actionButton");

    categorySelectorHbox.getChildren().addAll(categoryMenu, confirmCategory);
    categorySelectorHbox.setAlignment(Pos.CENTER);
    categorySelectorHbox.setSpacing(20);


    TableView<Income> budgetTable = new TableView<>();
    TableColumn<Income, String> budgetTableColumn1 = new TableColumn<>("Name: ");
    budgetTableColumn1.setCellValueFactory(new PropertyValueFactory<>("name"));

    TableColumn<Income, Double> budgetTableColumn2 = new TableColumn<>("Price: ");
    budgetTableColumn2.setCellValueFactory(new PropertyValueFactory<>("price"));

    TableColumn<Income, LocalDate> budgetTableColumn3 = new TableColumn<>("Date: ");
    budgetTableColumn3.setCellValueFactory(new PropertyValueFactory<>("date"));

    budgetTable.getColumns().addAll(budgetTableColumn1, budgetTableColumn2, budgetTableColumn3);
    budgetTable.getItems().addAll(getIncomes());
    budgetTable.setMaxWidth(500);



    HBox budgetAmountHbox = new HBox();

    TextField budgetAmountField = new TextField();
    budgetAmountField.setPromptText("Set budget for next month: ");
    budgetAmountField.setId("textField");

    Button confirmAmount = new Button("Confirm");
    confirmAmount.setId("actionButton");

    budgetAmountHbox.getChildren().addAll(budgetAmountField, confirmAmount);
    budgetAmountHbox.setAlignment(Pos.CENTER);
    budgetAmountHbox.setSpacing(20);



    TableView<Income> totalBudgetTable = new TableView<>();
    TableColumn<Income, String> totalBudgetTableColumn1 = new TableColumn<>("Name: ");
    totalBudgetTableColumn1.setCellValueFactory(new PropertyValueFactory<>("name"));

    TableColumn<Income, Double> totalBudgetTableColumn2 = new TableColumn<>("Price: ");
    totalBudgetTableColumn2.setCellValueFactory(new PropertyValueFactory<>("price"));

    TableColumn<Income, LocalDate> totalBudgetTableColumn3 = new TableColumn<>("Date: ");
    totalBudgetTableColumn3.setCellValueFactory(new PropertyValueFactory<>("date"));

    totalBudgetTable.getColumns().addAll(totalBudgetTableColumn1, totalBudgetTableColumn2, totalBudgetTableColumn3);
    totalBudgetTable.getItems().addAll(getIncomes());
    totalBudgetTable.setMaxWidth(500);

    budgetLayout.getChildren().addAll(editMonthBudget, categorySelectorHbox, budgetTable, budgetAmountHbox, totalBudgetTable);
    budgetLayout.setAlignment(Pos.TOP_CENTER);

    return budgetLayout;
  }


}
