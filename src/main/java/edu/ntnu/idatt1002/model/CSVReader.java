package edu.ntnu.idatt1002.model;

import edu.ntnu.idatt1002.backend.Account;
import edu.ntnu.idatt1002.frontend.GUI;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static edu.ntnu.idatt1002.backend.Accounts.accounts;

public class CSVReader {
    private static String CSV_FILE_PATH_1 = "src/main/resources/userfiles/" + GUI.getCurrentUser() + "/" + GUI.getCurrentUser() + "transfer.csv";
    private static String CSV_FILE_PATH_2 = "src/main/resources/userfiles/" + GUI.getCurrentUser() + "/" + GUI.getCurrentUser() + ".csv";
    private static final String CVS_SPLIT_BY = ",";
    private static List<Account> listOfTransfers;

    public static HashMap<String, Double> readCSV() throws IOException {
        HashMap<String, Double> newAccounts = new HashMap<>(); // Create a new instance of hashmap
        CSV_FILE_PATH_1 = "src/main/resources/userfiles/" + GUI.getCurrentUser() + "/" + GUI.getCurrentUser() + "transfer.csv";
        CSV_FILE_PATH_2 = "src/main/resources/userfiles/" + GUI.getCurrentUser() + "/" + GUI.getCurrentUser() + ".csv";
        File csvFile1 = new File(CSV_FILE_PATH_1);
        File csvFile2 = new File(CSV_FILE_PATH_2);
        if (!csvFile1.exists() && !csvFile2.exists()) {
            try {
                csvFile1.createNewFile();
                csvFile2.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return newAccounts; // Return empty hashmap since both files are empty
        }

        try (BufferedReader br1 = new BufferedReader(new FileReader(csvFile1));
             BufferedReader br2 = new BufferedReader(new FileReader(csvFile2))) {
            String line;
            while ((line = br1.readLine()) != null) {
                String[] account = line.split(CVS_SPLIT_BY);
                if (newAccounts.containsKey(account[0])) { // Use the new hashmap instance
                    newAccounts.put(account[0], newAccounts.get(account[0]) + Double.parseDouble(account[1]));
                } else {
                    newAccounts.put(account[0], Double.parseDouble(account[1]));
                }
            }
            while ((line = br2.readLine()) != null) {
                String[] account = line.split(CVS_SPLIT_BY);
                for (String key : newAccounts.keySet()) {
                    if (key.equals(account[4])) {
                        newAccounts.put(key, newAccounts.get(key) - Double.parseDouble(account[3]));
                    }
                }
            } return newAccounts;
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Account> listOfTransfers() throws IOException {
        CSV_FILE_PATH_1 = "src/main/resources/userfiles/" + GUI.getCurrentUser() + "/" + GUI.getCurrentUser() + "transfer.csv";
        listOfTransfers = new ArrayList<>();
        File csvFile1 = new File(CSV_FILE_PATH_1);
        if (!csvFile1.exists()) {
            try {
                csvFile1.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try (BufferedReader br1 = new BufferedReader(new FileReader(csvFile1))) {
            String line;
            while ((line = br1.readLine()) != null) {
                String[] account = line.split(CVS_SPLIT_BY);
                listOfTransfers.add(new Account(account[0], Double.parseDouble(account[1]), account[2]));
            }
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return listOfTransfers;
    }
}
