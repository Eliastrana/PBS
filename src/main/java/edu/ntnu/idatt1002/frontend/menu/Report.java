package edu.ntnu.idatt1002.frontend.menu;

import com.itextpdf.text.DocumentException;
import edu.ntnu.idatt1002.frontend.utility.SoundPlayer;
import edu.ntnu.idatt1002.model.ExcelExporter;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Report {
  public static VBox reportView() {


    System.out.println("opening more window");
    VBox moreVBox = new VBox();
    moreVBox.getChildren().add(new Label("This is the more page"));

    Button exportToExcell = new Button("Export to Excel");
    exportToExcell.setStyle("-fx-font-size: 30px; -fx-min-width: 100px; -fx-min-height: 50px;-fx-background-color: #9FB8AD; -fx-border-width: 2; -fx-padding: 10px; -fx-background-radius: 0.5em;");

    exportToExcell.setOnAction(e -> {
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

    VBox vbox = new VBox(moreVBox, exportToExcell);
    vbox.setAlignment(Pos.TOP_CENTER);
    return vbox;

  }
}
