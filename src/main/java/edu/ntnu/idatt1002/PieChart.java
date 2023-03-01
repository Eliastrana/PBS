package edu.ntnu.idatt1002;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import static edu.ntnu.idatt1002.Accounts.accounts;
import static edu.ntnu.idatt1002.Accounts.getTotalOfAccount;
import static edu.ntnu.idatt1002.Expenses.*;

public class PieChart {

    GUI gui = new GUI();


    private Stage primaryStage;

    public static ObservableList<javafx.scene.chart.PieChart.Data> createData(){
        ObservableList<javafx.scene.chart.PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (Map.Entry<String, Double> entry : accounts.entrySet()) {
            pieChartData.add(new javafx.scene.chart.PieChart.Data(entry.getKey() + ": \n" + getTotalOfAccount(entry.getKey()), entry.getValue()));
        }
        return pieChartData;
    }

    public static ObservableList<javafx.scene.chart.PieChart.Data> createData2() {
        return FXCollections.observableArrayList(
                new javafx.scene.chart.PieChart.Data("Rent: " + getTotalExpenses(rent) , Expenses.getTotalExpenses(rent)),
                new javafx.scene.chart.PieChart.Data("Transportation: " + getTotalExpenses(transportation), Expenses.getTotalExpenses(transportation)),
                new javafx.scene.chart.PieChart.Data("Clothing: " + getTotalExpenses(clothing), Expenses.getTotalExpenses(clothing)),
                new javafx.scene.chart.PieChart.Data("Entertainment: " + getTotalExpenses(entertainment), Expenses.getTotalExpenses(entertainment)),
                new javafx.scene.chart.PieChart.Data("Food: " + getTotalExpenses(food), Expenses.getTotalExpenses(food)),
                new javafx.scene.chart.PieChart.Data("Other: " + getTotalExpenses(other), Expenses.getTotalExpenses(other)));

    }

    // New method for changing the color of the pie chart wheel
//    public static void changePieChartColor(javafx.scene.chart.PieChart chart, Color color) {
//        ObservableList<javafx.scene.chart.PieChart.Data> pieChartData = chart.getData();
//        for (javafx.scene.chart.PieChart.Data data : pieChartData) {
//            data.getNode().setStyle("-fx-pie-color: " + color.toString().replace("0x", "#") + ";");
//        }
//    }


}
