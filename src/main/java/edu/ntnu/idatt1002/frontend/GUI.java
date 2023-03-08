package edu.ntnu.idatt1002.frontend;

import edu.ntnu.idatt1002.frontend.menu.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import javafx.scene.layout.VBox;


public class GUI extends Application {

    //Each page has its own method, all the buttons are in the same method.
    //The buttons are then connected to the methods that open the pages.
    //The buttons are added to every single page individually, but they should be a separate entity
    //Each window is a StackPane, and the buttons are added to the StackPane
    //The StackPane is then added to the scene, and the scene is added to the stage


    //This stackPane holds the method of the overview window, this is done so that it is easier to
    //refresh the overview window.
    public StackPane overviewWindowStackPane = new StackPane(new VBox(Overview.overviewView()));
    private StackPane transferWindow = new StackPane(new VBox(Transfer.transferView()));
    private StackPane payWindow = new StackPane(new VBox(Pay.payPane()));
    private StackPane addExpenseWindow = new StackPane(new VBox(AddExpense.expanseView()));
    private StackPane moreWindow = new StackPane(new VBox(Settings.settingsView()));


    //HERE END THE DIFFERENT PANES AND BEGINS THE START METHOD, UPDATER AND TOPMENU

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Bank");
        primaryStage.setWidth(1000);
        primaryStage.setHeight(700);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(false);
        BorderPane borderPane = new BorderPane();

        borderPane.setStyle("-fx-background-color: #E6E8E6;");
        scrollPane.setStyle("-fx-background-color: #E6E8E6;");
        scrollPane.setContent(borderPane);

        Scene scene = new Scene(scrollPane);
        primaryStage.setScene(scene);
        primaryStage.show();

        overviewWindowStackPane.setVisible(true);
        transferWindow.setVisible(false);
        payWindow.setVisible(false);
        addExpenseWindow.setVisible(false);
        moreWindow.setVisible(false);

        StackPane root = new StackPane();

        root.getChildren().addAll(overviewWindowStackPane, transferWindow, payWindow, addExpenseWindow, moreWindow);
        borderPane.setTop(topMenu(primaryStage));
        borderPane.setCenter(root);
    }


    private void updatePane() {
        // update the contents of the paneToUpdate
        overviewWindowStackPane.getChildren().clear();
        overviewWindowStackPane.getChildren().add(Overview.overviewView());

    }


    //TOP MENU
    public HBox topMenu(Stage primaryStage) {

        BorderPane borderPane = new BorderPane();
        MenuBar menuBar = new MenuBar();
        borderPane.setTop(menuBar);
        HBox topMenu = new HBox();
        topMenu.setAlignment(Pos.TOP_CENTER);
        primaryStage.show();

        topMenu.setSpacing(20);
        topMenu.setPadding(new Insets(20, 20, 20, 20));


        //BUTTON 1
        Button overviewButton = new Button("Overview");
        overviewButton.setOnAction(event -> {
            try {

                overviewWindowStackPane.visibleProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue) {
                        updatePane();
                    }});

                overviewWindowStackPane.setVisible(true);
                transferWindow.setVisible(false);
                payWindow.setVisible(false);
                addExpenseWindow.setVisible(false);
                moreWindow.setVisible(false);
                System.out.println("overview button pressed");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        overviewButton.setStyle("-fx-font-size: 25px; -fx-min-width: 155px; -fx-min-height: 50px; -fx-background-color: #9FB8AD; -fx-border-width: 2; -fx-background-radius: 5em;");
        topMenu.getChildren().add(overviewButton);


        //BUTTON 2
        Button transferButton = new Button("Transfer");
        transferButton.setOnAction(event -> {
            try {
                overviewWindowStackPane.setVisible(false);
                transferWindow.setVisible(true);
                payWindow.setVisible(false);
                addExpenseWindow.setVisible(false);
                moreWindow.setVisible(false);

                System.out.println("transfer button pressed");

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        transferButton.setStyle("-fx-font-size: 25px; -fx-min-width: 155px; -fx-min-height: 50px; -fx-background-color: #9FB8AD; -fx-border-width: 2; -fx-background-radius: 5em;");
        topMenu.getChildren().add(transferButton);

        //BUTTON 3
        Button payButton = new Button("Pay");
        payButton.setOnAction(event -> {
            try {
                overviewWindowStackPane.setVisible(false);
                transferWindow.setVisible(false);
                payWindow.setVisible(true);
                addExpenseWindow.setVisible(false);
                moreWindow.setVisible(false);


                System.out.println("Pay button pressed");

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        payButton.setStyle("-fx-font-size: 25px; -fx-min-width: 155px; -fx-min-height: 50px; -fx-background-color: #9FB8AD; -fx-border-width: 2; -fx-background-radius: 5em;");
        topMenu.getChildren().add(payButton);

        //BUTTON 4
        Button addExpenseButton = new Button("Add Expense");
        addExpenseButton.setOnAction(event -> {
            try {

                overviewWindowStackPane.setVisible(false);
                transferWindow.setVisible(false);
                payWindow.setVisible(false);
                addExpenseWindow.setVisible(true);
                moreWindow.setVisible(false);


                System.out.println("add expense button pressed");

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        addExpenseButton.setStyle("-fx-font-size: 25px; -fx-min-width: 155px; -fx-min-height: 50px; -fx-background-color: #9FB8AD; -fx-border-width: 2; -fx-background-radius: 5em;");
        topMenu.getChildren().add(addExpenseButton);

        //BUTTON 5
        Button moreButton = new Button("More");
        moreButton.setOnAction(event -> {
            try {

                overviewWindowStackPane.setVisible(false);
                transferWindow.setVisible(false);
                payWindow.setVisible(false);
                addExpenseWindow.setVisible(false);
                moreWindow.setVisible(true);

                System.out.println("more button pressed");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        moreButton.setStyle("-fx-font-size: 25px; -fx-min-width: 155px; -fx-min-height: 50px; -fx-background-color: #9FB8AD; -fx-border-width: 2; -fx-background-radius: 5em;");
        topMenu.getChildren().add(moreButton);

        return topMenu;
    }

// register a ChangeListener with the StackPane's visibleProperty

}
