package edu.ntnu.idatt1002.backend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExpensesTest {

    ArrayList<Expense> transportation;
    ArrayList<Expense> entertainment;
    ArrayList<Expense> clothing;

    ArrayList<Expense> rent;

    ArrayList<Expense> other;

    ArrayList<Expense> food;
    ArrayList<ArrayList> allLists;
    ArrayList<Expense> allExpenses;

    @BeforeEach
    void setUp() {
        transportation = new ArrayList<Expense>();
        entertainment = new ArrayList<Expense>();
        clothing = new ArrayList<Expense>();
        other = new ArrayList<Expense>();
        food = new ArrayList<Expense>();
        rent = new ArrayList<Expense>();
        allLists = new ArrayList<ArrayList>();
        allExpenses = new ArrayList<Expense>();
    }

    @Nested
    class Creators {
        @Test
        void testCreateTransportation() {
            Expenses.createTransportation();
            assertEquals(transportation, Expenses.transportation);
        }

        @Test
        void testCreateEntertainment() {
            Expenses.createEntertainment();
            assertEquals(entertainment, Expenses.entertainment);
        }

        @Test
        void testCreateClothing() {
            Expenses.createClothing();
            assertEquals(clothing, Expenses.clothing);
        }

        @Test
        void testCreateOther() {
            Expenses.createOther();
            assertEquals(other, Expenses.other);
        }

        @Test
        void testCreateFood() {
            Expenses.createFood();
            assertEquals(food, Expenses.food);
        }

        @Test
        void testCreateRent() {
            Expenses.createRent();
            assertEquals(rent, Expenses.rent);
        }

        @Test
        void testCreateAllExpenses() {
            Expenses.createAllExpenses();
            assertEquals(allExpenses, Expenses.allExpenses);
        }

        /*
        @Test
        @Disabled ("Not implemented yet")
        void testCreateAllAlist() {
            Expenses.createAllAlist();
            assertEquals(allLists, Expenses.allLists);
        }

         */
    }

    @Nested
    class Adders {
        @Test
        void testAddToArrayList() {

            Expenses.addToArrayList(new Expense("test", 1, 1, null), transportation);
            assertEquals(1, transportation.size());
        }
    }

    @Nested
    class Getters {
        @Test
        void testGetTotalExpenses() {
            Expenses.addToArrayList(new Expense("test", 1, 1, null), transportation);
            assertEquals(1, Expenses.getTotalExpenses(transportation));
        }

        /*
        @Test
        @Disabled ("Not implemented yet")
        void testGetExpensesOfAllCategories() {
            Expenses.addToArrayList(new Expense("test", 1, 1, null), transportation);
            Expenses.addToArrayList(new Expense("test", 1, 1, null), entertainment);
            Expenses.addToArrayList(new Expense("test", 1, 1, null), clothing);
            Expenses.addToArrayList(new Expense("test", 1, 1, null), other);
            Expenses.addToArrayList(new Expense("test", 1, 1, null), food);
            Expenses.addToArrayList(new Expense("test", 1, 1, null), rent);
            assertEquals(ArrayList<Expenses.getExpenses(), Expenses.getExpensesOfAllCategories());
            }


         */
    }

}
