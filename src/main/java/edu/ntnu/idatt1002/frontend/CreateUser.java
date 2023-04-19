package edu.ntnu.idatt1002.frontend;

import edu.ntnu.idatt1002.backend.CreateUserBackend;
import edu.ntnu.idatt1002.frontend.controllers.CreateUserController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.Random;

/**
 * A class that creates the view for the create user page.
 *
 * @author Emil J., Vegard J., Sander S. & Elias T.
 * @version 0.5 - 19.04.2023
 */
public class CreateUser {
  /**
   * A text field to enter the username.
   */
  public static TextField username = new TextField();
  /**
   * The current user.
   */
  public static String currentUser;

  /**
   * Returns the current user.
   *
   * @return the current user
   */
  public static String getCurrentUser() {
    return currentUser;
  }

  /**
   * Sets the current user.
   *
   * @param currentUser the current user
   */
  public static void setCurrentUser(String currentUser) {
    CreateUser.currentUser = currentUser;
  }

  /**
   * A method that creates the create user view.
   *
   * @param controller the controller
   * @return the view as a parent
   */
  public Parent createUserView(CreateUserController controller) {

    Pane background = new Pane();
    background.setPrefSize(1000,700);


    background.getStylesheets().add("/LightMode.css");

      Random randomBackground = new Random();
      int randomInt = randomBackground.nextInt(1)+1;
      background.getStyleClass().add("loginScreen"+randomInt);

    System.out.println("Opening createUser page");

    VBox loginVBox = new VBox();
    loginVBox.setPadding(new Insets(10));

    loginVBox.setAlignment(Pos.CENTER);
    loginVBox.setSpacing(20);
    loginVBox.setMaxSize(300, 400);

    loginVBox.getStylesheets().add("/LightMode.css");
    loginVBox.setId("overlayLogin");

    Text welcomeText = new Text("Create user ");
    welcomeText.setId("titleText");

    Text welcomeText2 = new Text("Please enter your information");
    welcomeText2.setId("underTitleText");

    VBox usernameBox = new VBox();
    usernameBox.setSpacing(1);

    username.setPromptText("Enter username");
    username.setId("textField");

    Text usernameError = new Text();
    usernameError.setId("errorText");

    usernameBox.getChildren().addAll(username, usernameError);

    VBox emailBox = new VBox();
    emailBox.setSpacing(1);

    TextField email = new TextField();
    email.setPromptText("Enter email");
    email.setId("textField");

    Text emailError = new Text();
    emailError.setId("errorText");

    emailBox.getChildren().addAll(email, emailError);

    VBox passwordBox = new VBox();
    passwordBox.setSpacing(1);

    PasswordField password = new PasswordField();
    password.setPromptText("Enter password");
    password.setId("textField");

    Text passwordError = new Text();
    passwordError.setId("errorText");

    passwordBox.getChildren().addAll(password, passwordError);

    VBox passwordBox2 = new VBox();
    passwordBox2.setSpacing(1);

    PasswordField password2 = new PasswordField();
    password2.setPromptText("Repeat password");
    password2.setId("textField");

    Text password2Error = new Text();
    password2Error.setId("errorText");

    passwordBox2.getChildren().addAll(password2, password2Error);

    VBox createUserBox = new VBox();
    createUserBox.setSpacing(1);
    createUserBox.getChildren().addAll(usernameBox, emailBox, passwordBox, passwordBox2);

    Button createUser = new Button("Create user");
    createUser.setId("loginButton");


    createUser.setOnAction( e -> {
        String passwordStringTest1 = password.getText();
        String passwordStringTest2 = password2.getText();

        String passwordString;
        usernameError.setText("");
        emailError.setText("");
        passwordError.setText("");
        password2Error.setText("");

      if (!CreateUserBackend.isValidEmail(email.getText())) {
        emailError.setText("""
                    Email is not valid. It needs to be in the format:
                    username@email.domain
                    """);
        System.out.println("Email is not valid");
        return;
      } else if (!CreateUserBackend.isValidPassword(passwordStringTest1)) {
        passwordError.setText("""
                            Password is not valid. It must:
                            Contain 1 uppercase letter
                            Contain 1 lowercase letter
                            Contain 1 number
                            Contain 1 special character
                            Be between 8 and 20 characters long
                            """);
        System.out.println("Password is not valid");
        return;
      } else if (!passwordStringTest1.equals(passwordStringTest2)) {
        password2Error.setText("Passwords do not match");
        System.out.println("Passwords do not match");
        return;
      } else {
        passwordString = passwordStringTest1;
      }

      try {
        currentUser = username.getText();
        controller.handleCreateButton(username.getText(), passwordString, email.getText());
      } catch (IOException ex) {
        throw new RuntimeException(ex);
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

    backButton.setOnAction(e -> controller.handleBackButton());


    loginVBox.getChildren().addAll(welcomeText,welcomeText2, createUserBox, createUser);

    StackPane backgroundAndLogin = new StackPane(background, backButtonBox, loginVBox);


    VBox vbox = new VBox(backgroundAndLogin);
    vbox.setAlignment(Pos.TOP_CENTER);
    return vbox;
  }
}
