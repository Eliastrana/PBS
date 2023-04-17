
package edu.ntnu.idatt1002;

import edu.ntnu.idatt1002.backend.*;
import edu.ntnu.idatt1002.frontend.GUI;
import edu.ntnu.idatt1002.frontend.utility.timeofdaychecker;
import edu.ntnu.idatt1002.model.CSVReader;
import edu.ntnu.idatt1002.model.ExcelExporter;
import javafx.application.Application;
import javafx.stage.Stage;
import org.apache.commons.math3.analysis.function.Exp;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;


public class Main extends Application {


    @Override
    public void start(Stage stage) throws Exception {
        GUI gui = new GUI();
        gui.start(stage);
    }
    public static void main(String[] args)  {

        Expenses.createTransportation();
        Expenses.createRent();
        Expenses.createEntertainment();
        Expenses.createClothing();
        Expenses.createOther();
        Expenses.createFood();
        Incomes.createIncomes();

        Expenses.createAllAlist();
        Expenses.createAllExpenses();
        Incomes.createAllIncomes();


        String uniqueID = timeofdaychecker.getCurrentMonth() + timeofdaychecker.getYear();

        launch(args);
    }

}