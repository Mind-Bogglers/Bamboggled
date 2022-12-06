package com.bamboggled.views;

import com.bamboggled.screenreader.ScreenReader;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Welcome screen and menu for Boggle game
 */

public class WelcomeView {

    /**
     * Stage that stores the GUI elements
     * */
    private final Stage stage;

    /**
     * ScreenReader object used to read text for visually impaired mode
     * */
    private final ScreenReader screenReader;

    /**
     * Boolean attribute that checks if visually impaired mode is on or off
     * */
    private boolean visImpaired;

    /**
     * Refers to the Button object that will redirect to PlayView and start the game
     * */
    private Button playButton;

    /**
     * Refers to the Button object that will redirect to InstructionsView and explain the rules
     * */
    private Button instructionsButton;

    private final String intro = "Welcome to Bamboggled.  Press Control B after this message is complete to disable Visually Impaired Mode. Press I for instructions. Press SHIFT to play. Press ESCAPE to exit.";


    /**
     * Initializes the welcome screen and menu for Boggle game
     * @param stage the Stage object that the game will be played on
     * @param visImpaired boolean value that checks if visually impaired mode is on or off
     * @throws IOException Exception thrown if WelcomeView could not be loaded
     * */
    public WelcomeView(Stage stage, boolean visImpaired) throws IOException {
        this.screenReader = new ScreenReader();
        this.visImpaired = visImpaired;
        this.stage = stage;
        start();
    }

    /**
     * Starts the game, initializes all relevant buttons, and sets listeners
     * @throws IOException Exception thrown if WelcomeView could not be loaded
     * */
    private void start() throws IOException {
        // loading FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/WelcomeView.fxml"));
        loader.setController(this);
        Parent root = loader.load();

        // initializing buttons
        this.playButton = (Button) root.lookup("#playBtn");
        this.instructionsButton = (Button) root.lookup("#rulesBtn");

        // listeners for buttons
        this.playButton.setOnAction(e -> {
            this.visImpaired = false;
            ScreenReader.voice.getAudioPlayer().cancel();
            play(e);
        });
        this.instructionsButton.setOnAction(e -> {
            this.visImpaired = false;
            ScreenReader.voice.getAudioPlayer().cancel();
            rules(e);
        });

        // listener for root event
        root.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                System.exit(0);
            } else if (event.isControlDown() && event.getCode() == KeyCode.B) {
                this.visImpaired = !this.visImpaired;
                if (!this.visImpaired) {
                    ScreenReader.voice.getAudioPlayer().cancel();
                }
            } else if (event.getCode() == KeyCode.SHIFT) {
                ScreenReader.voice.getAudioPlayer().cancel();
                try {
                    new PlayView((Stage) ((Node) event.getSource()).getScene().getWindow(), this.visImpaired);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else if (event.getCode() == KeyCode.I) {
                ScreenReader.voice.getAudioPlayer().cancel();
                try {
                    new InstructionsView((Stage) ((Node) event.getSource()).getScene().getWindow(), this.visImpaired);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        // setting stage properties
        this.stage.setTitle("Bamboggled");
        this.stage.setScene(new Scene(root));
        this.stage.show();

        if (this.visImpaired) {
            this.screenReader.speak(intro);
        } else {
            ScreenReader.voice.getAudioPlayer().cancel();
        }
    }

    /**
     * Action method for play button
     * @param e ActionEvent that led to the button being clicked
     * */
    public void play(ActionEvent e) {
        try {
            new PlayView((Stage) ((Node) e.getSource()).getScene().getWindow(), this.visImpaired);
        } catch (IOException ioException) {
            System.out.println("Error loading play view");
        }
//        stage.close();
    }

    /**
     * Action method for instructions button
     * @param e ActionEvent that led to the button being clicked
     * */
    public void rules(ActionEvent e) {
        ScreenReader.voice.getAudioPlayer().cancel();
        try {
            new InstructionsView((Stage) ((Node) e.getSource()).getScene().getWindow(), this.visImpaired);
        } catch (IOException ioException) {
            System.out.println("Error loading welcome view");
        }
    }

}
