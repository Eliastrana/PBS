package edu.ntnu.idatt1002.backend;

import java.time.LocalDate;
import java.util.Objects;

/**
 * A class that represents an expense.
 * An expense has a name, a price, a category and a date.
 *
 * @author Emil J., Vegard J., Sander S. & Elias T.
 * @version 0.5 - 19.04.2023
 */
public class Expense {
    /**
     * The name of the expense.
     */
    private String uniqueID;
    /**
     * The name of the expense.
     */
    private String name;
    /**
     * The price of the expense.
     */
    private double price;
    /**
     * The category of the expense as an int.
     */
    private int category;
    /**
     * The category of the expense as a string.
     */
    private String categoryAsString;
    /**
     * The account as a string.
     */
    private String accountAsString;
    /**
     * The date of the expense.
     */
    private LocalDate date;

    /**
     * Constructor for Expense.
     * The name must not be null or empty.
     * The price must not be negative.
     * The category must be between 1 and 6.
     * The date must not be null.
     *
     * @param name     the name of the expense
     * @param price    the price of the expense
     * @param category the category of the expense
     * @param date     the date of the expense
     */
    public Expense(String name, double price, int category, LocalDate date){
        if (name == null || name.isBlank()) {
            throw new NullPointerException("Name cannot be null or empty");
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

    /**
     * Constructor for Expense.
     * The name must not be null or empty.
     * The price must not be negative.
     * The date must not be null.
     * The category must be between 1 and 6.
     * The account must not be null or empty.
     *
     * @param name     the name
     * @param price    the price
     * @param date     the date
     * @param category the category
     * @param account  the account
     */
    public Expense(String name, Double price, LocalDate date, String category, String account) {
        if (name == null || name.isBlank()) {
            throw new NullPointerException("Name cannot be null or empty");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        if (date == null) {
            throw new NullPointerException("Date cannot be null");
        }
        if (category == null || category.isBlank()) {
            throw new NullPointerException("Category cannot be null or empty");
        }
        if (account == null || account.isBlank()) {
            throw new NullPointerException("Account cannot be null or empty");
        }
        this.name = name;
        this.price = price;
        this.date = date;
        this.categoryAsString = category;
        this.accountAsString = account;
    }

    /**
     * Constructor for Expense.
     * The name must not be null or empty.
     * The price must not be negative.
     * The date must not be null.
     * The category must be between 1 and 6.
     * The account must not be null or empty.
     * The uniqueID must not be null or empty.
     *
     * @param name     the name
     * @param price    the price
     * @param date     the date
     * @param category the category
     * @param account  the account
     * @param uniqueID the unique id
     */
    public Expense(String name, Double price, LocalDate date, String category, String account, String uniqueID) {
        if (name == null || name.isBlank()) {
            throw new NullPointerException("Name cannot be null or empty");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        if (date == null) {
            throw new NullPointerException("Date cannot be null");
        }
        if (category == null || category.isBlank()) {
            throw new NullPointerException("Category cannot be null or empty");
        }
        if (account == null || account.isBlank()) {
            throw new NullPointerException("Account cannot be null or empty");
        }
        if (uniqueID == null || uniqueID.isBlank()) {
            throw new NullPointerException("UniqueID cannot be null or empty");
        }

        this.name = name;
        this.price = price;
        this.date = date;
        this.categoryAsString = category;
        this.accountAsString = account;
        this.uniqueID = uniqueID;
    }

    /**
     * Gets the name of the expense.
     *
     * @return the name of the expense
     */
    public String getName() {
        return name.replaceAll("\"", "");
    }

    /**
     * Gets the price of the expense.
     *
     * @return the price of the expense
     */
    public double getPrice() {
        return price;
    }

    /**
     * Gets the date of the expense.
     *
     * @return the date of the expense
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Gets the category of the expense, as an integer.
     *
     * @return the category of the expense as an integer
     */
    public int getCategoryInt() {
        return category;
    }

    /**
     * Gets the category of the expense, as a string.
     *
     * @return the category of the expense as a string
     */
    public String getCategoryAsString() {
        return categoryAsString;
    }

    /**
     * Sets the name of the expense.
     *
     * @param name the name of the expense
     */
    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new NullPointerException("Name cannot be null or empty");
        }
        this.name = name;
    }

    /**
     * Sets the price of the expense.
     *
     * @param price the price of the expense
     */
    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        this.price = price;
    }


    /**
     * Sets the category of the expense, as an integer.
     *
     * @param category the category of the expense as an integer
     */
    public void setCategoryInt(int category) {
        if (category < 1 || category > 6) {
            throw new IllegalArgumentException("Category must be between 1 and 6");
        }
        this.category = category;
    }

    /**
     * Sets the category of the expense, as a string.
     *
     * @param category the category of the expense as a string
     */
    public void setCategoryAsString(String category) {
        if (category == null || category.isBlank()) {
            throw new NullPointerException("Category cannot be null or empty");
        }
        this.categoryAsString = category;
    }

    /**
     * Sets the date of the expense.
     *
     * @param date the date of the expense
     */
    public void setDate(LocalDate date) {
        if (date == null) {
            throw new NullPointerException("Date cannot be null");
        }
        this.date = date;
    }

    /**
     * Gets the account of the expense, as a string.
     *
     * @return the account of the expense as a string
     */
    public String getAccount() {
        return accountAsString;
    }

    /**
     * Gets the category of the expense.
     *
     * @return the category of the expense
     */
    public String getCategory() {
        return categoryAsString;
    }

    /**
     * Gets the unique id of the expense.
     *
     * @return the unique id of the expense
     */
    public String getUniqueID() {
        return uniqueID;
    }

    /**
     * Sets the unique id of the expense.
     *
     * @param uniqueID the unique id of the expense
     */
    public void setUniqueID(String uniqueID) {
        if (uniqueID == null || uniqueID.isBlank()) {
            throw new IllegalArgumentException("UniqueID cannot be null or empty");
        }
        this.uniqueID = uniqueID;
    }

    /**
     * Sets account as string.
     *
     * @param accountAsString the account as string
     */
    public void setAccountAsString(String accountAsString) {
        this.accountAsString = accountAsString;
    }

    /**
     * The equals method for Expense.
     * Two expenses are equal if they have the same name, price, category, account and date.
     * @param o the object to compare to
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Expense expense = (Expense) o;
        return Double.compare(expense.price, price) == 0 && Objects.equals(name, expense.name) && Objects.equals(categoryAsString, expense.categoryAsString) && Objects.equals(accountAsString, expense.accountAsString) && Objects.equals(date, expense.date);
    }

    /**
     * The hashCode method for Expense.
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, price, categoryAsString, accountAsString, date);
    }
}
