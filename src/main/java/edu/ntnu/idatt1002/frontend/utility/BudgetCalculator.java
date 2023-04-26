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

  /**
   * Gets instance.
   *
   * @return the instance
   */
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
    totalBudget = 0;
    for (String[] lineData : Budget.currentBudget) {
      try {
        totalBudget += Double.parseDouble(lineData[1]);
      } catch (NumberFormatException e) {
        throw new RuntimeException("Could not parse double from " + lineData[1]);
      }
    }
    return totalBudget;
  }
}
