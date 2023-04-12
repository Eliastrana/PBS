package edu.ntnu.idatt1002.frontend.controllers;

import edu.ntnu.idatt1002.backend.CreateUserBackend;
import edu.ntnu.idatt1002.frontend.CreateUser;
import edu.ntnu.idatt1002.frontend.GUI;

import java.io.IOException;

public class CreateUserController {
  private GUI gui;

  public CreateUserController(GUI gui) {
    this.gui = gui;
  }

  public void handleBackButton() {
    gui.navigateToLogin();
  }

  public void handleCreateButton(String username, String password, String email) throws IOException {
    String SALT = CreateUserBackend.generateSalt();
    String encryptedPassword = CreateUserBackend.encrypt(password, SALT);
    CreateUserBackend.saveUser(username, encryptedPassword, SALT, email);
    gui.navigateToMainApp();
  }

  // Other methods for handling UI interactions
}
