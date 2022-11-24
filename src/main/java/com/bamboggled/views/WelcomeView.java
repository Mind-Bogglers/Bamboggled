package com.bamboggled.views;

import com.bamboggled.model.model.BoggleModel;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;

import javax.swing.*;
import java.io.IOException;

public class WelcomeView {

    BoggleModel model;

    Stage stage;

    Button playButton, instructionsButton;

    public WelcomeView(BoggleModel model, Stage stage) throws IOException {
        this.model = model;
        this.stage = stage;
        start();
    }

    private void start() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/WelcomeView.fxml"));
        this.stage.setTitle("Bamboogled");
        this.stage.setScene(new Scene(root));
        this.stage.show();

    }

    public void play(ActionEvent e) throws IOException {
        new PlayView(this, this.model);
    }

    public void rules(ActionEvent e) {
        new InstructionsView(this, this.model);
    }


}
