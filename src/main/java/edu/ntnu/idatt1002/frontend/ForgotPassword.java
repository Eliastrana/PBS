package edu.ntnu.idatt1002.frontend;

import edu.ntnu.idatt1002.backend.LoginObserver;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.List;

public class ForgotPassword {

    public static boolean gotEmail = false;

    public static boolean getGotEmail() {
        return gotEmail;
    }
    private static List<LoginObserver> observers = new ArrayList<>();


    private Stage stage;
    private static TextField emailTextField;
    private static Label errorLabel;

    public static VBox forgottenPasswordView() {



        VBox forgottenPasswordVBox = new VBox();

        forgottenPasswordVBox.getStylesheets().add("/Styling.css");

        forgottenPasswordVBox.setPadding(new Insets(10));

        TextField emailTextField = new TextField();
        emailTextField.setPromptText("Enter email");
        emailTextField.setId("textField");
        emailTextField.setMaxWidth(250);


        PasswordField masterPassword = new PasswordField();
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

                gotEmail = true;

                Email email = new Email();
            try {
                email.sendEmail(emailTextField.getText());
            } catch (MessagingException ex) {
                throw new RuntimeException(ex);
            }

            masterPassword.setVisible(true);
                newPassword.setVisible(true);
                confirmNewPassword.setVisible(true);
                changePasswordButton.setVisible(true);



                handleSubmit();


                });

        submitButton.setId("actionButton");




        forgottenPasswordVBox.getChildren().addAll(emailTextField, submitButton, masterPassword, newPassword, confirmNewPassword, changePasswordButton);
        forgottenPasswordVBox.setSpacing(30);
        forgottenPasswordVBox.setAlignment(Pos.CENTER);


        return forgottenPasswordVBox;
    }

    public static void addObserver(LoginObserver observer) {
        observers.add(observer);
    }

    private static void notifyObservers() {
        for (LoginObserver observer : observers) {
            observer.update();
            System.out.println("Notified observer");
        }
    }

    private static void handleSubmit() {
        String email = emailTextField.getText();
        if (email.isEmpty()) {
            errorLabel.setText("Please enter your email.");
        } else {
            // TODO: Implement forgot password logic here
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Password Reset");
            alert.setHeaderText("Instructions to reset your password have been sent to your email.");
            alert.showAndWait();
        }
    }

}
