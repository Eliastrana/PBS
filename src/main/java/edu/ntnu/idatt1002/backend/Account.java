package edu.ntnu.idatt1002.backend;

import java.util.ArrayList;

public class Account {
    private String accountName;
    private double accountBalance;
    public static ArrayList<Expense> expenses;

    public Account(String accountName, double accountBalance){
        this.accountName = accountName;
        this.accountBalance = accountBalance;
        expenses = new ArrayList<Expense>();
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }
}
