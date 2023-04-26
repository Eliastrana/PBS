package edu.ntnu.idatt1002.frontend.utility;

import edu.ntnu.idatt1002.backend.budgeting.Accounts;
import edu.ntnu.idatt1002.model.CSVReader;
import edu.ntnu.idatt1002.model.ExcelExporter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static edu.ntnu.idatt1002.model.ExcelExporter.expensesToTable;

/**
 * A class that creates the pie chart.
 *
 * @author Emil J., Vegard J., Sander S. and Elias T.
 * @version 0.5 - 19.04.2023
 */
public class CustomPieChart {
  /**
   * Creates the data for the left pie chart.
   *
   * @return the list of data
   */
  public static ObservableList<javafx.scene.chart.PieChart.Data> createData() {
    HashMap<String, Double> accountsHashmap;
    try {
      CSVReader CSVReaderInstance = CSVReader.getInstance();
      Accounts accountsInstance = Accounts.getInstance();

      accountsInstance.setAccountsHashmap(CSVReaderInstance.readCSV());


    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    Accounts accountsInstance = Accounts.getInstance();
    ObservableList<javafx.scene.chart.PieChart.Data> pieChartData =
            FXCollections.observableArrayList();
    for (Map.Entry<String, Double> entry : accountsInstance.getAccounts()
            .entrySet()) {
      pieChartData.add(new javafx.scene.chart.PieChart.Data(
              entry.getKey() + ": \n" + accountsInstance
                      .getTotalOfAccount(entry.getKey()), entry.getValue()));
    }
    return pieChartData;
  }

  /**
   * Creates the data for the right pie chart.
   *
   * @return the list of data
   */
  public static ObservableList<javafx.scene.chart.PieChart.Data> createData2() {
    ExcelExporter instance = ExcelExporter.getInstance();
    return FXCollections.observableArrayList(new javafx.scene.chart.PieChart.Data(
                    "Rent: " + "\n" + instance.getTotalOfRent(expensesToTable),
                    instance.getTotalOfRent(expensesToTable)),
            new javafx.scene.chart.PieChart.Data(
                    "Transportation: " + "\n" + instance.getTotalOfTransportation(expensesToTable),
                    instance.getTotalOfTransportation(expensesToTable)),
            new javafx.scene.chart.PieChart.Data(
                    "Clothing: " + "\n" + instance.getTotalOfClothing(expensesToTable),
                    instance.getTotalOfClothing(expensesToTable)),
            new javafx.scene.chart.PieChart.Data(
                    "Entertainment: " + "\n" + instance.getTotalOfEntertainment(expensesToTable),
                    instance.getTotalOfEntertainment(expensesToTable)),
            new javafx.scene.chart.PieChart.Data(
                    "Food: " + "\n" + instance.getTotalOfFood(expensesToTable),
                    instance.getTotalOfFood(expensesToTable)),
            new javafx.scene.chart.PieChart.Data("Other: "
                    + "\n" + instance.getTotalOfOther(expensesToTable),
                    instance.getTotalOfOther(expensesToTable)));
  }
}
