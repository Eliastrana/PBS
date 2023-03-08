package edu.ntnu.idatt1002;

import edu.ntnu.idatt1002.backend.Accounts;
import edu.ntnu.idatt1002.backend.Expenses;
import edu.ntnu.idatt1002.backend.Incomes;
import edu.ntnu.idatt1002.frontend.GUI;
import javafx.application.Application;
import javafx.stage.Stage;



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

        Accounts.createAccountsHashmap();
        Accounts.addAccount("Card", 15000);
        Accounts.addAccount("Savings", 10000);
        Accounts.addAccount("Checkings", 10000);



        Expenses.createAllAlist();
        Expenses.createAllExpenses();
        Incomes.createAllIncomes();

        //testdata.createHashmap();
        //hei
        //testdata.addAccount("test", 100);
        //This launches the start method, that again opens the start-page in the GUI class
        //testdata.addAccount("test", 100);
        launch(args);
    }


}