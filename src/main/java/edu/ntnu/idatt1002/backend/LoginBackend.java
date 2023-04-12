package edu.ntnu.idatt1002.backend;

import edu.ntnu.idatt1002.frontend.controllers.LoginController;
import edu.ntnu.idatt1002.frontend.utility.SoundPlayer;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.spec.KeySpec;
import java.util.Base64;

public class LoginBackend {
  private static final String SECRET_KEY = "EliasErHeltSinnsyktKul";

  public static String decrypt(String password, String SALT) {
    try {
      byte[] iv = new byte[16];
      IvParameterSpec ivSpec = new IvParameterSpec(iv);

      SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
      KeySpec spec = new PBEKeySpec(SECRET_KEY.toCharArray(), SALT.getBytes(), 65536, 256);
      SecretKey tmp = factory.generateSecret(spec);
      SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

      Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
      cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
      return new String(cipher.doFinal(Base64.getDecoder().decode(password)));
    } catch (Exception e) {
      System.out.println("Error while decrypting: " + e.toString());
    }
    return null;
  }

  public static void login(String username, String password, LoginController controller) {
    String csvFile = "src/main/resources/users.csv";
    String line = "";
    BufferedReader br = null;
    try {
      br = new BufferedReader(new FileReader(csvFile));
    } catch (FileNotFoundException ex) {
      throw new RuntimeException(ex);
    }
    while (true) {
      try {
        if ((line = br.readLine()) == null) break;
      } catch (IOException ex) {
        throw new RuntimeException(ex);
      }
      String[] user = line.split(",");
      if (user[0].equals(username) || user[3].equals(username)) {
        String encryptedPassword = user[1];
        String SALT = user[2];
        String decryptedPassword = decrypt(encryptedPassword, SALT);
        if (password.equals(decryptedPassword)) {
          System.out.println("Logged in");
          SoundPlayer.play("src/main/resources/16bitconfirm.wav");
          controller.handleLoginButton();
        }
      }
    }
  }

}
