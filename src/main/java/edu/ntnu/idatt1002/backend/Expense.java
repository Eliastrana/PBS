package edu.ntnu.idatt1002.backend;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;

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
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        if (category < 1 || category > 6) {
            throw new IllegalArgumentException("Category must be between 1 and 6");
        }
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }

        this.name = name;
        this.price = price;
        this.category = category;
        this.date = date;
    }

    public Expense(String name, Double price, LocalDate date, String category, String account) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        if (category == null || category.isBlank()) {
            throw new IllegalArgumentException("Category cannot be null or empty");
        }
        this.name = name;
        this.price = price;
        this.date = date;
        this.categoryAsString = category;
        this.accountAsString = account;
    }

    public Expense(String name, Double price, LocalDate date, String category, String account, String uniqueID) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        if (category == null || category.isBlank()) {
            throw new IllegalArgumentException("Category cannot be null or empty");
        }
        if (uniqueID == null || uniqueID.isBlank()) {
            throw new IllegalArgumentException("UniqueID cannot be null or empty");
        }

        this.name = name;
        this.price = price;
        this.date = date;
        this.categoryAsString = category;
        this.accountAsString = account;
        this.uniqueID = uniqueID;
    }

    public String getName() {
        return name.replaceAll("\"", "");
    }

    public double getPrice() {
        return price;
    }

    public LocalDate getDate() {
        return date;
    }
    public int getCategoryInt() {
        return category;
    }
    public String getCategoryAsString() {
        return categoryAsString;
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.name = name;
    }

    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        this.price = price;
    }


    public void setCategoryInt(int category) {
        if (category < 1 || category > 5) {
            throw new IllegalArgumentException("Category must be between 1 and 5");
        }
        this.category = category;
    }
    public void setCategoryAsString(String category) {
        if (category == null || category.isBlank()) {
            throw new IllegalArgumentException("Category cannot be null or empty");
        }
        this.categoryAsString = category;
    }

    public void setDate(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
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
    public void setUniqueID(String uniqueID) {
        if (uniqueID == null || uniqueID.isBlank()) {
            throw new IllegalArgumentException("UniqueID cannot be null or empty");
        }
        this.uniqueID = uniqueID;
    }

    public void setAccountAsString(String accountAsString) {
        this.accountAsString = accountAsString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Expense expense = (Expense) o;
        return Double.compare(expense.price, price) == 0 && Objects.equals(name, expense.name) && Objects.equals(categoryAsString, expense.categoryAsString) && Objects.equals(accountAsString, expense.accountAsString) && Objects.equals(date, expense.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, categoryAsString, accountAsString, date);
    }
}
