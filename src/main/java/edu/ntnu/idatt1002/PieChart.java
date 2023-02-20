package edu.ntnu.idatt1002;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

    // THE SECOND PIE
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
}
