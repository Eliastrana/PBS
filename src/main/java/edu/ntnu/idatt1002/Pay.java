package edu.ntnu.idatt1002;

import javafx.scene.control.Button;

public class Pay {

public static String payWindow() {

    Button ilovepay = new Button("Pay class");

    ilovepay.setOnAction(e -> {
        System.out.println("Yippie");
    });
    return null;
}


public static String payText() {
    return "Money is cheap";
}

}
