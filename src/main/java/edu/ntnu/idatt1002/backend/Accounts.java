package edu.ntnu.idatt1002.backend;

import java.util.HashMap;

public class Accounts {
    public static  HashMap<String, Double> accounts;

    public static void createAccountsHashmap(){
        accounts = new HashMap<>();
    }

    public static void addAccount(String accountName, double accountBalance){
        if (accountName == null || accountName.isBlank()){
            throw new IllegalArgumentException("Account name cannot be empty");
        }
        if (accountBalance < 0){
            throw new IllegalArgumentException("Account balance cannot be negative");
        }

        Account newAccount = new Account(accountName, accountBalance);
        accounts.put(newAccount.getAccountName(), newAccount.getAccountBalance());
    }

    public static void transferBetweenAccounts(String removeFromAccount, String addToAccount, double amount){
        if (accounts.get(removeFromAccount)-amount < 0){
            amount = 0;
            System.out.println("Invalid input");
        }
        accounts.put(removeFromAccount, accounts.get(removeFromAccount)-amount);
        accounts.put(addToAccount, accounts.get(addToAccount)+amount);
    }

    public static void addToAccount(String accountName, double amount){
        if (amount < 0){
            amount = 0;
            System.out.println("Invalid input");
        }
        accounts.put(accountName, accounts.get(accountName)+amount);
    }
    public static double getTotalOfAccount(String accountName){
        return accounts.get(accountName);
    }
    public static double getTotalOfAllAccounts(){
        double total = 0;
        for (Double value : accounts.values()) {
            total += value;
        }
        return total;
    }

    public static void addExpenseToAccount(Expense expense, String accountName){
        Account.expenses.add(expense);
        accounts.put(accountName, accounts.get(accountName)-expense.getPrice());
    }

    public static HashMap<String, Double> getAccounts() {
        return accounts;
    }


}
