package edu.ntnu.idatt1002.backend;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Expense {
    private String name;
    private double price;
    private int category;

    private LocalDate date;
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    //String date = dateUnformatted.format(formatter);

    public Expense(String name, double price, int category, LocalDate date){
        this.name = name;
        this.price = price;
        this.category = category;
        this.date = date;
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
}
