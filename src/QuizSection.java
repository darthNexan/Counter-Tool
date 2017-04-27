import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
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
 * @author Dennis Guye
 * Sets up the stage for the quiz.
 *
 */

public class QuizSection {

    private static int score;
    private static int questionNo;

    //questionsToBeDisplayed
    private static ArrayList<Question> questions;

    private static ScrollPane sp;

    private static BorderPane borderPane;

    //center area nodes
    private static Text questionText;

    //left area nodes

    private static Text scoreText;


    //right area nodes

    private static ImageView questionImg;

    private static RadioButton[] answerToggles;

    private static String section;

    private static ToggleGroup answerGroup;

    private static VBox vBoxCenter;

    static {
        vBoxCenter = new VBox();

        score = 0;

        questionNo = 0;

        questionText = new Text();

        answerToggles = new RadioButton[4];

        borderPane = new BorderPane();

        scoreText = new Text();

        questionImg = new ImageView();

        answerGroup = new ToggleGroup();
    }




    /**
     * Returns an arraylist containing the questions to be displayed.
     * @param directoryLocation The question file to open
     * @return Questions to be displayed
     */

    private static ArrayList<Question> getQuestions(String directoryLocation){
        Scanner sc;
        ArrayList<Question> questions = new ArrayList<>();
        try {
            sc = new Scanner(new FileReader(new File(directoryLocation)));
            while(sc.hasNext()){
                sc.nextLine();
                Question temp = new Question();
                String tempStr;

                temp.setResource(sc.nextLine());//gets resource ie diagram

                temp.setQuestion(sc.nextLine());//gets question

                tempStr = sc.nextLine();//gets the first option

                if(tempStr.contains("*")){//checks if it is the answer
                    temp.setSoln('a');
                    temp.setChoice0(tempStr.substring(1));
                }
                else {
                    temp.setChoice0(tempStr);//if not set it to choice 0
                }

                tempStr = sc.nextLine();//second option
                if(tempStr.contains("*")){
                    temp.setSoln('b');
                    temp.setChoice1(tempStr.substring(1));
                }
                else {
                    temp.setChoice1(tempStr);
                }

                tempStr = sc.nextLine();//third option
                if(tempStr.contains("*")){
                    temp.setSoln('c');
                    temp.setChoice2(tempStr.substring(1));
                }
                else {
                    temp.setChoice2(tempStr);
                }


                tempStr = sc.nextLine();//fourth option
                if(tempStr.contains("*")){
                    temp.setSoln('d');
                    temp.setChoice3(tempStr.substring(1));
                }
                else {
                    temp.setChoice3(tempStr);
                }

                questions.add(temp);
            }
        }
        catch (FileNotFoundException e){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Could not open the question file: "+directoryLocation);
            alert.show();
        }
        catch (Exception e){
            AlertBox.displayAlert("Exception in quiz section", e.getMessage());
        }

        return questions;


    }


    /**
     * Generates scene for quiz and sets it to the stage
     * @param quizType Note to be displayed
     * @param window Reference to primary stage
     */
    public static void goToQuiz(String quizType, Stage window){
        sp = new ScrollPane();
        borderPane = new BorderPane();

        vBoxCenter = new VBox();
        score = 0;

        questionNo = 0;

        questionText = new Text();

        answerToggles = new RadioButton[4];

        scoreText = new Text();

        questionImg = new ImageView();

        answerGroup = new ToggleGroup();

        questions = getQuestions(System.getProperty("user.dir")+ "/resource/" + quizType + "quiz.txt");
        section = quizType;
        borderPane.getChildren().clear();

        leftPane();
        rightPane();
        centerPane();
        bottomPane(window);
        topPane();

        window.setScene(new Scene(borderPane));
        window.setWidth(1280);
        window.setHeight(720);
        window.centerOnScreen();

    }


    /**
     * Gives the user their current score
     */
    private static void leftPane(){
        VBox vBoxLeft = new VBox();
        vBoxLeft.setMaxWidth(150);
        vBoxLeft.setPadding(new Insets(10));
        vBoxLeft.setSpacing(10);
        vBoxLeft.setAlignment(Pos.TOP_CENTER);

        scoreText.setText("Score: " + score);
        scoreText.setFont(Font.font("System", FontWeight.BOLD,20));
        vBoxLeft.getChildren().add(scoreText);
        vBoxLeft.setStyle( "-fx-background-color: #FFFFFF;");


        borderPane.setLeft(vBoxLeft);
    }

    /**
     * displays any diagram needed
     */
    private static void rightPane(){
        VBox vBoxRight = new VBox();

        String resource = questions.get(questionNo).getResource().equalsIgnoreCase("null") ?
                "./resource/default.png" : questions.get(questionNo).getResource();

        questionImg.setImage(new Image("file:" + resource));
        questionImg.setFitWidth(450);

        sp.setContent(vBoxRight);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        sp.setMinWidth(450);
        sp.setMinHeight(300);
        sp.setPannable(true);

        questionImg.setPreserveRatio(true);

        vBoxRight.getChildren().add(questionImg);
        vBoxRight.setAlignment(Pos.CENTER);
        vBoxRight.setPadding(new Insets(20));


        vBoxRight.setStyle( "-fx-background-color: #375ba3;");
        borderPane.setRight(sp);

    }

    /**
     * Displays question to be answered
     */
    private static void centerPane(){

        vBoxCenter.setAlignment(Pos.CENTER_LEFT);
        vBoxCenter.setMinHeight(250);
        vBoxCenter.setMinWidth(450);

        vBoxCenter.setSpacing(15);

        questionText = new Text(questions.get(questionNo).getQuestion());
        questionText.setWrappingWidth(500);
        questionText.setFont(Font.font("System", 22));


        vBoxCenter.getChildren().add(questionText);

        vBoxCenter.setStyle( "-fx-background-color: #FFFFFF;");

        for (int i = 0; i < 4; i++) {

            if(!questions.get(questionNo).getChoice()[i].equals("-")) {
                RadioButton temp = new RadioButton(questions.get(questionNo).getChoice()[i]);
                temp.setFont(Font.font("System", 20));
                answerToggles[i] = temp;
                vBoxCenter.getChildren().add(temp);
                answerGroup.getToggles().add(temp);
            }

        }

        borderPane.setCenter(vBoxCenter);
    }


    /**
     * Sets up the bottom controls for the quiz section
     * @param window
     */
    private static void bottomPane(Stage window){
        Button buttonNext = new Button("Next");
        Button buttonHint = new Button("Hint");
        Button mainMenu = new Button("Main menu");

        mainMenu.setOnAction(event -> MainMenu.goToSetupMainMenu(window));

        buttonHint.setOnAction(event -> NoteSection.goToNoteSection(section, window));

        buttonNext.setOnAction( event -> {


            if(section.contains("Counters" ) && questionNo == questions.size() -1){//used for the structured questions
                if(AlertBox.showSolution(section))
                    score++;
                AlertBox.endOfQuizPrompt(score,window,section);
            }
            else if (answerGroup.getSelectedToggle() != null) {//only allows button to be used if an answer is selected

                int soln = questions.get(questionNo).getSoln() == 'a'? 0 : questions.get(questionNo).getSoln() == 'b'?
                        1 : questions.get(questionNo).getSoln() == 'c'? 2 : 3; //gets the subscript of the solution toggle

                if(answerToggles[soln].isSelected()){//checks to see if that toggle was selected
                    score ++;
                    AlertBox.displayAlert("You're AWESOME!", "You were correct");
                }
                else{

                    AlertBox.displayAlert("Sorry :'(","The correct answer was: " + questions.get(questionNo).displaySolution());
                    //else shows the user the correct answer
                }

                questionNo++;//changing current question
                if(questionNo < questions.size()){//checks to see if there are any more questions

                    vBoxCenter.getChildren().clear();
                    questionText.setText(questions.get(questionNo).getQuestion());
                    vBoxCenter.getChildren().add(questionText);//changes question

                    for (int i = 0; i < 4; i++) {


                        String choice = questions.get(questionNo).getChoice()[i];//makes new toggles
                        if(!choice.equals("-")){

                            answerToggles[i] = new RadioButton(choice);//changes the text displayed by the radio buttons
                            answerToggles[i].setFont(Font.font("System",20));
                            answerGroup.getToggles().add(answerToggles[i]);
                            vBoxCenter.getChildren().add(answerToggles[i]);

                        }
                    }

                    String resource = questions.get(questionNo).getResource().equalsIgnoreCase("null") ?
                            "./resource/default.png" : questions.get(questionNo).getResource();//sets new image diagram

                    questionImg.setImage(new Image("file:" + resource));
                    scoreText.setText("Score: " + score);

                }
                else{
                    AlertBox.endOfQuizPrompt(score, window, section);// Prompts the user with options at the end of the quiz
                }
            }

            if(section.contains("Counters" ) && questionNo == questions.size() -1) buttonNext.setText("View Solution");

        });

      //  System.out.println("This ran");
        HBox hBoxBottom = new HBox();
        hBoxBottom.setPadding(new Insets(10,10,10,10));
        hBoxBottom.setSpacing(15);
        hBoxBottom.setAlignment(Pos.CENTER_RIGHT);

        hBoxBottom.getChildren().addAll(mainMenu,buttonHint, buttonNext);
        borderPane.setBottom(hBoxBottom);

    }

    /**
     * Just a funny title
     */
    private static void topPane(){

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setStyle( "-fx-background-color: #aaaaaa;");

        Text title = new Text("Test yo' self");
        title.setFont(Font.font("System", FontWeight.BOLD, 26));
        title.setFill(Color.web("#FFFFFF"));

        vBox.getChildren().add(title);
        borderPane.setTop(vBox);

    }

}
