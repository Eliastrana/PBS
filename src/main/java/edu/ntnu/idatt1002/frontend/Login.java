package edu.ntnu.idatt1002.frontend;

import edu.ntnu.idatt1002.backend.LoginObserver;
import edu.ntnu.idatt1002.frontend.utility.SoundPlayer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class Login {

  private static List<LoginObserver> observers = new ArrayList<>();
  public static VBox loginView() {




    Pane background = new Pane();
    background.setPrefSize(1000,700);

    background.getStylesheets().add("/LightMode.css");
    background.getStyleClass().add("loginScreen");



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

    TextField username = new TextField();
    username.setPromptText("Enter username");
    username.setId("textField");

    TextField password = new TextField();
    password.setPromptText("Enter password");
    password.setId("textField");

    Button logIn = new Button("Log in");
    logIn.setId("loginButton");
    //logIn.setStyle("-fx-font-size: 30px; -fx-min-width: 100px; -fx-min-height: 50px;-fx-background-color: #9FB8AD; -fx-border-width: 2; -fx-padding: 10px; -fx-background-radius: 0.5em;");

    logIn.setOnAction(e -> {
      String csvFile = "src/main/resources/users.csv";
      String line = "";
      String delimiter = ",";

      try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
        while ((line = br.readLine()) != null) {
          String[] user = line.split(delimiter);
          if (user[0].equals(username.getText()) && user[1].equals(password.getText())) {
            System.out.println("Logged in");
            SoundPlayer.play("src/main/resources/16bitconfirm.wav");
            notifyObservers();
          }
        }
      } catch (FileNotFoundException ex) {
        System.out.println("File not found");
      } catch (IOException ex) {
        System.out.println("IO exception");
      }
    });

    Text createUser = new Text("Create user");
    createUser.setId("smallText");


    loginVBox.getChildren().addAll(welcomeText,welcomeText2, username, password, logIn, createUser);

    StackPane backgroundAndLogin = new StackPane(background , loginVBox);


    VBox vbox = new VBox(backgroundAndLogin);
    vbox.setAlignment(Pos.TOP_CENTER);
    return vbox;
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
