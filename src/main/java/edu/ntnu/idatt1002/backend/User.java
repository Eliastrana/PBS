package edu.ntnu.idatt1002.backend;

public class User extends Person {
  public User(String username, String password, String email) {
    super(username, password, email);
  }

  @Override
  public void login() {
    System.out.println("Admin logged in");
  }
}
