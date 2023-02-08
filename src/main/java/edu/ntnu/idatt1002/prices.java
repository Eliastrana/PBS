package edu.ntnu.idatt1002;

public class prices

{
    private String name;
    private double price;

    private int category;


    public prices(String name, double price) {
        this.name = name;
        this.price = price;
        this.category = category;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void getCategory(int category) {
        this.category = category;
    }
    public void setCategory(int category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }



    @Override
    public String toString() {
        return "prices{" +
                "name='" + name + '\'' +
                ", price=" + price
                ;
    }
}

