import javafx.application.Application;
import javafx.application.HostServices;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;


public class Main extends Application {





    public static void main(String[] args) {
        launch(args);//launches application

    }


    @Override
    public void init() throws Exception{
        super.init();

    }


    @Override
    public void start(Stage window) throws Exception {



        AlertBox.init(getHostServices());
        setUserAgentStylesheet(STYLESHEET_CASPIAN);
        window.setTitle("Computer Architecture Project - Group 4");
        window.getIcons().add(new Image("file:./resource/default.png"));
        window.setResizable(false);


        MainMenu.goToSetupMainMenu(window);
        AlertBox.showObjectives();

    }
}
