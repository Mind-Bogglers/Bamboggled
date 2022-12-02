package com.bamboggled.views;

import com.bamboggled.model.model.BoggleModel;
import com.bamboggled.screenreader.ScreenReader;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class InstructionsView {

    private WelcomeView parentView;
    private Scene scene;

    private Stage stage;

    private ScreenReader screenReader;

    private BoggleModel model;

    @FXML
    private Button backButton;

    public InstructionsView(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/InstructionsView.fxml"));
        this.model = BoggleModel.getInstance();
        this.scene = new Scene(root);
        this.screenReader = new ScreenReader();

        this.stage = stage;
        this.stage.setTitle("Instructions");
        this.stage.setScene(this.scene);
        this.stage.show();

        this.screenReader.speak("Instructions");

    }

    public InstructionsView() {

    }

    public void back(ActionEvent e) throws IOException {
        new WelcomeView((Stage) ((Node) e.getSource()).getScene().getWindow());
    }
}
