package edu.ntnu.idatt1002;

import java.util.ArrayList;

public class Incomes {
    protected static ArrayList<Income> incomes;
    protected static ArrayList<Income> allIncomes;

    public static void createIncomes(){
        incomes = new ArrayList<>();
    }

    public static ArrayList<Income> createAllIncomes(){
        allIncomes = new ArrayList<>();
        allIncomes.addAll(incomes);
        return allIncomes;
    }

    public static void addToArrayList(Income income, ArrayList<Income> aList){
        aList.add(income);
    }
}
