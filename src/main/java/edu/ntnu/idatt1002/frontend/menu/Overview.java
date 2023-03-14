package edu.ntnu.idatt1002.frontend.menu;

import edu.ntnu.idatt1002.backend.Expense;
import edu.ntnu.idatt1002.backend.Expenses;
import edu.ntnu.idatt1002.backend.Income;
import edu.ntnu.idatt1002.frontend.utility.DoughnutChart;
import edu.ntnu.idatt1002.frontend.utility.timeofdaychecker;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.chart.PieChart;
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
import java.time.LocalDate;

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
    Text text = new Text("Welcome Keira");
    text.setFont(Font.font("Helvetica", FontWeight.BOLD, FontPosture.REGULAR, 80));
    text.setStyle("-fx-fill: #3F403F; -fx-stroke: #3F403F; -fx-stroke-width: 1px; -fx-padding: 200px;");


    //Time of day text
    Text text2 = new Text(timeofdaychecker.timeofdaychecker() + "\n");
    text2.setFont(Font.font("helvetica", FontWeight.BOLD, FontPosture.REGULAR, 60));
    text2.setLineSpacing(0);
    text2.setFill(Color.LIGHTGREEN);
    text2.setStyle("-fx-fill: #9FB8AD");


    HBox hbox2 = new HBox(2);
    Text textSavings = new Text("Total savings: " +"\n"+ getTotalOfAllAccounts());
    textSavings.setTextAlignment(TextAlignment.CENTER);
    textSavings.setStyle("-fx-fill: #3F403F");
    textSavings.setFont(Font.font("Helvetica", FontWeight.BOLD, FontPosture.REGULAR, 30));
    hbox2.getChildren().add(textSavings);

    Text textSpending = new Text("Monthly spending: " +"\n"+ getExpensesOfAllCategories());
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

    rightTable.getColumns().addAll(rightColumn1, rightColumn2, rightColumn3);

    rightTable.getItems().addAll(Expenses.createAllExpenses());  //TODO: Change to getExpenses()

    vboxSpending.getChildren().add(rightTable);


    Text currentAccountStatusText = new Text("Current account status");
    currentAccountStatusText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 40));
    currentAccountStatusTextFormat.getChildren().add(currentAccountStatusText);


    //TESTING BARCHART

//        HBox barcharts = new HBox();
//
//        CategoryAxis xAxis = new CategoryAxis();
//        NumberAxis yAxis = new NumberAxis();
//        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
//        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
//        series1.setName("Series 1");
//        series1.getData().add(new XYChart.Data<>("Category 1", 10));
//        series1.getData().add(new XYChart.Data<>("Category 2", 20));
//        series1.getData().add(new XYChart.Data<>("Category 3", 30));
//        barChart.getData().addAll(series1);
//        barChart.setTitle("Bar Chart Example");
//        xAxis.setLabel("Categories");
//        yAxis.setLabel("Values");
//        barChart.setStyle("-fx-background-color: #F4F4F4");
//        series1.getNode().setStyle("-fx-bar-fill: #0099FF");
//
//        barcharts.getChildren().add(barChart);


    //END OF TEST OF BARCHART

    //topMenu(primaryStage);

    VBox vbox = new VBox(text, text2, hboxPieLayout, emptySpace, currentAccountStatusTextFormat);

    //overviewWindowStackPane.getChildren().add(vbox);
    return vbox;
  }
}
