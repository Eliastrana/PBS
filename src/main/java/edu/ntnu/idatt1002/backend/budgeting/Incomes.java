package edu.ntnu.idatt1002.backend.budgeting;

import java.util.ArrayList;

/**
 * A class that represents a collection of incomes.
 * The collection of incomes is an ArrayList with the income name as key and the income amount as value.
 * Uses singleton pattern to avoid multiple instances of the class, and to ensure data encapsulation and integrity.
 * The class also has methods for creating an ArrayList of all the incomes, and for adding an income to an ArrayList of incomes.
 *
 * @author Emil J., Vegard J., Sander S. and Elias T.
 * @version 0.5 - 19.04.2023
 */
public class Incomes {
  /**
   * An ArrayList consisting of incomes.
   */
  public static ArrayList<Income> incomes;

  /**
   * An ArrayList of all the incomes.
   */
  public static ArrayList<Income> allIncomes;

  /**
   * The single instance of the class used in the singleton pattern.
   */
  private static final Incomes instance = new Incomes();

  /**
   * Private constructor to avoid multiple instances of the class.
   */
  public Incomes() {
  } // Singleton

  /**
   * Returns the single instance of the class.
   *
   * @return the single instance of the class.
   */
  public static Incomes getInstance() {
    return instance;
  }

  /**
   * Creates an ArrayList of incomes.
   */
  public void createIncomes() {
    incomes = new ArrayList<>();
  }

  /**
   * Returns the ArrayList of incomes.
   *
   * @return the ArrayList of incomes.
   */
  public ArrayList<Income> getIncomes() {
    return incomes;
  }

  /**
   * Creates an ArrayList of all the incomes.
   *
   * @return an ArrayList of all the incomes.
   */
  public ArrayList<Income> createAllIncomes() {
    allIncomes = new ArrayList<>();
    allIncomes.addAll(incomes);
    return allIncomes;
  }

  /**
   * Adds an income to an ArrayList of incomes.
   *
   * @param income the income to be added.
   * @param aList  the ArrayList to which the income is to be added.
   */
  public void addToArrayList(Income income, ArrayList<Income> aList) {
    if (income == null) {
      throw new NullPointerException("Income cannot be null");
    }
    if (aList == null) {
      throw new NullPointerException("List cannot be null");
    }
    aList.add(income);
  }
}