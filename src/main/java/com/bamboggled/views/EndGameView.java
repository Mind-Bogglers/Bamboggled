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
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * View for end game screen
 */
public class EndGameView {
    /**
     * Boggle model
     */
    private BoggleModel model;

    /**
     * Stage that stores the GUI elements
     */
    private Stage stage;

    /**
     * Button to play again
     */
    @FXML
    private Button playAgainButton;

    /**
     * Label to be updated with winner's name
     */
    @FXML
    private Label winnerLabel;

    /**
     * Boolean attribute that checks if visually impaired mode is on or off
     */
    private boolean visImpaired;

    /**
     * Text to be formatted then read to announce winner
     */
    private final String winner_text = "Congratulations, %s won!";

    /**
     * Text read out for user commands
     */
    private final String command = "Press SHIFT to play again, or CONTROL key to exit";

    /**
     * ScreenReader object used to read text for visually impaired mode
     */
    private ScreenReader screenReader;

    /**
     * Initializes EndGameView
     * @param stage Stage that stores the GUI elements
     * @param visImpaired Boolean attribute that checks if visually impaired mode is on or off
     */
    public EndGameView(Stage stage, boolean visImpaired){
        this.visImpaired = visImpaired;
        System.out.println(visImpaired);
        this.model = BoggleModel.getInstance();
        this.stage = stage;
        this.screenReader = new ScreenReader();
        initEndGameView();
    }

    /**
     * Loads the end game screen
     */
    private void initEndGameView() {
        // loads FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/EndGameView.fxml"));

        try {
            AnchorPane root = loader.load();
            Scene scene = new Scene(root);

            // initializes all FXML elements
            winnerLabel = (Label) root.lookup("#winnerLabel");
            playAgainButton = (Button) root.lookup("#playAgainButton");

            // sets listeners
            playAgainButton.setOnAction(e -> {
                ScreenReader.voice.getAudioPlayer().cancel();
                restartGame(e);
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

            // listener for root
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

    /**
     * Empty initializer for initializing from button click
     */
    public EndGameView(){
        this.model = BoggleModel.getInstance();
    }

    /**
     * Action method for Play Again button
     * @param e ActionEvent that led to the button being clicked
     */
    public void restartGame(ActionEvent e){
        try {
            new PlayView((Stage) ((Node) e.getSource()).getScene().getWindow(), this.visImpaired);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
