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
import javafx.scene.text.TextAlignment;

import javax.mail.MessagingException;

import static javafx.scene.text.Font.font;


public class Settings {
  public static VBox settingsView() {

    VBox vbox = new VBox();

    HBox currentEmailHbox = new HBox();

    VBox currentStatus = new VBox();
    Text currentEmailText = new Text("Current email: ");
    currentEmailText.setTextAlignment(TextAlignment.RIGHT);
    Text currentPasswordText = new Text("Current password: ");
    currentPasswordText.setTextAlignment(TextAlignment.RIGHT);
    currentStatus.getChildren().addAll(currentEmailText, currentPasswordText);

    VBox currentPassword = new VBox();
    Text currentEmailLabel = new Text("eliastrana@gmail.com"); //eksempel email, legg inn variabel
    currentEmailLabel.setTextAlignment(TextAlignment.LEFT);
    Text currentPasswordLabel = new Text("12345"); //Eksempel passord, legg inn variabel
    currentPasswordLabel.setTextAlignment(TextAlignment.LEFT);
    currentPassword.getChildren().addAll(currentEmailLabel, currentPasswordLabel);

    currentEmailHbox.getChildren().addAll(currentStatus, currentPassword);
    currentEmailHbox.setAlignment(Pos.CENTER);

    HBox emailUpdateHbox = new HBox();

    VBox updateEmail = new VBox();
    Text updateEmailText = new Text("Update email: ");
    Text updatePasswordText = new Text("Update password: ");
    updateEmail.getChildren().addAll(updateEmailText, updatePasswordText);


    VBox updatePassword = new VBox();
    TextField updateEmailTextField = new TextField();
    TextField updatePasswordTextField = new TextField();
    updatePassword.getChildren().addAll(updateEmailTextField, updatePasswordTextField);
    emailUpdateHbox.getChildren().addAll(updateEmail, updatePassword);
    emailUpdateHbox.setAlignment(Pos.CENTER);



    HBox updatePasswordHbox = new HBox();

    VBox currentAndNewPasswordHbox = new VBox();
    Text currentPasswordText2 = new Text("Enter current password: ");
    Text enterNewPasswordText = new Text("Enter new password: ");
    currentAndNewPasswordHbox.getChildren().addAll(currentPasswordText2, enterNewPasswordText);


    VBox currentAndNewPasswordInputFields = new VBox();
    TextField currentPasswordTextField = new TextField();
    TextField newPasswordTextField = new TextField();
    currentAndNewPasswordInputFields.getChildren().addAll(currentPasswordTextField, newPasswordTextField);


    updatePasswordHbox.getChildren().addAll(currentAndNewPasswordHbox, currentAndNewPasswordInputFields);
    updatePasswordHbox.setAlignment(Pos.CENTER);

    vbox.getChildren().addAll(currentEmailHbox, emailUpdateHbox, updatePasswordHbox);
    vbox.setSpacing(40);

    vbox.setAlignment(Pos.TOP_CENTER);
    return vbox;

  }
}
