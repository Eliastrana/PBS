package edu.ntnu.idatt1002.frontend;

import edu.ntnu.idatt1002.backend.LoginObserver;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.Scene;
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

        forgottenPasswordVBox.setPadding(new Insets(10));

        TextField emailTextField = new TextField();


        Button submitButton = new Button("Submit");
        GridPane.setConstraints(submitButton, 1, 2);
        submitButton.setOnAction(e -> {
                gotEmail = true;

                Email email = new Email();
                try {
                    email.sendEmail(emailTextField.getText());
                } catch (MessagingException ex) {
                    throw new RuntimeException(ex);
                }

                handleSubmit();

                });
        forgottenPasswordVBox.getChildren().addAll(emailTextField, submitButton);

        // Create a GridPane layout to hold the UI elements


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
