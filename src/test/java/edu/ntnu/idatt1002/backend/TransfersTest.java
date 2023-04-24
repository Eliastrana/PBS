package edu.ntnu.idatt1002.backend;

import edu.ntnu.idatt1002.backend.budgeting.Transfers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

@DisplayName("Transfers Test")
public class TransfersTest {
  private Transfers transfers;

  @BeforeEach
  public void setUp() {
    transfers = new Transfers("test");
  }

  @Test
  @DisplayName("Test addTransfer()")
  void testAddTransfer() {
    transfers.addTransfer("account1", 100, "2023-04-24", 'A');
    List<Transfers> transfersList = transfers.transfersList();
    Assertions.assertEquals(1, transfersList.size());

    Transfers newTransfer = transfersList.get(0);
    Assertions.assertEquals("account1", newTransfer.getAccountName());
    Assertions.assertEquals(100, newTransfer.getAmount());
    Assertions.assertEquals("2023-04-24", newTransfer.getDate());
    Assertions.assertEquals('A', newTransfer.getTransferType());
  }

  @Test
  @DisplayName("Test addTransfer()")
  void testEquals() {
    Transfers transfer1 = new Transfers("account1", 100, "2023-04-24", 'A');
    Transfers transfer2 = new Transfers("account1", 100, "2023-04-24", 'A');
    Transfers transfer3 = new Transfers("account2", 200, "2023-04-25", 'B');

    Assertions.assertEquals(transfer1, transfer2);
    Assertions.assertNotEquals(transfer1, transfer3);
    Assertions.assertNotEquals(transfer2, transfer3);
  }
}