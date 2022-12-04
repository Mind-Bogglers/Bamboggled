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

    private ScreenReader screenReader;

    public EndGameView(Stage stage, boolean visImpaired){
        this.visImpaired = visImpaired;
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

            if (this.visImpaired) {
                this.screenReader.speak(String.format("Congratulations, %s won!", winner));
                this.screenReader.speak("Press SHIFT to play again, or ESCAPE key to exit");
            }



            this.stage.setScene(scene);
            stage.show();

            this.setListeners();

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

    private void setListeners() {
        this.stage.getScene().setOnKeyPressed(e -> {
            if (e.isControlDown() && e.getCode() == KeyCode.B) {
                this.visImpaired = !this.visImpaired;
            }
            if (e.getCode() == KeyCode.SHIFT) {
                playAgainButton.fire();
            }
            if (e.getCode() == KeyCode.ESCAPE) {
                System.exit(0);
            }
        });
    }



}
