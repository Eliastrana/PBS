package edu.ntnu.idatt1002.backend;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;
import java.util.Random;

import static javax.mail.Transport.send;

/**
 * A class that sends an email to the user.
 *
 * @author Emil J., Vegard J., Sander S. and Elias T.
 * @version 0.5 - 19.04.2023
 */
public class Email {

  /**
   * The email address of the sender.
   */
  private final String senderEmail = "idatt1002.ntnu.pbs@gmail.com";
  /**
   * The password of the sender email.
   */
  private final String senderPassword = "ewqtptwvusfuzrlm";
  /**
   * The Smtp host.
   */
  private final String smtpHost = "smtp.gmail.com";
  /**
   * The Smtp port.
   */
  private final String smtpPort = "587";

  /**
   * The email address of the recipient.
   */
  String recipientEmail;
  /**
   * The password of the recipient.
   */
  String passwordString;
  /**
   * The subject of the email.
   */
  String subject = "Forgotten your password?";

  /**
   * Sends an email to the user with their password.
   *
   * @param email    the email address
   * @param password the password of the user
   * @throws MessagingException the messaging exception
   */
  public void sendEmail(String email, String password) throws MessagingException {

    String message = "Your password is:";

    recipientEmail = email;
    passwordString = password;

    //String emailPhoto = "src/main/resources/icon.png";




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
    // Create a MimeMultipart object for the HTML content and the image
    MimeMultipart multipart = new MimeMultipart("related");

// Create a MimeBodyPart for the HTML content
    MimeBodyPart htmlPart = new MimeBodyPart();
    htmlPart.setContent("<html> <body style='background-color: #f2f2f2;'> " +
            "<div style='text-align: center;'>" +
            "<h1>You silly goose!</h1> " +
            "<img src=\"cid:image1\">" +
            "<p> Your master-password is: " + passwordString + "</p>" +
            "<p> Best regard, Public Banking Service.</p>" +
            "</div>" +
            "</body> </html>", "text/html");



// Create a MimeBodyPart for the image
    MimeBodyPart imagePart = new MimeBodyPart();
    Random rand = new Random();
    int n = rand.nextInt(5) + 1;
    DataSource fds = new FileDataSource("src/main/resources/memes/mailmeme"+n+".jpg");
    imagePart.setDataHandler(new DataHandler(fds));
    imagePart.setHeader("Content-ID", "<image1>");

// Add both parts to the MimeMultipart object
    multipart.addBodyPart(htmlPart);
    multipart.addBodyPart(imagePart);

// Set the content of the MimeMessage to the MimeMultipart object
    emailMessage.setContent(multipart);


    // Send the email message
    send(emailMessage, senderEmail, senderPassword);
  }
}
