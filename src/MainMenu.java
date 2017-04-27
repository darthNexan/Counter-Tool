import javafx.animation.Animation;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by dennis on 3/3/17.
 */
public class MainMenu {




    static {


    }
    /**
     * Sets up the menu for the Counter Tutorial
     * @param window
     */
    public static void goToCounterTutorial(Stage window){
        VBox vBox = new VBox();
        HBox hBox = new HBox();


        ComboBox<String> tutorials = new ComboBox<>();

        tutorials.getItems().addAll("TFlipFlopCounters","DFlipFlopCounters","JKFlipFlopCounters");
        tutorials.setValue("JKFlipFlopCounters");

        //Button to go to the quiz
        Button quizButton = new Button("Go to quiz");
        quizButton.setWrapText(true);
        quizButton.setOnAction(event -> {
            QuizSection.goToQuiz(tutorials.getValue(), window);
        });
        //Button to go to the tutorial
        Button tutorialButton = new Button("Go to tutorial");
        tutorialButton.setWrapText(true);
        tutorialButton.setOnAction(event -> {
            NoteSection.goToNoteSection(tutorials.getValue(), window);
        });
        quizButton.autosize();
        tutorialButton.autosize();

        //formatting hbox
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(20);
        hBox.setPadding(new Insets(10,0,10,0));
        hBox.getChildren().addAll(tutorialButton,quizButton);


        //button to return to main menu
        Button backButton = new Button("Back");
        backButton.setOnAction(event -> goToSetupMainMenu(window));



        //setting up vbox
        vBox.getChildren().addAll(tutorials,hBox,backButton);

        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(20);
        hBox.setSpacing(20);

        vBox.setPadding(new Insets(30,60,20,60));

        vBox.setStyle( "-fx-background-color: #375ba3;");  //Style Change


        //Sets a fixed width to the window
//        vBox.setMinHeight(300);
//        vBox.setMaxHeight(300);
//        vBox.setMinWidth(300);
//        vBox.setMaxWidth(300);
        Scene scene = new Scene(vBox);

        window.resizableProperty().setValue(false);
        window.setScene(scene);
        window.show();
    }


    /**
     * changes the current scene to the foundation menu
     * @param window
     */
    public static void goToFoundationTutorial(Stage window){

        VBox vBox = new VBox();
        HBox hBox = new HBox();

        ComboBox<String> tutorials = new ComboBox<>();

        tutorials.getItems().addAll("LogicGates","FlipFlops","KarnaughMaps");
        tutorials.setValue("LogicGates");

        vBox.setPadding(new Insets(30,60,20,60));
        vBox.setSpacing(20);
        hBox.setSpacing(20);
        vBox.setStyle( "-fx-background-color: #375ba3;");  //Style Change



        Button tutorialButton = new Button("Go to Tutorial");
        tutorialButton.setWrapText(true);
        Button quizButton = new Button("Go to Quiz");
        quizButton.setWrapText(true);
        tutorialButton.autosize();
        quizButton.autosize();
        tutorialButton.setOnAction(event -> {

            //gets the value int he combo box and passes it to the next scene
            //System.out.println(tutorials.getValue());
            NoteSection.goToNoteSection(tutorials.getValue(), window);
        });

        quizButton.setOnAction(event -> {
            QuizSection.goToQuiz(tutorials.getValue(), window);
        });
        quizButton.autosize();
        tutorialButton.autosize();

        Button backButton = new Button("Back");
        backButton.setOnAction(event -> goToSetupMainMenu(window));
        hBox.getChildren().addAll(tutorialButton,quizButton);

        vBox.getChildren().addAll(tutorials, hBox,backButton);


//        vBox.setMinHeight(300);
//        vBox.setMaxHeight(300);
//        vBox.setMinWidth(300);
//        vBox.setMaxWidth(300);
        vBox.setAlignment(Pos.CENTER);

        window.resizableProperty().setValue(false);
        window.setScene(new Scene(vBox));
        window.show();

    }


    /**
     *
     * @param window
     */
    public static void goToSetupMainMenu(Stage window){

        HBox hBox0 = new HBox();
        HBox hBox1 = new HBox();
        VBox vBox = new VBox();
        ImageView headerGif = new ImageView("file:./resource/header.gif");
        headerGif.setFitWidth(360);
        headerGif.setFitHeight(200);
        vBox.getChildren().add(headerGif);


        //used to go to the foundations menu
        Button practiceButton = new Button("Foundations");
        //used to go to the synchronous counters menu
        Button syncButton = new Button("Synchronous Counter");


        Text titleText = new Text("Synchronous counters teaching tool");
        titleText.setFill(Color.web("#ffffff")); //Style Change



        syncButton.setOnAction(e-> goToCounterTutorial(window));
        practiceButton.setOnAction(e-> goToFoundationTutorial(window));



        Button aboutButton = new Button("About");
        Button objButton = new Button("Objectives");

        objButton.setOnAction(event -> AlertBox.showObjectives());
        aboutButton.setOnAction(event -> AlertBox.showAbout());

        hBox0.setAlignment(Pos.CENTER);
        hBox1.setAlignment(Pos.CENTER);
        vBox.setAlignment(Pos.TOP_CENTER);

        hBox0.getChildren().addAll(practiceButton, syncButton);
        hBox1.getChildren().addAll(aboutButton,objButton);
        vBox.getChildren().addAll(titleText,hBox0,hBox1);
        vBox.setSpacing(20);
        hBox0.setSpacing(20);
        hBox1.setSpacing(20);
        hBox0.autosize();
        vBox.autosize();
        vBox.setStyle( "-fx-background-color: #375ba3;");  //Style Change



        Scene scene = new Scene(vBox);
        window.setWidth(360);
        window.setHeight(400);
        window.centerOnScreen();

        window.setScene(scene);
        window.show();
    }

}
