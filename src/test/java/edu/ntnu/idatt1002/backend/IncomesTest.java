package edu.ntnu.idatt1002.backend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class IncomesTest {

    ArrayList<Income> incomes;
    ArrayList<Income> allIncomes;

    @BeforeEach
    void setUp() {
        incomes = new ArrayList<>();
        allIncomes = new ArrayList<>();
    }

    @Nested
    class Creators {
        @Test
        void testCreateIncomes() {
            Incomes.createIncomes();
            assertEquals(incomes, Incomes.incomes);
        }

        @Test
        void testCreateAllIncomes() {
            Incomes.createAllIncomes();
            assertEquals(allIncomes, Incomes.allIncomes);
        }
    }

    /*
    @Nested
    class AddToArrayList {
        @Test
        @Disabled ("Not implemented yet")
        void testAddToArrayList() {
            Income income = new Income("test", 100, 1, LocalDate.now());
            Incomes.addToArrayList(income, incomes);
            assertEquals(incomes, Incomes.incomes);
        }
    }
    */
    @Nested
    class Getters {
        @Test
        void testGetIncomes() {
            Incomes.createIncomes();
            assertEquals(incomes, Incomes.getIncomes());
        }
    }

}