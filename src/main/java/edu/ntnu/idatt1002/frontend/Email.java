package edu.ntnu.idatt1002.frontend;
import java.security.SecureRandom;
import java.util.Properties;
import java.util.Random;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.internet.MimeMessage;
import static javax.mail.Transport.send;



public class Email {

  // Define the email sender's credentials and SMTP server information
  final String senderEmail = "idatt1002.ntnu.pbs@gmail.com";
  final String senderPassword = "ewqtptwvusfuzrlm";
  final String smtpHost = "smtp.gmail.com";
  final String smtpPort = "587";

  // Define the email recipient, subject, and message body
  String recipientEmail;
  String subject = "Hallo";

  public void sendEmail(String email) throws MessagingException {

    String message = "Your password is:";
    String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    StringBuilder sb = new StringBuilder();
    Random random = new Random();
    int length = 10;
    for (int i = 0; i < length; i++) {
      int index = random.nextInt(alphabet.length());
      char randomChar = alphabet.charAt(index);
      sb.append(randomChar);
    }
    recipientEmail = email;
    message = sb.toString();
    // Create a JavaMail session with the SMTP server
    Properties props = new Properties();
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.host", smtpHost);
    props.put("mail.smtp.port", smtpPort);

    Session session = Session.getInstance(props, new Authenticator() {
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(senderEmail, senderPassword);
      }
    });

    // Create a new email message
    MimeMessage emailMessage = new MimeMessage(session);
    emailMessage.setFrom(new InternetAddress(senderEmail));
    emailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
    emailMessage.setSubject(subject);
    emailMessage.setText(message);

    // Send the email message
    send(emailMessage, senderEmail, senderPassword);
  }
}
