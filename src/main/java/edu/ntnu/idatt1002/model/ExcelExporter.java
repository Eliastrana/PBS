package edu.ntnu.idatt1002.model;

import java.io.*;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;


public class ExcelExporter {
    static String inputFile = "src/main/resources/logger.txt";
    static String outputFile = "output.xlsx";
    static String outputFile1 = "src/main/resources/output.pdf";

    public static void exportToExcel() throws FileNotFoundException {
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile));
             Workbook workbook = new XSSFWorkbook()) {

            Sheet sheet = workbook.createSheet("Sheet1");

            String line;

            int rowNumber = 0;
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
                        table.addCell(cell.toString());
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
