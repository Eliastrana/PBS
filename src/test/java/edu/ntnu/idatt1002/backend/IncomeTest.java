package edu.ntnu.idatt1002.backend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IncomeTest {
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
            Income income = new Income(name, price, category, date);
            assertEquals(name, income.getName());
            assertEquals(price, income.getPrice());
            assertEquals(category, income.getCategory());
            assertEquals(date, income.getDate());
        }
    }

    @Nested
    class Getters {
        @Test
        void testGetName() {
            Income income = new Income(name, price, category, date);
            assertEquals(name, income.getName());
        }

        @Test
        void testGetPrice() {
            Income income = new Income(name, price, category, date);
            assertEquals(price, income.getPrice());
        }

        @Test
        void testGetCategory() {
            Income income = new Income(name, price, category, date);
            assertEquals(category, income.getCategory());
        }

        @Test
        void testGetDate() {
            Income income = new Income(name, price, category, date);
            assertEquals(date, income.getDate());
        }
    }

    @Nested
    class Setters {
        @Test
        void testSetName() {
            Income income = new Income(name, price, category, date);
            income.setName("test2");
            assertEquals("test2", income.getName());
        }

        @Test
        void testSetPrice() {
            Income income = new Income(name, price, category, date);
            income.setPrice(200);
            assertEquals(200, income.getPrice());
        }

        @Test
        void testSetCategory() {
            Income income = new Income(name, price, category, date);
            income.setCategory(2);
            assertEquals(2, income.getCategory());
        }

        @Test
        void testSetDate() {
            Income income = new Income(name, price, category, date);
            income.setDate(LocalDate.of(2020, 1, 1));
            assertEquals(LocalDate.of(2020, 1, 1), income.getDate());
        }
    }


}
