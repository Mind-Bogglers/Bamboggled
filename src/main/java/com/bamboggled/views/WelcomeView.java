package com.bamboggled.views;

import com.bamboggled.model.model.BoggleModel;
import com.bamboggled.screenreader.ScreenReader;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class WelcomeView {

    private BoggleModel model;

    private Stage stage;

    private ScreenReader screenReader;

    @FXML
    private Button playBtn;

    @FXML
    private Button rulesBtn;

    public WelcomeView(Stage stage) throws IOException {
        this.model = BoggleModel.getInstance();
        this.stage = stage;
        this.screenReader = new ScreenReader(this.model);
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

        if (model.visImpaired) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    ScreenReader.voice.speak("Welcome to Bamboggled.  Press Control B to disable Visually Impaired Mode at any time.");;
                }
            });
        }
        this.stage.getScene().setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.I) {
                try {
                    new InstructionsView(this.stage);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

    }


    public void play(ActionEvent e) throws IOException {
        new PlayView((Stage) ((Node) e.getSource()).getScene().getWindow());
//        stage.close();
    }

    public void rules(ActionEvent e) throws IOException {
        model.visImpaired = false;
        new InstructionsView((Stage) ((Node) e.getSource()).getScene().getWindow());
    }

}
