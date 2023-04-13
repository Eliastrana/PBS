package edu.ntnu.idatt1002.frontend.utility;

import edu.ntnu.idatt1002.frontend.menu.Budget;

public class BudgetCalculator {
    private static double totalBudget;

    public static double getTotalBudget() {
        for (String[] lineData : Budget.currentBudget) {
            totalBudget = 0;
            totalBudget += Double.parseDouble(lineData[1]);
        }
        return totalBudget;
    }
}
