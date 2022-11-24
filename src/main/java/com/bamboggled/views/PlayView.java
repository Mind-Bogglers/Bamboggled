package com.bamboggled.views;

import com.bamboggled.model.model.BoggleModel;
import com.bamboggled.model.player.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.util.ArrayList;

public class PlayView {

    private BoggleModel model;
    private WelcomeView parentView;

    private ArrayList<Player> players;

    private Stage stage;

    private Scene scene;

    private int boardSize;

    private RadioButton boardFour, boardFive;

    private TextField textInput;

    public PlayView(WelcomeView view, BoggleModel model) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/PlayView.fxml"));
        this.parentView = view;
        this.scene = new Scene(root);
        this.players = new ArrayList<>();
        this.model = model;
        this.boardSize = 4;

        this.stage = new Stage();
        this.stage.setTitle("Initialize");
        this.stage.setScene(this.scene);
        this.stage.show();

    }

    public void nextPlayer(ActionEvent e) {
        String player = textInput.getText();
        textInput.clear();
        this.players.add(new Player(player.strip()));
    }

    public void boardSize(ActionEvent e) {
        if (boardFive.isSelected()) {
            this.boardSize = 5;
        }
    }


    public void submit(ActionEvent e) {
        if (this.players.size() == 0) {
            nextPlayer(e);
        } else {
            this.model.newGame(this.boardSize, this.players);
        }
    }


}
