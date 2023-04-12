//BUDGET

package edu.ntnu.idatt1002.frontend.menu;

import edu.ntnu.idatt1002.backend.Income;
import edu.ntnu.idatt1002.frontend.GUI;
import edu.ntnu.idatt1002.frontend.utility.timeofdaychecker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import static edu.ntnu.idatt1002.backend.Incomes.getIncomes;

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

    categorySelectorHbox.getChildren().addAll(categoryMenu);
    categorySelectorHbox.setAlignment(Pos.CENTER);
    categorySelectorHbox.setSpacing(20);


    HBox budgetAmountHbox = new HBox();

    TextField budgetAmountField = new TextField();
    budgetAmountField.setPromptText("Set budget for next month: ");
    budgetAmountField.setId("textField");

    Button confirmAmount = new Button("Confirm");
    confirmAmount.setId("actionButton");

    confirmAmount.setOnAction(e -> {
      System.out.println("confirm amount");

      String category = categoryMenu.getValue().toString();
      String amount = budgetAmountField.getText();
      String month = timeofdaychecker.getCurrentMonth();

      try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File("src/main/resources/userfiles/" + GUI.getCurrentUser() + "/", GUI.getCurrentUser() + "budget.csv"), true))) {
        writer.write(category + "," + amount + "," + month + "\n");
      } catch (IOException f) {
        System.err.println("Error writing to file: " + f.getMessage());
      }

      String currentMonth = timeofdaychecker.getCurrentMonth();
      String previousMonth = timeofdaychecker.getPreviousMonth();

      // Read the CSV file
      String csvFile = ("src/main/resources/userfiles/" + GUI.getCurrentUser() + "/" + GUI.getCurrentUser() + "budget.csv");
      File file = new File(csvFile);
      if (!file.exists()) {
        try {
          file.createNewFile();
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
      try {
        br = new BufferedReader(new FileReader(csvFile));
        while ((line = br.readLine()) != null) {
          String[] data = line.split(csvSplitBy);
          // Filter the data by the current month
          if (data[2].equalsIgnoreCase(previousMonth)) {
            currentLines.add(data);
          }
          if (data[2].equalsIgnoreCase(currentMonth)) {
            previousLines.add(data);
          }
        }

        // Create the bar chart dataset
        ObservableList<XYChart.Data<String, Number>> currentData = FXCollections.observableArrayList();
        for (String[] lineData : currentLines) {
          currentData.add(new XYChart.Data<String, Number>(lineData[0], Double.parseDouble(lineData[1])));
        }
        XYChart.Series<String, Number> currentSeries = new XYChart.Series<String, Number>(currentData);
        currentSeries.setName(previousMonth);

        ObservableList<XYChart.Data<String, Number>> previousData = FXCollections.observableArrayList();
        for (String[] lineData : previousLines) {
          previousData.add(new XYChart.Data<String, Number>(lineData[0], Double.parseDouble(lineData[1])));
        }
        XYChart.Series<String, Number> previousSeries = new XYChart.Series<String, Number>(previousData);
        previousSeries.setName(currentMonth);

        // Create the bar chart
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Category");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Value");
        barChart = new BarChart<String, Number>(xAxis, yAxis);
        barChart.setTitle("Bar Chart");
        barChart.getData().addAll(currentSeries, previousSeries);
        budgetLayout.getChildren().clear();
        budgetLayout.getChildren().addAll(editMonthBudget, categorySelectorHbox, budgetAmountHbox, barChart);

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
    });

    String currentMonth = timeofdaychecker.getCurrentMonth();
    String previousMonth = timeofdaychecker.getPreviousMonth();

    // Read the CSV file
    String csvFile = ("src/main/resources/userfiles/" + GUI.getCurrentUser() + "/" + GUI.getCurrentUser() + "budget.csv");
    File file = new File(csvFile);
    if (!file.exists()) {
      try {
        file.createNewFile();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    BufferedReader br = null;
    String line = "";
    String csvSplitBy = ",";
    List<String[]> currentLines = new ArrayList<String[]>();
    List<String[]> previousLines = new ArrayList<String[]>();
    BarChart<String, Number> barChart = null;
    try {
      br = new BufferedReader(new FileReader(csvFile));
      while ((line = br.readLine()) != null) {
        String[] data = line.split(csvSplitBy);
        // Filter the data by the current month
        if (data[2].equalsIgnoreCase(previousMonth)) {
          currentLines.add(data);
        }
        if (data[2].equalsIgnoreCase(currentMonth)) {
          previousLines.add(data);
        }
      }

      // Create the bar chart dataset
      ObservableList<XYChart.Data<String, Number>> currentData = FXCollections.observableArrayList();
      for (String[] lineData : currentLines) {
        currentData.add(new XYChart.Data<String, Number>(lineData[0], Double.parseDouble(lineData[1])));
      }
      XYChart.Series<String, Number> currentSeries = new XYChart.Series<String, Number>(currentData);
      currentSeries.setName(previousMonth);

      ObservableList<XYChart.Data<String, Number>> previousData = FXCollections.observableArrayList();
      for (String[] lineData : previousLines) {
        previousData.add(new XYChart.Data<String, Number>(lineData[0], Double.parseDouble(lineData[1])));
      }
      XYChart.Series<String, Number> previousSeries = new XYChart.Series<String, Number>(previousData);
      previousSeries.setName(currentMonth);

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

    budgetAmountHbox.getChildren().addAll(budgetAmountField, confirmAmount);
    budgetAmountHbox.setAlignment(Pos.CENTER);
    budgetAmountHbox.setSpacing(20);

    budgetLayout.getChildren().addAll(editMonthBudget, categorySelectorHbox, budgetAmountHbox, barChart);
    budgetLayout.setAlignment(Pos.TOP_CENTER);

    return budgetLayout;
  }


}
