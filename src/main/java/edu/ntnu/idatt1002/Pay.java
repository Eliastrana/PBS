package edu.ntnu.idatt1002;

import com.sun.javafx.menu.MenuItemBase;
import com.sun.javafx.scene.control.InputField;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class Pay {

public static String payWindow() {
    BorderPane borderPane = new BorderPane();
    VBox vBox = new VBox();
    vBox.getChildren().add(new Label("This is the pay page"));
    Scene payScene = new Scene(vBox, 800, 600);
    borderPane.setCenter(payScene.getRoot());
    return "Pay";
}


public static String payText() {
    return "Money is cheap";
}



}
