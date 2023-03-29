package edu.ntnu.idatt1002.frontend.utility;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TextField;
import javafx.util.Duration;

import java.awt.*;

public class OutlinedTextField extends TextField {
    private Timeline timeline;

    public OutlinedTextField() {
        // Set the initial border color to transparent
        setStyle("-fx-border-color: transparent;");

        // Create a timeline to animate the border color
        timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(styleProperty(), "-fx-border-color: transparent;")),
                new KeyFrame(Duration.seconds(2), new KeyValue(styleProperty(), "-fx-border-color: red;")),
                new KeyFrame(Duration.seconds(4), new KeyValue(styleProperty(), "-fx-border-color: transparent;"))
        );

        // Show the timeline when the text field is focused
        focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (isNowFocused) {
                // Only play the timeline if the text field is empty
                if (getText().isEmpty()) {
                    timeline.playFromStart();
                }
            } else {
                timeline.stop();
                setStyle("-fx-border-color: transparent;");
            }
        });

        // Show the timeline if the text field is empty and focus is not on it
        textProperty().addListener((obs, oldText, newText) -> {
            if (newText.isEmpty() && !isFocused()) {
                timeline.playFromStart();
            } else {
                timeline.stop();
                setStyle("-fx-border-color: transparent;");
            }
        });
    }




    public StringProperty getOutlinedStyleProperty() {
        return styleProperty();
    }


}
