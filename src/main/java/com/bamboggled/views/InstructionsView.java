package com.bamboggled.views;

import com.bamboggled.model.model.BoggleModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class InstructionsView {

    private WelcomeView parentView;
    private Scene scene;

    private Stage stage;

    private BoggleModel model;

    public InstructionsView(BoggleModel model) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/InstructionsView.fxml"));
        this.model = model;
        this.scene = new Scene(root);

        this.stage = new Stage();
        this.stage.setTitle("Instructions");
        this.stage.setScene(this.scene);
        this.stage.show();

    }

    public InstructionsView() {

    }
}
