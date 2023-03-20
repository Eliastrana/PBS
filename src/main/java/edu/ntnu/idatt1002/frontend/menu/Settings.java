//SETTINGS PANE

package edu.ntnu.idatt1002.frontend.menu;

import com.itextpdf.text.DocumentException;
import edu.ntnu.idatt1002.frontend.Email;
import edu.ntnu.idatt1002.frontend.utility.SoundPlayer;
import edu.ntnu.idatt1002.model.ExcelExporter;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;

import javax.mail.MessagingException;

import static javafx.scene.text.Font.font;


public class Settings {
  public static VBox settingsView() {
    VBox vbox = new VBox();

    Text settingsText = new Text("Settings");

    VBox currentStatus = new VBox();

    HBox currentEmailHbox = new HBox();
    Text currentEmailText = new Text("Current email: ");
    currentEmailText.setStyle("-fx-fill: #3F403F");
    currentEmailText.setFont(font("helvetica", FontWeight.BOLD, FontPosture.REGULAR, 30));

    Label currentEmailLabel = new Label("eliastrana@gmail.com"); //eksempel email, legg inn variabel
    currentEmailLabel.setStyle("-fx-fill: #3F403F");
    currentEmailLabel.setFont(font("helvetica", FontWeight.BOLD, FontPosture.REGULAR, 30));

    currentEmailHbox.getChildren().addAll(currentEmailText, currentEmailLabel);

    HBox currentPassword = new HBox();
    Text currentPasswordText = new Text("Current password: ");
    currentPasswordText.setStyle("-fx-fill: #3F403F");
    currentPasswordText.setFont(font("helvetica", FontWeight.BOLD, FontPosture.REGULAR, 30));

    Label currentPasswordLabel = new Label("12345"); //Eksempel passord, legg inn variabel
    currentPassword.getChildren().addAll(currentPasswordText, currentPasswordLabel);

    currentStatus.getChildren().addAll(currentEmailHbox, currentPassword);

    HBox emailUpdateHbox = new HBox();

    VBox updateEmail = new VBox();
    Text updateEmailText = new Text("Update email: ");
    updateEmailText.setStyle("-fx-fill: #3F403F");
    updateEmailText.setFont(font("helvetica", FontWeight.BOLD, FontPosture.REGULAR, 30));

    Text updatePasswordText = new Text("Update password: ");
    updatePasswordText.setStyle("-fx-fill: #3F403F");
    updatePasswordText.setFont(font("helvetica", FontWeight.BOLD, FontPosture.REGULAR, 30));

    updateEmail.getChildren().addAll(updateEmailText, updatePasswordText);

    VBox updatePassword = new VBox();
    TextField updateEmailTextField = new TextField();
    TextField updatePasswordTextField = new TextField();
    updatePassword.getChildren().addAll(updateEmailTextField, updatePasswordTextField);

    Button updateButton = new Button("Confirm");
    emailUpdateHbox.getChildren().addAll(updateEmail, updatePassword, updateButton);


    HBox updatePasswordHbox = new HBox();

    VBox currentAndNewPasswordHbox = new VBox();
    Text currentPasswordText2 = new Text("Enter current password: ");
    currentPasswordText2.setStyle("-fx-fill: #3F403F");
    currentPasswordText2.setFont(font("helvetica", FontWeight.BOLD, FontPosture.REGULAR, 30));

    Text enterNewPasswordText = new Text("Enter new password: ");
    enterNewPasswordText.setStyle("-fx-fill: #3F403F");
    enterNewPasswordText.setFont(font("helvetica", FontWeight.BOLD, FontPosture.REGULAR, 30));
    currentAndNewPasswordHbox.getChildren().addAll(currentPasswordText2, enterNewPasswordText);


    VBox currentAndNewPasswordInputFields = new VBox();
    TextField currentPasswordTextField = new TextField();
    TextField newPasswordTextField = new TextField();
    currentAndNewPasswordInputFields.getChildren().addAll(currentPasswordTextField, newPasswordTextField);


    Button confirmPasswordUpdate = new Button("Confirm");


      confirmPasswordUpdate.setStyle("-fx-background-color: #3F403F");


    updatePasswordHbox.getChildren().addAll(currentAndNewPasswordHbox, currentAndNewPasswordInputFields, confirmPasswordUpdate);


    vbox.getChildren().addAll(settingsText,currentStatus, emailUpdateHbox, updatePasswordHbox);
    vbox.setSpacing(40);

    vbox.setAlignment(Pos.TOP_CENTER);
    return vbox;

  }
}
