package com.bamboggled.views.WelcomeView;

import com.bamboggled.model.model.BoggleModel;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;

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
        Parent root = FXMLLoader.load(getClass().getResource("WelcomeView.fxml"));
        System.out.println(5);
        this.stage.setTitle("Bamboogled");
        this.stage.setScene(new Scene(root));
        this.stage.show();

    }


}
