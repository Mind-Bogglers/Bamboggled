package com.bamboggled;

import com.bamboggled.views.WelcomeView;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Application for running the Bamboggled game.
 *
 * Created by Hassan El-Sheika, Mustafa Zafar, Sultan Al-Dhalaan, and Kevin Thevara
 * */

public class BoggleApp extends Application {

    /**
     * Attribute that refers to the view for welcome screen
     * */
    WelcomeView welcomeView;

    /**
     * Main method
     * @param args Launches the application
     * */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Starts the JavaFX application
     *
     * @param beginStage Stage object that will contain the application
     * @throws IOException Exception thrown if WelcomeView could not be loaded
     * */
    @Override
    public void start(Stage beginStage) throws IOException {
        this.welcomeView = new WelcomeView(beginStage, true);
    }


}
