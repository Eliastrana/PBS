package edu.ntnu.idatt1002;

public enum Category {

    FOOD(1),
    TRANSPORTATION(2),
    CLOTHING(3),
    ENTERTAINMENT(4),
    OTHER(5);

    private int category;

    Category(int category) {
        this.category = category;
    }

    public static Enum<Category> getCategoryNumber(int category) {
        return null;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Category{" +
                "category=" + category +
                '}';
    }
}
