
package edu.ntnu.idatt1002;

import edu.ntnu.idatt1002.backend.Expenses;
import edu.ntnu.idatt1002.backend.Incomes;
import edu.ntnu.idatt1002.frontend.GUI;
import edu.ntnu.idatt1002.frontend.utility.timeofdaychecker;
import javafx.application.Application;
import javafx.stage.Stage;


/**
 * A class that starts the GUI.
 */
public class Main extends Application {


    @Override
    public void start(Stage stage) throws Exception {
        GUI gui = new GUI();
        gui.start(stage);
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
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