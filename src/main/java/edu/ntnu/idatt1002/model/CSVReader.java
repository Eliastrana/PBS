package edu.ntnu.idatt1002.model;

import edu.ntnu.idatt1002.backend.Account;
import edu.ntnu.idatt1002.backend.Expense;
import edu.ntnu.idatt1002.frontend.GUI;
import edu.ntnu.idatt1002.frontend.utility.timeofdaychecker;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class CSVReader {
    private static String CSV_FILE_PATH_1;
    private static String CSV_FILE_PATH_2;
    private static final String CVS_SPLIT_BY = ",";
    private static String outPutDirectory;
    private static List<Account> listOfTransfers;

    public static HashMap<String, Double> readCSV() throws IOException {
        HashMap<String, Double> newAccounts = new HashMap<>(); // Create a new instance of hashmap
        CSV_FILE_PATH_1 = "src/main/resources/userfiles/" + GUI.getCurrentUser() + "/" + GUI.getCurrentUser() + "transfer.csv";
        CSV_FILE_PATH_2 = "src/main/resources/userfiles/" + GUI.getCurrentUser() + "/" + GUI.getCurrentUser() + ".csv";
        outPutDirectory = "src/main/resources/userfiles/" + GUI.getCurrentUser() + "/";
        File csvFile1 = new File(CSV_FILE_PATH_1);
        File csvFile2 = new File(CSV_FILE_PATH_2);
        if (!csvFile1.exists() && !csvFile2.exists()) {
            try {
                File outPutDirectoryFile = new File(outPutDirectory);
                outPutDirectoryFile.mkdirs();
                csvFile1.createNewFile();
                csvFile2.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return newAccounts; // Return empty hashmap since both files are empty
        }

        if (!csvFile1.exists()) {
            try {
                csvFile1.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return newAccounts; // Return empty hashmap since both files are empty
        }

        if (!csvFile2.exists()) {
            try {
                csvFile2.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
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

    public static List<Expense> getExpensesFromCSV() {
        List<Expense> expensesFromFile = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE_PATH_2))) {
            String line;
            while ((line = reader.readLine()) != null) {

                List<String> columnsList = new ArrayList<>();
                boolean insideQuotes = false;
                StringBuilder sb = new StringBuilder();

                for (char c : line.toCharArray()) {
                    if (c == '|') {
                        insideQuotes = !insideQuotes;
                    } else if (c == ',' && !insideQuotes) {
                        columnsList.add(sb.toString());
                        sb = new StringBuilder();
                    } else {
                        sb.append(c);
                    }
                }

                columnsList.add(sb.toString()); // Add last column

                String[] columns = columnsList.toArray(new String[0]);

                for (int i = 0; i < columns.length; i++) {
                    if (columns[i].startsWith("|") && columns[i].endsWith("|")) {
                        // remove the bars from the string
                        columns[i] = columns[i].substring(1, columns[i].length() - 1);
                    }
                    // remove quotes from the string
                    columns[i] = columns[i].replaceAll("^\"|\"$", "");
                }

                String month = timeofdaychecker.getSelectedMonth(columns[2]);
                String category = columns[0];
                String name = columns[1];
                String date = columns[2];
                String price = columns[3];
                String accountName = columns[4];
                Expense expense = new Expense(name, Double.parseDouble(price), LocalDate.parse(date), category, accountName);
                expensesFromFile.add(expense);
            }
            return expensesFromFile;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

        public static void removeFromCSVFileIfNotInTable(List<Expense> expensesFromFile,
                                                     List<Expense> expensesInTable) {
        for (Expense expenseFromFile : expensesFromFile) {
            if (!expensesInTable.contains(expenseFromFile)) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE_PATH_2))) {
                    for (Expense e : expensesFromFile) {
                        if (!e.equals(expenseFromFile)) {
                            writer.write(e.getCategory() + "," + e.getName() + "," + e.getDate() + "," + e.getPrice() + "," + e.getAccount());
                            writer.newLine();
                        }
                    }
                    writer.flush();
                    expensesFromFile = Files.readAllLines(Paths.get(CSV_FILE_PATH_2)).stream()
                        .map(line -> {
                            List<String> columnsList = new ArrayList<>();
                            boolean insideQuotes = false;
                            StringBuilder sb = new StringBuilder();

                            for (char c : line.toCharArray()) {
                                if (c == '|') {
                                    insideQuotes = !insideQuotes;
                                } else if (c == ',' && !insideQuotes) {
                                    columnsList.add(sb.toString());
                                    sb = new StringBuilder();
                                } else {
                                    sb.append(c);
                                }
                            }

                            columnsList.add(sb.toString()); // Add last column

                            String[] columns = columnsList.toArray(new String[0]);

                            for (int i = 0; i < columns.length; i++) {
                                if (columns[i].startsWith("|") && columns[i].endsWith("|")) {
                                    // remove the bars from the string
                                    columns[i] = columns[i].substring(1, columns[i].length() - 1);
                                }
                                // remove quotes from the string
                                columns[i] = columns[i].replaceAll("^\"|\"$", "");
                            }

                            String month = timeofdaychecker.getSelectedMonth(columns[2]);
                            String category = columns[0];
                            String name = columns[1];
                            String date = columns[2];
                            String price = columns[3];
                            String accountName = columns[4];

                            return new Expense(name, Double.parseDouble(price), LocalDate.parse(date), category, accountName);
                        })
                        .collect(Collectors.toList());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public static void updateRowsThatAreDifferentInTable(List<Expense> expensesInTable,
                                                         List<Expense> expensesFromFile) {
        List<Expense> updatedExpenses = new ArrayList<>();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE_PATH_2))) {
            for (Expense e : expensesInTable) {
                for (Expense expenseInFile : expensesFromFile) {
                    if (e.equals(expenseInFile)) {
                        e.setCategoryAsString(expenseInFile.getCategory());
                        e.setName(expenseInFile.getName());
                        e.setDate(expenseInFile.getDate());
                        e.setPrice(expenseInFile.getPrice());
                        e.setAccountAsString(expenseInFile.getAccount());
                    } else if (expenseInFile.getDate().getMonthValue() != LocalDate.now().getMonthValue()){
                        writer.write(expenseInFile.getCategory() + "," + "|" + expenseInFile.getName() + "|" + "," + expenseInFile.getDate() +
                            "," + expenseInFile.getPrice() + "," + expenseInFile.getAccount());
                        writer.newLine();
                    }
                }
                LocalDate currentDate = LocalDate.now();
                int currentMonth = currentDate.getMonthValue();
                int expenseMonth = e.getDate().getMonthValue();
                if (currentMonth == expenseMonth) {
                    writer.write(e.getCategory() + "," + "|" + e.getName() + "|" + "," + e.getDate() +
                        "," + e.getPrice() + "," + e.getAccount());
                    writer.newLine();
                    updatedExpenses.add(e);
                }
            }
            writer.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
