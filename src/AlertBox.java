import javafx.application.HostServices;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import javafx.scene.paint.Color;
/**
 * @author Dennis Guye
 *
 * Defines all the secondary windows used in the application
 */
public class AlertBox {

    private static HostServices hostServices;//used to access pdf opener thingy

    /**
     * Initializes hostservices needs to be run!
     * @param host
     */
    static void init(HostServices host){
        hostServices = host;
    }

    /**
     * Generic User prompt
     * @param title
     * @param message
     */

    static void displayAlert(String title, String message){
        Stage window = new Stage();

        window.setTitle(title);
        window.initModality(Modality.APPLICATION_MODAL);
        Button closeButton = new Button("OK");



        closeButton.setOnAction(event -> window.close());

        VBox vBox = new VBox(new Text(message), closeButton);

        vBox.setAlignment(Pos.CENTER);
        vBox.setMinWidth(300);
        vBox.setMinHeight(200);

        vBox.setAlignment(Pos.TOP_CENTER);

        window.setScene(new Scene(vBox));

        window.getIcons().add(new Image("file:./resource/default.png"));
        window.showAndWait();

    }

    /**
     * Prompts the user with options at the end of the quize
     * @param score
     * @param primaryWindow
     * @param section
     */
    static void endOfQuizPrompt(int score, Stage primaryWindow, String section){

        Stage window = new Stage();
        Text scoreText = new Text("Score: " + score);
        Text text = new Text();


        Button mainMenuButton = new Button("Go to Main menu");
        Button redoQuizButton = new Button("Redo quiz");
        Button notesButton = new Button("Go to Notes");
        HBox hBox = new HBox(10, mainMenuButton, redoQuizButton, notesButton);
        VBox vBox = new VBox(10, scoreText, text, hBox);
        vBox.setAlignment(Pos.TOP_LEFT);
        hBox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vBox);
        window.initModality(Modality.APPLICATION_MODAL);

        window.setTitle("You finished the quiz!");
        mainMenuButton.setOnAction(event ->{
            MainMenu.goToSetupMainMenu(primaryWindow);
            window.close();
        });

        redoQuizButton.setOnAction(event ->{
            QuizSection.goToQuiz(section,primaryWindow);
            window.close();
        });
        notesButton.setOnAction(event -> {
            NoteSection.goToNoteSection(section, primaryWindow);
            window.close();
        });

        if(score < 3) {
            text.setText("You should probably return to the notes.");
        }
        else {
            text.setText("Good job! You are ready to move on to the next section.");
        }

        window.getIcons().add(new Image("file:./resource/default.png"));
        window.setScene(scene);

        window.showAndWait();
    }

    /**
     * Prompts of users with options at the end of the quiz
     * @param primaryWindow
     * @param section
     */
    static void endOfNotesPrompt(Stage primaryWindow, String section){

        Stage window = new Stage();
        Text text = new Text("End of Tutorial.");


        Button mainMenuButton = new Button("Go to Main menu");
        Button quizButton = new Button("Test yo' Self");


        HBox hBox = new HBox(10, mainMenuButton, quizButton);
        VBox vBox = new VBox(10, text, hBox);
        vBox.setAlignment(Pos.TOP_LEFT);
        hBox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vBox);
        window.initModality(Modality.APPLICATION_MODAL);

        window.setTitle("You Finished the Tutorial!");
        mainMenuButton.setOnAction(event ->{
            MainMenu.goToSetupMainMenu(primaryWindow);
            window.close();
        });

        quizButton.setOnAction(event ->{
            QuizSection.goToQuiz(section,primaryWindow);
            window.close();
        });

        window.getIcons().add(new Image("file:./resource/default.png"));
        window.setScene(scene);



        window.showAndWait();
    }

    /**
     * Shows the objectives of the application upon opening
     */

    public static void showObjectives(){
        Stage secondaryStage = new Stage();
        secondaryStage.setTitle("Welcome!");
        VBox vBox = new VBox();
        vBox.setStyle( "-fx-background-color: #FFFFFF;");  //Style Change

        Text title = new Text();
        Text objectives = new Text();
        Button closeButton = new Button("Continue");//enables user to continue to application



        Scanner sc;
        try {
            sc = new Scanner(new File("./resource/Objectives.txt"));
            title.setText(sc.nextLine());//title of objectives
            title.setFill(Color.web("#375ba3"));

            String tempStr = "";

            while (sc.hasNext()){
                tempStr += sc.nextLine() +"\n";
            }

            vBox.setPadding(new Insets(30));
            vBox.setSpacing(20);
            vBox.setAlignment(Pos.CENTER);
            objectives.setText(tempStr);


            title.setFont(Font.font("System", FontWeight.BOLD, 16));
            title.setWrappingWidth(450);

            objectives.setFont(Font.font("System", 14));
            objectives.setWrappingWidth(550);
            closeButton.setOnAction(event -> secondaryStage.close());



            vBox.getChildren().addAll(title, objectives, closeButton);

            secondaryStage.getIcons().add(new Image("file:./resource/default.png"));
            secondaryStage.setScene(new Scene(vBox));
            secondaryStage.initModality(Modality.APPLICATION_MODAL);
            secondaryStage.setResizable(false);

            secondaryStage.showAndWait();


        }
        catch (FileNotFoundException e){
            AlertBox.displayAlert("File not found exception", "Objectives.txt was not found in the resource folder");

        }


    }

    /**
     * Shows the solution of structured questions
     * @param section
     * @return
     */
    public static boolean showSolution(String section){
        Stage secondaryStage = new Stage();
        final boolean[] result = {false};
        System.out.println(section);
        ImageView img = new ImageView("file:./resource/SynchronousCounter/"+section+"StructuredSolution.png");
        Button correctButton = new Button("Yes");

        correctButton.setOnAction(event -> {
            result[0] =true;
            secondaryStage.close();
        });


        Button wrongButton = new Button("No");
        wrongButton.setOnAction(event -> secondaryStage.close());
        HBox controls = new HBox(10,correctButton,wrongButton);
        VBox rootLayout = new VBox(new Text("Were you write?"),img,controls);



        secondaryStage.setTitle("Solution");
        secondaryStage.setScene(new Scene(rootLayout));
        secondaryStage.getIcons().add(new Image("file:./resource/default.png"));

        secondaryStage.initModality(Modality.APPLICATION_MODAL);
        secondaryStage.showAndWait();


        return result[0];
    }

    /**
     * Displays About window, gives user the opportunity to open the readme PDF
     */
    public static void showAbout(){
        Stage secondaryStage = new Stage();
        secondaryStage.getIcons().add(new Image("file:./resource/default.png"));
        secondaryStage.setTitle("About");
        HBox buttonLayout = new HBox();
        VBox rootLayout = new VBox();
        Text infoText = new Text("This application was developed for Computer Architecture by Chad Thomas" +
                "Dennis Guye, Kervel Marcelle, Kevin Johnson, and Ryvon Gittens.");
        infoText.setWrappingWidth(400);
        Button closeButton =  new Button("Close");

        closeButton.setOnAction(event -> secondaryStage.close());

        Button readme = new Button("Readme");

        readme.setOnAction(event -> {
            try {
                File readmeFile = new File("./resource/readme.pdf");

                hostServices.showDocument(readmeFile.getAbsolutePath());

            }catch (Exception ex){
                displayAlert("Oops something went wrong", ex.getMessage());

            }
            secondaryStage.close();

        });

        buttonLayout.getChildren().addAll(readme, closeButton);
        rootLayout.getChildren().addAll(infoText, buttonLayout);


        secondaryStage.setScene(new Scene(rootLayout));
        secondaryStage.initModality(Modality.APPLICATION_MODAL);
        secondaryStage.showAndWait();


    }
}
