package edu.ntnu.idatt1002.frontend;

import edu.ntnu.idatt1002.backend.LoginObserver;
import edu.ntnu.idatt1002.frontend.GUI;
import edu.ntnu.idatt1002.frontend.utility.SoundPlayer;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.management.NotificationFilter;
import java.io.*;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateUser {
  private static List<LoginObserver> observers = new ArrayList<>();
  private static final String SECRET_KEY = "EliasErHeltSinnsyktKul";
  private static String SALT;
  public static boolean createdUser = false;
  public static TextField username = new TextField();
  public static String currentUser;
  private static final String PASSWORD_PATTERN =
          "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";

  public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
          Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
  private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);


  public static String getCurrentUser() {
    return currentUser;
  }

  public static VBox createUserView() {

    Pane background = new Pane();
    background.setPrefSize(1000,700);

    background.getStylesheets().add("/LightMode.css");
    background.getStyleClass().add("loginScreen");

    System.out.println("Opening createUser page");

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
        SALT = generateSalt();
        byte[] key = new byte[16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(key);

        String passwordStringTest1 = password.getText();
        String passwordStringTest2 = password2.getText();

        String passwordString;
        Matcher passwordMatcher = pattern.matcher(passwordStringTest1);
        Matcher emailMatcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email.getText());

        usernameError.setText("");
        emailError.setText("");
        passwordError.setText("");
        password2Error.setText("");

        if (String .valueOf(username.getText()).isEmpty()) {
          usernameError.setText("Username is empty");
          System.out.println("Username is empty");
          return;
        } else if (String .valueOf(email.getText()).isEmpty()) {
          emailError.setText("Email is empty");
          System.out.println("Email is empty");
          return;
        } else if (!emailMatcher.matches()) {
          emailError.setText("""
          Email is not valid. It needs to be in the format:
          username@email.domain
          """);
          System.out.println("Email is not valid");
          return;
        } else if (String .valueOf(password.getText()).isEmpty()) {
          passwordError.setText("Password is empty");
          System.out.println("Password is empty");
          return;
        } else if (String .valueOf(password2.getText()).isEmpty()) {
          password2Error.setText("Password is empty");
          System.out.println("Password is empty");
          return;
        } else if (!passwordMatcher.matches()) {
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

        String encryptedPasswordString = encrypt(passwordString);
        currentUser = username.getText();
        BufferedWriter writer;
        try {
          writer = new BufferedWriter(new FileWriter("src/main/resources/users.csv", true));
        } catch (IOException ex) {
          throw new RuntimeException(ex);
        }
        try {
          writer.write(username.getText() + "," + encryptedPasswordString + "," + SALT + "," + email.getText());
        } catch (IOException ex) {
          throw new RuntimeException(ex);
        }
        try {
          writer.newLine();
          writer.close();
        } catch (IOException ex) {
          throw new RuntimeException(ex);
        }
        createdUser = true;
        notifyObservers();
      });

    loginVBox.getChildren().addAll(welcomeText,welcomeText2, createUserBox, createUser);

    StackPane backgroundAndLogin = new StackPane(background , loginVBox);


    VBox vbox = new VBox(backgroundAndLogin);
    vbox.setAlignment(Pos.TOP_CENTER);
    return vbox;
  }

  public static String encrypt (String password) {
    try {
      byte[] iv = new byte[16];
      IvParameterSpec ivSpec = new IvParameterSpec(iv);

      SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
      KeySpec spec = new PBEKeySpec(SECRET_KEY.toCharArray(), SALT.getBytes(), 65536, 256);
      SecretKey tmp = factory.generateSecret(spec);
      SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

      Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
      cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
      return Base64.getEncoder().encodeToString(cipher.doFinal(password.getBytes("UTF-8")));
    } catch (Exception e) {
      System.out.println("Error while encrypting: " + e.toString());
    }
    return null;
  }

  public static String generateSalt() {
    byte[] salt = new byte[16];
    SecureRandom random = new SecureRandom();
    random.nextBytes(salt);
    return Base64.getEncoder().encodeToString(salt);
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

  public static boolean isCreatedUser() {
    return createdUser;
  }
}
