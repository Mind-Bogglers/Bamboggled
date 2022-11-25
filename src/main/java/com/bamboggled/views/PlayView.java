
package com.bamboggled.views;

import com.bamboggled.model.model.BoggleModel;
import com.bamboggled.model.player.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;

public class PlayView {

    public ToggleGroup board;
    private BoggleModel model;
    private WelcomeView parentView;

    private ArrayList<Player> players;

    private Stage stage;

    private Scene scene;

    @FXML
    private int boardSize;

    @FXML
    private RadioButton boardFive;

    @FXML
    private TextField textfield;

    public PlayView(BoggleModel model) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/PlayView.fxml"));
        this.scene = new Scene(root);
        this.players = new ArrayList<>();
        this.model = model;
        this.boardSize = 4;

        this.stage = new Stage();
        this.stage.setTitle("Initialize");
        this.stage.setScene(this.scene);
        this.stage.show();

    }

    public PlayView() {

    }


    public void nextPlayer(ActionEvent e) {
        if (this.players == null) {
            this.players = new ArrayList<>();
        }
        String player = textfield.getText();
        textfield.clear();
        this.players.add(new Player(player.strip()));
    }

    public void boardSize(ActionEvent e) {
        if (boardFive.isSelected()) {
            this.boardSize = 5;
        } else {
            this.boardSize = 4;
        }
    }


    public void submit(ActionEvent e) {
        if (this.players.size() == 0) {
            nextPlayer(e);
        } else {
            WelcomeView.model.newGame(this.boardSize, this.players);
        }
    }


}
