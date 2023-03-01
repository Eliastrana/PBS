package edu.ntnu.idatt1002;

import java.time.LocalDate;
import java.time.LocalTime;

public class Expense {
    private String name;
    private double price;
    private int category;

    private LocalTime date;

    Expense(String name, double price, int category, LocalTime date){
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getCategory() {
        return category;
    }

    public LocalTime getDate() {
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

    public void setDate(LocalTime date) {
        this.date = date;
    }
}
