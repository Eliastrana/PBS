package edu.ntnu.idatt1002;

import java.io.*;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
public class ExcelExporter {
    static String inputFile = "src/main/resources/logger.txt";
    String outputFile = "output.xlsx";

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
}
