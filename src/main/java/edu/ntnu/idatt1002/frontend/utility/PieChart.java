package edu.ntnu.idatt1002.frontend.utility;

import edu.ntnu.idatt1002.model.CSVReader;
import edu.ntnu.idatt1002.model.ExcelExporter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import static edu.ntnu.idatt1002.backend.Accounts.accounts;
import static edu.ntnu.idatt1002.backend.Accounts.getTotalOfAccount;
import static edu.ntnu.idatt1002.model.ExcelExporter.*;

/**
 * A class that creates the pie chart.
 */
public class PieChart {

    /**
     * Creates the data for the left pie chart.
     *
     * @return the list of data
     */
    public static ObservableList<javafx.scene.chart.PieChart.Data> createData(){
        try {
            accounts = CSVReader.readCSV();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ObservableList<javafx.scene.chart.PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (Map.Entry<String, Double> entry : accounts.entrySet()) {
            pieChartData.add(new javafx.scene.chart.PieChart.Data(entry.getKey() + ": \n" + getTotalOfAccount(entry.getKey()), entry.getValue()));
        }
        return pieChartData;
    }

    /**
     * Creates the data for the right pie chart.
     *
     * @return the list of data
     */
    public static ObservableList<javafx.scene.chart.PieChart.Data> createData2() {

        return FXCollections.observableArrayList(
                new javafx.scene.chart.PieChart.Data("Rent: " +"\n"+ getTotalOfRent(expensesToTable) , ExcelExporter.getTotalOfRent(expensesToTable)),
                new javafx.scene.chart.PieChart.Data("Transportation: " +"\n"+ getTotalOfTransportation(expensesToTable), ExcelExporter.getTotalOfTransportation(expensesToTable)),
                new javafx.scene.chart.PieChart.Data("Clothing: " +"\n"+ getTotalOfClothing(expensesToTable), ExcelExporter.getTotalOfClothing(expensesToTable)),
                new javafx.scene.chart.PieChart.Data("Entertainment: " +"\n"+ getTotalOfEntertainment(expensesToTable), ExcelExporter.getTotalOfEntertainment(expensesToTable)),
                new javafx.scene.chart.PieChart.Data("Food: " +"\n"+ getTotalOfFood(expensesToTable), ExcelExporter.getTotalOfFood(expensesToTable)),
                new javafx.scene.chart.PieChart.Data("Other: " +"\n"+ getTotalOfOther(expensesToTable), ExcelExporter.getTotalOfOther(expensesToTable)));


    }
}
