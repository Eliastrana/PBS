package edu.ntnu.idatt1002;

import javafx.application.Application;
import javafx.stage.Stage;
import static edu.ntnu.idatt1002.testdata.*;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        GUI gui = new GUI();
        gui.start(stage);
    }

    public static void main(String[] args) {
        testdata.createTransportation();
        testdata.createRent();
        testdata.createEntertainment();
        testdata.createClothing();
        testdata.createOther();
        testdata.createFood();

        testdata.createAccountsHashmap();
        testdata.addAccount("Sparekonto", 15000);
        testdata.addAccount("Reisekonto", 10000);

        testdata.addToArrayList(new Expense("awdwad", 15.0, 1), transportation);
        testdata.addToArrayList(new Expense("awdawdsw", 20.0, 1), transportation);
        testdata.addToArrayList(new Expense("awdlkasnem", 100, 1), rent);
        testdata.addToArrayList(new Expense("awdlkasnem", 50, 1), rent);

        //testdata.createHashmap();
        //hei
        //testdata.addAccount("test", 100);
        //This launches the start method, that again opens the start-page in the GUI class
        launch(args);
    }


}