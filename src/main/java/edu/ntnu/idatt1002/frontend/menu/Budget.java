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
    editMonthBudget.setStyle("-fx-fill: #3F403F");
    editMonthBudget.setFont(font("helvetica", FontWeight.BOLD, FontPosture.REGULAR, 60));


    HBox categorySelectorHbox = new HBox();

    Text categorySelectorText = new Text("Select category: ");
    categorySelectorText.setStyle("-fx-fill: #3F403F");
    categorySelectorText.setFont(font("helvetica", FontWeight.BOLD, FontPosture.REGULAR, 30));

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
    categoryMenu.setPromptText("Select category test");
    categoryMenu.setStyle("-fx-font-size: 20px; -fx-min-width: 100px; -fx-min-height: 50px;-fx-background-color: #9FB8AD; -fx-border-width: 2; -fx-padding: 10px; -fx-background-radius: 0.5em; -fx-prompt-text-fill: #FFFFFF; -fx-text-fill: #FFFFFF;");

    categorySelectorHbox.getChildren().addAll(categorySelectorText, categoryMenu);
    categorySelectorHbox.setAlignment(Pos.CENTER_LEFT);


    TableView<Income> budgetTable = new TableView<>();
    TableColumn<Income, String> budgetTableColumn1 = new TableColumn<>("Name: ");
    budgetTableColumn1.setCellValueFactory(new PropertyValueFactory<>("name"));

    TableColumn<Income, Double> budgetTableColumn2 = new TableColumn<>("Price: ");
    budgetTableColumn2.setCellValueFactory(new PropertyValueFactory<>("price"));

    TableColumn<Income, LocalDate> budgetTableColumn3 = new TableColumn<>("Date: ");
    budgetTableColumn3.setCellValueFactory(new PropertyValueFactory<>("date"));

    budgetTable.getColumns().addAll(budgetTableColumn1, budgetTableColumn2, budgetTableColumn3);
    budgetTable.getItems().addAll(getIncomes());




    HBox budgetAmountHbox = new HBox();

    Text budgetAmountText = new Text("Set budget for next month: ");
    budgetAmountText.setStyle("-fx-fill: #3F403F");
    budgetAmountText.setFont(font("helvetica", FontWeight.BOLD, FontPosture.REGULAR, 30));

    TextField budgetAmountField = new TextField();
    budgetAmountField.setPromptText("Enter amount");
    budgetAmountField.setStyle("-fx-font-size: 20px; -fx-min-width: 100px; -fx-min-height: 50px;-fx-background-color: #9FB8AD; -fx-border-width: 2; -fx-padding: 10px; -fx-background-radius: 0.5em; -fx-prompt-text-fill: #FFFFFF; -fx-text-fill: #FFFFFF;");

    budgetAmountHbox.getChildren().addAll(budgetAmountText, budgetAmountField);
    budgetAmountHbox.setAlignment(Pos.CENTER_LEFT);



    TableView<Income> totalBudgetTable = new TableView<>();
    TableColumn<Income, String> totalBudgetTableColumn1 = new TableColumn<>("Name: ");
    totalBudgetTableColumn1.setCellValueFactory(new PropertyValueFactory<>("name"));

    TableColumn<Income, Double> totalBudgetTableColumn2 = new TableColumn<>("Price: ");
    totalBudgetTableColumn2.setCellValueFactory(new PropertyValueFactory<>("price"));

    TableColumn<Income, LocalDate> totalBudgetTableColumn3 = new TableColumn<>("Date: ");
    totalBudgetTableColumn3.setCellValueFactory(new PropertyValueFactory<>("date"));

    totalBudgetTable.getColumns().addAll(totalBudgetTableColumn1, totalBudgetTableColumn2, totalBudgetTableColumn3);
    totalBudgetTable.getItems().addAll(getIncomes());


    budgetLayout.getChildren().addAll(editMonthBudget, categorySelectorHbox, budgetTable, budgetAmountHbox, totalBudgetTable);


    return budgetLayout;
  }


}
