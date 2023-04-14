package edu.ntnu.idatt1002.frontend.utility;

import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class MyToggleGroupClass {
    private ToggleGroup toggleGroup;

    public MyToggleGroupClass() {
        RadioButton lightmode = new RadioButton("Lightmode");
        RadioButton darkmode = new RadioButton("Darkmode");
        RadioButton colorblind = new RadioButton("Colorblindmode");

        toggleGroup = new ToggleGroup();
        lightmode.setToggleGroup(toggleGroup);
        darkmode.setToggleGroup(toggleGroup);
        colorblind.setToggleGroup(toggleGroup);

        // Add change listener to toggle group
        toggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            RadioButton selectedRadioButton = (RadioButton) toggleGroup.getSelectedToggle();
            if (selectedRadioButton != null) {
                String selectedText = selectedRadioButton.getText();
                // Notify other classes of the selected radio button change
                notifySelectedRadioButtonChange(selectedText);
            }
        });
    }

    public ToggleGroup getToggleGroup() {
        return toggleGroup;
    }

    // Method to notify other classes of the selected radio button change
    private void notifySelectedRadioButtonChange(String selectedText) {
        // TODO: Implement logic to notify other classes of the selected radio button change
    }
}

