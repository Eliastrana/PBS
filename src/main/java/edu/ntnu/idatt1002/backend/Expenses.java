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
     * An ArrayList with all the arraylists of expenses.
     */
    public static ArrayList<ArrayList<Expense>> allLists;
    /**
     * An ArrayList with all the expenses.
     */
    public static ArrayList<Expense> allExpenses;

    /**
     * Create the arraylists of expenses in the category transportation.
     */
    public static void createTransportation(){
        transportation = new ArrayList<Expense>();
    }

    /**
     * Create the arraylists of expenses in the category entertainment.
     */
    public static void createEntertainment(){
        entertainment = new ArrayList<Expense>();
    }

    /**
     * Create the arraylists of expenses in the category clothing.
     */
    public static void createClothing(){
        clothing = new ArrayList<Expense>();
    }

    /**
     * Create the arraylists of expenses in the category other.
     */
    public static void createOther(){
        other = new ArrayList<Expense>();
    }

    /**
     * Create the arraylists of expenses in the category food.
     */
    public static void createFood(){
        food = new ArrayList<Expense>();
    }

    /**
     * Create the arraylists of expenses in the category rent.
     */
    public static void createRent(){
        rent = new ArrayList<Expense>();
    }

    /**
     * Create the arraylists of all expenses.
     *
     * @return the array list of all expenses
     */
    public static ArrayList<Expense> createAllExpenses(){
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
     * Create the arraylists of all arraylists of expenses.
     */
    public static void createAllAlist(){
        allLists = new ArrayList<>();
        allLists.add(rent);
        allLists.add(transportation);
        allLists.add(clothing);
        allLists.add(other);
        allLists.add(food);
        allLists.add(entertainment);
    }

    /**
     * Adds an expense to an arraylist.
     *
     * @param expense the expense
     * @param aList   the arraylist
     */
    public static void addToArrayList(Expense expense, ArrayList<Expense> aList) {
        if (expense == null) {
            throw new IllegalArgumentException("Expense cannot be null");
        }
        if (aList == null) {
            throw new IllegalArgumentException("ArrayList cannot be null");
        }


        aList.add(expense);
    }

    /**
     * Returns the total expenses in an arraylist.
     *
     * @param aList the arraylist
     * @return the total expenses
     */
    public static double getTotalExpenses(ArrayList<Expense> aList){
        double totalExpense = 0;
        for (int i = 0; i < aList.size(); i++){
            totalExpense += aList.get(i).getPrice();
        }
        return totalExpense;
    }

    /**
     * Returns the total expenses of all categories.
     *
     * @return the total expenses of all categories
     */
    public static double getExpensesOfAllCategories(){
        double totalExpense = 0;
        totalExpense += (getTotalExpenses(transportation) + getTotalExpenses(rent) + getTotalExpenses(food) + getTotalExpenses(other) + getTotalExpenses(entertainment) + getTotalExpenses(clothing));
        return totalExpense;
    }

    /**
     * Returns the arraylist of all expenses.
     *
     * @return the arraylist of all expenses
     */
    public static ArrayList<Expense> getExpenses() {
        return allExpenses;
    }
}
