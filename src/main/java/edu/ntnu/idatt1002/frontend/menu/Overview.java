package edu.ntnu.idatt1002.frontend.menu;

import edu.ntnu.idatt1002.backend.LoginBackend;
import edu.ntnu.idatt1002.backend.Account;
import edu.ntnu.idatt1002.frontend.CreateUser;
import edu.ntnu.idatt1002.backend.Expense;
import edu.ntnu.idatt1002.backend.Income;
import edu.ntnu.idatt1002.frontend.GUI;
import edu.ntnu.idatt1002.frontend.Login;
import edu.ntnu.idatt1002.frontend.utility.DoughnutChart;
import edu.ntnu.idatt1002.frontend.utility.timeofdaychecker;
import edu.ntnu.idatt1002.model.CSVReader;
import edu.ntnu.idatt1002.model.ExcelExporter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.chart.*;
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
import java.util.List;

import static edu.ntnu.idatt1002.backend.Accounts.getTotalOfAllAccounts;
import static edu.ntnu.idatt1002.backend.Incomes.getIncomes;
import static edu.ntnu.idatt1002.frontend.utility.PieChart.createData;

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

    Text text = new Text("Welcome " + name + "!");
    text.setId("welcomeTitleText");


    //Time of day text
    Text text2 = new Text(timeofdaychecker.timeofdaychecker() + "\n");
    text2.setFont(Font.font("helvetica", FontWeight.BOLD, FontPosture.REGULAR, 60));
    text2.setLineSpacing(0);
    text2.setFill(Color.LIGHTGREEN);
    text2.setStyle("-fx-fill: #404f44");

    welcomeAndTimeOfDay.getChildren().addAll(text, text2);
    welcomeAndTimeOfDay.setPadding(new javafx.geometry.Insets(30));

    HBox hbox2 = new HBox(2);
    Text textSavings = new Text("Total savings: " + "\n" + getTotalOfAllAccounts());
    textSavings.setTextAlignment(TextAlignment.CENTER);
    textSavings.setStyle("-fx-fill: #3F403F");
    textSavings.setFont(Font.font("Helvetica", FontWeight.BOLD, FontPosture.REGULAR, 30));
    hbox2.getChildren().add(textSavings);

    Text textSpending = new Text("Monthly spending: " + "\n" + ExcelExporter.getMonthlyTotal());
    textSpending.setTextAlignment(TextAlignment.CENTER);

    textSpending.setStyle("-fx-fill: #3F403F");
    textSpending.setFont(Font.font("Helvetica", FontWeight.BOLD, FontPosture.REGULAR, 30));
    hbox2.getChildren().add(textSpending);

    Text emptySpace = new Text("\n");
    emptySpace.setFont(Font.font("Helvetica", FontWeight.BOLD, FontPosture.REGULAR, 20));

    VBox vboxSavings = new VBox();
    DoughnutChart pieChart1 = new DoughnutChart(pieChartData);
    vboxSavings.setAlignment(Pos.CENTER);
    pieChart1.getStyleClass().add("default-color0.chart-pie"); //DELETE THIS TO GO BACK TO NORMAL COLORS
    vboxSavings.getChildren().addAll(textSavings, pieChart1);

    VBox vboxSpending = new VBox();
    DoughnutChart pieChart2 = new DoughnutChart(pieChartData2);
    vboxSpending.setAlignment(Pos.CENTER);
    pieChart2.getStyleClass().add("my-pie-chart"); //DELETE THIS TO GO BACK TO NORMAL COLORS
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

    rightTable.getColumns().addAll(rightColumn1, rightColumn2, rightColumn3, rightColumn4, rightColumn5);

    rightTable.getItems().addAll(ExcelExporter.getExpensesForMonth());

//    try {
//      rightTable.getItems().addAll(ExcelExporter.getExpensesForCurrentMonth());
//    } catch (IOException e) {
//    throw new RuntimeException(e);
//    }

    vboxSpending.getChildren().add(rightTable);


    Text currentAccountStatusText = new Text("Current account status");
    currentAccountStatusText.setFont(Font.font("Helvetica", FontWeight.BOLD, FontPosture.REGULAR, 40));
    currentAccountStatusTextFormat.getChildren().add(currentAccountStatusText);
    currentAccountStatusText.setStyle("-fx-fill: #3F403F");


    //TESTING BARCHART


    //BARCHART INCOME
//    CategoryAxis xAxis = new CategoryAxis();
//    NumberAxis yAxis = new NumberAxis();
//    BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
//
//    XYChart.Series<String, Number> series1 = new XYChart.Series<>();
//    series1.setName("Income");
//
//    series1.getData().add(new XYChart.Data<>("Category 1", 10));
//    series1.getData().add(new XYChart.Data<>("Category 2", 20));
//    series1.getData().add(new XYChart.Data<>("Category 3", 30));
//    series1.getData().add(new XYChart.Data<>("Category 4", 40));
//
//    XYChart.Series<String, Number> series2 = new XYChart.Series<>();
//    series2.setName("Expenses");
//    series2.getData().add(new XYChart.Data<>("Category 1", 8));
//    series2.getData().add(new XYChart.Data<>("Category 2", 15));
//    series2.getData().add(new XYChart.Data<>("Category 3", 27));
//    series2.getData().add(new XYChart.Data<>("Category 4", 35));
//
//
//    barChart.getData().addAll(series1, series2);
//    barChart.setTitle("Bar Chart Example");
//    xAxis.setLabel("Categories");
//    yAxis.setLabel("Values");
//
//    barChart.setCategoryGap(50); // Gap of 10 pixels between Category 1 and Category 2
//    barChart.setBarGap(5); // Gap of 20 pixels between Category 2 and Category 3
//
//    // set the color of the legend symbol for series1
//    //series1.getNode().setStyle("-fx-bar-legend-symbol: #3F403F;");
//    //series2.getNode().setStyle("-fx-bar-legend-symbol: #9FB8AD;");
//
//
//    for (XYChart.Series<String, Number> series : barChart.getData()) {
//      if (series.getName().equals("Income")) {
//        for (XYChart.Data<String, Number> data : series.getData()) {
//          Node node = data.getNode();
//          node.setStyle("-fx-bar-fill: #3F403F; -fx-bar-legend-symbol: #3F403F;");
//        }
//      }
//    }
//
//    for (XYChart.Series<String, Number> series : barChart.getData()) {
//      if (series.getName().equals("Expenses")) {
//        for (XYChart.Data<String, Number> data : series.getData()) {
//          Node node = data.getNode();
//          node.setStyle("-fx-bar-fill: #9FB8AD; -fx-bar-legend-symbol: #9FB8AD;");
//        }
//      }
//    }

    String currentMonth = timeofdaychecker.getCurrentMonth();
    String previousMonth = timeofdaychecker.getPreviousMonth();

    String csvFileBudget = ("src/main/resources/userfiles/" + GUI.getCurrentUser() + "/" + GUI.getCurrentUser() + "budget.csv");
    String csvFileExpense = ("src/main/resources/userfiles/" + GUI.getCurrentUser() + "/" + GUI.getCurrentUser() + ".csv");

    File fileBudget = new File(csvFileBudget);
    File fileExpense = new File(csvFileExpense);
    if (!fileBudget.exists()) {
      try {
        fileBudget.createNewFile();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    BufferedReader br = null;
    String line = "";
    String csvSplitBy = ",";
    List<String[]> budgetLines = new ArrayList<>();
    BarChart<String, Number> barChart = null;
    List<String[]> expensesLines;
    try {
      br = new BufferedReader(new FileReader(fileBudget));
      while ((line = br.readLine()) != null) {
        String[] data = line.split(csvSplitBy);
        // Filter the data by the current month
        if (data[2].equalsIgnoreCase(currentMonth)) {
          budgetLines.add(data);
        }
      }
      expensesLines = Expense.getExpensesByMonth(csvFileExpense, currentMonth);

      // Create the bar chart
      ObservableList<XYChart.Data<String, Number>> barChartData = FXCollections.observableArrayList();
      for (String[] lineData : budgetLines) {
        barChartData.add(new XYChart.Data<String, Number>(lineData[0], Double.parseDouble(lineData[1])));
      }
      XYChart.Series<String, Number> series = new XYChart.Series<String, Number>(barChartData);
      series.setName(currentMonth);

      ObservableList<XYChart.Data<String, Number>> barChartData2 = FXCollections.observableArrayList();
      for (String[] lineData : expensesLines) {
        barChartData2.add(new XYChart.Data<String, Number>(lineData[0], Double.parseDouble(lineData[1])));
      }
      XYChart.Series<String, Number> series2 = new XYChart.Series<String, Number>(barChartData2);
      series2.setName(previousMonth);

      CategoryAxis xAxis = new CategoryAxis();
      NumberAxis yAxis = new NumberAxis();
      xAxis.setLabel("Categories");
      yAxis.setLabel("Values");
      barChart = new BarChart<String, Number>(xAxis, yAxis);
      barChart.setTitle("Budget");
      barChart.getData().addAll(series, series2);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (br != null) {
        try {
          br.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }

    if (!fileBudget.exists()) {
      try {
        fileBudget.createNewFile();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    br = null;
    line = "";
    csvSplitBy = ",";
    budgetLines = new ArrayList<>();
    expensesLines = new ArrayList<>();
    barChart = null;
    try {
      br = new BufferedReader(new FileReader(fileBudget));
      while ((line = br.readLine()) != null) {
        String[] data = line.split(csvSplitBy);
        // Filter the data by the current month
        if (data[2].equalsIgnoreCase(currentMonth)) {
          budgetLines.add(data);
        }
      }
      expensesLines = Expense.getExpensesByMonth(csvFileExpense, currentMonth);

      // Create the bar chart dataset
      ObservableList<XYChart.Data<String, Number>> currentData = FXCollections.observableArrayList();
      for (String[] lineData : budgetLines) {
        currentData.add(new XYChart.Data<String, Number>(lineData[0], Double.parseDouble(lineData[1])));
      }
      XYChart.Series<String, Number> currentSeries = new XYChart.Series<String, Number>(currentData);
      currentSeries.setName("Budget");

      ObservableList<XYChart.Data<String, Number>> previousData = FXCollections.observableArrayList();
      for (String[] lineData : expensesLines) {
        previousData.add(new XYChart.Data<String, Number>(lineData[0], Double.parseDouble(lineData[1])));
      }
      XYChart.Series<String, Number> previousSeries = new XYChart.Series<String, Number>(previousData);
      previousSeries.setName("Expenses");

      // Create the bar chart
      CategoryAxis xAxis = new CategoryAxis();
      xAxis.setLabel("Category");
      NumberAxis yAxis = new NumberAxis();
      yAxis.setLabel("Value");
      barChart = new BarChart<String, Number>(xAxis, yAxis);
      barChart.setTitle("Bar Chart");
      barChart.getData().addAll(currentSeries, previousSeries);

      // Show the bar chart
    } catch (Exception f) {
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

    return new VBox(welcomeAndTimeOfDay, hboxPieLayout, emptySpace, currentAccountStatusTextFormat, barChart);
  }
  }

