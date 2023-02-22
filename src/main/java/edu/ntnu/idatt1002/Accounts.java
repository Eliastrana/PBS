package edu.ntnu.idatt1002;

import java.util.HashMap;

public class Accounts {
    public static HashMap<String, Double> accounts;

    public static void createAccountsHashmap(){
        accounts = new HashMap<String, Double>();
    }

    public static void addAccount(String accountName, double accountBalance){
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
}
