package edu.ntnu.idatt1002.backend;

import edu.ntnu.idatt1002.frontend.CreateUser;
import edu.ntnu.idatt1002.frontend.GUI;
import edu.ntnu.idatt1002.frontend.Login;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserHandling {

    private static String email;

    private static String password;
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$");
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);


    public static String getEmail() {
        String line = "";
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/users.csv"))) {

            while ((line = reader.readLine()) != null) {
                String[] user = line.split(",");
                if (user[0].equals(GUI.getCurrentUser())) {
                    email = user[3];
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return email;
    }

    public static String getPassword() {
        String line = "";
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/users.csv"))) {

            GUI.getCurrentUser();
            while ((line = reader.readLine()) != null) {
                String[] user = line.split(",");
                if (user[0].equals(GUI.getCurrentUser())) {
                    String salt = user[2];
                    password = user[1];
                    password = LoginBackend.decrypt(password, salt);

                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return password;
    }

    public static void changeEmail(String email) throws Exception {
            String csvFile = "src/main/resources/users.csv";
            String tempCsvFile = "src/main/resources/temp_users.csv";
            String line = "";
            String csvSplitBy = ",";
            Matcher emailMatcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);

            if (!emailMatcher.matches()) {
                throw new Exception("Invalid email");
            }
            try (BufferedReader br = new BufferedReader(new FileReader(csvFile));
                 FileWriter fw = new FileWriter(tempCsvFile)) {

                // Loop through each line in the CSV file
                while ((line = br.readLine()) != null) {

                    // Split the line by commas
                    String[] user = line.split(csvSplitBy);

                    // Check if the email matches the input email
                    if (user[3].equals(getEmail())) {
                        // Update the password and salt
                        user[3] = email;
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
        }


    public static void changePassword(String password) throws Exception {
        String csvFile = "src/main/resources/users.csv";
        String tempCsvFile = "src/main/resources/temp_users.csv";
        String line = "";
        String csvSplitBy = ",";
        String salt = CreateUserBackend.generateSalt();
        String newPassword = CreateUserBackend.encrypt(password, salt);
        Matcher passwordMatcher = PASSWORD_PATTERN.matcher(password);

        if (!passwordMatcher.matches()) {
            throw new Exception("Invalid password");
        }
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile));
             FileWriter fw = new FileWriter(tempCsvFile)) {

            // Loop through each line in the CSV file
            while ((line = br.readLine()) != null) {

                // Split the line by commas
                String[] user = line.split(csvSplitBy);

                // Check if the email matches the input email
                if (user[3].equals(getEmail())) {
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
    }
}


