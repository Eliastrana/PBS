package edu.ntnu.idatt1002.frontend;

import edu.ntnu.idatt1002.backend.LoginObserver;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.mail.MessagingException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ForgotPassword {

  public static boolean changedPassword = false;
  private static final List<LoginObserver> observers = new ArrayList<>();
  private static TextField emailTextField;
  private static Label errorLabel;
  private static String emailString;
  private static String newPasswordString;
  private static String masterPasswordString;
  private Stage stage;

  public static VBox forgottenPasswordView() {


    VBox forgottenPasswordVBox = new VBox();

    forgottenPasswordVBox.getStylesheets().add("/Styling.css");

    forgottenPasswordVBox.setPadding(new Insets(10));

    emailTextField = new TextField();
    emailTextField.setPromptText("Enter email");
    emailTextField.setId("textField");
    emailTextField.setMaxWidth(250);


    TextField masterPassword = new TextField();
    masterPassword.setPromptText("Enter master password");
    masterPassword.setId("textField");
    masterPassword.setMaxWidth(250);


    PasswordField newPassword = new PasswordField();
    newPassword.setPromptText("Enter new password");
    newPassword.setId("textField");
    newPassword.setMaxWidth(250);


    PasswordField confirmNewPassword = new PasswordField();
    confirmNewPassword.setPromptText("Confirm new password");
    confirmNewPassword.setId("textField");
    confirmNewPassword.setMaxWidth(250);

    Button changePasswordButton = new Button("Update Password");
    changePasswordButton.setId("actionButton");
    changePasswordButton.setOnKeyPressed(e -> {
      if (e.getCode() == KeyCode.ENTER) {
        changePasswordButton.fire(); // Simulate a click event on the logIn button
      }
    });


    masterPassword.setVisible(false);
    newPassword.setVisible(false);
    confirmNewPassword.setVisible(false);
    changePasswordButton.setVisible(false);

    Button submitButton = new Button("Submit");
    emailTextField.setOnKeyPressed(e -> {
      if (e.getCode() == KeyCode.ENTER) {
        submitButton.fire(); // Simulate a click event on the logIn button
      }
    });
    GridPane.setConstraints(submitButton, 1, 2);
    submitButton.setOnAction(e -> {

      Email email = new Email();
      emailString = emailTextField.getText();

      String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
      StringBuilder sb = new StringBuilder();
      Random random = new Random();
      int length = 10;
      for (int i = 0; i < length; i++) {
        int index = random.nextInt(alphabet.length());
        char randomChar = alphabet.charAt(index);
        sb.append(randomChar);
      }
      masterPasswordString = sb.toString();
      try {
        email.sendEmail(emailString, masterPasswordString);
      } catch (MessagingException ex) {
        throw new RuntimeException(ex);
      }
      masterPassword.setVisible(true);
      newPassword.setVisible(true);
      confirmNewPassword.setVisible(true);
      changePasswordButton.setVisible(true);

      Alert alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setTitle("Password Reset");
      alert.setHeaderText("Instructions to reset your password have been sent to your email.");
      alert.showAndWait();
    });


    submitButton.setId("actionButton");

    changePasswordButton.setOnAction(e -> {
      if (newPassword.getText().equals(confirmNewPassword.getText()) && masterPassword.getText().equals(masterPasswordString)) {
        newPasswordString = newPassword.getText();
        try {
          handleSubmit();
        } catch (IOException ex) {
          throw new RuntimeException(ex);
        } catch (Exception ex) {
          throw new RuntimeException(ex);
        }
      }
    });


    forgottenPasswordVBox.getChildren().addAll(emailTextField, submitButton, masterPassword, newPassword, confirmNewPassword, changePasswordButton);
    forgottenPasswordVBox.setSpacing(30);
    forgottenPasswordVBox.setAlignment(Pos.CENTER);


    return forgottenPasswordVBox;
  }

  public static void addObserver(LoginObserver observer) {
    observers.add(observer);
  }

  private static void notifyObservers() throws Exception {
    for (LoginObserver observer : observers) {
      observer.update();
      System.out.println("Notified observer");
    }
  }

  private static void handleSubmit() throws Exception {
    if (emailString.isEmpty()) {
      errorLabel.setText("Please enter your email.");
    } else {
      String csvFile = "src/main/resources/users.csv";
      String tempCsvFile = "src/main/resources/temp_users.csv";
      String line = "";
      String csvSplitBy = ",";
      String salt = CreateUser.generateSalt();
      String newPassword = CreateUser.encrypt(newPasswordString, salt);
      try (BufferedReader br = new BufferedReader(new FileReader(csvFile));
           FileWriter fw = new FileWriter(tempCsvFile)) {
        while ((line = br.readLine()) != null) {
          String[] user = line.split(csvSplitBy);
          if (user[3].equals(emailString)) {
            user[1] = newPassword;
            user[2] = salt;
          }
          fw.write(String.join(",", user) + "\n");
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
      File originalFile = new File(csvFile);
      File tempFile = new File(tempCsvFile);
      if (originalFile.delete()) {
        if (!tempFile.renameTo(originalFile)) {
          throw new IOException("Failed to rename temp file");
        }
      } else {
        throw new IOException("Failed to delete original file");
      }
      System.out.println("Password reset");
      changedPassword = true;
      notifyObservers();
    }
  }

  public static boolean isChangedPassword() {
    return changedPassword;
  }
}
