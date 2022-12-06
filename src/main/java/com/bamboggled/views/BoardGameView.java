package com.bamboggled.views;

import com.bamboggled.model.exceptions.*;
import com.bamboggled.model.model.BoggleModel;
import com.bamboggled.model.path.Path;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;

/**
 * Initializes Boggle board view
 */
public class BoardGameView {

    /**
     * 4x4 array of Labels for board of size 4
     */
    private Label[][] labels = new Label[4][4];

    /**
     * Boggle model
     */
    private BoggleModel model;

    /**
     * Stage that stores the GUI elements
     */
    private Stage stage;

    /**
     * GridPane object for board of size 4
     */
    private GridPane grid4;

    /**
     * Timeline object to keep track of events
     */
    private Timeline timeline;

    /**
     * Label to store current player's name
     */
    private Label playerNameLabel;

    /**
     * Label to store current player's score
     */
    private Label playerScoreLabel;

    /**
     * Integer that is assigned to a particular color, which indicates if the path associated
     * with a given word is valid (green), invalid (red), or already played (yellow)
     */
    private int pathColor;

    /**
     * Boolean attribute that checks if visually impaired mode is on or off
     */
    private boolean visImpaired;


    /**
     * Initializes BoardGameView
     * @param stage Stage that stores the GUI elements
     * @param visImpaired Boolean attribute that checks if visually impaired mode is on or off
     */
    public BoardGameView(Stage stage, boolean visImpaired) {
        System.out.println(visImpaired);
        this.visImpaired = visImpaired;
        this.model = BoggleModel.getInstance();
        this.stage = stage;
        initBoardViewUI();
    }

    /**
     * Empty initializer for initializing from button click
     */
    public BoardGameView(){

    }

    /**
     * Loads the BoardGameView screen
     */
    public void initBoardViewUI(){

        try {
            // Set the root node, scene and also get a reference to the grid for grid4
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/BoardGameView.fxml"));
            loader.setController(this);
            AnchorPane root = loader.load();
            grid4 = (GridPane) root.getChildren().get(0);
            playerNameLabel = (Label) root.lookup("#playerName");
            playerScoreLabel = (Label) root.lookup("#playerScore");
            Scene scene = new Scene(root);
            
            // timeline
            timeline = new Timeline(new KeyFrame(Duration.seconds(0.25), e -> updateBoard()));
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();

            // populate the 2d label array
            initLabelArray();

            // start the game for the first player
            try {
                model.startGameForNextPlayer();
            } catch (NoMorePlayersException e) {

                throw new RuntimeException(e);

            } catch (GameAlreadyInProgressException e) {
                throw new RuntimeException(e);
            } catch (PlayerAlreadyPlayedException e) {
                throw new RuntimeException(e);
            }

            // the path color starts as -1
            this.pathColor = -1;

            // key event handler for user key inputs
            scene.setOnKeyPressed(keyEvent -> {
                if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                    int result;
                    Path oldPath;
                    try {
                        oldPath = model.getPathToWord();
                    } catch (EmptyWordException e) {
                        return;
                    }
                    pathColor = model.submitCurrentWordColored();
                    paintBoard(pathColor, oldPath);
                    pathColor = -1;
                } else if (keyEvent.getCode().equals(KeyCode.ESCAPE)) {
                    try {
                        model.endGame();
                        model.startGameForNextPlayer();
                    } catch (GameNotInProgressException e) {
                        throw new RuntimeException(e);
                    } catch (NoMorePlayersException e) {
                        timeline.stop();
                        new EndGameView(this.stage, this.visImpaired);
                    } catch (GameAlreadyInProgressException e) {
                        throw new RuntimeException(e);
                    } catch (PlayerAlreadyPlayedException e) {
                        throw new RuntimeException(e);
                    }
                } else if (keyEvent.getCode().equals(KeyCode.BACK_SPACE)) {
                    try {
                        model.removeLetterFromCurrentWord();
                    } catch (NoHistoryException e) {
                        return;
                    }

                } else {
                    try {
                        model.addLetterToCurrentWord(keyEvent.getText().charAt(0));
                    } catch (NoPathException e) {
                        return;
                    }
                }
            });

            this.stage.setScene(scene);
            this.stage.setMaximized(true);
            this.stage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method to update board
     */
    private void updateBoard() {
        try {
            paintBoard(this.pathColor, model.getPathToWord());
        } catch (EmptyWordException e) {
            paintBoard();
        }
    }

    /**
     * Method to paint board
     */
    private void paintBoard() {
        Path path;
        try {
            path = model.getPathToWord();
        } catch (EmptyWordException e) {
            path = null;
        }
        // update labels
        playerNameLabel.setText(model.getCurrentPlayer().getName());
        playerScoreLabel.setText(String.valueOf(model.getCurrentPlayer().getScore()));
        // change path color
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                labels[i][j].setText(Character.toString(model.getCurrentGrid().getCharAt(i, j)));
                if (path != null && path.contains(i, j)) {
                    labels[i][j].setStyle("-fx-background-color: #ff6600");
                } else {
                    labels[i][j].setStyle("-fx-background-color: #ffffff");
                }
            }
        }
    }

    /**
     * Method to paint board with parameters
     * @param customPathColor color of path, depending on correctness of word
     * @param path path of word
     */
    private void paintBoard(int customPathColor, Path path) {
        // update labels
        playerNameLabel.setText(model.getCurrentPlayer().getName());
        playerScoreLabel.setText(String.valueOf(model.getCurrentPlayer().getScore()));

        // change color of tiles
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                labels[i][j].setText(Character.toString(model.getCurrentGrid().getCharAt(i, j)));
                if (path != null && path.contains(i, j)) {
                    if (customPathColor == model.GREEN) {
                        labels[i][j].setStyle("-fx-background-color: #00ff00");
                    } else if (customPathColor == model.RED) {
                        labels[i][j].setStyle("-fx-background-color: #ff0000");
                    } else if (customPathColor == model.GRAY){
                        labels[i][j].setStyle("-fx-background-color: #ffeb00");
                    } else if (customPathColor == -1){
                        labels[i][j].setStyle("-fx-background-color: #ff6600");
                    }
                } else {
                    labels[i][j].setStyle("-fx-background-color: #ffffff");
                }
            }
        }
    }

    /**
     * Initialize each label in board
     */
    private void initLabelArray(){
        for (int i = 0; i<4; i++){
            for (int j = 0; j<4; j++){
                for (Node n: grid4.getChildren()){
                    if (n instanceof Label){
                        if ((GridPane.getRowIndex(n) != null && GridPane.getColumnIndex(n) != null)&&(GridPane.getRowIndex(n) == i && GridPane.getColumnIndex(n) == j)){
                            labels[i][j] = (Label) n;
                        }

                    }
                }
            }
        }
    }
}
