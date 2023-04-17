package edu.ntnu.idatt1002.frontend.menu;

import com.itextpdf.text.DocumentException;
import edu.ntnu.idatt1002.frontend.GUI;
import edu.ntnu.idatt1002.frontend.utility.SoundPlayer;
import edu.ntnu.idatt1002.model.ExcelExporter;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Report {
  public static VBox reportView() {

    VBox reportLayout = new VBox();
    reportLayout.setAlignment(Pos.CENTER);
    reportLayout.setSpacing(20);

    Text printOutAReport = new Text("Print out a report");
    printOutAReport.setId("titleText");
    HBox selectReportHBox = new HBox();

//    ObservableList<String> options =
//            FXCollections.observableArrayList(
//                    "Result",
//                    "Balance"
//            );
//    final ComboBox reportType = new ComboBox(options);
//    reportType.setId("categoryMenuButton");
//    reportType.setPromptText("Select report type");
//    selectReportHBox.getChildren().addAll(reportType);
//    selectReportHBox.setAlignment(Pos.CENTER);
//    HBox selectMonthHBox = new HBox();
//    selectMonthHBox.setAlignment(Pos.CENTER);
//    DatePicker datePicker = new DatePicker();
//    datePicker.setValue(LocalDate.now());
//    datePicker.setShowWeekNumbers(true);
//    selectMonthHBox.getChildren().add(datePicker);

    HBox printOutVBox = new HBox();
    printOutVBox.setAlignment(Pos.CENTER);

    Button exportToPDF = new Button("Export to PDF");
    exportToPDF.setId("actionButton");

    exportToPDF.setOnAction(e -> {
      System.out.println("Exporting to PDF");
      try {

        ExcelExporter.exportToExcel();
        ExcelExporter.convertToPdf(ExcelExporter.exportToExcel(), "report");
      } catch (DocumentException | IOException ex) {
        throw new RuntimeException(ex);
      }

      if (Desktop.isDesktopSupported()) {
        try {
          File myFile =
              new File("src/main/resources/userfiles/" + GUI.getCurrentUser() + "/" + GUI.getCurrentUser() + "report.pdf");
          Desktop.getDesktop().open(myFile);
        } catch (IOException ex) {
          // no application registered for PDFs
        }
      }
      SoundPlayer.play("src/main/resources/16bitconfirm.wav");

    });

    //THIS ONE DOES NOT WORK BUT EMIL IS SICK SO WE DONT KNOW WHAT TO DO
    Button printToExcel = new Button("Print to Excel");
    printToExcel.setId("actionButton");
    printToExcel.setOnAction(e -> {
      System.out.println("Exporting to PDF");
      String excelFile = null;
      try {

        excelFile = ExcelExporter.exportToExcel();
      } catch (IOException ex) {
        throw new RuntimeException(ex);
      }

      if (Desktop.isDesktopSupported()) {
        try {
          File myFile =
                  new File(excelFile);
          Desktop.getDesktop().open(myFile);
        } catch (IOException ex) {
          // no application registered for PDFs
        }
      }
      SoundPlayer.play("src/main/resources/16bitconfirm.wav");
    });

    printOutVBox.getChildren().addAll(exportToPDF, printToExcel);
    printOutVBox.setSpacing(30);
    reportLayout.getChildren().addAll(printOutAReport, selectReportHBox, printOutVBox);
    reportLayout.setSpacing(30);
    reportLayout.setAlignment(Pos.TOP_CENTER);

    return reportLayout;

  }
}
