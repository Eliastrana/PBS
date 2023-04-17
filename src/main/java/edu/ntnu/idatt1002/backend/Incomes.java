package edu.ntnu.idatt1002.backend;

import edu.ntnu.idatt1002.backend.Income;

import java.util.ArrayList;

public class Incomes {
    public static ArrayList<Income> incomes;
    public static ArrayList<Income> allIncomes;

    public static void createIncomes(){
        incomes = new ArrayList<>();
    }

    public static ArrayList<Income> getIncomes(){
        return incomes;
    }

    public static ArrayList<Income> createAllIncomes(){
        allIncomes = new ArrayList<>();
        allIncomes.addAll(incomes);
        return allIncomes;
    }

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
