package edu.ntnu.idatt1002.backend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Expenses Test")
class ExpensesTest {

  private Expense testExpense;

  @BeforeEach
  public void setUp() {
    Expenses.createTransportation();
    Expenses.createEntertainment();
    Expenses.createClothing();
    Expenses.createOther();
    Expenses.createFood();
    Expenses.createRent();
    Expenses.createAllAlist();
    LocalDate testDate = LocalDate.parse("2023-04-14");
    testExpense = new Expense("Test item", 100.0, 2, testDate);
  }

  @Test
  @DisplayName("Test addToArrayList")
  public void testAddToArrayList() {
    Expenses.addToArrayList(testExpense, Expenses.transportation);
    assertTrue(Expenses.transportation.contains(testExpense));
  }

  @Test
  @DisplayName("Test addToArray with null expense")
  public void testAddToArrayListNullExpense() {
    assertThrows(IllegalArgumentException.class, () -> Expenses.addToArrayList(null, Expenses.transportation));
  }


  @Test
  @DisplayName("Test getTotalExpenses")
  public void testGetTotalExpenses() {
    Expenses.addToArrayList(testExpense, Expenses.transportation);
    assertEquals(100.0, Expenses.getTotalExpenses(Expenses.transportation));
  }

  @Test
  @DisplayName("Test getExpensesOfAllCategories")
  public void testGetExpensesOfAllCategories() {
    Expenses.addToArrayList(testExpense, Expenses.transportation);
    Expenses.addToArrayList(testExpense, Expenses.entertainment);
    Expenses.addToArrayList(testExpense, Expenses.clothing);
    Expenses.addToArrayList(testExpense, Expenses.other);
    Expenses.addToArrayList(testExpense, Expenses.food);
    Expenses.addToArrayList(testExpense, Expenses.rent);
    assertEquals(600.0, Expenses.getExpensesOfAllCategories());
  }

  @Test
  @DisplayName("Test createAllExpenses")
  public void testCreateAllExpenses() {
    Expenses.addToArrayList(testExpense, Expenses.transportation);
    Expenses.addToArrayList(testExpense, Expenses.entertainment);
    Expenses.addToArrayList(testExpense, Expenses.clothing);
    Expenses.addToArrayList(testExpense, Expenses.other);
    Expenses.addToArrayList(testExpense, Expenses.food);
    Expenses.addToArrayList(testExpense, Expenses.rent);
    ArrayList<Expense> allExpenses = Expenses.createAllExpenses();
    assertEquals(6, allExpenses.size());
  }

  @Test
  @DisplayName("Test getExpenses")
  public void testGetExpenses() {
    Expenses.addToArrayList(testExpense, Expenses.transportation);
    Expenses.addToArrayList(testExpense, Expenses.entertainment);
    Expenses.addToArrayList(testExpense, Expenses.clothing);
    Expenses.addToArrayList(testExpense, Expenses.other);
    Expenses.addToArrayList(testExpense, Expenses.food);
    Expenses.addToArrayList(testExpense, Expenses.rent);
    Expenses.createAllExpenses();
    ArrayList<Expense> allExpenses = Expenses.getExpenses();
    assertEquals(6, allExpenses.size());
  }


}
