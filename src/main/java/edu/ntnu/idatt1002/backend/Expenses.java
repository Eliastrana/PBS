package edu.ntnu.idatt1002.backend;
import edu.ntnu.idatt1002.backend.Expense;

import java.util.ArrayList;

public class Expenses {
    public static ArrayList<Expense> transportation;

    public static ArrayList<Expense> entertainment;

    public static ArrayList<Expense> clothing;

    public static ArrayList<Expense> rent;

    public static ArrayList<Expense> other;

    public static ArrayList<Expense> food;
    public static ArrayList<ArrayList> allLists;
    public static ArrayList<Expense> allExpenses;

    public static void createTransportation(){
        transportation = new ArrayList<Expense>();
    }

    public static void createEntertainment(){
        entertainment = new ArrayList<Expense>();
    }

    public static void createClothing(){
        clothing = new ArrayList<Expense>();
    }

    public static void createOther(){
        other = new ArrayList<Expense>();
    }

    public static void createFood(){
        food = new ArrayList<Expense>();
    }

    public static void createRent(){
        rent = new ArrayList<Expense>();
    }

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

    public static void createAllAlist(){
        allLists = new ArrayList<ArrayList>();
        allLists.add(rent);
        allLists.add(transportation);
        allLists.add(clothing);
        allLists.add(other);
        allLists.add(food);
        allLists.add(entertainment);
    }

    public static void addToArrayList(Expense expense, ArrayList<Expense> aList){
        aList.add(expense);
    }

    public static double getTotalExpenses(ArrayList<Expense> aList){
        double totalExpense = 0;
        for (int i = 0; i < aList.size(); i++){
            totalExpense += aList.get(i).getPrice();
        }
        return totalExpense;
    }
    public static double getExpensesOfAllCategories(){
        double totalExpense = 0;
        totalExpense += (getTotalExpenses(transportation) + getTotalExpenses(rent) + getTotalExpenses(food) + getTotalExpenses(other) + getTotalExpenses(entertainment) + getTotalExpenses(clothing));
        return totalExpense;
    }

    public static ArrayList<Expense> getExpenses() {
        return allExpenses;
    }
}
