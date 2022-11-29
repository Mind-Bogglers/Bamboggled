package com.bamboggled.views;

import com.bamboggled.model.model.BoggleModel;
import com.bamboggled.model.player.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class EndGameView {

    BoggleModel model;

    Stage stage;

    @FXML
    Button playAgainButton;
    Label winnerLabel;

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
            winnerLabel = (Label) root.lookup("#winnerLabel");
            playAgainButton = (Button) root.lookup("#playAgainButton");
            playAgainButton.setOnAction(this::restartgame);
            playAgainButton.setText("Play Again");

            //get the player who won
            String winner = "Nobody";
            int maxScore = -1;
            for (Player p: model.getPlayers()) {
                if (p.getScore() > maxScore) {
                    maxScore = p.getScore();
                    winner = p.getName();
                }
            }
            //set the label to the winner
            winnerLabel.setText(winner + " won!");



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
            new PlayView((Stage) ((Node) e.getSource()).getScene().getWindow());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }



}
