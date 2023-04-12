package edu.ntnu.idatt1002.frontend.controllers;

import edu.ntnu.idatt1002.frontend.ForgotPassword;
import edu.ntnu.idatt1002.frontend.GUI;

public class ForgotPasswordController {
  private GUI gui;
  private ForgotPassword forgotPassword;

  public ForgotPasswordController(GUI gui) {
    this.gui = gui;
  }

  public void handleBackButton() {
    gui.navigateToLogin();
  }

  public void handleResetButton() {
    gui.navigateToLogin();
  }

  // Other methods for handling UI interactions
}
