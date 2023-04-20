package edu.ntnu.idatt1002.frontend.utility;

import edu.ntnu.idatt1002.frontend.GUI;
import edu.ntnu.idatt1002.frontend.menu.Budget;

import java.io.File;

/**
 * A class that calculates the total budget.
 */
public class BudgetCalculator {
    private static BudgetCalculator instance = new BudgetCalculator();

    private BudgetCalculator() {
    }

    public static BudgetCalculator getInstance() {
        return instance;
    }
    /**
     * The total budget of a user.
     */
    private double totalBudget;

    /**
     * Returns the total budget of a user.
     *
     * @return the total budget of a user
     */
    public double getTotalBudget() {
        String outputDirectory = "src/main/resources/userfiles/" + GUI.getCurrentUser() + "/";
        String budgetFile = outputDirectory + "budget.csv";
        totalBudget = 0; // Move initialization outside the loop
        for (String[] lineData : Budget.currentBudget) {
            try {
                totalBudget += Double.parseDouble(lineData[1]); // Convert budget value to double
            } catch (NumberFormatException e) {
                // Handle the case where lineData[1] is not a valid double
                // You can log an error, display a message, or take other appropriate action
                e.printStackTrace(); // Example of printing the stack trace
            }
        }
        return totalBudget;
    }
}
