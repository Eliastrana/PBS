package edu.ntnu.idatt1002.backend;

import java.util.HashMap;

/**
 * A class that represents a collection of accounts.
 * The collection of accounts is a HashMap with the account name as key and the account balance as value.
 *
 * @author Emil J., Vegard J., Sander S. & Elias T.
 * @version 0.5 - 19.04.2023
 */
public class Accounts {
    /**
     * A HashMap with all the accounts.
     */
    public static HashMap<String, Double> accounts;

    /**
     * Constructor for Accounts.
     * Creates a new HashMap
     * The account name must not be null or empty.
     * The account balance must not be negative.
     *
     * @param accountName    the name of the account
     * @param accountBalance the balance of the account
     */
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

    /**
     * Adds to an account.
     *
     * @param accountName the name of the account
     * @param amount      the amount to be added
     */
    public static void addToAccount(String accountName, double amount){
        if (amount < 0){
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        if (accountName == null || accountName.isBlank()){
            throw new NullPointerException("Account name cannot be empty");
        }

        accounts.put(accountName, accounts.get(accountName)+amount);
    }

    /**
     * Returns the balance of an account.
     *
     * @param accountName the name of the account
     * @return the balance of the account
     */
    public static double getTotalOfAccount(String accountName){
        return accounts.get(accountName);
    }

    /**
     * Returns the total of all accounts.
     *
     * @return the total of all accounts
     */
    public static double getTotalOfAllAccounts(){
        double total = 0;
        for (Double value : accounts.values()) {
            total += value;
        }
        return total;
    }

    /**
     * Returns a map of the accounts.
     *
     * @return the accounts
     */
    public static HashMap<String, Double> getAccounts() {
        return accounts;
    }
}
