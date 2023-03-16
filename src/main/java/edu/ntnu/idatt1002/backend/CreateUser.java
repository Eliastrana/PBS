package edu.ntnu.idatt1002.backend;

import edu.ntnu.idatt1002.frontend.GUI;
import edu.ntnu.idatt1002.frontend.utility.SoundPlayer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
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
import java.io.*;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class CreateUser {
  private static List<LoginObserver> observers = new ArrayList<>();
  private static final String SECRET_KEY = "EliasErHeltSinnsyktKul";
  private static String SALT;
  public static boolean createdUser = false;
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

    TextField username = new TextField();
    username.setPromptText("Enter username");
    username.setId("textField");

    TextField email = new TextField();
    email.setPromptText("Enter email");
    email.setId("textField");

    PasswordField password = new PasswordField();
    password.setPromptText("Enter password");
    password.setId("textField");

    PasswordField password2 = new PasswordField();
    password2.setPromptText("Repeat password");
    password2.setId("textField");

    Button createUser = new Button("Log in");
    createUser.setId("loginButton");

    createUser.setOnAction( e -> {
        SALT = generateSalt();
        byte[] key = new byte[16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(key);

        String passwordStringTest1 = password.getText();
        String passwordStringTest2 = password2.getText();

        String passwordString;

        if (passwordStringTest1.equals(passwordStringTest2)) {
          passwordString = passwordStringTest1;
        } else {
          System.out.println("Passwords do not match");
          return;
        }

        String encryptedPasswordString = encrypt(passwordString);
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

    loginVBox.getChildren().addAll(welcomeText,welcomeText2, username, email, password, password2, createUser);

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
