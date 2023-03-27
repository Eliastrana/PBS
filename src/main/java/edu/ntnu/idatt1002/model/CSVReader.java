package edu.ntnu.idatt1002.model;

import edu.ntnu.idatt1002.frontend.GUI;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import static edu.ntnu.idatt1002.backend.Accounts.accounts;

public class CSVReader {
    private static String CSV_FILE_PATH = "src/main/resources/userfiles/" + GUI.getCurrentUser() + "/" + GUI.getCurrentUser() + "transfer.csv";
    private static final String CVS_SPLIT_BY = ",";

    public static HashMap<String, Double> readCSV() throws FileNotFoundException {
        HashMap<String, Double> newAccounts = new HashMap<>(); // Create a new instance of hashmap
        CSV_FILE_PATH = "src/main/resources/userfiles/" + GUI.getCurrentUser() + "/" + GUI.getCurrentUser() + "transfer.csv";
        File csvFile = new File(CSV_FILE_PATH);
        System.out.println("File path: " + csvFile.getAbsolutePath());
        if (!csvFile.exists()) {
            try {
                csvFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return newAccounts; // Return empty hashmap since file is empty
        }

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] account = line.split(CVS_SPLIT_BY);
                if (newAccounts.containsKey(account[0])) { // Use the new hashmap instance
                    newAccounts.put(account[0], newAccounts.get(account[0]) + Double.parseDouble(account[1]));
                } else {
                    newAccounts.put(account[0], Double.parseDouble(account[1]));
                }
            }
            return newAccounts; // Return the new hashmap instance
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
