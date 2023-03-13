package edu.ntnu.idatt1002.backend;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CategoryTest {

    private int category;


    @Test
    public void ConstructorTest() {
        Category category = Category.FOOD;
        assertEquals(1, category.getCategory());
    }
    @Test
    public void testCategory() {
        Category category = Category.FOOD;
        assertEquals(1, category.getCategory());
    }

    @Test
    public void testGetCategoryNumber() {
        Category category = Category.FOOD;
        assertEquals(1, category.getCategory());
    }

    @Test
    public void testSetCategory() {
        Category category = Category.FOOD;
        category.setCategory(2);
        assertEquals(2, category.getCategory());
    }




}
