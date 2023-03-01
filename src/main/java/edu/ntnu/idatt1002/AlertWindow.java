package edu.ntnu.idatt1002;

import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AlertWindow {


    public static void emptyFieldAlert() {

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Empty Field Alert");
        dialog.setHeaderText(null);
        dialog.setResizable(false);
        dialog.setWidth(300);
        dialog.setHeight(200);

        // Create a label with the message
        Label message = new Label("You have left a field empty");
        message.setStyle("-fx-font-size: 20px;");

        // Create a vertical box to hold the label and the button
        VBox content = new VBox(message);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(10, 10, 10, 10));

        // Create an "OK" button
        ButtonType okButton = new ButtonType("OK");

        // Add the "OK" button to the dialog's button types
        dialog.getDialogPane().getButtonTypes().add(okButton);

        // Style the "OK" button using CSS
        String okButtonStyle = "-fx-font-size: 30px; -fx-min-width: 60px; -fx-min-height: 30px;-fx-background-color: #9FB8AD; -fx-border-width: 2; -fx-padding: 10px; -fx-background-radius: 0.5em;";
        Button okButtonNode = (Button) dialog.getDialogPane().lookupButton(okButton);
        okButtonNode.setStyle(okButtonStyle);
        okButtonNode.setAlignment(Pos.CENTER);

        // Add the "OK" button to the vertical box
        content.getChildren().add(okButtonNode);

        // Set the spacing between the label and the button to help center the button within the dialog box
        content.setSpacing(20);

        // Set the content of the dialog to the vertical box
        dialog.getDialogPane().setContent(content);

        // Show the dialog and wait for the user to respond
        dialog.showAndWait();
    }




}
