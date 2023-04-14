package edu.ntnu.idatt1002.frontend.menu;

import edu.ntnu.idatt1002.backend.LoginBackend;
import edu.ntnu.idatt1002.backend.Account;
import edu.ntnu.idatt1002.frontend.CreateUser;
import edu.ntnu.idatt1002.backend.Expense;
import edu.ntnu.idatt1002.frontend.GUI;
import edu.ntnu.idatt1002.frontend.utility.BudgetCalculator;
import edu.ntnu.idatt1002.frontend.utility.DoughnutChart;
import edu.ntnu.idatt1002.frontend.utility.timeofdaychecker;
import edu.ntnu.idatt1002.model.CSVReader;
import edu.ntnu.idatt1002.model.ExcelExporter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.scene.text.Font;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.LocalDateStringConverter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static edu.ntnu.idatt1002.backend.Accounts.getTotalOfAllAccounts;
import static edu.ntnu.idatt1002.frontend.utility.PieChart.createData;
import static edu.ntnu.idatt1002.model.ExcelExporter.expensesToTable;

public class Overview {
  public static String name;
  public static VBox overviewView() {
    ObservableList<PieChart.Data> pieChartData = createData();
    ObservableList<PieChart.Data> pieChartData2 = edu.ntnu.idatt1002.frontend.utility.PieChart.createData2();

    VBox welcomeAndTimeOfDay = new VBox();

    if (LoginBackend.getCurrentUser() != null) {
      name = LoginBackend.getCurrentUser();
    } else if (CreateUser.getCurrentUser() != null) {
      name = CreateUser.getCurrentUser();
    } else {
      name = "User";
    }

    System.out.println("open overview window");

    Text welcome = new Text(timeofdaychecker.timeofdaychecker()+" " + name + "!");
    welcome.setId("welcomeTitleText");

    Text budgetRemaining = new Text("Budget remaining: ");
    budgetRemaining.setId("goodMorningText");

    Text budgetText = new Text((BudgetCalculator.getTotalBudget() - ExcelExporter.getMonthlyTotal())+" kr");
    budgetText.setId("goodMorningText");
    if (BudgetCalculator.getTotalBudget() - ExcelExporter.getMonthlyTotal() < 0) {
      budgetText.setFill(Color.RED);
    } else {
      budgetText.setFill(Color.GREEN);
    }

    welcomeAndTimeOfDay.getChildren().addAll(welcome,budgetRemaining, budgetText);
    welcomeAndTimeOfDay.setPadding(new javafx.geometry.Insets(30));

    HBox hbox2 = new HBox(2);

    Text textSavings = new Text("Total savings: " + "\n" + getTotalOfAllAccounts());
    textSavings.setTextAlignment(TextAlignment.CENTER);
    textSavings.setId("helveticaTitle");
    hbox2.getChildren().add(textSavings);


    Text textSpending = new Text("Monthly spending: " + "\n" + ExcelExporter.getMonthlyTotal());
    textSpending.setTextAlignment(TextAlignment.CENTER);
    textSpending.setId("helveticaTitle");
    hbox2.getChildren().add(textSpending);

    Text emptySpace = new Text("\n");
    emptySpace.setFont(Font.font("Helvetica", FontWeight.BOLD, FontPosture.REGULAR, 20));

    VBox vboxSavings = new VBox();
    DoughnutChart pieChart1 = new DoughnutChart(pieChartData);
    vboxSavings.setAlignment(Pos.CENTER);
    vboxSavings.getChildren().addAll(textSavings, pieChart1);

    VBox vboxSpending = new VBox();
    DoughnutChart pieChart2 = new DoughnutChart(pieChartData2);
    vboxSpending.setAlignment(Pos.CENTER);
    vboxSpending.getChildren().addAll(textSpending, pieChart2);

    HBox hboxPieLayout = new HBox(vboxSavings, vboxSpending);
    hboxPieLayout.setAlignment(Pos.CENTER);
    hboxPieLayout.setSpacing(50);

    HBox currentAccountStatusTextFormat = new HBox();
    currentAccountStatusTextFormat.setAlignment(Pos.CENTER);

    //LeftTable
    TableView<Account> leftTable = new TableView<>();
    TableColumn<Account, String> leftColumn1 = new TableColumn<>("Account: ");
    leftColumn1.setCellValueFactory(new PropertyValueFactory<>("accountName"));

    TableColumn<Account, Double> leftColumn2 = new TableColumn<>("Amount: ");
    leftColumn2.setCellValueFactory(new PropertyValueFactory<>("amount"));

    TableColumn<Account, String> leftColumn3 = new TableColumn<>("Date: ");
    leftColumn3.setCellValueFactory(new PropertyValueFactory<>("date"));

    try {
      leftTable.getItems().addAll(CSVReader.listOfTransfers());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    leftTable.getColumns().addAll(leftColumn1, leftColumn2, leftColumn3);

    leftTable.setEditable(true);

    leftTable.setOnMouseClicked(event -> {
      if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
        leftTable.edit(leftTable.getSelectionModel().getSelectedIndex(), leftColumn1);
      }
    });

    vboxSavings.getChildren().add(leftTable);



    //TODO vboxSpending.getChildren().add(rightTable);

    //RightTable
    TableView<Expense> rightTable = new TableView<>();
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
      Expense expense = event.getTableView().getItems().get(event.getTablePosition().getRow());       //Add error message if not a string

      // Set the new name value on the expense object
      expense.setName(event.getNewValue());
      CSVReader.updateRowsThatAreDifferentInTable(rightTable.getItems(),
           CSVReader.getExpensesFromCSV());
    });

    rightColumn2.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));      //Add errror message if not a double
    rightColumn2.setOnEditCommit(event -> {
      // Get the expense object that was edited
      Expense expense = event.getTableView().getItems().get(event.getTablePosition().getRow());

      // Set the new name value on the expense object
      expense.setPrice(event.getNewValue());
      CSVReader.updateRowsThatAreDifferentInTable(rightTable.getItems(),
          CSVReader.getExpensesFromCSV());
    });

    rightColumn3.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));   //Add error message if date is not valid
    rightColumn3.setOnEditCommit(event -> {
      // Get the expense object that was edited
      Expense expense = event.getTableView().getItems().get(event.getTablePosition().getRow());

      // Set the new name value on the expense object
      expense.setDate(event.getNewValue());
      CSVReader.updateRowsThatAreDifferentInTable(rightTable.getItems(),
          CSVReader.getExpensesFromCSV());
    });

    rightColumn4.setCellFactory(TextFieldTableCell.forTableColumn());                                 //Add error message if not a valid category
    rightColumn4.setOnEditCommit(event -> {
      // Get the expense object that was edited
      Expense expense = event.getTableView().getItems().get(event.getTablePosition().getRow());

      // Set the new name value on the expense object
      expense.setCategoryAsString(event.getNewValue());
      CSVReader.updateRowsThatAreDifferentInTable(rightTable.getItems(),
          CSVReader.getExpensesFromCSV());
    });

    rightColumn5.setCellFactory(TextFieldTableCell.forTableColumn());                                 //Add error message if not a valid account
    rightColumn5.setOnEditCommit(event -> {
      // Get the expense object that was edited
      Expense expense = event.getTableView().getItems().get(event.getTablePosition().getRow());

      // Set the new name value on the expense object
      expense.setAccountAsString(event.getNewValue());
      CSVReader.updateRowsThatAreDifferentInTable(rightTable.getItems(),
          CSVReader.getExpensesFromCSV());
    });

    rightTable.getColumns().addAll(rightColumn1, rightColumn2, rightColumn3, rightColumn4, rightColumn5);

    rightTable.getItems().addAll(ExcelExporter.getExpensesForMonth());

    Button removeButton = new Button("Remove Selected");
    removeButton.setOnAction(event -> {
      ObservableList<Expense> selectedExpenses = rightTable.getSelectionModel().getSelectedItems();
      rightTable.getItems().removeAll(selectedExpenses);
    });

    vboxSpending.getChildren().add(removeButton);
    vboxSpending.getChildren().add(rightTable);

    Text currentAccountStatusText = new Text("Current account status");
    currentAccountStatusText.setFont(Font.font("Helvetica", FontWeight.BOLD, FontPosture.REGULAR, 40));
    currentAccountStatusTextFormat.getChildren().add(currentAccountStatusText);
    currentAccountStatusText.setStyle("-fx-fill: #3F403F");


    File csvFile = new File("src/main/resources/userfiles/" + GUI.getCurrentUser() + "/" + GUI.getCurrentUser() + "budget.csv");
    String currentMonth = timeofdaychecker.getCurrentMonth();
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
          expensesToBarChart.put(data[0], ExcelExporter.getTotalOfRent(expensesToTable));
        }
        if (data[0].equalsIgnoreCase("Entertainment")) {
          expensesToBarChart.put(data[0], ExcelExporter.getTotalOfEntertainment(expensesToTable));
        }
        if (data[0].equalsIgnoreCase("Food")) {
          expensesToBarChart.put(data[0], ExcelExporter.getTotalOfFood(expensesToTable));
        }
        if (data[0].equalsIgnoreCase("Transportation")) {
          expensesToBarChart.put(data[0], ExcelExporter.getTotalOfTransportation(expensesToTable));
        }
        if (data[0].equalsIgnoreCase("Other")) {
          expensesToBarChart.put(data[0], ExcelExporter.getTotalOfOther(expensesToTable));
        }
        if (data[0].equalsIgnoreCase("Clothing")) {
          expensesToBarChart.put(data[0], ExcelExporter.getTotalOfClothing(expensesToTable));
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

    for (XYChart.Series<String, Number> series : barChart.getData()) {
      if (series.getName().equals("Income")) {
        for (XYChart.Data<String, Number> data : series.getData()) {
          Node node = data.getNode();
          node.setStyle("-fx-bar-fill: #3F403F; -fx-bar-legend-symbol: #3F403F;");
        }
      }
    }

    for (XYChart.Series<String, Number> series : barChart.getData()) {
      if (series.getName().equals("Expenses")) {
        for (XYChart.Data<String, Number> data : series.getData()) {
          Node node = data.getNode();
          node.setStyle("-fx-bar-fill: #9FB8AD; -fx-bar-legend-symbol: #9FB8AD;");
        }
      }
    }
    VBox vbox = new VBox(welcomeAndTimeOfDay, hboxPieLayout, emptySpace, currentAccountStatusTextFormat, barChart);

      return vbox;
    }
  }

