package edu.ntnu.idatt1002.backend;

import java.time.LocalDate;
import java.util.ArrayList;

public class Account {
    private String accountName;
    private double accountBalance;
    public static ArrayList<Expense> expenses;
    private double amount;
    private String date;

    public Account(String accountName, double accountBalance){
        if (accountName == null || accountName.isBlank()){
            throw new NullPointerException("Account name cannot be empty");
        }
        if (accountBalance < 0){
            throw new IllegalArgumentException("Account balance cannot be negative");
        }
        this.accountName = accountName;
        this.accountBalance = accountBalance;
        expenses = new ArrayList<Expense>();
    }

    public Account(String accountName, double amount, String date){
        if (accountName == null || accountName.isBlank()){
            throw new NullPointerException("Account name cannot be empty");
        }
        if (amount < 0){
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        if (date == null || date.isBlank()){
            throw new NullPointerException("Date cannot be empty");
        }
        this.accountName = accountName;
        this.amount = amount;
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        if (accountName == null || accountName.isBlank()){
            throw new NullPointerException("Account name cannot be empty");
        }
        this.accountName = accountName;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        if (accountBalance < 0){
            throw new IllegalArgumentException("Account balance cannot be negative");
        }
        this.accountBalance = accountBalance;
    }
}
