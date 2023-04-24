package edu.ntnu.idatt1002.backend;

import edu.ntnu.idatt1002.backend.budgeting.Expense;
import edu.ntnu.idatt1002.backend.budgeting.Expenses;
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
    Expenses expenseInstance = Expenses.getInstance();

    expenseInstance.createTransportation();
    expenseInstance.createEntertainment();
    expenseInstance.createClothing();
    expenseInstance.createOther();
    expenseInstance.createFood();
    expenseInstance.createRent();
    expenseInstance.createAllAlist();
    LocalDate testDate = LocalDate.parse("2023-04-14");
    testExpense = new Expense("Test item", 100.0, 2, testDate);
  }

  @Test
  @DisplayName("Test addToArrayList")
  public void testAddToArrayList() {
    Expenses instance = Expenses.getInstance();
    instance.addToArrayList(testExpense, Expenses.transportation);
    assertTrue(Expenses.transportation.contains(testExpense));
  }

  @Test
  @DisplayName("Test addToArray with null expense")
  public void testAddToArrayListNullExpense() {
    Expenses instance = Expenses.getInstance();
    assertThrows(IllegalArgumentException.class, () -> instance.addToArrayList(null, Expenses.transportation));
  }


  @Test
  @DisplayName("Test getTotalExpenses")
  public void testGetTotalExpenses() {
    Expenses instance = Expenses.getInstance();
    instance.addToArrayList(testExpense, Expenses.transportation);
    assertEquals(100.0, instance.getTotalExpenses(Expenses.transportation));
  }

  @Test
  @DisplayName("Test getExpensesOfAllCategories")
  public void testGetExpensesOfAllCategories() {
    Expenses instance = Expenses.getInstance();
    instance.addToArrayList(testExpense, Expenses.transportation);
    instance.addToArrayList(testExpense, Expenses.entertainment);
    instance.addToArrayList(testExpense, Expenses.clothing);
    instance.addToArrayList(testExpense, Expenses.other);
    instance.addToArrayList(testExpense, Expenses.food);
    instance.addToArrayList(testExpense, Expenses.rent);
    assertEquals(600.0, instance.getExpensesOfAllCategories());
  }

  @Test
  @DisplayName("Test createAllExpenses")
  public void testCreateAllExpenses() {
    Expenses instance = Expenses.getInstance();
    instance.addToArrayList(testExpense, instance.transportation);
    instance.addToArrayList(testExpense, instance.entertainment);
    instance.addToArrayList(testExpense, instance.clothing);
    instance.addToArrayList(testExpense, instance.other);
    instance.addToArrayList(testExpense, instance.food);
    instance.addToArrayList(testExpense, instance.rent);
    ArrayList<Expense> allExpenses = instance.createAllExpenses();
    assertEquals(6, allExpenses.size());
  }

  @Test
  @DisplayName("Test getExpenses")
  public void testGetExpenses() {
    Expenses instance = Expenses.getInstance();
    instance.addToArrayList(testExpense, instance.transportation);
    instance.addToArrayList(testExpense, instance.entertainment);
    instance.addToArrayList(testExpense, instance.clothing);
    instance.addToArrayList(testExpense, instance.other);
    instance.addToArrayList(testExpense, instance.food);
    instance.addToArrayList(testExpense, instance.rent);
    instance.createAllExpenses();
    ArrayList<Expense> allExpenses = instance.getExpenses();
    assertEquals(6, allExpenses.size());
  }
}
