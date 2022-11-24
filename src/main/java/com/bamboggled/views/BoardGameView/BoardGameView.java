package com.bamboggled.views.BoardGameView;

//import javafx.*;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import static javafx.scene.layout.GridPane.getRowIndex;

public class BoardGameView {

    Node[][] labels = new Node[4][4];

    @FXML GridPane grid4;


    public void test() {
        for (Node n: grid4.getChildren()) {
            Label newNode = (Label) n;
            System.out.println(newNode.getText());
            System.out.println(newNode.getBackground().getFills());
        }
    }






}
