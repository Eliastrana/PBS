package edu.ntnu.idatt1002;

import java.util.ArrayList;

public class Incomes {
    public static ArrayList<Income> incomes;
    public static ArrayList<Income> allIncomes;

    public static void createIncomes(){
        incomes = new ArrayList<Income>();
    }

    public static ArrayList<Income> createAllIncomes(){
        allIncomes = new ArrayList<Income>();
        allIncomes.addAll(incomes);
        return allIncomes;
    }

    public static void addToArrayList(Income income, ArrayList<Income> aList){
        aList.add(income);
    }
}
