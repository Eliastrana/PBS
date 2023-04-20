//BUDGET

package edu.ntnu.idatt1002.frontend.menu;

import edu.ntnu.idatt1002.frontend.GUI;
import edu.ntnu.idatt1002.frontend.utility.SoundPlayer;
import edu.ntnu.idatt1002.frontend.utility.TimeOfDayChecker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A class that creates the budget view.
 *
 * @author Emil J., Vegard J., Sander S. & Elias T.
 * @version 0.5 - 19.04.2023
 */
public class Budget {
  /**
   * A list that contains the current budget.
   */
  public static List<String[]> currentBudget = new ArrayList<String[]>();

  /**
   * A method that creates the budget view.
   * The method is used by the GUI class.
   *
   * @return the vertical box
   */
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

    confirmAmount.setOnKeyPressed(event -> {
      if (event.getCode() == KeyCode.ENTER) {
        confirmAmount.fire();
      }
    });

    budgetAmountField.setOnKeyPressed(event -> {
      if (event.getCode() == KeyCode.ENTER) {
        confirmAmount.fire();
      }
    });

    confirmAmount.setOnAction(e -> {

      String category = categoryMenu.getValue().toString();
      String amount = budgetAmountField.getText();
      String month = TimeOfDayChecker.getCurrentMonth();

      String categorymonth = category + month;

      File csvFile = new File("src/main/resources/userfiles/" + GUI.getCurrentUser() + "/" + GUI.getCurrentUser() + "budget.csv");

      try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {

        ArrayList<String> lines = new ArrayList<String>();

        // Read the lines of the file into the list
        String line;

        while ((line = reader.readLine()) != null) {
          String oldCategoryMonth = line.split(",")[0] + line.split(",")[2];
          if (oldCategoryMonth.equals(categorymonth)) {
            continue;
          }
          lines.add(line);
        }

        reader.close();
        // Remove the old file
        System.out.println(csvFile.delete());

        // Create a new file and write the updated data to it
        FileWriter fw = new FileWriter(csvFile);
        BufferedWriter bw = new BufferedWriter(fw);

        // Write the lines to the new file
        for (String l : lines) {
          bw.write(l);
          bw.newLine();
        }

        // Write the updated line to the new file
        bw.write(category + "," + amount + "," + month);
        bw.newLine();
        bw.flush();


      } catch (IOException f) {
        System.err.println("Error writing to file: " + f.getMessage());
      }


      String currentMonth = TimeOfDayChecker.getCurrentMonth();
      String previousMonth = TimeOfDayChecker.getPreviousMonth();

      // Read the CSV file
      File file = new File("src/main/resources/userfiles/" + GUI.getCurrentUser() + "/" + GUI.getCurrentUser() + "budget.csv");
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
          if (data.length >= 3 && data[2].equalsIgnoreCase(currentMonth)) {
            currentLines.add(data);
          }
          if (data.length >= 3 && data[2].equalsIgnoreCase(previousMonth)) {
            previousLines.add(data);
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
        for (String[] lineData : previousLines) {
          previousData.add(new XYChart.Data<String, Number>(lineData[0], Double.parseDouble(lineData[1])));
        }
        XYChart.Series<String, Number> previousSeries = new XYChart.Series<String, Number>(previousData);
        previousSeries.setName(previousMonth);

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
      categoryMenu.setValue("Select Category");
      budgetAmountField.clear();
      SoundPlayer.play("src/main/resources/16bitconfirm.wav");

      GUI.setPaneToUpdate("overviewView");
      GUI.updatePane();
    });

    String currentMonth = TimeOfDayChecker.getCurrentMonth();
    String previousMonth = TimeOfDayChecker.getPreviousMonth();

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
        if (data.length >= 3 && data[2].equalsIgnoreCase(currentMonth)) {
          currentLines.add(data);
        }
        if (data.length >= 3 && data[2].equalsIgnoreCase(previousMonth)) {
          previousLines.add(data);
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
      for (String[] lineData : previousLines) {
        previousData.add(new XYChart.Data<String, Number>(lineData[0], Double.parseDouble(lineData[1])));
      }
      XYChart.Series<String, Number> previousSeries = new XYChart.Series<String, Number>(previousData);
      previousSeries.setName(previousMonth);

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

    currentBudget = currentLines;
    return budgetLayout;
  }
}
