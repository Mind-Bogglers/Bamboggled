package com.bamboggled.views;

import com.bamboggled.model.model.BoggleModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class EndGameView {

    BoggleModel model;

    Stage stage;

    @FXML
    Button button;

    public EndGameView(Stage stage){
        this.model = BoggleModel.getInstance();
        this.stage = stage;
        initEndGameView();
    }

    private void initEndGameView() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/EndGameView.fxml"));

        try {
            AnchorPane root = loader.load();
            Scene scene = new Scene(root);
            this.stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public EndGameView(){
        this.model = BoggleModel.getInstance();
    }

    public void restartgame(ActionEvent e){
        try {
            new PlayView(this.stage);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }



}
