package edu.ntnu.idatt1002.frontend.utility;

import edu.ntnu.idatt1002.frontend.GUI;
import edu.ntnu.idatt1002.frontend.menu.Budget;

/**
 * A class that calculates the total budget.
 *
 * @author Emil J., Vegard J., Sander S. and Elias T.
 * @version 0.5 - 19.04.2023
 */
public class BudgetCalculator {
  private static final BudgetCalculator instance = new BudgetCalculator();
  /**
   * The total budget of a user.
   */
  private double totalBudget;

  private BudgetCalculator() {
  }

  public static BudgetCalculator getInstance() {
    return instance;
  }

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
