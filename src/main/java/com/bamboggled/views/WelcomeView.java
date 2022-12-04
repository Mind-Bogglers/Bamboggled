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

public class WelcomeView {
    private final Stage stage;
    private final ScreenReader screenReader;
    private boolean visImpaired;
    private Button playButton;
    private Button instructionsButton;


    public WelcomeView(Stage stage, boolean visImpaired) throws IOException {
        this.screenReader = new ScreenReader();
        this.visImpaired = visImpaired;
        this.stage = stage;
        start();
    }


    private void start() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/WelcomeView.fxml"));
        loader.setController(this);
        Parent root = loader.load();
        this.playButton = (Button) root.lookup("#playBtn");
        this.instructionsButton = (Button) root.lookup("#rulesBtn");
        this.playButton.setOnAction(this::play);
        this.instructionsButton.setOnAction(this::rules);
        root.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                System.exit(0);
            } else if (event.isControlDown() && event.getCode() == KeyCode.B) {
                this.visImpaired = !this.visImpaired;
                if (this.visImpaired) {
//                    this.screenReader.speak("Welcome to Bamboggled.  Press Control B after this message is complete to disable Visually Impaired Mode. Press I for instructions. Press P to play. Press Escape to exit.");
                    this.screenReader.speak("Test");
                } else {
                    ScreenReader.voice.getAudioPlayer().cancel();
                }
            } else if (event.getText().equals("p") || event.getCharacter().equals("P")) {
                System.out.println("HERE");
                playButton.fire();
            } else if (event.getText().equals("i") || event.getCharacter().equals("I")) {
                instructionsButton.fire();
            }
        });
        this.stage.setTitle("Bamboggled");
        this.stage.setScene(new Scene(root));
        this.stage.show();

        if (this.visImpaired) {

            //        this.screenReader.speak("Welcome to Bamboggled.  Press Control B after this message is complete to disable Visually Impaired Mode. Press I for instructions. Press P to play. Press Escape to exit.");
            this.screenReader.speak("Test");
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
        try {
            new InstructionsView((Stage) ((Node) e.getSource()).getScene().getWindow(), this.visImpaired);
        } catch (IOException ioException) {
            System.out.println("Error loading welcome view");
        }
    }

    private void setVoice(Voice voice) {
        voice.setRate(100);
        voice.setPitch(150);
        voice.setVolume(10);
    }

}
