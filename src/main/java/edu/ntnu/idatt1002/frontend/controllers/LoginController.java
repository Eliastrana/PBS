package edu.ntnu.idatt1002.frontend.controllers;

import edu.ntnu.idatt1002.backend.LoginBackend;
import edu.ntnu.idatt1002.frontend.CreateUser;
import edu.ntnu.idatt1002.frontend.ForgotPassword;
import edu.ntnu.idatt1002.frontend.GUI;
import edu.ntnu.idatt1002.frontend.Login;
import edu.ntnu.idatt1002.frontend.utility.SoundPlayer;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class LoginController {
  private GUI gui;
  public static String currentUser;
  boolean isAuthenticated = false;

  public LoginController(GUI gui) {
    this.gui = gui;
  }

  public void handleLoginButton() {
    gui.navigateToMainApp();
  }

  public void handleCreateUserButton() {
    gui.navigateToCreateUser();
  }

  public void handleForgotPasswordButton() {
    gui.navigateToForgotPassword();
  }

  // Add other methods for handling CreateUserView and ForgotPasswordView actions
}
