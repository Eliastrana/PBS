package edu.ntnu.idatt1002.backend;

import edu.ntnu.idatt1002.frontend.utility.SoundPlayer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Accounts Test")
class AccountsTest {


  @Nested
  class AddAccountTest {

    @Test
    @DisplayName("Valid addAccount test")
    void addAccount() {
      Accounts.createAccountsHashmap();
      Accounts.addAccount("test", 100.00);
      assertEquals(100.00, Accounts.getTotalOfAccount("test"));
      assertEquals("test", Accounts.getAccounts().keySet().toArray()[0]);
    }

    @Test
    @DisplayName("Test addAccount with null accountName")
    void addAccountWithNullAccountName() {
      assertThrows(NullPointerException.class, () -> Accounts.addAccount(null, 100));
    }

    @Test
    @DisplayName("Test addAccount with empty accountName")
    void addAccountWithEmptyAccountName() {
      assertThrows(NullPointerException.class, () -> Accounts.addAccount("", 100));
    }

    @Test
    @DisplayName("Test addAccount with negative accountBalance")
    void addAccountWithNegativeAccountBalance() {
      assertThrows(IllegalArgumentException.class, () -> Accounts.addAccount("test", -1));

    }
  }
  @Nested
  class GetTotalOfAccountTest {

    @Test
    @DisplayName("Valid getTotalOfAccount test")
    void getTotalOfAccount() {
      Accounts.createAccountsHashmap();
      Accounts.addAccount("test", 100);
      assertEquals(100, Accounts.getTotalOfAccount("test"));
      SoundPlayer.play("src/main/resources/16bitconfirm.wav");

    }
    @Test
    @DisplayName("Test getTotalOfAccount with null accountName")
    void getTotalOfAccountWithNullAccountName() {
      assertThrows(NullPointerException.class, () -> Accounts.getTotalOfAccount(null));
      SoundPlayer.play("src/main/resources/16bitconfirm.wav");

    }
    @Test
    @DisplayName("Test getTotalOfAccount with empty accountName")
    void getTotalOfAccountWithEmptyAccountName() {
      assertThrows(NullPointerException.class, () -> Accounts.getTotalOfAccount(""));
      SoundPlayer.play("src/main/resources/16bitconfirm.wav");
    }
  }

  @Nested
  class GetTotalOfAllAccountsTest {

    @Test
    @DisplayName("Valid getTotalOfAllAccounts test")
    void getTotalOfAllAccounts() {
      Accounts.createAccountsHashmap();
      Accounts.addAccount("test", 100);
      Accounts.addAccount("test2", 100);
      assertEquals(200, Accounts.getTotalOfAllAccounts());
      SoundPlayer.play("src/main/resources/16bitconfirm.wav");
    }
  }
@Nested
  class GetAccountsTest {
    @Test
    @DisplayName("Valid getAccounts test")
    void getAccounts() {
      Accounts.createAccountsHashmap();
      Accounts.addAccount("test", 100);
      Accounts.addAccount("test2", 100);
      assertEquals(2, Accounts.getAccounts().size());
      SoundPlayer.play("src/main/resources/16bitconfirm.wav");
    }
  }


}