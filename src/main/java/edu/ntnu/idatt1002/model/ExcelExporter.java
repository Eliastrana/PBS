package edu.ntnu.idatt1002.model;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import edu.ntnu.idatt1002.backend.Expense;
import edu.ntnu.idatt1002.frontend.utility.timeofdaychecker;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.itextpdf.text.pdf.PdfWriter;


public class ExcelExporter {
    static String inputFile = "src/main/resources/logger.txt";
    static String outputFile = "src/main/resources/output.xlsx";
    static String outputFile1 = "src/main/resources/output.pdf";
    public static List<Expense> expensesToTable = new ArrayList<>();

    public static void exportToExcel() throws FileNotFoundException {
        File inputFileObj = new File(inputFile);
        if (inputFileObj.length() == 0) {
            // Create empty workbook with default sheet
            Workbook workbook = new XSSFWorkbook();
            workbook.createSheet("Sheet1");
            try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
                workbook.write(outputStream);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else
            try (BufferedReader br = new BufferedReader(new FileReader(inputFile));
                 Workbook workbook = new XSSFWorkbook()) {

                String line;

                while ((line = br.readLine()) != null) {

                    String[] columns = line.split(",");
                    String month = timeofdaychecker.getSelectedMonth(columns[2]);

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
                    for (int i = 0; i < columns.length; i++) {
                        Cell cell = dataRow.createCell(i);
                        cell.setCellValue(columns[i]);
                    }

                }

                workbook.write(new FileOutputStream(outputFile));

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
    }
    public static void convertToPdf() throws IOException, DocumentException {
        try (Workbook workbook = new XSSFWorkbook(new FileInputStream(outputFile));
             FileOutputStream fos = new FileOutputStream(outputFile1)) {

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
    public static List<Expense> getExpensesForMonth(){
        List<Expense> expenses = new ArrayList<>();
        String currentMonth = timeofdaychecker.getCurrentMonth();
        try (Workbook workbook = new XSSFWorkbook(new FileInputStream(outputFile))) {
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                Sheet sheet = workbook.getSheet(currentMonth);
                if (sheet == null){
                    sheet = workbook.createSheet(currentMonth);
                }
                for (Row row : sheet) {
                    if (row.getRowNum() == 0) {
                        // Skip header row
                        continue;
                    }
                    String name = row.getCell(1).getStringCellValue();
                    Double price = Double.valueOf(row.getCell(3).getStringCellValue());
                    LocalDate date = LocalDate.parse(row.getCell(2).getStringCellValue());
                    String category = row.getCell(0).getStringCellValue();
                    String account = row.getCell(4).getStringCellValue();
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

    public static String uniqueID = timeofdaychecker.getCurrentMonth() + timeofdaychecker.getYear();

    public static double getTotalOfFood(List<Expense> expenses) {
        double totalFood = 0;
        for (Expense expense : expenses) {
            if (expense.getCategory().equals("Food") && expense.getUniqueID().equals(uniqueID)) {
                totalFood += expense.getPrice();
            }
        }
        return totalFood;
    }
    public static double getTotalOfTransportation(List<Expense> expenses){
        double totalTransportation = 0;
        for (Expense expense : expenses) {
            if (expense.getCategory().equals("Transportation") && expense.getUniqueID().equals(uniqueID)) {
                totalTransportation += expense.getPrice();
            }
        }
        return totalTransportation;
    }
    public static double getTotalOfEntertainment(List<Expense> expenses){
        double totalEntertainment = 0;
        for (Expense expense : expenses) {
            if (expense.getCategory().equals("Entertainment") && expense.getUniqueID().equals(uniqueID)) {
                totalEntertainment += expense.getPrice();
            }
        }
        return totalEntertainment;
    }
    public static double getTotalOfClothing(List<Expense> expenses){
        double totalClothing = 0;
        for (Expense expense : expenses) {
            if (expense.getCategory().equals("Clothing") && expense.getUniqueID().equals(uniqueID)) {
                totalClothing += expense.getPrice();
            }
        }
        return totalClothing;
    }
    public static double getTotalOfOther(List<Expense> expenses){
        double totalOther = 0;
        for (Expense expense : expenses) {
            if (expense.getCategory().equals("Other") && expense.getUniqueID().equals(uniqueID)) {
                totalOther += expense.getPrice();
            }
        }
        return totalOther;
    }
    public static double getTotalOfRent(List<Expense> expenses){
        double totalRent = 0;
        for (Expense expense : expenses) {
            if (expense.getCategory().equals("Rent") && expense.getUniqueID().equals(uniqueID)) {
                totalRent += expense.getPrice();
            }
        }
        return totalRent;
    }

    public static double getMonthlyTotal(){
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
