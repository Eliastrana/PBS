package edu.ntnu.idatt1002.backend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ExpenseTest {

    String name;
    double price;
    int category;
    LocalDate date;

    @BeforeEach
    void setUp() {
        name = "test";
        price = 100;
        category = 1;
        date = LocalDate.now();
    }


    @Nested
    class Constructor {
        @Test
        void testConstructor() {
            Expense expense = new Expense(name, price, category, date);
            assertEquals(name, expense.getName());
            assertEquals(price, expense.getPrice());
            assertEquals(category, expense.getCategory());
            assertEquals(date, expense.getDate());
        }
    }

    @Nested
    class Getters {
        @Test
        void testGetName() {
            Expense expense = new Expense(name, price, category, date);
            assertEquals(name, expense.getName());
        }

        @Test
        void testGetPrice() {
            Expense expense = new Expense(name, price, category, date);
            assertEquals(price, expense.getPrice());
        }

        @Test
        void testGetCategory() {
            Expense expense = new Expense(name, price, category, date);
            assertEquals(category, expense.getCategory());
        }

        @Test
        void testGetDate() {
            Expense expense = new Expense(name, price, category, date);
            assertEquals(date, expense.getDate());
        }
    }

    @Nested
    class Setters {
        @Test
        void testSetName() {
            Expense expense = new Expense(name, price, category, date);
            expense.setName("test2");
            assertEquals("test2", expense.getName());
        }

        @Test
        void testSetPrice() {
            Expense expense = new Expense(name, price, category, date);
            expense.setPrice(200);
            assertEquals(200, expense.getPrice());
        }

        @Test
        void testSetCategory() {
            Expense expense = new Expense(name, price, category, date);
            expense.setCategory(2);
            assertEquals(2, expense.getCategory());
        }

        @Test
        void testSetDate() {
            Expense expense = new Expense(name, price, category, date);
            expense.setDate(LocalDate.now().plusDays(1));
            assertEquals(LocalDate.now().plusDays(1), expense.getDate());
        }
    }
}

