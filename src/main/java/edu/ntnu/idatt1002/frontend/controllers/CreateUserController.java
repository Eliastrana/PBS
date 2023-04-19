package edu.ntnu.idatt1002.frontend.controllers;

import edu.ntnu.idatt1002.backend.CreateUserBackend;
import edu.ntnu.idatt1002.frontend.GUI;

import java.io.IOException;

/**
 * A class that handles the creation of a new user.
 * The class is used for creating a new user and saving it to a file.
 * The class also handles the navigation between the CreateUserView and the LoginView.
 * The class is used by the CreateUserView.
 *
 * @author Emil J., Vegard J., Sander S. & Elias T.
 * @version 0.5 - 19.04.2023
 */
public class CreateUserController {
  /**
   * The Gui.
   */
  private GUI gui;

  /**
   * Instantiates a new Create user controller.
   *
   * @param gui the gui
   */
  public CreateUserController(GUI gui) {
    this.gui = gui;
  }

  /**
   * A method that handles the back button.
   */
  public void handleBackButton() {
    gui.navigateToLogin();
  }

  /**
   * A method that handles the create button.
   *
   * @param username the username of the user
   * @param password the password of the user
   * @param email    the email of the user
   * @throws IOException the io exception
   */
  public void handleCreateButton(String username, String password, String email) throws IOException {
    String SALT = CreateUserBackend.generateSalt();
    String encryptedPassword = CreateUserBackend.encrypt(password, SALT);
    CreateUserBackend.saveUser(username, encryptedPassword, SALT, email);
    gui.navigateToMainApp();
  }
}
