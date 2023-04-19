package edu.ntnu.idatt1002.backend;

import java.util.ArrayList;


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
    private static Incomes instance = new Incomes();

    /**
     * Private constructor to avoid multiple instances of the class.
     */
    public Incomes(){} // Singleton

    /**
     * Returns the single instance of the class.
     * @return the single instance of the class.
     */
    public static Incomes getInstance() {
        return instance;
    }

    /**
     * Creates an ArrayList of incomes.
     */
    public void createIncomes(){
        incomes = new ArrayList<>();
    }

    /**
     * Returns the ArrayList of incomes.
     * @return the ArrayList of incomes.
     */
    public ArrayList<Income> getIncomes(){
        return incomes;
    }

    /**
     * Creates an ArrayList of all the incomes.
     * @return an ArrayList of all the incomes.
     */
    public ArrayList<Income> createAllIncomes(){
        allIncomes = new ArrayList<>();
        allIncomes.addAll(incomes);
        return allIncomes;
    }

    /**
     * Adds an income to an ArrayList of incomes.
     * @param income the income to be added.
     * @param aList the ArrayList to which the income is to be added.
     */
    public void addToArrayList(Income income, ArrayList<Income> aList){
        if (income == null) {
            throw new NullPointerException("Income cannot be null");
        }
        if (aList == null) {
            throw new NullPointerException("List cannot be null");
        }
        aList.add(income);
    }
}