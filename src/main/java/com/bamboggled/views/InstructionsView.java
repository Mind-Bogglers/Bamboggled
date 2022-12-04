package com.bamboggled.views;

import com.bamboggled.screenreader.ScreenReader;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.io.IOException;

public class InstructionsView {
    private final ScreenReader screenReader;
    private boolean visImpaired;
    private Button backButton;

    public InstructionsView(Stage stage, boolean visImpaired) throws IOException {
        ScreenReader.voice.getAudioPlayer().cancel();
        this.visImpaired = visImpaired;
        this.screenReader = new ScreenReader();

        System.out.println(this.visImpaired);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/InstructionsView.fxml"));
        loader.setController(this);
        Parent root = loader.load();
        root.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                backButton.fire();
            } else if (event.isControlDown() && event.getCode() == KeyCode.B) {
                this.visImpaired = !this.visImpaired;
                if (this.visImpaired) {
                    this.screenReader.speak("Each player is given a board that contains anywhere from 16 to 25 letters.  The objective is to find every possible legal word on the board.  A legal word can be defined by a string of letters such that each letter is either directly to the left or right, above or below, or diagonal to any letter preceding or following it.  The player that finds the most number of words wins.");
                    this.screenReader.speak("Press the escape key to return to the main menu.");
                } else {
                    ScreenReader.voice.getAudioPlayer().cancel();
                }
            }
        });
        this.backButton = (Button) root.lookup("#backButton");
        backButton.setOnAction(this::back);


        stage.setTitle("Instructions");
        stage.setScene(new Scene(root));
        stage.show();


        if (visImpaired) {
            this.screenReader.speak("Each player is given a board that contains anywhere from 16 to 25 letters.  The objective is to find every possible legal word on the board.  A legal word can be defined by a string of letters such that each letter is either directly to the left or right, above or below, or diagonal to any letter preceding or following it.  The player that finds the most number of words wins.");
            this.screenReader.speak("Press the escape key to return to the main menu.");
        }
    }

    public void back(ActionEvent e) {
        try {
            new WelcomeView((Stage) ((Node) e.getSource()).getScene().getWindow(), visImpaired);
        } catch (IOException ioException) {
            System.out.println("Error loading welcome view");
        }
    }
}
