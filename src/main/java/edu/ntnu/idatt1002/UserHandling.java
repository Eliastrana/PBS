package edu.ntnu.idatt1002;

import edu.ntnu.idatt1002.frontend.CreateUser;
import edu.ntnu.idatt1002.frontend.GUI;
import edu.ntnu.idatt1002.frontend.Login;

import java.io.*;

public class UserHandling {

    private static String email;

    private static String password;

    public static String getEmail() {
        String line = "";
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/users.csv"))) {

            GUI.getCurrentUser();
            while ((line = reader.readLine()) != null) {
                String[] user = line.split(",");
                if (user[0].equals(GUI.getCurrentUser())) {
                    email = user[3];
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
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
                    password = Login.decrypt(password, salt);

                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
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
        String salt = CreateUser.generateSalt();
        String newPassword = CreateUser.encrypt(password, salt);

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


