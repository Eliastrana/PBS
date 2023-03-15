package edu.ntnu.idatt1002.backend;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Expense {
    private String uniqueID;
    private String name;
    private double price;
    private int category;
    private String categoryAsString;
    private String accountAsString;

    private LocalDate date;
    private Date Date;

    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    //String date = dateUnformatted.format(formatter);

    public Expense(String name, double price, int category, LocalDate date){
        this.name = name;
        this.price = price;
        this.category = category;
        this.date = date;
    }

    public Expense(String name, Double price, LocalDate date, String category, String account) {
        this.name = name;
        this.price = price;
        this.date = date;
        this.categoryAsString = category;
        this.accountAsString = account;
    }

    public Expense(String name, Double price, LocalDate date, String category, String account, String uniqueID) {
        this.name = name;
        this.price = price;
        this.date = date;
        this.categoryAsString = category;
        this.accountAsString = account;
        this.uniqueID = uniqueID;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    public void setCategory(int category) {
        this.category = category;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
    public String getAccount() {
        return accountAsString;
    }
    public String getCategory() {
        return categoryAsString;
    }
    public String getUniqueID() {
        return uniqueID;
    }
}
