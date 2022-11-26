package com.bamboggled.views;

//import javafx.*;
import com.bamboggled.model.exceptions.*;
import com.bamboggled.model.model.BoggleModel;
import com.bamboggled.model.path.Path;
import com.bamboggled.model.player.Player;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

import static java.lang.Thread.sleep;


public class BoardGameView {

    private Label[][] labels = new Label[4][4];

    private BoggleModel model;

    private Stage stage;

    private GridPane grid4;

    private Timeline timeline;

    private Label playerNameLabel;
    private Label playerScoreLabel;
    private int pathColor;

    public BoardGameView(Stage stage){
        this.model = BoggleModel.getInstance();
        this.stage = stage;
        initBoardViewUI();
        //sanity test
        for (Player p: this.model.getPlayers()) {
            System.out.println(p.getName());
        }

    }

    public BoardGameView(){

    }


    // TODO: add event handlers for key events and implement updateBoard and paintBoard
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
            
            //timeline
            timeline = new Timeline(new KeyFrame(Duration.seconds(0.25), e -> updateBoard()));
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();

            //populate the 2d label array
            initLabelArray();

            //start the game for the first player
            try {
                model.startGameForNextPlayer();
            } catch (NoMorePlayersException e) {

                throw new RuntimeException(e);

            } catch (GameAlreadyInProgressException e) {
                throw new RuntimeException(e);
            } catch (PlayerAlreadyPlayedException e) {
                throw new RuntimeException(e);
            }

            System.out.println(model.getAllWordsOnGrid());  //for cheating purposes

            //the path color starts as -1
            this.pathColor = -1;

            //key event handler for user key inputs
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
                        new EndGameView(this.stage);
                        //throw new RuntimeException(e);
                        //TODO: Connect with gameEndView
                    } catch (GameAlreadyInProgressException e) {
                        throw new RuntimeException(e);
                    } catch (PlayerAlreadyPlayedException e) {
                        throw new RuntimeException(e);
                    }
                }
                else {
                    try {
                        model.addLetterToCurrentWord(keyEvent.getText().charAt(0));
                    } catch (NoPathException e) {
                        return;
                    }
                }
            });




            stage.setScene(scene);
            stage.setMaximized(true);
            stage.show();



        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    private void updateBoard() {
        try {
            paintBoard(this.pathColor, model.getPathToWord());
        } catch (EmptyWordException e) {
            paintBoard();
        }
    }

    private void paintBoard() {
        Path path;
        try {
            path = model.getPathToWord();
        } catch (EmptyWordException e) {
            path = null;
        }
        playerNameLabel.setText(model.getCurrentPlayer().getName());
        playerScoreLabel.setText(String.valueOf(model.getCurrentPlayer().getScore()));
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

    private void paintBoard(int customPathColor, Path path) {
        playerNameLabel.setText(model.getCurrentPlayer().getName());
        playerScoreLabel.setText(String.valueOf(model.getCurrentPlayer().getScore()));
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



    public void test() {
        for (Node n: grid4.getChildren()) {
            Label newNode = (Label) n;
            System.out.println(newNode.getText());
            System.out.println(newNode.getBackground().getFills());
        }
    }






}
