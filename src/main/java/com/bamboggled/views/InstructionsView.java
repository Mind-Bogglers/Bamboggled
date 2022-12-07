package com.bamboggled.views;

import com.bamboggled.screenreader.ScreenReader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Screen that explains rules of Boggle game
 */
public class InstructionsView {
    /**
     * ScreenReader object used to read text for visually impaired mode
     */
    private final ScreenReader screenReader;

    /**
     * Boolean attribute that checks if visually impaired mode is on or off
     */
    private boolean visImpaired;

    /**
     * Stage that stores the GUI elements
     * */
    private Stage stage;

    /**
     * Button that redirects back to WelcomeView
     */
    @FXML
    private Button backButton;

    /**
     * Text read out for instructions
     */
    private final String instructions = "Each player is given a board that contains anywhere from 16 to 25 letters.  The objective is to find every possible legal word on the board.  A legal word can be defined by a string of letters such that each letter is either directly to the left or right, above or below, or diagonal to any letter preceding or following it.  The player that finds the most number of words wins.";

    /**
     * Text read out for user commands
     */
    private final String command = "Press the escape key to return to the main menu.  Press CONTROL to exit the game.";

    /**
     * Initializes screen that explain rules of Boggle game
     * @param stage Stage for GUI elements to be contained in
     * @param visImpaired Boolean attribute that checks if visually impaired mode is on or off
     * @throws IOException Exception thrown if InstructionsView could not be loaded
     */
    public InstructionsView(Stage stage, boolean visImpaired) throws IOException {
        this.visImpaired = visImpaired;
        this.stage = stage;
        this.screenReader = new ScreenReader();
        start();
    }

    /**
     * Loads the InstructionsView screen
     * @throws IOException Exception thrown if InstructionsView could not be loaded
     */
    private void start() throws IOException {
        // loading FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/InstructionsView.fxml"));
        loader.setController(this);
        Parent root = loader.load();

        // initializing button
        backButton = (Button) root.lookup("#backButton");

        // listener for root
        root.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                try {
                    new WelcomeView((Stage) ((Node) event.getSource()).getScene().getWindow(), visImpaired);
                } catch (IOException ioException) {
                    System.out.println("Error loading Welcome view");
                }
            } else if (event.isControlDown() && event.getCode() == KeyCode.B) {
                this.visImpaired = !this.visImpaired;
                if (this.visImpaired) {
                    this.screenReader.speak(instructions);
                    this.screenReader.speak(command);
                } else {
                    ScreenReader.voice.getAudioPlayer().cancel();
                }
            }
        });

        // button listener
        backButton.setOnAction(e -> {
            ScreenReader.voice.getAudioPlayer().cancel();
            this.visImpaired = false;
            back(e);
        });

        // setting stage properties
        this.stage.setTitle("Instructions");
        this.stage.setScene(new Scene(root));
        this.stage.show();


        if (visImpaired) {
            this.screenReader.speak(instructions);
            this.screenReader.speak(command);
        }
    }

    /**
     * Action method for back button
     * @param e ActionEvent that led to the button being clicked
     */
    public void back(ActionEvent e) {
        try {
            new WelcomeView((Stage) ((Node) e.getSource()).getScene().getWindow(), visImpaired);
        } catch (IOException ioException) {
            System.out.println("Error loading Welcome view");
        }
    }
}
