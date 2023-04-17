package edu.ntnu.idatt1002.backend;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Incomes Test")
class IncomesTest {

  @Nested
  @DisplayName("CreateIncomes Test")
  class CreateIncomeTest {
    @Test
    @DisplayName("Valid createIncomes test")
    void createIncomes() {
      Incomes.createIncomes();
      assertEquals(0, Incomes.getIncomes().size());
    }
  }
  @Nested
  @DisplayName("GetIncomes Test")
  class GetIncomesTest {
    @Test
    @DisplayName("Valid getIncomes test")
    void getIncomes() {
      Incomes.createIncomes();
      Incomes.addToArrayList(new Income("test", 100,2, LocalDate.now()),Incomes.getIncomes());
      assertEquals(1, Incomes.getIncomes().size());
    }

  }


  @Nested
  @DisplayName("CreateAllIncomes Test")
  class CreateAllIncomesTest {
    @Test
    @DisplayName("Valid createAllIncomes test")
    void createAllIncomes() {
      Incomes.createIncomes();
      Incomes.addToArrayList(new Income("test", 100,2, LocalDate.now()),Incomes.getIncomes());
      Incomes.createAllIncomes();
      assertEquals(1, Incomes.getIncomes().size());
    }

  }


  @Nested
  @DisplayName("AddToArrayList Test")
  class AddToArrayListTest {
    @Test
    @DisplayName("Valid addToArrayList test")
    void addToArrayList() {
      Incomes.createIncomes();
      Incomes.addToArrayList(new Income("test", 100,2, LocalDate.now()),Incomes.getIncomes());
      assertEquals(1, Incomes.getIncomes().size());
    }

    @Test
    @DisplayName("Invalid addToArrayList test with null income")
    void addToArrayListInvalid() {
      assertThrows(NullPointerException.class, () -> Incomes.addToArrayList(null,Incomes.getIncomes()));
    }

    @Test
    @DisplayName("Invalid addToArrayList test with null arraylist")
    void addToArrayListInvalid2() {
      assertThrows(NullPointerException.class, () -> Incomes.addToArrayList(new Income("test", 100,2, LocalDate.now()),null));
    }
  }

}