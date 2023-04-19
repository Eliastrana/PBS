package edu.ntnu.idatt1002.backend;

import java.util.ArrayList;

/**
 * A class that represents a collection of expenses.
 * The collection of expenses is a HashMap with the expense name as key and the expense amount as value.
 *
 * @author Emil J., Vegard J., Sander S. & Elias T.
 * @version 0.5 - 19.04.2023
 */
public class Expenses {
    /**
     * An ArrayList with all the expenses in the transportation category.
     */
    public static ArrayList<Expense> transportation;

    /**
     * An ArrayList with all the expenses in the entertainment category.
     */
    public static ArrayList<Expense> entertainment;

    /**
     * An ArrayList with all the expenses in the clothing category.
     */
    public static ArrayList<Expense> clothing;

    /**
     * An ArrayList with all the expenses in the rent category.
     */
    public static ArrayList<Expense> rent;

    /**
     * An ArrayList with all the expenses in the other category.
     */
    public static ArrayList<Expense> other;

    /**
     * An ArrayList with all the expenses in the food category.
     */
    public static ArrayList<Expense> food;

    /**
     * An ArrayList with all the expenses in all categories.
     */
    public static ArrayList<ArrayList<Expense>> allLists;

    /**
     * An ArrayList with all the expenses in all categories.
     */
    public static ArrayList<Expense> allExpenses;

    /**
     * The single instance of the class used in the singleton pattern.
     */
    private static Expenses instance = new Expenses();

    /**
     * Private constructor to avoid multiple instances of the class.
     */
    private Expenses(){} // Singleton

    /**
     * Returns the single instance of the class.
     * @return the single instance of the class.
     */
    public static Expenses getInstance(){
        return instance;
    }

    /**
     * Creates the ArrayLists for the transportation category.
     */
    public void createTransportation(){
        transportation = new ArrayList<Expense>();
    }

    /**
     * Creates the ArrayLists for the entertainment category.
     */
    public void createEntertainment(){
        entertainment = new ArrayList<Expense>();
    }

    /**
     * Creates the ArrayLists for the clothing category.
     */
    public void createClothing(){
        clothing = new ArrayList<Expense>();
    }

    /**
     * Creates the ArrayLists for the other category.
     */
    public void createOther(){
        other = new ArrayList<Expense>();
    }

    /**
     * Creates the ArrayLists for the food category.
     */
    public void createFood(){
        food = new ArrayList<Expense>();
    }

    /**
     * Creates the ArrayLists for the rent category.
     */
    public void createRent(){
        rent = new ArrayList<Expense>();
    }

    /**
     * Creates the ArrayLists for all categories.
     */
    public ArrayList<Expense> createAllExpenses(){
        allExpenses = new ArrayList<Expense>();
        allExpenses.addAll(transportation);
        allExpenses.addAll(entertainment);
        allExpenses.addAll(clothing);
        allExpenses.addAll(other);
        allExpenses.addAll(food);
        allExpenses.addAll(rent);
        return allExpenses;
    }

    /**
     * Creates the adds the arraylists to the arraylist for all arraylists.
     */
    public void createAllAlist(){
        allLists = new ArrayList<>();
        allLists.add(rent);
        allLists.add(transportation);
        allLists.add(clothing);
        allLists.add(other);
        allLists.add(food);
        allLists.add(entertainment);
    }

    /**
     * Adds an expense to the ArrayList.
     * @param expense the expense to be added.
     * @param aList the ArrayList to add the expense to.
     */
    public void addToArrayList(Expense expense, ArrayList<Expense> aList) {
        if (expense == null) {
            throw new IllegalArgumentException("Expense cannot be null");
        }
        if (aList == null) {
            throw new IllegalArgumentException("ArrayList cannot be null");
        }
        aList.add(expense);
    }

    /**
     * Gets the total expenses of a category.
     * @param aList the ArrayList to get the total expenses from.
     * @return the total expenses of a category.
     */
    public double getTotalExpenses(ArrayList<Expense> aList){
        double totalExpense = 0;
        for (int i = 0; i < aList.size(); i++){
            totalExpense += aList.get(i).getPrice();
        }
        return totalExpense;
    }

    /**
     * Gets the total expenses of all categories.
     * @return the total expenses of all categories.
     */
    public double getExpensesOfAllCategories(){
        double totalExpense = 0;
        totalExpense += (getTotalExpenses(transportation) + getTotalExpenses(rent) + getTotalExpenses(food) + getTotalExpenses(other) + getTotalExpenses(entertainment) + getTotalExpenses(clothing));
        return totalExpense;
    }

    /**
     * Gets the ArrayList with all the expenses in the transportation category.
     * @return the ArrayList with all the expenses in the transportation category.
     */
    public ArrayList<Expense> getExpenses() {
        return allExpenses;
    }
}