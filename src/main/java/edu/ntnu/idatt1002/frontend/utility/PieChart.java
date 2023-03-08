package edu.ntnu.idatt1002.frontend.utility;

import edu.ntnu.idatt1002.backend.Expenses;
import edu.ntnu.idatt1002.frontend.GUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

import java.util.Map;

import static edu.ntnu.idatt1002.backend.Accounts.accounts;
import static edu.ntnu.idatt1002.backend.Accounts.getTotalOfAccount;
import static edu.ntnu.idatt1002.backend.Expenses.*;

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
                new javafx.scene.chart.PieChart.Data("Rent: " +"\n"+ getTotalExpenses(rent) , Expenses.getTotalExpenses(rent)),
                new javafx.scene.chart.PieChart.Data("Transportation: " +"\n"+ getTotalExpenses(transportation), Expenses.getTotalExpenses(transportation)),
                new javafx.scene.chart.PieChart.Data("Clothing: " +"\n"+ getTotalExpenses(clothing), Expenses.getTotalExpenses(clothing)),
                new javafx.scene.chart.PieChart.Data("Entertainment: " +"\n"+ getTotalExpenses(entertainment), Expenses.getTotalExpenses(entertainment)),
                new javafx.scene.chart.PieChart.Data("Food: " +"\n"+ getTotalExpenses(food), Expenses.getTotalExpenses(food)),
                new javafx.scene.chart.PieChart.Data("Other: " +"\n"+ getTotalExpenses(other), Expenses.getTotalExpenses(other)));

    }

    // New method for changing the color of the pie chart wheel
//    public static void changePieChartColor(javafx.scene.chart.PieChart chart, Color color) {
//        ObservableList<javafx.scene.chart.PieChart.Data> pieChartData = chart.getData();
//        for (javafx.scene.chart.PieChart.Data data : pieChartData) {
//            data.getNode().setStyle("-fx-pie-color: " + color.toString().replace("0x", "#") + ";");
//        }
//    }


}
