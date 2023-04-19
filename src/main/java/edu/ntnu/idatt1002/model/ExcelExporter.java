package edu.ntnu.idatt1002.model;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfWriter;
import edu.ntnu.idatt1002.backend.Expense;
import edu.ntnu.idatt1002.frontend.GUI;
import edu.ntnu.idatt1002.frontend.utility.TimeOfDayChecker;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * A class that exports a csv file to excel.
 *
 * @author Emil J., Vegard J., Sander S. & Elias T.
 * @version 0.5 - 19.04.2023
 */
public class ExcelExporter {
    /**
     * The Output directory.
     */
    static String outputDirectory = "src/main/resources/userfiles/" + GUI.getCurrentUser() + "/";
    /**
     * The Output directory file.
     */
    static File outputDirectoryFile = new File(outputDirectory);
    /**
     * The Input file.
     */
    static String inputFile = outputDirectory + GUI.getCurrentUser() + ".csv";
    /**
     * The Output file.
     */
    static String outputFile = outputDirectory + GUI.getCurrentUser() + ".xlsx";
    /**
     * The Output file 1.
     */
    static String outputFile1 = outputDirectory + GUI.getCurrentUser() + ".pdf";
    /**
     * The Output file 2.
     */
    static String outputFile2 = outputDirectory + GUI.getCurrentUser() + "_" + "bankstatement.xlsx";
    /**
     * The Output file 3.
     */
    static String outputFile3 = outputDirectory + GUI.getCurrentUser() + "_" + "bankstatement" +
            ".pdf";   //Need to rename all outputfiles to be unique
    /**
     * The constant expensesToTable.
     */
    public static List<Expense> expensesToTable = new ArrayList<>();
    public static ExcelExporter instance = new ExcelExporter();

    private ExcelExporter() {
    }

    public static ExcelExporter getInstance() {
        return instance;
    }

    /**
     * A method that exports a csv file to excel.
     *
     * @return the string of the output file
     * @throws FileNotFoundException the file not found exception
     */
    public String exportToExcel() throws FileNotFoundException {
        if (!outputDirectoryFile.exists()) {
            outputDirectoryFile.mkdirs();
        }
        File inputFileObj = new File(inputFile);
        if (inputFileObj.length() == 0) {
            // Create empty workbook with default sheet
            Workbook workbook = new XSSFWorkbook();
            workbook.createSheet(TimeOfDayChecker.getCurrentMonth());
            try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
                workbook.write(outputStream);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            try (BufferedReader br = new BufferedReader(new FileReader(inputFile));
                 Workbook workbook = new XSSFWorkbook()) {

                String line;

                while ((line = br.readLine()) != null) {

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

                    String month = TimeOfDayChecker.getSelectedMonth(columns[2]);
                    String category = columns[0];
                    String name = columns[1];
                    String date = columns[2];
                    String price = columns[3];
                    String accountName = columns[4];

                    Sheet sheet = workbook.getSheet(month);
                    if (sheet == null) {
                        // Create new sheet and header row
                        sheet = workbook.createSheet(month);
                        Row headerRow = sheet.createRow(0);
                        String[] headerColumns = {"Category", "Name", "Date", "Price", "Account"};
                        for (int i = 0; i < headerColumns.length; i++) {
                            Cell cell = headerRow.createCell(i);
                            cell.setCellValue(headerColumns[i]);
                        }
                    }

                    // Create new data row
                    Row dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
                    Cell categoryCell = dataRow.createCell(0);
                    categoryCell.setCellValue(category);
                    Cell nameCell = dataRow.createCell(1);
                    nameCell.setCellValue(name);
                    Cell dateCell = dataRow.createCell(2);
                    dateCell.setCellValue(date);
                    Cell priceCell = dataRow.createCell(3);
                    priceCell.setCellValue(Double.parseDouble(price));
                    Cell accountNameCell = dataRow.createCell(4);
                    accountNameCell.setCellValue(accountName);
                }
                // Calculate monthly total outside of the loop
                for (Sheet sheet : workbook) {
                    double monthlyTotal = 0;
                    for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                        Row row = sheet.getRow(i);
                        if (row != null) {
                            Cell priceCell = row.getCell(3);
                            if (priceCell != null && priceCell.getCellType() == CellType.NUMERIC) {
                                monthlyTotal += priceCell.getNumericCellValue();
                            }
                            if (priceCell != null && priceCell.getCellType().equals(CellType.STRING)) {
                                monthlyTotal += Double.parseDouble(priceCell.getStringCellValue());
                            }
                        }
                    }

                    // Write monthly total to cell at the bottom of the sheet
                    Row totalRow = sheet.createRow(sheet.getLastRowNum() + 1);
                    Cell totalLabelCell = totalRow.createCell(0);
                    totalLabelCell.setCellValue("Monthly total: ");
                    Cell totalValueCell = totalRow.createCell(1);
                    totalValueCell.setCellValue(monthlyTotal);
                }

                workbook.write(new FileOutputStream(outputFile));

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return outputFile;
    }

    /**
     * A method that exports an Excel file to pdf.
     *
     * @param excelFile the Excel file
     * @param fileName  the file name
     * @throws IOException       the io exception
     * @throws DocumentException the document exception
     */
    public void convertToPdf(String excelFile, String fileName) throws IOException, DocumentException {
        try (Workbook workbook = new XSSFWorkbook(new FileInputStream(excelFile));
             FileOutputStream fos = new FileOutputStream(outputDirectory + GUI.getCurrentUser() + fileName + ".pdf")){

            Document document = new Document();
            PdfWriter.getInstance(document, fos);
            document.setPageSize(PageSize.A4.rotate());
            document.open();

            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                Sheet sheet = workbook.getSheetAt(i);
                for (Row row : sheet) {
                    com.itextpdf.text.pdf.PdfPTable table = new com.itextpdf.text.pdf.PdfPTable(row.getLastCellNum());
                    float[] columnWidths = new float[row.getLastCellNum()];
                    for (int j = 0; j < row.getLastCellNum(); j++) {
                        Cell cell = row.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                        com.itextpdf.text.Font font1 = new com.itextpdf.text.Font();
                        if (row.getRowNum() == 0) {
                            font1.setStyle(com.itextpdf.text.Font.BOLD);
                        }
                        PdfPCell pdfPCell = new PdfPCell(new Phrase(cell.toString(), font1));
                        table.addCell(pdfPCell);
                        columnWidths[j] = sheet.getColumnWidthInPixels(j);
                    }
                    table.setWidths(columnWidths);
                    document.add(table);
                }
                document.add(new Paragraph("\n"));
            }
            document.close();
        }
    }

    /**
     * A method that creates a bank statement based on the category and date range.
     *
     * @param account  the account name
     * @param category the category name
     * @param dateFrom the date from
     * @param dateTo   the date to
     * @return the file name
     * @throws IOException       the io exception
     * @throws DocumentException the document exception
     */
    public String createBankStatement(String account, String category, String dateFrom, String dateTo) throws IOException, DocumentException {
        // Read CSV file
        BufferedReader csvReader = new BufferedReader(new FileReader(inputFile));
        String row;
        List<String[]> rows = new ArrayList<>();
        while ((row = csvReader.readLine()) != null) {
            String[] data = row.split(",");
            rows.add(data);
        }
        csvReader.close();

        // Filter rows based on category and date range
        List<String[]> filteredRows = new ArrayList<>();
        for (String[] data : rows) {
            String date = data[2];
            if (data[0].equals(category) && data[4].equals(account) && date.compareTo(dateFrom) >= 0 && date.compareTo(dateTo) <= 0) {
                filteredRows.add(data);
            }
        }

        // Write to Excel file
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Sheet1");
        int rowNum = 0;
        Row headerRow = sheet.createRow(rowNum++);
        String[] headerColumns = {"Category", "Name", "Date", "Price", "Account"};
        int colNum = 0;
        for (String header : headerColumns) {
            Cell cell = headerRow.createCell(colNum++);
            cell.setCellValue(header);
        }
        for (String[] data : filteredRows) {
            Row row1 = sheet.createRow(rowNum++);
            colNum = 0;
            for (String cellData : data) {
                Cell cell = row1.createCell(colNum++);
                if (cellData instanceof String) {
                    cell.setCellValue((String) cellData);
                }
            }
        }
        FileOutputStream outputStream = new FileOutputStream(outputFile2);
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();

        return outputFile2;
    }

    /**
     * A method that gets the expenses for the current month.
     *
     * @return the expenses for month
     */
    public List<Expense> getExpensesForMonth(){
        List<Expense> expenses = new ArrayList<>();
        String currentMonth = TimeOfDayChecker.getCurrentMonth();
        try{
            exportToExcel();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try (Workbook workbook = new XSSFWorkbook(new FileInputStream(outputFile))) {{
            Sheet sheet = workbook.getSheet(currentMonth);
            if (sheet == null){
                sheet = workbook.createSheet(currentMonth);
            }
            for (int i = 0; i < sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row.getRowNum() == 0) {
                    // Skip header row
                    continue;
                }
                Cell categoryCell = row.getCell(0);
                String category = "";
                if (categoryCell != null) {
                    if (categoryCell.getCellType() == CellType.STRING) {
                        category = categoryCell.getStringCellValue();
                    } else if (categoryCell.getCellType() == CellType.NUMERIC) {
                        category = String.valueOf(categoryCell.getNumericCellValue());
                    }
                }

                Cell nameCell = row.getCell(1);
                String name = "";
                if (nameCell != null) {
                    if (nameCell.getCellType() == CellType.STRING) {
                        name = nameCell.getStringCellValue();
                    } else if (nameCell.getCellType() == CellType.NUMERIC) {
                        name = String.valueOf(nameCell.getNumericCellValue());
                    }
                }

                Cell dateCell = row.getCell(2);
                LocalDate date = null;
                if (dateCell != null && dateCell.getCellType() == CellType.STRING) {
                    date = LocalDate.parse(dateCell.getStringCellValue());
                }

                Cell priceCell = row.getCell(3);
                Double price = null;
                if (priceCell != null) {
                    if (priceCell.getCellType() == CellType.NUMERIC) {
                        price = priceCell.getNumericCellValue();
                    } else if (priceCell.getCellType() == CellType.STRING) {
                        price = Double.parseDouble(priceCell.getStringCellValue());
                    }
                }

                Cell accountCell = row.getCell(4);
                String account = "";
                if (accountCell != null) {
                    if (accountCell.getCellType() == CellType.STRING) {
                        account = accountCell.getStringCellValue();
                    } else if (accountCell.getCellType() == CellType.NUMERIC) {
                        account = String.valueOf(accountCell.getNumericCellValue());
                    }
                }

                Expense expense = new Expense(name, price, date, category, account, uniqueID);
                expenses.add(expense);
                expensesToTable = expenses;
            }
        }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return expenses;
    }

    /**
     * A method that exports the expenses to an Excel file.
     */
    public static String uniqueID = TimeOfDayChecker.getCurrentMonth() + TimeOfDayChecker.getYear();

    /**
     * Returns the total of all expenses in the category food.
     *
     * @param expenses the expenses
     * @return the total of food
     */
    public double getTotalOfFood(List<Expense> expenses) {
        double totalFood = 0;
        for (Expense expense : expenses) {
            if (expense.getCategory().equals("Food") && expense.getUniqueID().equals(uniqueID)) {
                totalFood += expense.getPrice();
            }
        }
        return totalFood;
    }

    /**
     * Returns the total of all expenses in the category transportation.
     *
     * @param expenses the expenses
     * @return the total of transportation
     */
    public double getTotalOfTransportation(List<Expense> expenses){
        double totalTransportation = 0;
        for (Expense expense : expenses) {
            if (expense.getCategory().equals("Transportation") && expense.getUniqueID().equals(uniqueID)) {
                totalTransportation += expense.getPrice();
            }
        }
        return totalTransportation;
    }

    /**
     * Returns the total of all expenses in the category entertainment.
     *
     * @param expenses the expenses
     * @return the total of entertainment
     */
    public double getTotalOfEntertainment(List<Expense> expenses){
        double totalEntertainment = 0;
        for (Expense expense : expenses) {
            if (expense.getCategory().equals("Entertainment") && expense.getUniqueID().equals(uniqueID)) {
                totalEntertainment += expense.getPrice();
            }
        }
        return totalEntertainment;
    }

    /**
     * Returns the total of all expenses in the category clothing.
     *
     * @param expenses the expenses
     * @return the total of clothing
     */
    public double getTotalOfClothing(List<Expense> expenses){
        double totalClothing = 0;
        for (Expense expense : expenses) {
            if (expense.getCategory().equals("Clothing") && expense.getUniqueID().equals(uniqueID)) {
                totalClothing += expense.getPrice();
            }
        }
        return totalClothing;
    }

    /**
     * Returns the total of all expenses in the category other.
     *
     * @param expenses the expenses
     * @return the total of other
     */
    public double getTotalOfOther(List<Expense> expenses){
        double totalOther = 0;
        for (Expense expense : expenses) {
            if (expense.getCategory().equals("Other") && expense.getUniqueID().equals(uniqueID)) {
                totalOther += expense.getPrice();
            }
        }
        return totalOther;
    }

    /**
     * Returns the total of all expenses in the category rent.
     *
     * @param expenses the expenses
     * @return the total of rent
     */
    public double getTotalOfRent(List<Expense> expenses){
        double totalRent = 0;
        for (Expense expense : expenses) {
            if (expense.getCategory().equals("Rent") && expense.getUniqueID().equals(uniqueID)) {
                totalRent += expense.getPrice();
            }
        }
        return totalRent;
    }

    /**
     * Returns the total of all expenses.
     *
     * @return the total of all expenses
     */
    public double getMonthlyTotal(){
        double monthlyTotal = 0;
        monthlyTotal += getTotalOfClothing(expensesToTable);
        monthlyTotal += getTotalOfEntertainment(expensesToTable);
        monthlyTotal += getTotalOfFood(expensesToTable);
        monthlyTotal += getTotalOfOther(expensesToTable);
        monthlyTotal += getTotalOfRent(expensesToTable);
        monthlyTotal += getTotalOfTransportation(expensesToTable);
        return monthlyTotal;
    }
}