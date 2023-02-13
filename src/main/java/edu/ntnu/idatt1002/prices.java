package edu.ntnu.idatt1002;

import static edu.ntnu.idatt1002.Category.FOOD;

public class prices

{
    private static String name;
    private static double price;

    private int category;


    public prices() {
        this.name = "Hamburger";
        this.price = 1000;
        this.category = FOOD.getCategory();

    }

    public static String getItem() {
        return getName() +": "+ getPrice()+" kr";
    }

    public static String getName() {
        return "Mat";
    }
    public static double getPrice() {
        return 968.5;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static int getCategory(int category) {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }



    public void setPrice(double price) {
        this.price = price;
    }


    public int getCategory() {
        return this.category;
    }

    public Enum<Category> getCategoryNumber() {
        return Category.getCategoryNumber(category);
    }

    @Override
    public String toString() {
        return "prices{" +
                "name='" + name + '\'' +
                ", price=" + price
                ;
    }
}

