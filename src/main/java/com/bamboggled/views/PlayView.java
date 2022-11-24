package com.bamboggled.views;

import com.bamboggled.model.model.BoggleModel;
import com.bamboggled.model.player.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.util.ArrayList;

public class PlayView {

    BoggleModel model;
    WelcomeView parentView;

    ArrayList<Player> players;

    Stage stage;

    Scene scene;
    public PlayView(WelcomeView view, BoggleModel model) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/PlayView.fxml"));
        this.parentView = view;
        this.scene = new Scene(root);
        this.players = new ArrayList<>();
        this.model = model;

        this.stage = new Stage();
        this.stage.setTitle("Initialize");
        this.stage.setScene(this.scene);
        this.stage.show();

    }

    public void nextPlayer(ActionEvent e) {
        boolean check = true;
        TextField textfield = (TextField) this.scene.lookup("#textInput");
        String player = textfield.getText();
        textfield.clear();
        for (int i = 0; i < player.length(); i++) {
            if (player.charAt(i) == '\n') {
                check = false;
                Label label = (Label) this.scene.lookup("#warning");
                label.setText("Please input names one at a time.");
            }
        }
        if (check) {
            this.players.add(new Player(player.strip()));
        }
    }

    public void submit(ActionEvent e) {
        if (this.players.size() == 0) {
            nextPlayer(e);
        } else {
            new NextView();
        }
    }


}
