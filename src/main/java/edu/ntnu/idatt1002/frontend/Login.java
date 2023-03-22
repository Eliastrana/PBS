package edu.ntnu.idatt1002.frontend;

import edu.ntnu.idatt1002.backend.LoginObserver;
import edu.ntnu.idatt1002.frontend.utility.SoundPlayer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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

  private static List<LoginObserver> observers = new ArrayList<>();
  private static final String SECRET_KEY = "EliasErHeltSinnsyktKul";
  private static String SALT;
  public static boolean loggedIn = false;
  public static TextField username = new TextField();
  public static String currentUser;

  public static boolean forgotPasswordBoolean = false;


  public static String getCurrentUser() {
    return currentUser;
  }

  public static VBox loginView() {

    Pane background = new Pane();
    background.setPrefSize(1000,700);

    Random random = new Random();
    int randomInt = random.nextInt(1)+1;
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
      String csvFile = "src/main/resources/users.csv";
      String line = "";
      BufferedReader br = null;
      try {
        br = new BufferedReader(new FileReader(csvFile));
      } catch (FileNotFoundException ex) {
        throw new RuntimeException(ex);
      }
      while (true) {
        try {
          if ((line = br.readLine()) == null) break;
        } catch (IOException ex) {
          throw new RuntimeException(ex);
        }
        String[] user = line.split(",");
          if (user[0].equals(username.getText())) {
            currentUser = username.getText();
            String encryptedPassword = user[1];
            String SALT = user[2];
            String decryptedPassword = decrypt(encryptedPassword, SALT);
            if (password.getText().equals(decryptedPassword)) {
              System.out.println("Logged in");
              SoundPlayer.play("src/main/resources/16bitconfirm.wav");
              loggedIn = true;
              notifyObservers();
            }
          }
        }
    });

    Text createUser = new Text("Create user");
    createUser.setId("linkSmallText");

    createUser.setOnMouseClicked(e -> {
      System.out.println("Opening create user page");
      notifyObservers();
    });

    Text forgotPassword = new Text("Forgot password");
    forgotPassword.setId("linkSmallText");
    forgotPassword.setOnMouseClicked(e -> {
      forgotPasswordBoolean = true;
      System.out.println("Opening forgot password page");
      notifyObservers();
      });


//    createUser.setOnMouseClicked(e -> {
//      SALT = generateSalt();
//      byte[] key = new byte[16];
//      SecureRandom random = new SecureRandom();
//      random.nextBytes(key);
//
//      String passwordString = password.getText();
//
//      String encryptedPasswordString = encrypt(passwordString);
//      BufferedWriter writer;
//      try {
//        writer = new BufferedWriter(new FileWriter("src/main/resources/users.csv", true));
//      } catch (IOException ex) {
//        throw new RuntimeException(ex);
//      }
//      try {
//        writer.write(username.getText() + "," + encryptedPasswordString + "," + SALT);
//      } catch (IOException ex) {
//        throw new RuntimeException(ex);
//      }
//      try {
//        writer.newLine();
//        writer.close();
//      } catch (IOException ex) {
//        throw new RuntimeException(ex);
//      }
//      notifyObservers();
//    });

    loginVBox.getChildren().addAll(welcomeText,welcomeText2, username, password, logIn, createUser, forgotPassword);

    StackPane backgroundAndLogin = new StackPane(background , loginVBox);


    VBox vbox = new VBox(backgroundAndLogin);
    vbox.setAlignment(Pos.TOP_CENTER);
    return vbox;
  }

//  public static String generateSalt() {
//    byte[] salt = new byte[16];
//    SecureRandom random = new SecureRandom();
//    random.nextBytes(salt);
//    return Base64.getEncoder().encodeToString(salt);
//  }
//
//  public static String encrypt (String password) {
//    try {
//      byte[] iv = new byte[16];
//      IvParameterSpec ivSpec = new IvParameterSpec(iv);
//
//      SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
//      KeySpec spec = new PBEKeySpec(SECRET_KEY.toCharArray(), SALT.getBytes(), 65536, 256);
//      SecretKey tmp = factory.generateSecret(spec);
//      SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");
//
//      Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
//      cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
//      return Base64.getEncoder().encodeToString(cipher.doFinal(password.getBytes("UTF-8")));
//    } catch (Exception e) {
//      System.out.println("Error while encrypting: " + e.toString());
//    }
//    return null;
//  }

  public static String decrypt (String password, String SALT) {
    try {
      byte[] iv = new byte[16];
      IvParameterSpec ivSpec = new IvParameterSpec(iv);

      SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
      KeySpec spec = new PBEKeySpec(SECRET_KEY.toCharArray(), SALT.getBytes(), 65536, 256);
      SecretKey tmp = factory.generateSecret(spec);
      SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

      Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
      cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
      return new String(cipher.doFinal(Base64.getDecoder().decode(password)));
    } catch (Exception e) {
      System.out.println("Error while decrypting: " + e.toString());
    }
    return null;
  }

  public static boolean isLoggedIn() {
    return loggedIn;
  }

  public static boolean isForgotPassword() {
    return forgotPasswordBoolean;
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
}
