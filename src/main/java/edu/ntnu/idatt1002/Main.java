package edu.ntnu.idatt1002;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.util.HashMap;
import javafx.scene.control.Button;

import javax.swing.*;

import static edu.ntnu.idatt1002.testdata.*;

public class Main extends Application {


    @Override
    public void start(Stage stage) throws Exception {
        GUI gui = new GUI();
        gui.start(stage);
    }
    //MAIN STRING
    //testdata.itemList();
    public static void main(String[] args) {
        testdata.createTransportation();
        testdata.createRent();
        testdata.addToArrayList(new Expense("awdwad", 15.0, 1), transportation);
        testdata.addToArrayList(new Expense("awdawdsw", 20.0, 1), transportation);
        testdata.addToArrayList(new Expense("awdlkasnem", 100, 1), rent);
        testdata.addToArrayList(new Expense("awdlkasnem", 50, 1), rent);
        testdata.createHashmap();

        launch(args);
    }


}