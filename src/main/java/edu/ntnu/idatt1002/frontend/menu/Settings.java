//SETTINGS PANE

package edu.ntnu.idatt1002.frontend.menu;

import edu.ntnu.idatt1002.backend.UserHandling;
import edu.ntnu.idatt1002.frontend.utility.MyToggleGroupClass;
import edu.ntnu.idatt1002.frontend.utility.SoundPlayer;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class Settings {

  private MyToggleGroupClass myToggleGroupClass;


  public static VBox settingsView() {

    VBox vbox = new VBox();
    vbox.getChildren().clear();

    HBox currentEmailHbox = new HBox();

    VBox currentStatus = new VBox();

    Text title = new Text("Settings");
    title.setId("titleText");
    Text currentEmailText = new Text("Current email: ");
    currentEmailText.setId("bodyText");
    currentEmailText.setTextAlignment(TextAlignment.RIGHT);
    Text currentPasswordText = new Text("Current password: ");
    currentPasswordText.setId("bodyText");
    currentPasswordText.setTextAlignment(TextAlignment.RIGHT);

    currentStatus.getChildren().addAll(currentEmailText, currentPasswordText);
    currentStatus.setAlignment(Pos.TOP_RIGHT);

    VBox currentPassword = new VBox();

    Text currentEmailLabel = new Text(UserHandling.getEmail()); //eksempel email, legg inn variabel
    currentEmailLabel.setId("bodyText");
    currentEmailLabel.setVisible(false);
    currentEmailLabel.setTextAlignment(TextAlignment.LEFT);
    Text currentPasswordLabel = new Text(UserHandling.getPassword()); //Eksempel passord, legg inn variabel
    currentPasswordLabel.setVisible(false);
    currentPasswordLabel.setId("bodyText");

    ImageView visibilityImage = new ImageView(new Image("icons/visibility.png"));
    visibilityImage.setFitHeight(20);
    visibilityImage.setFitWidth(20);

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
        currentEmailLabel.setText(UserHandling.getEmail());
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

    currentEmailHbox.getChildren().addAll(currentStatus, currentPassword, showPrivateInformation);

    currentEmailHbox.setSpacing(20);
    currentEmailHbox.setAlignment(Pos.CENTER);


    HBox emailUpdateHbox = new HBox();

    VBox updateEmail = new VBox();
    Text updateEmailText = new Text("Update email: ");
    updateEmailText.setId("bodyText");

    updateEmail.getChildren().addAll(updateEmailText);
    updateEmail.setAlignment(Pos.CENTER);

    VBox updatePassword = new VBox();
    TextField updateEmailTextField = new TextField();
    updatePassword.getChildren().addAll(updateEmailTextField);
    updatePassword.setAlignment(Pos.CENTER);

    VBox confirmEmailUpdateVbox = new VBox();
    Button confirmEmailUpdate = new Button("Confirm");

    confirmEmailUpdate.setOnAction(e -> {
      if (updateEmailTextField.getText().length() > 8 || updateEmailTextField.getText().length() < 20) {
        try {
          UserHandling.changeEmail(updateEmailTextField.getText());
        } catch (Exception ex) {
          throw new RuntimeException(ex);
        }
        currentEmailLabel.setText(UserHandling.getEmail());
        SoundPlayer.play("src/main/resources/16bitconfirm.wav");
        updateEmailTextField.clear();
      }
    });

    confirmEmailUpdate.setId("actionButton");
    confirmEmailUpdateVbox.getChildren().addAll(confirmEmailUpdate);
    confirmEmailUpdateVbox.setAlignment(Pos.TOP_CENTER);

    emailUpdateHbox.getChildren().addAll(updateEmail, updatePassword, confirmEmailUpdateVbox);
    emailUpdateHbox.setSpacing(20);
    emailUpdateHbox.setAlignment(Pos.CENTER);



    HBox updatePasswordHbox = new HBox();

    VBox currentAndNewPasswordHbox = new VBox();
    Text currentPasswordText2 = new Text("Update password: ");
    currentPasswordText2.setId("bodyText");
    currentAndNewPasswordHbox.getChildren().addAll(currentPasswordText2);
    currentAndNewPasswordHbox.setAlignment(Pos.CENTER);

    VBox currentAndNewPasswordInputFields = new VBox();
    TextField newPasswordTextField = new TextField();
    currentAndNewPasswordInputFields.getChildren().addAll(newPasswordTextField);
    currentAndNewPasswordInputFields.setAlignment(Pos.CENTER);

    VBox confirmPasswordUpdateVBox = new VBox();
    Button confirmPasswordUpdate = new Button("Confirm");
    confirmPasswordUpdate.setOnAction(e -> {
      if (newPasswordTextField.getText().length() > 8 || newPasswordTextField.getText().length() < 20) {
              try {
                UserHandling.changePassword(newPasswordTextField.getText());
              } catch (Exception ex) {
                throw new RuntimeException(ex);
              }
              currentPasswordLabel.setText(UserHandling.getPassword());
              SoundPlayer.play("src/main/resources/16bitconfirm.wav");
              newPasswordTextField.clear();

      }
      });
    confirmPasswordUpdate.setId("actionButton");
    confirmPasswordUpdateVBox.getChildren().addAll(confirmPasswordUpdate);
    confirmPasswordUpdateVBox.setAlignment(Pos.CENTER);





    updatePasswordHbox.getChildren().addAll(currentAndNewPasswordHbox, currentAndNewPasswordInputFields, confirmPasswordUpdateVBox);
    updatePasswordHbox.setAlignment(Pos.CENTER);
    updatePasswordHbox.setSpacing(20);

    Text prefrences = new Text("Preferences");
    prefrences.setId("titleText");
    prefrences.setTextAlignment(TextAlignment.CENTER);

    HBox viewmodeHbox = new HBox();



    RadioButton lightmode = new RadioButton("Lightmode");
    RadioButton darkmode = new RadioButton("Darkmode");
    RadioButton colorblind = new RadioButton("Colorblindmode");

    ToggleGroup toggleGroup = new ToggleGroup();
    lightmode.setToggleGroup(toggleGroup);
    darkmode.setToggleGroup(toggleGroup);
    colorblind.setToggleGroup(toggleGroup);

    RadioButton selectedRadioButton = (RadioButton) toggleGroup.getSelectedToggle();
    if (selectedRadioButton != null) {
      String selectedText = selectedRadioButton.getText();
      // do something with the selected text
    }


    viewmodeHbox.getChildren().addAll(lightmode, darkmode, colorblind);
    viewmodeHbox.setSpacing(20);
    viewmodeHbox.setAlignment(Pos.CENTER);

    vbox.getChildren().addAll(title, currentEmailHbox, emailUpdateHbox, updatePasswordHbox, prefrences, viewmodeHbox);
    vbox.setSpacing(40);

    vbox.setAlignment(Pos.TOP_CENTER);
    return vbox;

  }
}
