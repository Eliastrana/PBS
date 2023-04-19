package edu.ntnu.idatt1002.backend;

import java.util.ArrayList;

/**
 * A class that represents a collection of incomes.
 * The collection of incomes is an ArrayList.
 *
 * @author Emil J., Vegard J., Sander S. & Elias T.
 * @version 0.5 - 19.04.2023
 */
public class Incomes {
    /**
     * A list of incomes.
     */
    public static ArrayList<Income> incomes;
    /**
     * A list of all incomes.
     */
    public static ArrayList<Income> allIncomes;

    /**
     * Create the incomes array list.
     */
    public static void createIncomes(){
        incomes = new ArrayList<>();
    }

    /**
     * Returns the incomes array list.
     *
     * @return the incomes array list
     */
    public static ArrayList<Income> getIncomes(){
        return incomes;
    }

    /**
     * Create all incomes array list.
     *
     * @return the array list
     */
    public static ArrayList<Income> createAllIncomes(){
        allIncomes = new ArrayList<>();
        allIncomes.addAll(incomes);
        return allIncomes;
    }

    /**
     * Adds an income to the incomes array list.
     *
     * @param income the income
     * @param aList  the arraylist
     */
    public static void addToArrayList(Income income, ArrayList<Income> aList){
        if (income == null) {
            throw new NullPointerException("Income cannot be null");
        }
        if (aList == null) {
            throw new NullPointerException("List cannot be null");
        }
        aList.add(income);
    }
}
