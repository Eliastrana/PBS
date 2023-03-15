package edu.ntnu.idatt1002.backend;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Income {

    private String name;
    private double price;
    private int category;

    private LocalDate date;
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    //String date = dateUnformatted.format(formatter);

    public Income(String name, double price, int category, LocalDate date) {
        if (name == null || name.isBlank()){
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if (price < 0){
            throw new IllegalArgumentException("Price cannot be negative");
        }
        if (category < 1 || category > 5){
            throw new IllegalArgumentException("Category must be between 1 and 5");
        }
        if (date == null){
            throw new IllegalArgumentException("Date cannot be empty");
        }

        this.name = name;
        this.price = price;
        this.category = category;
        this.date = date;
    }

    public Income(){}

    public Income(String name, double price, LocalDate date){
        if (name == null || name.isBlank()){
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if (price < 0){
            throw new IllegalArgumentException("Price cannot be negative");
        }
        if (date == null){
            throw new IllegalArgumentException("Date cannot be empty");
        }
        this.name = name;
        this.price = price;
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
        if (name == null || name.isBlank()){
            throw new IllegalArgumentException("Name cannot be empty");
        }
        this.name = name;
    }

    public void setPrice(double price) {
        if (price < 0){
            throw new IllegalArgumentException("Price cannot be negative");
        }
        this.price = price;
    }


    public void setCategory(int category) {
        if (category < 1 || category > 5){
            throw new IllegalArgumentException("Category must be between 1 and 5");
        }
        this.category = category;
    }

    public void setDate(LocalDate date) {
        if (date == null){
            throw new IllegalArgumentException("Date cannot be empty");
        }
        this.date = date;
    }
}
