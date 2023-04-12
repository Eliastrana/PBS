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

public class CreateUserBackend {
  private static final String SECRET_KEY = "EliasErHeltSinnsyktKul";
  public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
          Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
  private static final Pattern VALID_PASSWORD_REGEX = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$");
  public static String currentUser;

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

  public static String generateSalt() {
    byte[] salt = new byte[16];
    SecureRandom random = new SecureRandom();
    random.nextBytes(salt);
    return Base64.getEncoder().encodeToString(salt);
  }

  public static boolean isValidEmail(String email) {
    Matcher emailMatcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
    return emailMatcher.matches();
  }

  public static boolean isValidPassword(String password) {
    Matcher passwordMatcher = VALID_PASSWORD_REGEX.matcher(password);
    return passwordMatcher.matches();
  }

  public static void saveUser(String username, String encryptedPassword, String salt, String email) throws IOException {
    BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/users.csv", true));
    writer.write(username + "," + encryptedPassword + "," + salt + "," + email);
    writer.newLine();
    writer.close();
    currentUser = username;
  }
}
