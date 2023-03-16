package edu.ntnu.idatt1002.frontend.menu;

import edu.ntnu.idatt1002.backend.Expense;
import edu.ntnu.idatt1002.backend.Expenses;
import edu.ntnu.idatt1002.backend.Income;
import edu.ntnu.idatt1002.frontend.Login;
import edu.ntnu.idatt1002.frontend.utility.DoughnutChart;
import edu.ntnu.idatt1002.frontend.utility.timeofdaychecker;
import edu.ntnu.idatt1002.model.ExcelExporter;
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

import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static edu.ntnu.idatt1002.backend.Accounts.getTotalOfAllAccounts;
import static edu.ntnu.idatt1002.backend.Expenses.getExpensesOfAllCategories;
import static edu.ntnu.idatt1002.backend.Incomes.getIncomes;
import static edu.ntnu.idatt1002.backend.Incomes.incomes;
import static edu.ntnu.idatt1002.frontend.utility.PieChart.createData;

public class Overview {
  public static VBox overviewView() {
    ObservableList<PieChart.Data> pieChartData = createData();
    ObservableList<PieChart.Data> pieChartData2 = edu.ntnu.idatt1002.frontend.utility.PieChart.createData2();

    //BarChartSample barChart = new BarChartSample();


    System.out.println("open overview window");
    Text text = new Text("Welcome " + Login.username.getText() + "!");
    text.setFont(Font.font("Helvetica", FontWeight.BOLD, FontPosture.REGULAR, 80));
    text.setStyle("-fx-fill: #3F403F; -fx-stroke: #3F403F; -fx-stroke-width: 1px; -fx-padding: 200px;");


    //Time of day text
    Text text2 = new Text(timeofdaychecker.timeofdaychecker() + "\n");
    text2.setFont(Font.font("helvetica", FontWeight.BOLD, FontPosture.REGULAR, 60));
    text2.setLineSpacing(0);
    text2.setFill(Color.LIGHTGREEN);
    text2.setStyle("-fx-fill: #9FB8AD");


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

    VBox vboxSavings = new VBox(textSavings, new DoughnutChart(pieChartData));
    vboxSavings.setAlignment(Pos.CENTER);

    VBox vboxSpending = new VBox(textSpending, new DoughnutChart(pieChartData2));
    vboxSpending.setAlignment(Pos.CENTER);

    HBox hboxPieLayout = new HBox(vboxSavings, vboxSpending);
    hboxPieLayout.setAlignment(Pos.CENTER);
    hboxPieLayout.setSpacing(50);

    HBox currentAccountStatusTextFormat = new HBox();
    currentAccountStatusTextFormat.setAlignment(Pos.CENTER);


    //LeftTable
    TableView<Income> leftTable = new TableView<>();
    TableColumn<Income, String> leftColumn1 = new TableColumn<>("Name: ");
    leftColumn1.setCellValueFactory(new PropertyValueFactory<>("name"));

    TableColumn<Income, Double> leftColumn2 = new TableColumn<>("Price: ");
    leftColumn2.setCellValueFactory(new PropertyValueFactory<>("price"));

    TableColumn<Income, LocalDate> leftColumn3 = new TableColumn<>("Date: ");
    leftColumn3.setCellValueFactory(new PropertyValueFactory<>("date"));

    leftTable.getItems().addAll(getIncomes());

    leftColumn1.setCellFactory(TextFieldTableCell.forTableColumn());
    leftColumn1.setOnEditCommit(event -> {
      Income data = event.getRowValue();
      data.setName(event.getNewValue());
    });

    leftColumn2.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
    leftColumn2.setOnEditCommit(event -> {
      Income data = event.getRowValue();
      data.setPrice(event.getNewValue());
    });

    leftColumn3.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));
    leftColumn3.setOnEditCommit(event -> {
      Income data = event.getRowValue();
      data.setDate(event.getNewValue());
    });

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
    CategoryAxis xAxis = new CategoryAxis();
    NumberAxis yAxis = new NumberAxis();
    BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);

    XYChart.Series<String, Number> series1 = new XYChart.Series<>();
    series1.setName("Income");

    series1.getData().add(new XYChart.Data<>("Category 1", 10));
    series1.getData().add(new XYChart.Data<>("Category 2", 20));
    series1.getData().add(new XYChart.Data<>("Category 3", 30));
    series1.getData().add(new XYChart.Data<>("Category 4", 40));

    XYChart.Series<String, Number> series2 = new XYChart.Series<>();
    series2.setName("Expenses");
    series2.getData().add(new XYChart.Data<>("Category 1", 8));
    series2.getData().add(new XYChart.Data<>("Category 2", 15));
    series2.getData().add(new XYChart.Data<>("Category 3", 27));
    series2.getData().add(new XYChart.Data<>("Category 4", 35));


    barChart.getData().addAll(series1, series2);
    barChart.setTitle("Bar Chart Example");
    xAxis.setLabel("Categories");
    yAxis.setLabel("Values");

    barChart.setCategoryGap(50); // Gap of 10 pixels between Category 1 and Category 2
    barChart.setBarGap(5); // Gap of 20 pixels between Category 2 and Category 3

    // set the color of the legend symbol for series1
    //series1.getNode().setStyle("-fx-bar-legend-symbol: #3F403F;");
    //series2.getNode().setStyle("-fx-bar-legend-symbol: #9FB8AD;");


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



    VBox vbox = new VBox(text, text2, hboxPieLayout, emptySpace, currentAccountStatusTextFormat, barChart);

      return vbox;
    }
  }

