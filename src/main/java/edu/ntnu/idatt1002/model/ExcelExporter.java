package edu.ntnu.idatt1002.model;

import java.io.*;

import com.itextpdf.text.pdf.PdfPCell;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;


public class ExcelExporter {
    static String inputFile = "src/main/resources/logger.txt";
    static String outputFile = "src/main/resources/output.xlsx";
    static String outputFile1 = "src/main/resources/output.pdf";

    public static void exportToExcel() throws FileNotFoundException {
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile));
             Workbook workbook = new XSSFWorkbook()) {

            Sheet sheet = workbook.createSheet("Sheet1");

            Row categoryRow = sheet.createRow(0);
            String[] categories = {"Category", "Name", "Date", "Price", "Account"}; // Replace with your own category names
            Font font = workbook.createFont();
            font.setFontName("Arial");
            font.setBold(true);
            CellStyle boldStyle = workbook.createCellStyle();
            boldStyle.setFont(font);
            for (int i = 0; i < categories.length; i++) {
                Cell cell = categoryRow.createCell(i);
                cell.setCellValue(categories[i]);
                cell.setCellStyle(boldStyle);
            }

            String line;

            int rowNumber = 1; // Start at row 1 since row 0 is used for categories
            while ((line = br.readLine()) != null) {
                Row row = sheet.createRow(rowNumber++);

                String[] columns = line.split(",");

                int columnNumber = 0;
                for (String column : columns) {
                    Cell cell = row.createCell(columnNumber++);
                    cell.setCellValue(column);
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
            }
            document.close();
        }
    }
}
