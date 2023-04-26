package edu.ntnu.idatt1002.frontend.menu;

import edu.ntnu.idatt1002.backend.user.UserHandling;
import edu.ntnu.idatt1002.frontend.GUI;
import edu.ntnu.idatt1002.frontend.utility.ContactUs;
import edu.ntnu.idatt1002.frontend.utility.FileUtil;
import edu.ntnu.idatt1002.frontend.utility.SoundPlayer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.io.IOException;
import java.util.Objects;

import static edu.ntnu.idatt1002.frontend.utility.AlertWindow.showAlert;

/**
 * A class that creates the settings view.
 *
 * @author Emil J., Vegard J., Sander S. and Elias T.
 * @version 0.5 - 19.04.2023
 */
public class Settings {
  private static String currentMode = "Lightmode";

  /**
   * A method that creates the settings view.
   * The method is used by the GUI class.
   *
   * @return the vertical box
   */
  public static VBox settingsView() {
    VBox vbox = new VBox();
    vbox.getChildren().clear();

    Label passwordLabel = new Label("Enter current password: ");
    passwordLabel.setId("smallTitle");
    PasswordField passwordField = new PasswordField();
    passwordField.setPromptText("Enter password");
    passwordField.setId("textField");
    passwordField.setMaxWidth(200);

    Button submitButton = new Button("Submit");

    passwordField.setOnKeyPressed(event -> {
      if (event.getCode() == KeyCode.ENTER) {
        submitButton.fire();
      }
    });

    submitButton.setOnKeyPressed(event -> {
      if (event.getCode() == KeyCode.ENTER) {
        submitButton.fire();
      }
    });
    submitButton.setId("actionButton");
    vbox.getChildren().addAll(passwordLabel, passwordField, submitButton);
    vbox.setSpacing(20);
    vbox.setPadding(new Insets(20, 20, 20, 20));


    submitButton.setOnAction(event -> {
      try {
        if (Objects.equals(passwordField.getText(), UserHandling.getPassword())) {

          Text title = new Text("Settings");
          title.setId("titleText");
          Text currentEmailText = new Text("Current email: ");
          currentEmailText.setId("bodyText");
          currentEmailText.setTextAlignment(TextAlignment.RIGHT);
          Text currentPasswordText = new Text("Current password: ");
          currentPasswordText.setId("bodyText");
          currentPasswordText.setTextAlignment(TextAlignment.RIGHT);

          VBox currentStatus = new VBox();
          currentStatus.getChildren().addAll(currentEmailText, currentPasswordText);
          currentStatus.setAlignment(Pos.TOP_RIGHT);

          Text currentEmailLabel = new Text(UserHandling.getEmail());
          currentEmailLabel.setId("bodyText");
          currentEmailLabel.setVisible(false);
          currentEmailLabel.setTextAlignment(TextAlignment.LEFT);
          Text currentPasswordLabel = new Text(UserHandling.getPassword());
          currentPasswordLabel.setVisible(false);
          currentPasswordLabel.setId("bodyText");

          ImageView visibilityImage = new ImageView(new Image("icons/visibility.png"));
          visibilityImage.setFitHeight(20);
          visibilityImage.setFitWidth(20);

          VBox currentPassword = new VBox();

          Button showPrivateInformation = new Button();
          showPrivateInformation.setOnAction(e -> {
            currentPassword.getChildren().remove(currentPasswordLabel);
            currentPassword.getChildren().add(currentPasswordLabel);
            currentPassword.getChildren().remove(currentEmailLabel);
            currentPassword.getChildren().add(currentEmailLabel);

          });
          showPrivateInformation.setGraphic(visibilityImage);
          showPrivateInformation.setId("squareButton");

          showPrivateInformation.setOnAction(e -> {
            if (currentEmailLabel.isVisible() && currentPasswordLabel.isVisible()) {
              try {
                currentEmailLabel.setText(UserHandling.getEmail());
              } catch (IOException ex) {
                throw new RuntimeException("Could not get email");
              }
              currentPasswordLabel.setText(UserHandling.getPassword());
              currentEmailLabel.setVisible(false);
              currentPasswordLabel.setVisible(false);
              visibilityImage.setImage(new Image("icons/visibility.png"));
            } else {
              currentEmailLabel.setVisible(true);
              currentPasswordLabel.setVisible(true);
              visibilityImage.setImage(new Image("icons/notvisibility.png"));
            }
          });

          currentPassword.getChildren().addAll(currentEmailLabel, currentPasswordLabel);

          HBox currentEmailHbox = new HBox();

          currentEmailHbox.getChildren().addAll(currentStatus, currentPassword,
                  showPrivateInformation);

          currentEmailHbox.setSpacing(20);
          currentEmailHbox.setAlignment(Pos.CENTER);

          VBox updateEmail = new VBox();
          Text updateEmailText = new Text("Update email: ");
          updateEmailText.setId("bodyText");

          updateEmail.getChildren().addAll(updateEmailText);
          updateEmail.setAlignment(Pos.CENTER);

          VBox updatePassword = new VBox();
          TextField updateEmailTextField = new TextField();
          updateEmailTextField.setId("textField");
          updateEmailTextField.setPromptText("Enter new email");
          updatePassword.getChildren().addAll(updateEmailTextField);
          updatePassword.setAlignment(Pos.CENTER);


          VBox confirmEmailUpdateVbox = new VBox();
          Button confirmEmailUpdate = new Button("Confirm");

          confirmEmailUpdate.setOnAction(e -> {
            try {
              if (updateEmailTextField.getText().length() > 8
                      || updateEmailTextField.getText().length() < 20) {
                UserHandling.changeEmail(updateEmailTextField.getText());
                currentEmailLabel.setText(UserHandling.getEmail());
                SoundPlayer.play(FileUtil.getResourceFilePath("16bitconfirm.wav"));
                updateEmailTextField.clear();
              } else {
                throw new IllegalArgumentException("Email must be between 8 and 20 characters");
              }
            } catch (Exception ex) {
              SoundPlayer.play(FileUtil.getResourceFilePath("error.wav"));
              showAlert(ex.getMessage());
            }
          });

          confirmEmailUpdate.setId("actionButton");
          confirmEmailUpdateVbox.getChildren().addAll(confirmEmailUpdate);
          confirmEmailUpdateVbox.setAlignment(Pos.TOP_CENTER);

          HBox emailUpdateHbox = new HBox();

          emailUpdateHbox.getChildren().addAll(updateEmail, updatePassword, confirmEmailUpdateVbox);
          emailUpdateHbox.setSpacing(20);
          emailUpdateHbox.setAlignment(Pos.CENTER);

          VBox currentAndNewPasswordHbox = new VBox();
          Text currentPasswordText2 = new Text("Update password: ");
          currentPasswordText2.setId("bodyText");
          currentAndNewPasswordHbox.getChildren().addAll(currentPasswordText2);
          currentAndNewPasswordHbox.setAlignment(Pos.CENTER);

          VBox currentAndNewPasswordInputFields = new VBox();
          TextField newPasswordTextField = new TextField();
          newPasswordTextField.setId("textField");
          newPasswordTextField.setPromptText("Enter new password");
          currentAndNewPasswordInputFields.getChildren().addAll(newPasswordTextField);
          currentAndNewPasswordInputFields.setAlignment(Pos.CENTER);

          VBox confirmPasswordUpdateVBox = new VBox();
          Button confirmPasswordUpdate = new Button("Confirm");
          confirmPasswordUpdate.setOnAction(e -> {
            try {
              if (newPasswordTextField.getText().length() > 8
                      || newPasswordTextField.getText().length() < 20) {
                UserHandling.changePassword(newPasswordTextField.getText());
                currentPasswordLabel.setText(UserHandling.getPassword());
                SoundPlayer.play(FileUtil.getResourceFilePath("16bitconfirm.wav"));
                newPasswordTextField.clear();
              } else {
                throw new IllegalArgumentException("Password must be between 8 and 20 characters");
              }
            } catch (Exception ex) {
              SoundPlayer.play(FileUtil.getResourceFilePath("error.wav"));
              showAlert(ex.getMessage());
            }
          });
          confirmPasswordUpdate.setId("actionButton");
          confirmPasswordUpdateVBox.getChildren().addAll(confirmPasswordUpdate);
          confirmPasswordUpdateVBox.setAlignment(Pos.CENTER);

          HBox updatePasswordHbox = new HBox();
          updatePasswordHbox.getChildren().addAll(currentAndNewPasswordHbox,
                  currentAndNewPasswordInputFields, confirmPasswordUpdateVBox);
          updatePasswordHbox.setAlignment(Pos.CENTER);
          updatePasswordHbox.setSpacing(20);

          Text prefrences = new Text("Preferences");
          prefrences.setId("titleText");
          prefrences.setTextAlignment(TextAlignment.CENTER);

          RadioButton lightmode = new RadioButton("Lightmode");
          RadioButton darkmode = new RadioButton("Darkmode");
          RadioButton colorblind = new RadioButton("Colorblindmode");
          lightmode.setId("radioButton");
          darkmode.setId("radioButton");
          colorblind.setId("radioButton");

          ToggleGroup toggleGroup = new ToggleGroup();
          lightmode.setToggleGroup(toggleGroup);
          darkmode.setToggleGroup(toggleGroup);
          colorblind.setToggleGroup(toggleGroup);
          switch (currentMode) {
            case "Darkmode" -> darkmode.setSelected(true);
            case "Colorblindmode" -> colorblind.setSelected(true);
            default -> lightmode.setSelected(true);
          }

          toggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (toggleGroup.getSelectedToggle() != null) {
              if (!Objects.equals(((RadioButton)
                      toggleGroup.getSelectedToggle()).getText(), "Lightmode")) {
                String selectedText = ((RadioButton) toggleGroup.getSelectedToggle()).getText();
                currentMode = selectedText;
                GUI.setStyle(selectedText);
              } else {
                currentMode = "Lightmode";
                GUI.setStyle("Styling");
              }
            }
          });

          HBox viewmodeHbox = new HBox();
          viewmodeHbox.getChildren().addAll(lightmode, darkmode, colorblind);
          viewmodeHbox.setSpacing(20);
          viewmodeHbox.setAlignment(Pos.CENTER);

          Button contactUSButton = new Button("Contact Us");
          contactUSButton.setId("actionButton");
          contactUSButton.setAlignment(Pos.CENTER);

          contactUSButton.setOnAction(e -> {
            ContactUs.sendEmail();
            SoundPlayer.play(FileUtil.getResourceFilePath("16bitconfirm.wav"));
          });

          VBox contactUS = new VBox();
          contactUS.getChildren().addAll(contactUSButton);
          contactUS.setAlignment(Pos.CENTER);

          vbox.getChildren().clear();
          vbox.getChildren().addAll(title, currentEmailHbox,
                  emailUpdateHbox, updatePasswordHbox, prefrences, viewmodeHbox, contactUS);
          vbox.setSpacing(40);

          vbox.setAlignment(Pos.TOP_CENTER);
        } else {
          throw new IllegalArgumentException("Incorrect password");
        }
      } catch (Exception e) {
        SoundPlayer.play(FileUtil.getResourceFilePath("error.wav"));
        showAlert(e.getMessage());
      }
    });
    return vbox;
  }
}
