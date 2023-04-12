package edu.ntnu.idatt1002.frontend;

import edu.ntnu.idatt1002.backend.LoginObserver;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

import static edu.ntnu.idatt1002.backend.ForgotPasswordBackend.handleSubmit;

public class ForgotPassword {

    private static List<LoginObserver> observers = new ArrayList<>();

    private static TextField emailTextField;
    public static Label errorLabel;
    public static String emailString;
    public static String newPasswordString;
    public static boolean backToLogin = false;
    private static final String PASSWORD_PATTERN =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);


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

        VBox backButtonBox = new VBox();
        Button backButton = new Button();
        backButton.setId("backButton");

        ImageView backImage = new ImageView(new Image("icons/back.png"));
        backImage.setFitHeight(50);
        backImage.setFitWidth(50);
        backButton.setGraphic(backImage);
        backButtonBox.getChildren().add(backButton);
        backButtonBox.setAlignment(Pos.TOP_LEFT);
        backButtonBox.setPadding(new Insets(20, 0, 0, 20));

        backButton.setOnAction(e -> {

            try {
                backToLogin = true;
                notifyObservers();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
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
            String masterPasswordString = sb.toString();
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

            if (newPassword.getText().equals(confirmNewPassword.getText())) {
                if (!pattern.matcher(newPassword.getText()).matches()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Password must contain at least one uppercase letter, one lowercase letter, one number, one special character and be between 8 and 20 characters long.");
                    alert.showAndWait();
                } else {
                    newPasswordString = newPassword.getText();
                    try {
                        handleSubmit();
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        forgottenPasswordVBox.getChildren().addAll(backButtonBox, emailTextField, submitButton, masterPassword, newPassword, confirmNewPassword, changePasswordButton);
        forgottenPasswordVBox.setSpacing(30);
        forgottenPasswordVBox.setAlignment(Pos.CENTER);

        return forgottenPasswordVBox;
    }

    public static void addObserver(LoginObserver observer) {
        observers.add(observer);
    }

    public static void notifyObservers() throws Exception {
        for (LoginObserver observer : observers) {
            observer.update();
            System.out.println("Notified observer");
        }
    }

    public static boolean isBackToLogin() {
        return backToLogin;
    }

    public static void setBackToLogin(boolean backToLogin) {
        ForgotPassword.backToLogin = backToLogin;
    }
}