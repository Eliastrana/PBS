package edu.ntnu.idatt1002.backend;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


@Nested
@DisplayName("Constructor Test")
class  AccountConstructorTest {

  @Test
  @DisplayName("Test valid constructor")
  void validConstructorTest() {
    Account account = new Account("AccountName", 100);
    assertEquals("AccountName", account.getAccountName());
    assertEquals(100, account.getAccountBalance());
  }

  @Test
  @DisplayName("Test constructor with null accountName")
  void constructorTestWithNullAccountName() {
    assertThrows(IllegalArgumentException.class, () -> new Account(null, 100));
  }

  @Test
  @DisplayName("Test constructor with empty accountName")
  void constructorTestWithEmptyAccountName() {
    assertThrows(IllegalArgumentException.class, () -> new Account("", 100));
  }

  @Test
  @DisplayName("Test constructor with negative accountBalance")
  void constructorTestWithNegativeAccountBalance() {
    assertThrows(IllegalArgumentException.class, () -> new Account("AccountName", -1));
  }
}

@Nested
@DisplayName("Getters Test")
class AccountGettersTest {

  @Test
  @DisplayName("Test getAccountName")
  void getAccountName() {
    Account account = new Account("AccountName", 100);
    assertEquals("AccountName", account.getAccountName());
  }

  @Test
  @DisplayName("Test getAccountBalance")
  void getAccountBalance() {
    Account account = new Account("AccountName", 100);
    assertEquals(100, account.getAccountBalance());
  }
}
@Nested
@DisplayName("Setters Test")
class AccountSettersTest {

    @Test
    @DisplayName("Test valid setAccountName")
    void setAccountName() {
      Account account = new Account("AccountName", 100);
      account.setAccountName("NewAccountName");
      assertEquals("NewAccountName", account.getAccountName());
    }

    @Test
    @DisplayName("Test setAccountName with null value")
    void setAccountNameWithNullValue() {
      Account account = new Account("AccountName", 100);
      assertThrows(IllegalArgumentException.class, () -> account.setAccountName(null));
    }

    @Test
    @DisplayName("Test setAccountName with empty name")
    void setAccountNameWithEmptyName() {
      Account account = new Account("AccountName", 100);
      assertThrows(IllegalArgumentException.class, () -> account.setAccountName(""));
    }

    @Test
    @DisplayName("Test setAccountBalance")
    void setAccountBalance() {
      Account account = new Account("AccountName", 100);
      account.setAccountBalance(200);
      assertEquals(200, account.getAccountBalance());
    }

    @Test
    @DisplayName("Test setAccountBalance with negative value")
    void setAccountBalanceWithNegativeValue() {
      Account account = new Account("AccountName", 100);
      assertThrows(IllegalArgumentException.class, () -> account.setAccountBalance(-1));
    }
}


