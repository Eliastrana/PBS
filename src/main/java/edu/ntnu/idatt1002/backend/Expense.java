package edu.ntnu.idatt1002.backend;

import java.time.LocalDate;
import java.util.Arrays;
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
     * The uniqueID of the expense, used for creating the bankstatement, where uniqueID is the month and category
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
     * The category of the expense.
     */
    private int category;

    /**
     * The category of the expense as a string.
     */
    private String categoryAsString;

    /**
     * The account of the expense as a string.
     */
    private String accountAsString;

    /**
     * The date of the expense.
     */
    private LocalDate date;

    /**
     * The single instance of the class used in the singleton pattern.
     */
    private static Expense instance = new Expense();

    /**
     * Private constructor to avoid multiple instances of the class.
     */
    public Expense(){} // Singleton

    /**
     * Returns the single instance of the class.
     * @return the single instance of the class.
     */
    public static Expense getInstance(){
        return instance;
    }

    /**
     * Constructor for the class.
     * @param name the name of the expense.
     * @param price the price of the expense.
     * @param category the category of the expense.
     * @param date the date of the expense.
     * @throws NullPointerException if the name is null or empty.
     * @throws IllegalArgumentException if the price is negative or the category is not between 1 and 6.
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
     * Constructor for the class.
     * @param name the name of the expense.
     * @param price the price of the expense.
     * @param category the category of the expense.
     * @param date the date of the expense.
     * @throws NullPointerException if the name is null or empty.
     * @throws IllegalArgumentException if the price is negative or the category is not between 1 and 6.
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
        this.name = name;
        this.price = price;
        this.date = date;
        this.categoryAsString = category;
        this.accountAsString = account;
    }

    /**
     * Constructor for the class.
     * @param name the name of the expense.
     * @param price the price of the expense.
     * @param category the category of the expense.
     * @param date the date of the expense.
     * @param uniqueID the uniqueID of the expense.
     * @throws NullPointerException if the name is null or empty.
     * @throws IllegalArgumentException if the price is negative or the category is not between 1 and 6.
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
     * Returns the name of the expense.
     * @return the name of the expense.
     */
    public String getName() {
        return name.replaceAll("\"", "");
    }

    /**
     * Returns the price of the expense.
     * @return the price of the expense.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Returns the date of the expense.
     * @return the date of the expense.
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Returns the category of the expense.
     * @return the category of the expense.
     */
    public int getCategoryInt() {
        return category;
    }

    /**
     * Returns the category of the expense as a string.
     * @return the category of the expense as a string.
     */
    public String getCategoryAsString() {
        return categoryAsString;
    }

    /**
     * Sets the name of the expense.
     * @param name the new name of the expense.
     */
    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new NullPointerException("Name cannot be null or empty");
        }
        this.name = name;
    }

    /**
     * Sets the price of the expense.
     * @param price the new price of the expense.
     */
    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        this.price = price;
    }


    /**
     * Sets the category of the expense.
     * @param category the new category of the expense.
     */
    public void setCategoryInt(int category) {
        if (category < 1 || category > 6) {
            throw new IllegalArgumentException("Category must be between 1 and 6");
        }
        this.category = category;
    }

    /**
     * Sets the category as a string of the expense.
     * @param category the new category as string of the expense.
     */
    public void setCategoryAsString(String category) {
        if (category == null || category.isBlank()) {
            throw new NullPointerException("Category cannot be null or empty");
        }
        this.categoryAsString = category;
    }

    /**
     * Sets the date of the expense.
     * @param date the new date of the expense.
     */
    public void setDate(LocalDate date) {
        if (date == null) {
            throw new NullPointerException("Date cannot be null");
        }
        this.date = date;
    }

    /**
     * Returns the account of the expense.
     * @return the account of the expense.
     */
    public String getAccount() {
        return accountAsString;
    }

    /**
     * Gets the category of the expense.
     * @return the category of the expense.
     */
    public String getCategory() {
        return categoryAsString;
    }

    /**
     * Gets the uniqueID of the expense.
     * @return the uniqueID of the expense.
     */
    public String getUniqueID() {
        return uniqueID;
    }

    /**
     * Sets the uniqueID of the expense.
     * @param uniqueID the new uniqueID of the expense.
     */
    public void setUniqueID(String uniqueID) {
        if (uniqueID == null || uniqueID.isBlank()) {
            throw new IllegalArgumentException("UniqueID cannot be null or empty");
        }
        this.uniqueID = uniqueID;
    }


    /**
     * Sets the account as string of the expense.
     * @param accountAsString the new account as string of the expense.
     */
    public void setAccountAsString(String accountAsString) {
        this.accountAsString = accountAsString;
    }

    public boolean validateCategory(String categoryAsString) {
        String[] categories = {"Food", "Transportation", "Entertainment", "Clothing", "Other", "Rent"};
        if (categoryAsString == null || categoryAsString.isBlank()) {
            return false;
        } else {
            return Arrays.asList(categories).contains(categoryAsString);
        }
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
     * @return the hashCode of the expense.
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, price, categoryAsString, accountAsString, date);
    }
}

