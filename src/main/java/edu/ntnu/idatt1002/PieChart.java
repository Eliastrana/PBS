package edu.ntnu.idatt1002;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import static edu.ntnu.idatt1002.testdata.*;

public class PieChart {

    GUI gui = new GUI();


    private Stage primaryStage;

    public static ObservableList<javafx.scene.chart.PieChart.Data> createData(){
        ObservableList<javafx.scene.chart.PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (Map.Entry<String, Double> entry : accounts.entrySet()) {
            pieChartData.add(new javafx.scene.chart.PieChart.Data(entry.getKey(), entry.getValue()));
        }
        return pieChartData;
    }

    public static ObservableList<javafx.scene.chart.PieChart.Data> createData2() {
        return FXCollections.observableArrayList(
                new javafx.scene.chart.PieChart.Data("Rent" , testdata.getTotalExpenses(rent)),
                new javafx.scene.chart.PieChart.Data("Transportation", testdata.getTotalExpenses(transportation)),
                new javafx.scene.chart.PieChart.Data("Cava", 50),
                new javafx.scene.chart.PieChart.Data("Clothing", testdata.getTotalExpenses(clothing)),
                new javafx.scene.chart.PieChart.Data("Entertainment", testdata.getTotalExpenses(entertainment)),
                new javafx.scene.chart.PieChart.Data("Food", testdata.getTotalExpenses(food)),
                new javafx.scene.chart.PieChart.Data("Other", testdata.getTotalExpenses(other)));

    }

    // New method for changing the color of the pie chart wheel
//    public static void changePieChartColor(javafx.scene.chart.PieChart chart, Color color) {
//        ObservableList<javafx.scene.chart.PieChart.Data> pieChartData = chart.getData();
//        for (javafx.scene.chart.PieChart.Data data : pieChartData) {
//            data.getNode().setStyle("-fx-pie-color: " + color.toString().replace("0x", "#") + ";");
//        }
//    }


}
