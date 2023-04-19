package edu.ntnu.idatt1002.backend;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A class that represents a collection of transfers.
 * The collection of transfers is an ArrayList.
 * The class is used for displaying the transfers in the GUI.
 * The class is also used for deleting transfers, depending on the type of the transfer.
 *
 * @author Emil J., Vegard J., Sander S. & Elias T.
 * @version 0.5 - 19.04.2023
 */
public class Transfers {
    /**
     * A list of transfers.
     */
    public static List<Transfers> transfers;
    /**
     * The account name of the account that the transfer is from.
     */
    private String accountName;
    /**
     * The date of the transfer.
     */
    private String date;
    /**
     * The amount of the transfer.
     */
    private double amount;
    /**
     * The type of the transfer.
     */
    private char transferType;
    /**
     * The type of the transfer.
     * The type is either "a" or "b".
     */
    private String type;

    /**
     * Instantiates a new Transfers.
     *
     * @param type the type of the transfer
     */
    public Transfers(String type){
        transfers = new ArrayList<Transfers>();
        this.type = type;
    }

    /**
     * Instantiates a new Transfers.
     */
    Transfers(){};

    /**
     * Returns the list of transfers.
     *
     * @return the list of transfers
     */
    public List<Transfers> transfersList(){
        return transfers;
    }

    /**
     * Constructor for Transfers.
     *
     * @param accountName  the account name of the account that the transfer is from
     * @param amount       the amount of the transfer
     * @param date         the date of the transfer
     * @param transferType the transfer type
     */
    public Transfers(String accountName, double amount, String date, char transferType){
        this.accountName = accountName;
        this.date = date;
        this.amount = amount;
        this.transferType = transferType;
    }

    /**
     * Adds a transfer to the list of transfers.
     *
     * @param account      the account of the transfer
     * @param amount       the amount of the transfer
     * @param date         the date of the transfer
     * @param transferType the transfer type of the transfer
     */
    public static void addTransfer(String account, double amount, String date, char transferType){
        Transfers newTransfer = new Transfers(account, amount, date, transferType);
        transfers.add(newTransfer);
    }

    /**
     * Returns the type of the transfer.
     *
     * @return the type of the transfer
     */
    public char getTransferType() {
        return transferType;
    }

    /**
     * Sets the transfer type.
     *
     * @param transferType the transfer type
     */
    public void setTransferType(char transferType) {
        this.transferType = transferType;
    }

    /**
     * Returns a list of transfers.
     *
     * @return a list of transfers
     */
    public static List<Transfers> getTransfers() {
        return transfers;
    }

    /**
     * Sets the list of transfers.
     *
     * @param transfers the list of transfers
     */
    public static void setTransfers(List<Transfers> transfers) {
        Transfers.transfers = transfers;
    }

    /**
     * Returns the account name of the transfer
     *
     * @return the account name of the transfer
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * Sets the account name of the transfer.
     *
     * @param accountName the account name of the transfer
     */
    public void setAccount(String accountName) {
        this.accountName = accountName;
    }

    /**
     * Returns the date of the transfer.
     *
     * @return the date of the transfer
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets the date of the transfer.
     *
     * @param date the date of the transfer
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Returns the amount of the transfer.
     *
     * @return the amount of the transfer
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Sets the amount of the transfer.
     *
     * @param amount the amount of the transfer
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * Equals method for Transfers.
     * The method is used for comparing two transfers.
     *
     * @param o the object to compare with
     * @return true if the objects are equal, false if not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transfers transfers = (Transfers) o;
        return Double.compare(transfers.amount, amount) == 0 && transferType == transfers.transferType && Objects.equals(accountName, transfers.accountName) && Objects.equals(date, transfers.date) && Objects.equals(type, transfers.type);
    }

    /**
     * Hashcode method for Transfers.
     * The method is used for comparing two transfers.
     *
     * @return the hashcode of the transfer
     */
    @Override
    public int hashCode() {
        return Objects.hash(accountName, date, amount, transferType, type);
    }
}
