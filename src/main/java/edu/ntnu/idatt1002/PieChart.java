package edu.ntnu.idatt1002;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import static edu.ntnu.idatt1002.testdata.rent;

public class PieChart {


    static ObservableList<javafx.scene.chart.PieChart.Data> createData() {
        return FXCollections.observableArrayList(
                new javafx.scene.chart.PieChart.Data("Card", 2000),
                new javafx.scene.chart.PieChart.Data("Checkings",8000 ),
                new javafx.scene.chart.PieChart.Data("Savings", 26000));

    }

    //THE SECOND PIE
    public static ObservableList<javafx.scene.chart.PieChart.Data> createData2() {
        return FXCollections.observableArrayList(
                new javafx.scene.chart.PieChart.Data("Rent" + 1000 + " kr", 13),
                new javafx.scene.chart.PieChart.Data(prices.getItem(), 25),
                //new javafx.scene.chart.PieChart.Data("Transportation", testdata.getValue()),
                new javafx.scene.chart.PieChart.Data("Rent", testdata.getTotalExpenses(rent)),
                new javafx.scene.chart.PieChart.Data("Cava", 50),
                new javafx.scene.chart.PieChart.Data("Transportation", 22));


    }
}
