package edu.ntnu.idatt1002.frontend.menu;

import edu.ntnu.idatt1002.backend.budgeting.Accounts;
import edu.ntnu.idatt1002.backend.budgeting.Expense;
import edu.ntnu.idatt1002.backend.budgeting.Transfers;
import edu.ntnu.idatt1002.backend.user.LoginBackend;
import edu.ntnu.idatt1002.frontend.CreateUser;
import edu.ntnu.idatt1002.frontend.GUI;
import edu.ntnu.idatt1002.frontend.utility.*;
import edu.ntnu.idatt1002.model.CSVReader;
import edu.ntnu.idatt1002.model.ExcelExporter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.LocalDateStringConverter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

import static edu.ntnu.idatt1002.frontend.utility.AlertWindow.showAlert;
import static edu.ntnu.idatt1002.frontend.utility.CustomPieChart.createData;
import static edu.ntnu.idatt1002.model.ExcelExporter.expensesToTable;

/**
 * A class that creates the overview view.
 *
 * @author Emil J., Vegard J., Sander S. and Elias T.
 * @version 0.5 - 19.04.2023
 */
public class Overview {
  /**
   * The name of the current user.
   */
  public static String name;

  /**
   * A method that creates the overview view.
   * The method is used by the GUI class.
   *
   * @return the vertical box
   */
  public static VBox overviewView() {
    ObservableList<PieChart.Data> pieChartData = createData();
    ObservableList<PieChart.Data> pieChartData2 = CustomPieChart.createData2();

    VBox welcomeAndTimeOfDay = new VBox();

    if (LoginBackend.getCurrentUser() != null) {
      name = LoginBackend.getCurrentUser();
    } else if (CreateUser.getCurrentUser() != null) {
      name = CreateUser.getCurrentUser();
    } else {
      name = "User";
    }

    System.out.println("open overview window");

    Text welcome = new Text(TimeOfDayChecker.timeofdaychecker()+" " + name + "!");
    welcome.setId("welcomeTitleText");
    welcome.setFocusTraversable(true);

    Text budgetRemaining = new Text("Budget remaining: ");
    budgetRemaining.setId("goodMorningTextTitle");
    budgetRemaining.setFocusTraversable(true);

    ExcelExporter instance = ExcelExporter.getInstance();
    BudgetCalculator budgetInstance = BudgetCalculator.getInstance();

    Text budgetText = new Text((budgetInstance.getTotalBudget() - instance.getMonthlyTotal())+" kr");
    budgetText.setId("goodMorningText");
    if (budgetInstance.getTotalBudget() - instance.getMonthlyTotal() < 0) {
      budgetText.setFill(Color.RED);
    } else {
      budgetText.setFill(Color.GREEN);
    }

    welcomeAndTimeOfDay.getChildren().addAll(welcome,budgetRemaining, budgetText);
    welcomeAndTimeOfDay.setPadding(new javafx.geometry.Insets(30));

    HBox hbox2 = new HBox(2);

    Accounts accountsInstance = Accounts.getInstance();

    Text textSavings = new Text("Total savings: " + "\n" + accountsInstance.getTotalOfAllAccounts());
    textSavings.setTextAlignment(TextAlignment.CENTER);
    textSavings.setId("helveticaTitle");
    hbox2.getChildren().add(textSavings);


    Text textSpending = new Text("Monthly spending: " + "\n" + instance.getMonthlyTotal());
    textSpending.setTextAlignment(TextAlignment.CENTER);
    textSpending.setId("helveticaTitle");
    hbox2.getChildren().add(textSpending);

    Text emptySpace = new Text("\n");
    emptySpace.setFont(Font.font("Helvetica", FontWeight.BOLD, FontPosture.REGULAR, 20));

    VBox vboxSavings = new VBox();
    DoughnutChart pieChart1 = new DoughnutChart(pieChartData, GUI.getStylesheet());
    pieChart1.setFocusTraversable(true);
    vboxSavings.setAlignment(Pos.CENTER);
    vboxSavings.getChildren().addAll(textSavings, pieChart1);

    VBox vboxSpending = new VBox();
    DoughnutChart pieChart2 = new DoughnutChart(pieChartData2, GUI.getStylesheet());
    pieChart2.setFocusTraversable(true);
    vboxSpending.setAlignment(Pos.CENTER);
    vboxSpending.getChildren().addAll(textSpending, pieChart2);

    HBox hboxPieLayout = new HBox(vboxSavings, vboxSpending);
    hboxPieLayout.setAlignment(Pos.CENTER);
    hboxPieLayout.setSpacing(50);

    HBox currentAccountStatusTextFormat = new HBox();
    currentAccountStatusTextFormat.setAlignment(Pos.CENTER);

    //LeftTable
    TableView<Transfers> leftTable = new TableView<>();
    TableColumn<Transfers, String> leftColumn1 = new TableColumn<>("Account: ");
    leftColumn1.setCellValueFactory(new PropertyValueFactory<>("accountName"));

    TableColumn<Transfers, Double> leftColumn2 = new TableColumn<>("Amount: ");
    leftColumn2.setCellValueFactory(new PropertyValueFactory<>("amount"));

    TableColumn<Transfers, String> leftColumn3 = new TableColumn<>("Date: ");
    leftColumn3.setCellValueFactory(new PropertyValueFactory<>("date"));

    try {
      CSVReader csvInstance = CSVReader.getInstance();

      leftTable.getItems().addAll(csvInstance.listOfTransfers());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    leftTable.getColumns().addAll(leftColumn1, leftColumn2, leftColumn3);

    leftTable.setEditable(true);

    Button removeButton1 = new Button("Remove Selected");
    removeButton1.setFocusTraversable(true);
    removeButton1.setId("actionButton");
    removeButton1.setOnKeyPressed(event -> {
      if (event.getCode() == KeyCode.DELETE) {
        removeButton1.fire();
      }
    });

    removeButton1.setOnAction(event -> {
      CSVReader csvInstance = CSVReader.getInstance();

      try {
        Transfers selectedTransfer = leftTable.getSelectionModel().getSelectedItem();
        if (selectedTransfer == null) {
          throw new IllegalArgumentException("No transfer selected");
        } else {
          String transferType = String.valueOf(selectedTransfer.getTransferType());
          if (transferType.equals("A")) {
            if (selectedTransfer.getAmount() > accountsInstance.getTotalOfAccount(selectedTransfer.getAccountName())) {
              throw new IllegalArgumentException("Cannot remove transfer from account, not enough money");
            } else {
              leftTable.getItems().remove(selectedTransfer);
              csvInstance.removeTransfer(leftTable.getItems());
              GUI.setPaneToUpdate("overview");
              GUI.updatePane();
            }
          } else if (transferType.equals("B")) {
            throw new IllegalArgumentException("Cannot remove transfer from account to account");
          }
        }
      } catch (IllegalArgumentException ex) {
        SoundPlayer.play(FileUtil.getResourceFilePath("error.wav"));
        String errorMessage = ex.getMessage();
        showAlert(errorMessage);
      }
    });



    vboxSavings.getChildren().add(removeButton1);
    vboxSavings.getChildren().add(leftTable);

    vboxSavings.setSpacing(20);
    vboxSpending.setSpacing(20);


    //TODO vboxSpending.getChildren().add(rightTable);

    //RightTable
    TableView<Expense> rightTable = new TableView<>();
    rightTable.setFocusTraversable(true);
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

    rightTable.setEditable(true);

    rightColumn1.setCellFactory(TextFieldTableCell.forTableColumn());
    rightColumn1.setOnEditCommit(event -> {
      // Get the expense object that was edited
      CSVReader csvInstance = CSVReader.getInstance();

      Expense expense = event.getTableView().getItems().get(event.getTablePosition().getRow());

      // Set the new name value on the expense object
        expense.setName(event.getNewValue());
        csvInstance.updateRowsThatAreDifferentInTable(rightTable.getItems(),
                csvInstance.getExpensesFromCSV());
        GUI.setPaneToUpdate("overview");
        GUI.updatePane();
    });

    rightColumn2.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
    rightColumn2.setOnEditCommit(event -> {
      CSVReader csvInstance = CSVReader.getInstance();

      // Get the expense object that was edited
      Expense expense = event.getTableView().getItems().get(event.getTablePosition().getRow());

      // Set the new name value on the expense object
      if (event.getNewValue()>accountsInstance.getTotalOfAccount(expense.getAccount())) {
        String errorMessage = "Cannot remove transfer from account, not enough money";
        showAlert(errorMessage);
        event.getTableView().refresh();
        GUI.updatePane();
      } else {
        expense.setPrice(event.getNewValue());
        csvInstance.updateRowsThatAreDifferentInTable(rightTable.getItems(),
                csvInstance.getExpensesFromCSV());
        GUI.setPaneToUpdate("overview");
        GUI.updatePane();
      }
    });

    rightColumn3.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));
    rightColumn3.setOnEditCommit(event -> {
      CSVReader csvInstance = CSVReader.getInstance();

      // Get the expense object that was edited
      Expense expense = event.getTableView().getItems().get(event.getTablePosition().getRow());

      // Set the new name value on the expense object
      expense.setDate(event.getNewValue());
      csvInstance.updateRowsThatAreDifferentInTable(rightTable.getItems(),
              csvInstance.getExpensesFromCSV());
      GUI.setPaneToUpdate("overview");
      GUI.updatePane();
    });

    rightColumn4.setCellFactory(TextFieldTableCell.forTableColumn());                                 //Add error message if not a valid category
    rightColumn4.setOnEditCommit(event -> {
      CSVReader csvInstance = CSVReader.getInstance();

      // Get the expense object that was edited
      Expense expense = event.getTableView().getItems().get(event.getTablePosition().getRow());

      // Set the new name value on the expense object
      if (!expense.validateCategory(event.getNewValue())){
        String errorMessage = "Category does not exist";
        showAlert(errorMessage);
        event.getTableView().refresh();
        GUI.updatePane();
      } else {
        expense.setCategoryAsString(event.getNewValue());
        csvInstance.updateRowsThatAreDifferentInTable(rightTable.getItems(),
                csvInstance.getExpensesFromCSV());
        GUI.setPaneToUpdate("overview");
        GUI.updatePane();
      }
    });

    rightColumn5.setCellFactory(TextFieldTableCell.forTableColumn());
    rightColumn5.setOnEditCommit(event -> {
      CSVReader csvInstance = CSVReader.getInstance();

      // Get the expense object that was edited
      Expense expense = event.getTableView().getItems().get(event.getTablePosition().getRow());

      // Set the new name value on the expense object
      if (!accountsInstance.validateAccountName(event.getNewValue())){
        String errorMessage = "Account name does not exist";
        showAlert(errorMessage);
        event.getTableView().refresh();
        GUI.updatePane();
      } else {
        expense.setAccountAsString(event.getNewValue());
        csvInstance.updateRowsThatAreDifferentInTable(rightTable.getItems(),
                csvInstance.getExpensesFromCSV());
        GUI.setPaneToUpdate("overview");
        GUI.updatePane();
      }
    });

    rightTable.getColumns().addAll(rightColumn1, rightColumn2, rightColumn3, rightColumn4, rightColumn5);

    rightTable.getItems().addAll(instance.getExpensesForMonth());

    Button removeButton = new Button("Remove Selected");
    removeButton.setId("actionButton");
    removeButton.setFocusTraversable(true);

    removeButton.setOnKeyPressed(event -> {
      if (event.getCode() == KeyCode.ENTER) {
        removeButton.fire();
      }
    });

    removeButton.setOnAction(event -> {
      try {
      if (rightTable.getSelectionModel().getSelectedItems().isEmpty()) {
        throw new IllegalArgumentException("No expense selected");
      }
      CSVReader csvInstance = CSVReader.getInstance();

      ObservableList<Expense> selectedExpenses = rightTable.getSelectionModel().getSelectedItems();
      rightTable.getItems().removeAll(selectedExpenses);
      csvInstance.updateRowsThatAreDifferentInTable(rightTable.getItems(),
              csvInstance.getExpensesFromCSV());
      GUI.setPaneToUpdate("overview");
      GUI.updatePane();
    } catch (IllegalArgumentException e) {
        SoundPlayer.play(FileUtil.getResourceFilePath("error.wav"));
        showAlert(e.getMessage());
      }
    });

    vboxSpending.getChildren().add(removeButton);
    vboxSpending.getChildren().add(rightTable);

    Text currentAccountStatusText = new Text("Current account status");
    currentAccountStatusText.setId("titleText");
    currentAccountStatusTextFormat.getChildren().add(currentAccountStatusText);


    File csvFile = new File("src/main/resources/userfiles/" + GUI.getCurrentUser() + "/" + GUI.getCurrentUser() + "budget.csv");
    String currentMonth = TimeOfDayChecker.getCurrentMonth();
    if (!csvFile.exists()) {
      try {
        csvFile.createNewFile();
      } catch (IOException f) {
        f.printStackTrace();
      }
    }
    BufferedReader br = null;
    String line = "";
    String csvSplitBy = ",";
    List<String[]> currentLines = new ArrayList<String[]>();
    List<String[]> previousLines = new ArrayList<String[]>();
    BarChart<String, Number> barChart = null;
    HashMap<String, Double> expensesToBarChart = new HashMap<>();
    try {
      br = new BufferedReader(new FileReader(csvFile));
      while ((line = br.readLine()) != null) {
        String[] data = line.split(csvSplitBy);
        // Filter the data by the current month
        if (data.length >= 3 && data[2].equalsIgnoreCase(currentMonth)) {
          currentLines.add(data);
        }
        if (data[0].equalsIgnoreCase("Rent")) {
          expensesToBarChart.put(data[0], instance.getTotalOfRent(expensesToTable));
        }
        if (data[0].equalsIgnoreCase("Entertainment")) {
          expensesToBarChart.put(data[0], instance.getTotalOfEntertainment(expensesToTable));
        }
        if (data[0].equalsIgnoreCase("Food")) {
          expensesToBarChart.put(data[0], instance.getTotalOfFood(expensesToTable));
        }
        if (data[0].equalsIgnoreCase("Transportation")) {
          expensesToBarChart.put(data[0], instance.getTotalOfTransportation(expensesToTable));
        }
        if (data[0].equalsIgnoreCase("Other")) {
          expensesToBarChart.put(data[0], instance.getTotalOfOther(expensesToTable));
        }
        if (data[0].equalsIgnoreCase("Clothing")) {
          expensesToBarChart.put(data[0], instance.getTotalOfClothing(expensesToTable));
        }
      }
      // Create the bar chart dataset
      ObservableList<XYChart.Data<String, Number>> currentData = FXCollections.observableArrayList();
      for (String[] lineData : currentLines) {
        currentData.add(new XYChart.Data<String, Number>(lineData[0], Double.parseDouble(lineData[1])));
      }
      XYChart.Series<String, Number> currentSeries = new XYChart.Series<String, Number>(currentData);
      currentSeries.setName(currentMonth);

      ObservableList<XYChart.Data<String, Number>> previousData = FXCollections.observableArrayList();
      for (Map.Entry<String, Double> entry : expensesToBarChart.entrySet()) {
        String category = entry.getKey();
        double total = entry.getValue();
        previousData.add(new XYChart.Data<String, Number>(category, total));
      }

      XYChart.Series<String, Number> previousSeries = new XYChart.Series<String, Number>(previousData);
      previousSeries.setName("Expenses for " + currentMonth);

      // Create the bar chart
      CategoryAxis xAxis = new CategoryAxis();
      xAxis.setLabel("Category");
      NumberAxis yAxis = new NumberAxis();
      yAxis.setLabel("Value");
      barChart = new BarChart<String, Number>(xAxis, yAxis);
      barChart.setTitle("Bar Chart");
      barChart.getData().addAll(currentSeries, previousSeries);

      if (Objects.equals(GUI.getStylesheet(), "Darkmode")) {
        applyDarkModeStyles(barChart);
      }

      // Show the bar chart
    } catch (IOException f) {
      f.printStackTrace();
    } finally {
      if (br != null) {
        try {
          br.close();
        } catch (IOException f) {
          f.printStackTrace();
        }
      }
    }

    barChart.setCategoryGap(50); // Gap of 10 pixels between Category 1 and Category 2
    barChart.setBarGap(5); // Gap of 20 pixels between Category 2 and Category 3
    barChart.setFocusTraversable(true);

    VBox vbox = new VBox(welcomeAndTimeOfDay, hboxPieLayout, emptySpace, currentAccountStatusTextFormat, barChart);
    vbox.setSpacing(20);

    return vbox;
  }

  private static void applyDarkModeStyles(BarChart<String, Number> barChart) {
    barChart.setStyle("-fx-background-color: transparent;");

    CategoryAxis xAxis = (CategoryAxis) barChart.getXAxis();
    xAxis.setStyle("-fx-tick-label-fill: #FFFFFF;");

    NumberAxis yAxis = (NumberAxis) barChart.getYAxis();
    yAxis.setStyle("-fx-tick-label-fill: #FFFFFF;");

    barChart.lookupAll(".chart-legend-item-text").forEach(node -> {
      node.setStyle("-fx-text-fill: #FFFFFF;");
    });

    barChart.lookupAll(".series0 .chart-bar").forEach(node -> {
      node.setStyle("-fx-bar-fill: #FF0000;"); // Red
    });

    barChart.lookupAll(".series1 .chart-bar").forEach(node -> {
      node.setStyle("-fx-bar-fill: #FFFF00;"); // Yellow
    });

    // Set legend symbol colors
    barChart.lookupAll(".series0 .chart-legend-symbol").forEach(node -> {
      node.setStyle("-fx-background-color: #FF0000, white;"); // Red
    });

    barChart.lookupAll(".series1 .chart-legend-symbol").forEach(node -> {
      node.setStyle("-fx-background-color: #FFFF00, white;"); // Yellow
    });
  }


}

