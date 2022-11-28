package com.bamboggled.views;

import com.bamboggled.model.model.BoggleModel;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.logging.Logger;

public class WelcomeView {

    private BoggleModel model;

    private Stage stage;


    public WelcomeView(Stage stage) throws IOException {
        this.model = BoggleModel.getInstance();
        this.stage = stage;
        start();
    }

    public WelcomeView() {
        this.model = BoggleModel.getInstance();
    }


    private void start() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/WelcomeView.fxml")));
        this.stage.setTitle("Bamboogled");
        this.stage.setScene(new Scene(root));
        this.stage.show();

        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        Voice voice = VoiceManager.getInstance().getVoice("kevin16");
        if (voice != null) {
            voice.allocate();// Allocating Voice
            try {
                voice.setRate(100);// Setting the rate of the voice
                voice.setPitch(150);// Setting the Pitch of the voice
                voice.setVolume(10);// Setting the volume of the voice
                voice.speak("Welcome to Bamboggled.  Press Control B to disable Visually Impaired Mode at any time.");// Calling speak() method

            } catch (Exception e1) {
                e1.printStackTrace();
            }

        } else {
            throw new IllegalStateException("Cannot find voice: kevin16");
        }

    }


    public void play(ActionEvent e) throws IOException {
        Voice voice = VoiceManager.getInstance().getVoice("kevin16");
        if (voice != null) {
            voice.allocate();// Allocating Voice
            try {
                voice.setRate(100);// Setting the rate of the voice
                voice.setPitch(150);// Setting the Pitch of the voice
                voice.setVolume(10);// Setting the volume of the voice
                voice.speak("OH TERRY");// Calling speak() method

            } catch (Exception e1) {
                e1.printStackTrace();
            }

        } else {
            throw new IllegalStateException("Cannot find voice: kevin16");
        }
        new PlayView((Stage) ((Node) e.getSource()).getScene().getWindow());
//        stage.close();
    }

    public void rules(ActionEvent e) throws IOException {
        new InstructionsView(model);
    }

}
