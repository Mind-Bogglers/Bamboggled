package com.bamboggled.views;

import com.bamboggled.model.model.BoggleModel;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;

import java.io.IOException;

public class WelcomeView {

    public static BoggleModel model;

    Stage stage;

    Button playButton, instructionsButton;

    public WelcomeView(BoggleModel model, Stage stage) throws IOException {
        this.model = model;
        this.stage = stage;
        start();
    }
    public WelcomeView(){

    }


    private void start() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/WelcomeView.fxml"));
        this.stage.setTitle("Bamboogled");
        this.stage.setScene(new Scene(root));
        this.stage.show();

    }

    public void play(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/PlayView.fxml"));
        stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void rules(ActionEvent e) throws IOException {
        new InstructionsView(WelcomeView.model);
    }


}
