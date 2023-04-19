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
     * The account name of the account that made/recieved the transfer.
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
     * The type of the transfer. A for adding to an account, B for transfering between accounts.
     */
    private char transferType;

    /**
     * Dummy variable for initializing the arraylist.
     */
    private String type;

    /**
     * The single instance of the class used in the singleton pattern.
     */
    private static Transfers instance = new Transfers();

    /**
     * Constructor to initialize an object of the transfers arraylist.
     */
    public Transfers(String type){
        transfers = new ArrayList<Transfers>();
        this.type = type;
    }

    /**
     * Private constructor to avoid multiple instances of the class.
     */
    private Transfers(){}

    /**
     * Returns the single instance of the class.
     * @return the single instance of the class.
     */
    public Transfers getInstance(){
        return instance;
    }

    /**
     * Returns the list of transfers.
     * @return the list of transfers.
     */
    public List<Transfers> transfersList(){
        return transfers;
    }

    /**
     * Adds a transfer to the list of transfers.
     * @param accountName the account name of the account that made/recieved the transfer.
     * @param amount the amount of the transfer.
     * @param date the date of the transfer.
     * @param transferType the type of the transfer. A for adding to an account, B for transfering between accounts.
     */
    public Transfers(String accountName, double amount, String date, char transferType){
        this.accountName = accountName;
        this.date = date;
        this.amount = amount;
        this.transferType = transferType;
    }

    /**
     * Adds a transfer to the list of transfers.
     * @param account the account name of the account that made/recieved the transfer.
     * @param amount the amount of the transfer.
     * @param date the date of the transfer.
     * @param transferType the type of the transfer. A for adding to an account, B for transfering between accounts.
     */
    public void addTransfer(String account, double amount, String date, char transferType){
        Transfers newTransfer = new Transfers(account, amount, date, transferType);
        transfers.add(newTransfer);
    }

    /**
     * Returns the transfer type.
     * @return the transfer type.
     */
    public char getTransferType() {
        return transferType;
    }

    /**
     * Sets the transfer type.
     * @param transferType the transfer type.
     */
    public void setTransferType(char transferType) {
        this.transferType = transferType;
    }

    /**
     * Returns the account name.
     * @return the account name.
     */
    public List<Transfers> getTransfers() {
        return transfers;
    }

    /**
     * Sets the account name.
     * @param transfers the account name.
     */
    public void setTransfers(List<Transfers> transfers) {
        Transfers.transfers = transfers;
    }

    /**
     * Returns the account name.
     * @return the account name.
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * Sets the account name.
     * @param accountName the account name.
     */
    public void setAccount(String accountName) {
        this.accountName = accountName;
    }

    /**
     * Returns the date.
     * @return the date.
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets the date.
     * @param date the date.
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Returns the amount.
     * @return the amount.
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Sets the amount.
     * @param amount the amount.
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * The equals method for the Transfers class.
     * @param o the object to compare to.
     * @return true if the objects are equal, false if they are not.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transfers transfers = (Transfers) o;
        return Double.compare(transfers.amount, amount) == 0 && transferType == transfers.transferType && Objects.equals(accountName, transfers.accountName) && Objects.equals(date, transfers.date) && Objects.equals(type, transfers.type);
    }

    /**
     * The hashCode method for the Transfers class.
     * @return the hashcode of the object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(accountName, date, amount, transferType, type);
    }
}
