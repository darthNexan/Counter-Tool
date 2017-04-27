import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.scene.paint.Color;




/**
 * @author Dennis Guye and Chad Thomas
 * Sets up the stage for the notes section.
 *
 */


public class NoteSection {


    private static int count;
    private static ArrayList<Note> notes;
    private static String section;
    private static ImageView noteDiagram;
    private static Text notesText;
    private static BorderPane root;
    private static Text diagramText;

    static {
        notesText = new Text();
        notes = new ArrayList<>();
        noteDiagram = new ImageView();
        root = new BorderPane();
        diagramText = new Text();

    }

    /**
     * Goes to the next note specified by the counter and changes the text and image
     */
    private static void changeNote(){
        String diagramLocation = (notes.get(count).getResource().equalsIgnoreCase("null")? "./resource/default.png"
                : notes.get(count).getResource());
        noteDiagram.setImage(new Image("file:"+ diagramLocation));
//        noteDiagram.setFitWidth(380);
//        noteDiagram.setFitHeight(380);
//
//        noteDiagram.setPreserveRatio(true);
        notesText.setText(notes.get(count).getLine());
    }

    /**
     * Sets up the rightpane, which contains the diagram for each note
     *
     */
    private static void rightPane(){
        //VBox to display the diagrams within the notes
        VBox diagram = new VBox();
        ScrollPane sp = new ScrollPane();
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        sp.setMinWidth(410);
        sp.setMaxWidth(410);
        sp.setMinHeight(400);
        sp.setMaxHeight(400);

        String diagramLocation = (notes.get(count).getResource().equalsIgnoreCase("null")? "./resource/default.png"
                : notes.get(count).getResource());


        noteDiagram.setImage(new Image("file:"+ diagramLocation));
//        noteDiagram.setFitWidth(380);
//        noteDiagram.setFitHeight(380);
        sp.setContent(noteDiagram);

//        diagram.setMinHeight(350);
        diagram.setMinWidth(410);
        //noteDiagram.fitWidthProperty().bind(diagram.widthProperty());
        //noteDiagram.fitHeightProperty().bind(diagram.heightProperty());

//
//        noteDiagram.setPreserveRatio(true);
        diagram.setAlignment(Pos.CENTER);
//        diagram.setPadding(new Insets(10));
        sp.setPannable(true);
        diagram.setStyle( "-fx-border-style: solid inside;" +
                "-fx-border-width: 1;" +
                "-fx-border-insets: 0;" +
                "-fx-border-radius: 0;" +
                "-fx-border-color: #375ba3;" +
                "-fx-background-color: WHITE;");

        diagram.getChildren().addAll(diagramText, sp);
        root.setRight(diagram);

    }

    /**
     * Sets up the left pan that contains the menu for each note
     *
     */
    private static void leftPane(Stage window){
        //VBox to display the menu
        VBox menu = new VBox();
        HBox hBox = new HBox();
        menu.setAlignment(Pos.CENTER_LEFT);
        menu.setStyle( "-fx-border-style: solid inside;" +
                "-fx-border-width: 1;" +
                "-fx-border-insets: 0;" +
                "-fx-border-radius: 0;" +
                "-fx-border-color: #375ba3;" +  //Style Change
                "-fx-background-color: WHITE;");

        ComboBox<String> comboBox  = new ComboBox<>();
        comboBox.getItems().addAll("LogicGates","FlipFlops","KarnaughMaps","TFlipFlopCounters","DFlipFlopCounters","JKFlipFlopCounters");
        comboBox.setValue("LogicGates");


        Button goButton = new Button("Go to Section");
        goButton.setWrapText(true);

        goButton.setOnAction(e -> goToNoteSection(comboBox.getValue(), window));


        menu.setPadding(new Insets(20));
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER);



        Text menuT = new Text("Menu");
        menuT.setFont(Font.font("System", FontWeight.BOLD, 16));



        hBox.getChildren().addAll(comboBox, goButton);
        menu.getChildren().addAll(menuT, hBox);

        menu.setMaxWidth(270);
        root.setLeft(menu);
    }

    /**
     * Sets up the centerpane that contains the text for each note
     */
    private static void centerPane(){
        //VBox to display the notes
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(50));
        vBox.setAlignment(Pos.TOP_LEFT);

        vBox.setStyle( "-fx-border-style: solid inside;" +
                "-fx-border-width: 1;" +
                "-fx-border-insets: 0;" +
                "-fx-border-radius: 0;" +
                "-fx-border-color: #375ba3;" +   //Style Change
                "-fx-background-color: WHITE;");
        notesText.setText(notes.get(count).getLine());
        vBox.setMaxWidth(600);
        vBox.setMinWidth(600);

        vBox.getChildren().addAll(notesText);

        root.setCenter(vBox);

    }

    /**
     * Sets up the top pane that holds the current title
     */
    private static void topPane(){

        HBox title = new HBox();
        title.setAlignment(Pos.TOP_CENTER);
        title.setPadding(new Insets(30));
        title.setStyle( "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 0;" +
                "-fx-border-radius: 0;" +
                "-fx-border-color: #375ba3;" +   //Style Change
                "-fx-background-color: WHITE;");

        Text titleText = new Text(section);
        titleText.setFont(Font.font("System", FontPosture.ITALIC, 32));
        title.getChildren().addAll(titleText);
        root.setTop(title);


    }

    /**
     * Sets up the bottom pane which contains the user controls ie. Main menu, previous Page and next Page.
     * @param window Reference to the primary stage
     */
    private static void bottomPane(Stage window){

        Button backButton = new Button("Main Menu");
        Button prevButton = new Button("Previous Page");
        Button nextButton = new Button("Next Page");

        //HBox to display the back, previous and next buttons
        HBox bar = new HBox();
        bar.setAlignment(Pos.CENTER_RIGHT);
        bar.setSpacing(50);
        bar.setPadding(new Insets(50));
        bar.setStyle( "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 0;" +
                "-fx-border-radius: 0;" +
                "-fx-border-color: #375ba3;" +  //Style Change
                "-fx-background-color: WHITE;");
        prevButton.setOnAction(event -> {
            if(count>0){
                count--;
                changeNote();
            }

        });
        nextButton.setOnAction(event -> {
            if(count <= notes.size()-2) {

                count++;
                changeNote();
            }
            else {
                AlertBox.endOfNotesPrompt(window, section);
            }

        });
        backButton.setOnAction(event -> MainMenu.goToSetupMainMenu(window));



        bar.getChildren().addAll(backButton, prevButton, nextButton);


        root.setBottom(bar);

    }

    /**
     * Initializes the notes Arraylist with the notes and diagrams for the section
     * @param noteType The notes to be displayed
     */

    private static void getNotes(String noteType){
        Scanner scNote;
        Scanner scResource;
        notes = new ArrayList<>();

        try {
            scNote = new Scanner(new FileReader(new File("./resource/" + noteType + "Note.txt")));
            scResource = new Scanner(new FileReader(new File("./resource/"+ noteType + "Resource.txt")));
            Note temp = new Note();
            int i = 0;

            //each note will have 15 lines
            while(scNote.hasNext()) {

                temp.setLine(scNote.nextLine());
                if (i == 14 || !scNote.hasNext()) {
                    i = 0;
                    notes.add(temp);
                    temp = new Note();
                }
                else {
                    i++;

                }

            }

            i=0;
            //System.out.println(notes.size());
            //reading in the diagrams
            while (scResource.hasNext()){
                //System.out.println(i);
                notes.get(i).setResource(scResource.nextLine());
                i++;
               // System.out.println(i);
            }
        }
        catch (FileNotFoundException e){
            AlertBox.displayAlert("FILE NOT FOUND", e.getMessage());

        }
        catch (Exception e){
            AlertBox.displayAlert("ERROR", e.getMessage());
        }


    }


    /**
     * Sets up the stage for the note sections
     * @param noteType Note to be displayed
     * @param window A reference to the primary stage
     */

    public static void goToNoteSection(String noteType, Stage window){

        root = new BorderPane();
        count = 0;
        section = noteType;
        getNotes(noteType);
        topPane();
        bottomPane(window);
        rightPane();



        centerPane();
        leftPane(window);
        root.autosize();

        Scene scene = new Scene(root);
        window.setScene(scene);

        window.setWidth(1280);
        window.setHeight(720);
        window.centerOnScreen();
    }
}
