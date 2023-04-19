package edu.ntnu.idatt1002.frontend.utility;

import java.awt.Desktop;
import java.net.URI;

public class ContactUs {
    private static final String RECIPIENT_EMAIL = "idatt1002.ntnu.pbs@gmail.com";

    public static void sendEmail() {
        try {
            Desktop.getDesktop().mail(new URI("mailto:" + RECIPIENT_EMAIL));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}




