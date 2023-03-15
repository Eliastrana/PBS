//REPORT

package edu.ntnu.idatt1002.frontend.menu;

import com.itextpdf.text.DocumentException;
import edu.ntnu.idatt1002.frontend.utility.SoundPlayer;
import edu.ntnu.idatt1002.model.ExcelExporter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

import static javafx.scene.text.Font.font;

public class Report {
  public static VBox reportView() {

    VBox reportLayout = new VBox();
    reportLayout.setAlignment(Pos.CENTER);
    reportLayout.setSpacing(20);

    Text printOutAReport = new Text("Print out a report");
    printOutAReport.setStyle("-fx-fill: #3F403F");
    printOutAReport.setFont(font("helvetica", FontWeight.BOLD, FontPosture.REGULAR, 60));

    HBox selectReportHBox = new HBox();
    selectReportHBox.setAlignment(Pos.CENTER_LEFT);
    Text selectReportText = new Text("Select report type: ");

    ObservableList<String> options =
            FXCollections.observableArrayList(
                    "Result",
                    "Balance"
            );
    final ComboBox reportType = new ComboBox(options);
    reportType.setStyle("-fx-font-size: 20px; -fx-min-width: 100px; -fx-min-height: 50px;-fx-background-color: #9FB8AD; -fx-border-width: 2; -fx-padding: 10px; -fx-background-radius: 0.5em;");
    selectReportHBox.getChildren().addAll(selectReportText, reportType);

    HBox selectMonthHBox = new HBox();
    Text selectMonthText = new Text("Select month: ");
    DatePicker datePicker = new DatePicker();
    datePicker.setValue(LocalDate.now());
    datePicker.setShowWeekNumbers(true);
    datePicker.setStyle("-fx-font-size: 20px; -fx-min-width: 100px; -fx-min-height: 50px;-fx-background-color: #9FB8AD; -fx-border-width: 2; -fx-padding: 10px; -fx-background-radius: 0.5em;");
    selectMonthHBox.getChildren().addAll(selectMonthText, datePicker);



    HBox printOutVBox = new HBox();

    Button exportToPDF = new Button("Export to PDF");
    exportToPDF.setStyle("-fx-font-size: 30px; -fx-min-width: 100px; -fx-min-height: 50px;-fx-background-color: #9FB8AD; -fx-border-width: 2; -fx-padding: 10px; -fx-background-radius: 0.5em;");

    exportToPDF.setOnAction(e -> {
      System.out.println("Exporting to PDF");
      try {
        ExcelExporter.exportToExcel();
        ExcelExporter.convertToPdf();
      } catch (DocumentException | IOException ex) {
        throw new RuntimeException(ex);
      }

      if (Desktop.isDesktopSupported()) {
        try {
          File myFile = new File("src/main/resources/output.pdf");
          Desktop.getDesktop().open(myFile);
        } catch (IOException ex) {
          // no application registered for PDFs
        }
      }
      SoundPlayer.play("src/main/resources/16bitconfirm.wav");


    });


    Button printToExcel = new Button("Print to Excel");

    printOutVBox.getChildren().addAll(exportToPDF, printToExcel);


    reportLayout.getChildren().addAll(printOutAReport, selectReportHBox, selectMonthHBox, printOutVBox);
    reportLayout.setAlignment(Pos.TOP_CENTER);

    return reportLayout;

  }
}
