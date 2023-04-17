package edu.ntnu.idatt1002.backend;

import java.util.HashMap;

public class Accounts {
    public static HashMap<String, Double> accounts;

    public static void addAccount(String accountName, double accountBalance){
        if (accountName == null || accountName.isBlank()){
            throw new NullPointerException("Account name cannot be empty");
        }
        if (accountBalance < 0){
            throw new IllegalArgumentException("Account balance cannot be negative");
        }

        Account newAccount = new Account(accountName, accountBalance);
        accounts.put(newAccount.getAccountName(), newAccount.getAccountBalance());
    }

    public static void addToAccount(String accountName, double amount){
        if (amount < 0){
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        if (accountName == null || accountName.isBlank()){
            throw new NullPointerException("Account name cannot be empty");
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

    public static HashMap<String, Double> getAccounts() {
        return accounts;
    }
}
