package com.bamboggled.views;

import com.bamboggled.model.model.BoggleModel;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class BoggleApp extends Application {

    BoardGameView view;

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) {

        this.view = new BoardGameView(BoggleModel.getInstance(), stage);







        /*
        try{
            AnchorPane root = FXMLLoader.load(getClass().getResource("/BoardGameView.fxml"));
            Scene scene = new Scene(root);
            //body
            Color c = Color.CYAN;
            int count = 1;

             GridPane grid = (GridPane) root.getChildren().get(0);
             for (Node label: grid.getChildren()){
                 if (label instanceof Label){
                     ((Label)label).setText(Integer.toString(count));
                     count+=1;
                     ((Label)label).setBackground(new Background(new BackgroundFill(c, null, null)));

                 }

             }



            stage.setScene(scene);
            stage.show();


        } catch (Exception e) {
            throw new RuntimeException(e);
        }

         */
    }
}
