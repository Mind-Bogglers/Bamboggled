package com.bamboggled.views.BoardGameView;

//import javafx.*;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;


public class BoardGameView {

    Label[][] labels = new Label[4][4];

    @FXML GridPane grid4;

    public void initLabelArray(){
        for (int i = 0; i<4; i++){
            for (int j = 0; j<4; j++){
                for (Node n: grid4.getChildren()){
                    if (n instanceof Label){
                        if (GridPane.getRowIndex(n) == i && GridPane.getColumnIndex(n) == j){
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
