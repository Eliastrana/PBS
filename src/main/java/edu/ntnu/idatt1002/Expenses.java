package edu.ntnu.idatt1002;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Expenses {
    public static ArrayList<Expense> transportation;

    public static ArrayList<Expense> entertainment;

    public static ArrayList<Expense> clothing;

    public static ArrayList<Expense> rent;

    public static ArrayList<Expense> other;

    public static ArrayList<Expense> food;

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
}
