package edu.ntnu.idatt1002.backend;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Transfers {
    public static List<Transfers> transfers;
    private String accountName;
    private String date;
    private double amount;
    private char transferType;
    private String type;

    public Transfers(String type){
        transfers = new ArrayList<Transfers>();
        this.type = type;
    }

    Transfers(){};

    public List<Transfers> transfersList(){
        return transfers;
    }

    public Transfers(String accountName, double amount, String date, char transferType){
        this.accountName = accountName;
        this.date = date;
        this.amount = amount;
        this.transferType = transferType;
    }

    public static void addTransfer(String account, double amount, String date, char transferType){
        Transfers newTransfer = new Transfers(account, amount, date, transferType);
        transfers.add(newTransfer);
    }

    public char getTransferType() {
        return transferType;
    }

    public void setTransferType(char transferType) {
        this.transferType = transferType;
    }

    public static List<Transfers> getTransfers() {
        return transfers;
    }

    public static void setTransfers(List<Transfers> transfers) {
        Transfers.transfers = transfers;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccount(String accountName) {
        this.accountName = accountName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transfers transfers = (Transfers) o;
        return Double.compare(transfers.amount, amount) == 0 && transferType == transfers.transferType && Objects.equals(accountName, transfers.accountName) && Objects.equals(date, transfers.date) && Objects.equals(type, transfers.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountName, date, amount, transferType, type);
    }
}
