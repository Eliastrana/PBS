package edu.ntnu.idatt1002;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class testdata {
    public static ArrayList<Expense> transportation;

    public static ArrayList<Expense> entertainment;
    public static ArrayList<Expense> clothing;

    public static ArrayList<Expense> rent;

    public static ArrayList<Expense> other;

    public static ArrayList<Expense> food;



    public static HashMap<String, Double> accounts;

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

    public static void createAccountsHashmap(){
        accounts = new HashMap<String, Double>();
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

    public static void addAccount(String accountName, double accountBalance){
        Account newAccount = new Account(accountName, accountBalance);
        accounts.put(newAccount.getAccountName(), newAccount.getAccountBalance());
    }

    public static HashMap<String, Double> getAccounts() {
        return accounts;
    }

//    public static double getValue() {
//        Iterator<Map.Entry<String, Double>> iterator = testHMap.entrySet().iterator();
//        Double Value = 0.0;
//        if (iterator.hasNext()) {
//            Map.Entry<String, Double> firstEntry = iterator.next();
//            Value = firstEntry.getValue();
//        }
//        return 0.0;
//    }

//    public static String getKey() {
//        Iterator<Map.Entry<String, Double>> iterator = testHMap.entrySet().iterator();
//        String key = "";
//        if (iterator.hasNext()) {
//            Map.Entry<String, Double> firstEntry = iterator.next();
//            key = String.valueOf(firstEntry.getValue());
//        }
//        return key;
//    }

    public static String getItem(){
        return "Item: " + prices.getName() + prices.getPrice();
    }

    public static void main(String[] args) {
       //createTestArrayList();
       //addToTransportation(new Expense("awdwad", 15.0, 1));
       //addToTransportation(new Expense("awdawdsw", 20.0, 1));
        // System.out.println(getTotalExpenses(transportation));
    }
}
