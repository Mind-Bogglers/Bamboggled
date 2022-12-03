package com.bamboggled.views;

import com.bamboggled.model.model.BoggleModel;
import com.bamboggled.screenreader.ScreenReader;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
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

    private final String intro = "Welcome to Bamboggled.  Press Control B to disable Visually Impaired Mode at any time.";

    private final String commands = "Press I for Instructions, or the Shift key to play";

    public WelcomeView(Stage stage) throws IOException {
        this.model = BoggleModel.getInstance();
        this.stage = stage;
        this.screenReader = new ScreenReader();
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
//        this.screenReader.speak(intro);
//        this.screenReader.speak(commands);
        this.setListeners();

    }


    public void play(ActionEvent e) throws IOException {
        model.visImpaired = false;
        new PlayView((Stage) ((Node) e.getSource()).getScene().getWindow());
//        stage.close();
    }

    public void rules(ActionEvent e) throws IOException {
        model.visImpaired = false;
        new InstructionsView((Stage) ((Node) e.getSource()).getScene().getWindow());
    }

    private void setListeners() {
        this.stage.getScene().setOnKeyPressed(e -> {
            if (e.isControlDown() && e.getCode() == KeyCode.B) {
                model.visImpaired = false;
            } else if (e.getCode() == KeyCode.I) {
                try {
                    new InstructionsView(this.stage);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } else if (e.getCode() == KeyCode.SHIFT) {
                try {
                    new PlayView(this.stage);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

}
