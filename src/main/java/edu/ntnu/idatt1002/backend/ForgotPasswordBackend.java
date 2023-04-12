package edu.ntnu.idatt1002.backend;

import edu.ntnu.idatt1002.frontend.Email;
import edu.ntnu.idatt1002.frontend.ForgotPassword;
import edu.ntnu.idatt1002.frontend.controllers.ForgotPasswordController;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.regex.Pattern;

public class ForgotPasswordBackend {

  private static final String PASSWORD_PATTERN =
          "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
  private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

  public static boolean changedPassword = false;

  public static void handleSubmit(ForgotPasswordController controller) throws Exception {
    if (ForgotPassword.emailString.isEmpty()) {
      ForgotPassword.errorLabel.setText("Please enter your email.");
    } else {
      String csvFile = "src/main/resources/users.csv";
      String tempCsvFile = "src/main/resources/temp_users.csv";
      String line = "";
      String csvSplitBy = ",";
      String salt = CreateUserBackend.generateSalt();
      String newPassword = CreateUserBackend.encrypt(ForgotPassword.newPasswordString, salt);

      try (BufferedReader br = new BufferedReader(new FileReader(csvFile));
      FileWriter fw = new FileWriter(tempCsvFile)) {

        // Loop through each line in the CSV file
        while ((line = br.readLine()) != null) {

          // Split the line by commas
          String[] user = line.split(csvSplitBy);

          // Check if the email matches the input email
          if (user[3].equals(ForgotPassword.emailString)) {

            // Update the password and salt
            user[1] = newPassword;
            user[2] = salt;
          }

          // Write the line to the temporary CSV file
          fw.write(String.join(",", user) + "\n");
        }
      } catch (IOException e) {
        e.printStackTrace();
      }

      // Replace the original CSV file with the updated content
      File originalFile = new File(csvFile);
      File tempFile = new File(tempCsvFile);
      if (originalFile.delete()) {
        if (!tempFile.renameTo(originalFile)) {
          throw new IOException("Failed to rename temp file");
        }
      } else {
        throw new IOException("Failed to delete original file");
      }
      System.out.println("Password reset");
      changedPassword = true;
      controller.handleResetButton();
    }
  }
}