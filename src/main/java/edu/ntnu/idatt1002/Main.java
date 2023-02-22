package edu.ntnu.idatt1002;

import javafx.application.Application;
import javafx.stage.Stage;
import static edu.ntnu.idatt1002.Expenses.*;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        GUI gui = new GUI();
        gui.start(stage);


    }

    public static void main(String[] args) {
        Expenses.createTransportation();
        Expenses.createRent();
        Expenses.createEntertainment();
        Expenses.createClothing();
        Expenses.createOther();
        Expenses.createFood();

        Accounts.createAccountsHashmap();
        Accounts.addAccount("Sparekonto", 15000);
        Accounts.addAccount("Reisekonto", 10000);
        Accounts.transferBetweenAccounts("Sparekonto", "Reisekonto", 5000);

        Expenses.addToArrayList(new Expense("awdwad", 15.0, 1), transportation);
        Expenses.addToArrayList(new Expense("awdawdsw", 20.0, 1), transportation);
        Expenses.addToArrayList(new Expense("awdlkasnem", 100, 1), rent);
        Expenses.addToArrayList(new Expense("awdlkasnem", 50, 1), rent);

        //testdata.createHashmap();
        //hei
        //testdata.addAccount("test", 100);
        //This launches the start method, that again opens the start-page in the GUI class
        //testdata.addAccount("test", 100);
        launch(args);
    }


}