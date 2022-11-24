package com.bamboggled.views;

import com.bamboggled.model.model.BoggleModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class InstructionsView {

    private BoggleModel model;
    private WelcomeView parentView;
    private Scene scene;

    private Stage stage;

    public InstructionsView(WelcomeView view, BoggleModel model) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/InstructionsView.fxml"));
        this.model = model;
        this.parentView = view;
        this.scene = new Scene(root);

        this.stage = new Stage();
        this.stage.setTitle("Instructions");
        this.stage.setScene(this.scene);
        this.stage.show();

    }
}
