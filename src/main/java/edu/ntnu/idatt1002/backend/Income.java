package edu.ntnu.idatt1002.backend;

import java.time.LocalDate;

/**
 * A class that represents an income.
 * An income has a name, a price, a category and a date.
 *
 * @author Emil J., Vegard J., Sander S. & Elias T.
 * @version 0.5 - 19.04.2023
 */
public class Income {
    /**
     * The name of the income.
     */
    private String name;
    /**
     * The price of the income.
     */
    private double price;
    /**
     * The category of the income.
     */
    private int category;
    /**
     * The date of the income.
     */
    private LocalDate date;

    /**
     * Constructor for Income.
     * The name must not be null or empty.
     * The price must not be negative.
     * The category must be between 1 and 6.
     * The date must not be null.
     *
     * @param name     the name of the income
     * @param price    the price of the income
     * @param category the category of the income
     * @param date     the date of the income
     */
    public Income(String name, double price, int category, LocalDate date) {
        if (name == null || name.isBlank()){
            throw new NullPointerException("Name cannot be empty");
        }
        if (price < 0){
            throw new IllegalArgumentException("Price cannot be negative");
        }
        if (category < 1 || category > 6){
            throw new IllegalArgumentException("Category must be between 1 and 6");
        }
        if (date == null){
            throw new NullPointerException("Date cannot be empty");
        }

        this.name = name;
        this.price = price;
        this.category = category;
        this.date = date;
    }

    /**
     * Instantiates a new Income.
     */
    public Income(){}

    /**
     * Constructor for Income.
     * The name must not be null or empty.
     * The price must not be negative.
     * The date must not be null.
     *
     * @param name  the name of the income
     * @param price the price of the income
     * @param date  the date of the income
     */
    public Income(String name, double price, LocalDate date){
        if (name == null || name.isBlank()){
            throw new NullPointerException("Name cannot be empty");
        }
        if (price < 0){
            throw new IllegalArgumentException("Price cannot be negative");
        }
        if (date == null){
            throw new NullPointerException("Date cannot be empty");
        }
        this.name = name;
        this.price = price;
        this.date = date;
    }

    /**
     * Returns the name of the income.
     *
     * @return the name of the income
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the price of the income.
     *
     * @return the price of the income
     */
    public double getPrice() {
        return price;
    }

    /**
     * Returns the category of the income.
     *
     * @return the category of the income
     */
    public int getCategory() {
        return category;
    }

    /**
     * Returns the date of the income.
     *
     * @return the date of the income
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Sets the name of the income.
     *
     * @param name the name of the income
     */
    public void setName(String name) {
        if (name == null || name.isBlank()){
            throw new NullPointerException("Name cannot be empty");
        }
        this.name = name;
    }

    /**
     * Sets the price of the income.
     *
     * @param price the price of the income
     */
    public void setPrice(double price) {
        if (price < 0){
            throw new IllegalArgumentException("Price cannot be negative");
        }
        this.price = price;
    }


    /**
     * Sets the category of the income.
     *
     * @param category the category of the income
     */
    public void setCategory(int category) {
        if (category < 1 || category > 6){
            throw new IllegalArgumentException("Category must be between 1 and 6");
        }
        this.category = category;
    }

    /**
     * Sets the date of the income.
     *
     * @param date the date of the income
     */
    public void setDate(LocalDate date) {
        if (date == null){
            throw new NullPointerException("Date cannot be empty");
        }
        this.date = date;
    }
}
