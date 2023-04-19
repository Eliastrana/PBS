package edu.ntnu.idatt1002.backend;

import java.util.ArrayList;

/**
 * A class that represents an account.
 * An account has a name and a balance.
 *
 * @author Emil J., Vegard J., Sander S. & Elias T.
 * @version 0.5 - 19.04.2023
 */
public class Account {
    /**
     * The name of the account.
     */
    private String accountName;
    /**
     * The balance of the account.
     */
    private double accountBalance;
    /**
     * A list of all the expenses in the account.
     */
    public static ArrayList<Expense> expenses;

    /**
     * Constructor for Account.
     * The account name must not be null or empty.
     * The account balance must not be negative.
     *
     * @param accountName    the name of the account
     * @param accountBalance the balance of the account
     */
    public Account(String accountName, double accountBalance){
        if (accountName == null || accountName.isBlank()){
            throw new IllegalArgumentException("Account name cannot be empty");
        }
        if (accountBalance < 0){
            throw new IllegalArgumentException("Account balance cannot be negative");
        }
        this.accountName = accountName;
        this.accountBalance = accountBalance;
        expenses = new ArrayList<Expense>();
    }

    /**
     * Returns the name of the account.
     *
     * @return the name of the account
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * Sets the name of the account.
     *
     * @param accountName the name of the account
     */
    public void setAccountName(String accountName) {
        if (accountName == null || accountName.isBlank()){
            throw new IllegalArgumentException("Account name cannot be empty");
        }
        this.accountName = accountName;
    }

    /**
     * Gets the balance of the account.
     *
     * @return the balance of the account
     */
    public double getAccountBalance() {
        return accountBalance;
    }

    /**
     * Sets the balance of the account.
     *
     * @param accountBalance the balance of the account
     */
    public void setAccountBalance(double accountBalance) {
        if (accountBalance < 0){
            throw new IllegalArgumentException("Account balance cannot be negative");
        }
        this.accountBalance = accountBalance;
    }
}
