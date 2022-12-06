package com.bamboggled.views;

import com.bamboggled.model.model.BoggleModel;

import com.bamboggled.screenreader.ScreenReader;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * Welcome screen and menu for Boggle game
 */

public class WelcomeView {

    /**
     * Refers to the Stage object of the game
     * */
    private final Stage stage;

    /**
     * Refers to the ScreenReader object, needed to read text
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/WelcomeView.fxml"));
        loader.setController(this);
        Parent root = loader.load();
        this.playButton = (Button) root.lookup("#playBtn");
        this.instructionsButton = (Button) root.lookup("#rulesBtn");

        //
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
        this.stage.setTitle("Bamboggled");
        this.stage.setScene(new Scene(root));
        this.stage.show();

        if (this.visImpaired) {
            this.screenReader.speak(intro);
        } else {
            ScreenReader.voice.getAudioPlayer().cancel();
        }
    }


    public void play(ActionEvent e) {
        try {
            new PlayView((Stage) ((Node) e.getSource()).getScene().getWindow(), this.visImpaired);
        } catch (IOException ioException) {
            System.out.println("Error loading play view");
        }
//        stage.close();
    }

    public void rules(ActionEvent e) {
        ScreenReader.voice.getAudioPlayer().cancel();
        try {
            new InstructionsView((Stage) ((Node) e.getSource()).getScene().getWindow(), this.visImpaired);
        } catch (IOException ioException) {
            System.out.println("Error loading welcome view");
        }
    }

}
