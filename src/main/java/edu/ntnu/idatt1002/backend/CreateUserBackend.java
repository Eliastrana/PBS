package edu.ntnu.idatt1002.backend;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class that handles the creation of a new user.
 * The class encrypts the password and writes the user to a file.
 * The class also checks if the email and password is valid.
 *
 * @author Emil J., Vegard J., Sander S. & Elias T.
 * @version 0.5 - 19.04.2023
 */
public class CreateUserBackend {
  /**
   * The secret key used to encrypt the password.
   */
  private static final String SECRET_KEY = "EliasErHeltSinnsyktKul";
  /**
   * A pattern that checks if the email is valid.
   */
  public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
          Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
  /**
   * A pattern that checks if the password is valid.
   */
  private static final Pattern VALID_PASSWORD_REGEX = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$");
  /**
   * The current user.
   */
  public static String currentUser;

  /**
   * A method that encrypts the password.
   * The method uses the secret key and a salt to encrypt the password.
   *
   * @param password the password to encrypt
   * @param SALT     the salt used to encrypt the password
   * @return the encrypted password
   */
  public static String encrypt(String password, String SALT) {
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

  /**
   * A method that generates a salt used to encrypt the password.
   *
   * @return the salt
   */
  public static String generateSalt() {
    byte[] salt = new byte[16];
    SecureRandom random = new SecureRandom();
    random.nextBytes(salt);
    return Base64.getEncoder().encodeToString(salt);
  }

  /**
   * Checks if the email and password is valid.
   *
   * @param email the email
   * @return a boolean that is true if the email is valid
   */
  public static boolean isValidEmail(String email) {
    Matcher emailMatcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
    return emailMatcher.matches();
  }

  /**
   * Checks if the password is valid.
   *
   * @param password the password
   * @return a boolean that is true if the password is valid
   */
  public static boolean isValidPassword(String password) {
    Matcher passwordMatcher = VALID_PASSWORD_REGEX.matcher(password);
    return passwordMatcher.matches();
  }

  /**
   * A method that writes the user to a file.
   * The method writes the username, encrypted password, salt and email to a file.
   *
   * @param username          the username
   * @param encryptedPassword the encrypted password
   * @param salt              the salt
   * @param email             the email
   * @throws IOException the io exception
   */
  public static void saveUser(String username, String encryptedPassword, String salt, String email) throws IOException {
    BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/users.csv", true));
    writer.write(username + "," + encryptedPassword + "," + salt + "," + email);
    writer.newLine();
    writer.close();
    currentUser = username;
  }
}
