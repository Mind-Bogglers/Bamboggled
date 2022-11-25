package com.bamboggled.views;

//import javafx.*;
import com.bamboggled.model.model.BoggleModel;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;


public class BoardGameView {

    Label[][] labels = new Label[4][4];

    BoggleModel model;

    Stage stage;

    GridPane grid4;
    
    Timeline timeline;

    public BoardGameView(BoggleModel model, Stage stage){
        this.model = model;
        this.stage = stage;
        initBoardViewUI();

    }

    public BoardGameView(){

    }


    // TODO: add event handlers for key events and implement updateBoard and paintBoard
    public void initBoardViewUI(){

        try {
            // Set the root node, scene and also get a reference to the grid for grid4

            AnchorPane root = FXMLLoader.load(getClass().getResource("/BoardGameView.fxml"));
            Scene scene = new Scene(root);
            grid4 = (GridPane) root.getChildren().get(0);
            
            //timeline
            timeline = new Timeline(new KeyFrame(Duration.seconds(0.25), e -> updateBoard()));
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();

            //populate the 2d label array
            initLabelArray();

            //key event handler for user key inputs
            scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent keyEvent) {
                    //ToDo
                }
            });




            stage.setScene(scene);
            stage.show();



        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    private void updateBoard() {
        paintBoard();
    }

    private void paintBoard() {
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
