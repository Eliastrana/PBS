package edu.ntnu.idatt1002.frontend.utility;

import edu.ntnu.idatt1002.frontend.menu.Budget;

/**
 * A class that calculates the total budget.
 */
public class BudgetCalculator {
    /**
     * The total budget of a user.
     */
    private static double totalBudget;

    /**
     * Returns the total budget of a user.
     *
     * @return the total budget of a user
     */
    public static double getTotalBudget() {
        for (String[] lineData : Budget.currentBudget) {
            totalBudget = 0;
            totalBudget += Double.parseDouble(lineData[1]);
        }
        return totalBudget;
    }
}
