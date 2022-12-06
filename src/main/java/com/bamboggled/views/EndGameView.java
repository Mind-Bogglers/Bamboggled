package com.bamboggled.views;

import com.bamboggled.model.model.BoggleModel;
import com.bamboggled.model.player.Player;
import com.bamboggled.screenreader.ScreenReader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class EndGameView {
    private BoggleModel model;
    private Stage stage;
    private Button playAgainButton;
    private Label winnerLabel;
    private boolean visImpaired;

    private final String winner_text = "Congratulations, %s won!";

    private final String command = "Press SHIFT to play again, or CONTROL key to exit";

    private ScreenReader screenReader;

    public EndGameView(Stage stage, boolean visImpaired){
        this.visImpaired = visImpaired;
        System.out.println(visImpaired);
        this.model = BoggleModel.getInstance();
        this.stage = stage;
        this.screenReader = new ScreenReader();
        initEndGameView();
    }

    private void initEndGameView() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/EndGameView.fxml"));

        try {
            AnchorPane root = loader.load();
            Scene scene = new Scene(root);
            winnerLabel = (Label) root.lookup("#winnerLabel");
            playAgainButton = (Button) root.lookup("#playAgainButton");
            playAgainButton.setOnAction(e -> {
                ScreenReader.voice.getAudioPlayer().cancel();
                restartgame(e);
            });
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

            String finalWinner = winner;
            root.setOnKeyPressed(event -> {
                if (event.isControlDown() && event.getCode() == KeyCode.B) {
                    this.visImpaired = !this.visImpaired;
                    if (this.visImpaired) {
                        this.screenReader.speak(String.format(winner_text, finalWinner));
                        this.screenReader.speak(command);
                    } else {
                        ScreenReader.voice.getAudioPlayer().cancel();
                    }
                } else if (event.getCode() == KeyCode.CONTROL) {
                    System.exit(0);
                } else if (event.getCode() == KeyCode.SHIFT) {
                    try {
                        new PlayView((Stage) ((Node) event.getSource()).getScene().getWindow(), this.visImpaired);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

            if (this.visImpaired) {
                this.screenReader.speak(String.format(winner_text, winner));
                this.screenReader.speak(command);
            }

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
            new PlayView((Stage) ((Node) e.getSource()).getScene().getWindow(), this.visImpaired);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }



}
