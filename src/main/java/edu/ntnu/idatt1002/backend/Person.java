package edu.ntnu.idatt1002.backend;

import java.util.HashMap;

public abstract class Person {
  private String username;
  private String password;
  private String email;

  public Person(String username, String password, String email) {
    this.username = username;
    this.password = password;
    this.email = email;
  }

  public abstract void login();
  public void changePassword(String newPassword) {
    this.password = newPassword;
  }
  public void changeEmail(String newEmail) {
    this.email = newEmail;
  }
  public HashMap<String, String> getLoginInfo() {
    HashMap<String, String> loginInfo = new HashMap<>();
    loginInfo.put("username", this.username);
    loginInfo.put("password", this.password);
    return loginInfo;
  }
}
