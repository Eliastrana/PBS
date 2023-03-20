package edu.ntnu.idatt1002.frontend.utility;

import edu.ntnu.idatt1002.backend.Expenses;
import edu.ntnu.idatt1002.frontend.GUI;
import edu.ntnu.idatt1002.model.ExcelExporter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Map;

import static edu.ntnu.idatt1002.backend.Accounts.accounts;
import static edu.ntnu.idatt1002.backend.Accounts.getTotalOfAccount;
import static edu.ntnu.idatt1002.backend.Expenses.*;
import static edu.ntnu.idatt1002.model.ExcelExporter.expensesToTable;
import static edu.ntnu.idatt1002.model.ExcelExporter.getTotalOfRent;
import static edu.ntnu.idatt1002.model.ExcelExporter.getTotalOfTransportation;
import static edu.ntnu.idatt1002.model.ExcelExporter.getTotalOfOther;
import static edu.ntnu.idatt1002.model.ExcelExporter.getTotalOfClothing;
import static edu.ntnu.idatt1002.model.ExcelExporter.getTotalOfEntertainment;
import static edu.ntnu.idatt1002.model.ExcelExporter.getTotalOfFood;

public class PieChart {

    GUI gui = new GUI();


    private Stage primaryStage;

    public PieChart() {
    }

    public static ObservableList<javafx.scene.chart.PieChart.Data> createData(){
        ObservableList<javafx.scene.chart.PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (Map.Entry<String, Double> entry : accounts.entrySet()) {
            pieChartData.add(new javafx.scene.chart.PieChart.Data(entry.getKey() + ": \n" + getTotalOfAccount(entry.getKey()), entry.getValue()));
        }
        return pieChartData;
    }

    public static ObservableList<javafx.scene.chart.PieChart.Data> createData2() {

        return FXCollections.observableArrayList(
                new javafx.scene.chart.PieChart.Data("Rent: " +"\n"+ getTotalOfRent(expensesToTable) , ExcelExporter.getTotalOfRent(expensesToTable)),
                new javafx.scene.chart.PieChart.Data("Transportation: " +"\n"+ getTotalOfTransportation(expensesToTable), ExcelExporter.getTotalOfTransportation(expensesToTable)),
                new javafx.scene.chart.PieChart.Data("Clothing: " +"\n"+ getTotalOfClothing(expensesToTable), ExcelExporter.getTotalOfClothing(expensesToTable)),
                new javafx.scene.chart.PieChart.Data("Entertainment: " +"\n"+ getTotalOfEntertainment(expensesToTable), ExcelExporter.getTotalOfEntertainment(expensesToTable)),
                new javafx.scene.chart.PieChart.Data("Food: " +"\n"+ getTotalOfFood(expensesToTable), ExcelExporter.getTotalOfFood(expensesToTable)),
                new javafx.scene.chart.PieChart.Data("Other: " +"\n"+ getTotalOfOther(expensesToTable), ExcelExporter.getTotalOfOther(expensesToTable)));


    }

    // New method for changing the color of the pie chart wheel
//    public static void changePieChartColor(javafx.scene.chart.PieChart chart, Color color) {
//        ObservableList<javafx.scene.chart.PieChart.Data> pieChartData = chart.getData();
//        for (javafx.scene.chart.PieChart.Data data : pieChartData) {
//            data.getNode().setStyle("-fx-pie-color: " + color.toString().replace("0x", "#") + ";");
//        }
//    }


}
