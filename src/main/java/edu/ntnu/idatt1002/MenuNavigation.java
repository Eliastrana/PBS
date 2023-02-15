package edu.ntnu.idatt1002;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;


//THIS CLASS IS CURRENTLY USELESS
public class MenuNavigation {

    GUI gui = new GUI();


    public void topMenu() {


        BorderPane borderPane = new BorderPane();
        ScrollPane scrollPane = new ScrollPane();
        MenuBar menuBar = new MenuBar();
        borderPane.setTop(menuBar);
        HBox topMenu = new HBox();
        topMenu.setAlignment(Pos.TOP_LEFT);


        Button overviewButton = new Button("Overview");
        overviewButton.setOnAction(event -> {


        });


    }

}



