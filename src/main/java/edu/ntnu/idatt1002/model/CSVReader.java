package edu.ntnu.idatt1002.model;

import edu.ntnu.idatt1002.backend.Expense;
import edu.ntnu.idatt1002.backend.Transfers;
import edu.ntnu.idatt1002.frontend.GUI;
import edu.ntnu.idatt1002.frontend.utility.timeofdaychecker;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * A class that reads a csv file.
 */
public class CSVReader {
    /**
     * The path to the csv file.
     */
    private static String CSV_FILE_PATH_1;
    /**
     * The path to the csv file.
     */
    private static String CSV_FILE_PATH_2;
    /**
     * The delimiter used in the csv file.
     */
    private static final String CVS_SPLIT_BY = ",";
    /**
     * The path to the output directory.
     */
    private static String outPutDirectory;

    /**
     * A method that reads a csv file.
     *
     * @return a map of expenses
     * @throws IOException the io exception
     */
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

    /**
     * A method that reads a csv file and returns a list of transfers.
     *
     * @return a list of transfers
     * @throws IOException the io exception
     */
    public static List<Transfers> listOfTransfers() throws IOException {
        CSV_FILE_PATH_1 = "src/main/resources/userfiles/" + GUI.getCurrentUser() + "/" + GUI.getCurrentUser() + "transfer.csv";
        Transfers transfers = new Transfers("listConstructor");
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
                char transferType = account[3].charAt(0);
                transfers.addTransfer(account[0], Double.parseDouble(account[1]), account[2], transferType);
            }
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return transfers.transfersList();
    }

    /**
     * A method that reads a csv file and returns a list of expenses.
     *
     * @return a list of expenses
     */
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

    /**
     * A method that updates the rows that are different in the table.
     *
     * @param expensesInTable  the expenses in table
     * @param expensesFromFile the expenses from file
     */
    public static void updateRowsThatAreDifferentInTable(List<Expense> expensesInTable,
                                                         List<Expense> expensesFromFile) {
        List<Expense> expensesToBeUpdated = new ArrayList<>();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE_PATH_2))) {
            for (Expense expenseFromFile : expensesFromFile) {
                if (!(expenseFromFile.getDate().getMonthValue() == LocalDate.now().getMonthValue())){
                    expensesToBeUpdated.add(expenseFromFile);
                }
            }
            for (Expense expenseInTable : expensesInTable) {
                expensesToBeUpdated.add(expenseInTable);
            }
            for (Expense expense : expensesToBeUpdated) {
                System.out.println(expense.getName() + " " + expense.getPrice() + " " + expense.getDate() + " " + expense.getCategory() + " " + expense.getAccount());
            }
            for (Expense expenseToUpdatedFile : expensesToBeUpdated) {
                writer.write(expenseToUpdatedFile.getCategory() + ",|" + expenseToUpdatedFile.getName() + "|," + expenseToUpdatedFile.getDate() + "," + expenseToUpdatedFile.getPrice() + "," + expenseToUpdatedFile.getAccount());
                writer.newLine();
            }
            writer.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * A method that removes a transfer from the csv file.
     *
     * @param transfersListInTable the transfers list in table
     */
    public static void removeTransfer(List<Transfers> transfersListInTable){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE_PATH_1))) {
            for (Transfers transfer : transfersListInTable) {
                writer.write(String.format(Locale.US, "%s,%.2f,%s,%c\n", transfer.getAccountName(), transfer.getAmount(), transfer.getDate(), transfer.getTransferType()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
