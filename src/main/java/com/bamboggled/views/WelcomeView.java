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

    @FXML
    private Button playBtn;

    @FXML
    private Button rulesBtn;

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

    }


    public void play(ActionEvent e) throws IOException {
        new PlayView((Stage) ((Node) e.getSource()).getScene().getWindow());
//        stage.close();
    }

    public void rules(ActionEvent e) throws IOException {
        new InstructionsView((Stage) ((Node) e.getSource()).getScene().getWindow());
    }


}
