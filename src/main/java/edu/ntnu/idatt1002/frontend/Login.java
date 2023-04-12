package edu.ntnu.idatt1002.frontend;

import edu.ntnu.idatt1002.backend.LoginBackend;
import edu.ntnu.idatt1002.backend.LoginObserver;
import edu.ntnu.idatt1002.frontend.controllers.LoginController;
import edu.ntnu.idatt1002.frontend.utility.SoundPlayer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Random;


public class Login {

  public static TextField username = new TextField();
  public static String currentUser;

  public static String getCurrentUser() {
    return currentUser;
  }

  public static Parent loginView(LoginController controller) {

    Pane background = new Pane();
    background.setPrefSize(1000,700);

    Random random = new Random();
    int randomInt = random.nextInt(2)+1;
    background.getStylesheets().add("/LightMode.css");

    background.getStyleClass().add("loginScreen"+randomInt);

    System.out.println("Opening login page");

    VBox loginVBox = new VBox();
    loginVBox.setPadding(new Insets(10));

    loginVBox.setAlignment(Pos.CENTER);
    loginVBox.setSpacing(20);
    loginVBox.setMaxSize(300, 400);

    loginVBox.getStylesheets().add("/LightMode.css");

    loginVBox.setId("overlayLogin");

    Text welcomeText = new Text("Take back ");
    welcomeText.setId("titleText");

    Text welcomeText2 = new Text("control of your life");
    welcomeText2.setId("underTitleText");

    username.setPromptText("Enter username");
    username.setId("textField");

    PasswordField password = new PasswordField();
    password.setPromptText("Enter password");
    password.setId("textField");

    Button logIn = new Button("Log in");
    logIn.setId("loginButton");

    password.setOnKeyPressed(e -> {
      if (e.getCode() == KeyCode.ENTER) {
        logIn.fire(); // Simulate a click event on the logIn button
      }
    });

    logIn.setOnAction(e -> {
      try {
        LoginBackend.login(username.getText(), password.getText(), controller);
      } catch (IOException ex) {
        throw new RuntimeException(ex);
      }
    });

    Text createUser = new Text("Create user");
    createUser.setId("linkSmallText");

    createUser.setOnMouseClicked(e -> {
      System.out.println("Opening create user page");
      try {
        controller.handleCreateUserButton();
      } catch (Exception ex) {
        throw new RuntimeException(ex);
      }
    });

    Text forgotPassword = new Text("Forgot password");
    forgotPassword.setId("linkSmallText");
    forgotPassword.setOnMouseClicked(e -> {
      System.out.println("Opening forgot password page");
      try {
        controller.handleForgotPasswordButton();
      } catch (Exception ex) {
        throw new RuntimeException(ex);
      }
    });

    loginVBox.getChildren().addAll(welcomeText,welcomeText2, username, password, logIn, createUser, forgotPassword);

    StackPane backgroundAndLogin = new StackPane(background , loginVBox);

    VBox vbox = new VBox(backgroundAndLogin);
    vbox.setAlignment(Pos.TOP_CENTER);
    return vbox;
  }
}
