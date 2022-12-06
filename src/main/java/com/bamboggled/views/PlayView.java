package com.bamboggled.views;

import com.bamboggled.model.model.BoggleModel;
import com.bamboggled.model.player.Player;
import com.bamboggled.screenreader.ScreenReader;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;

/**
 * View for player initialization and board selection screen
 * */

public class PlayView {

    /**
     * Board size selection radio buttons
     * */
    public ToggleGroup board;

    /**
     * Boggle model
     */
    private BoggleModel model;

    /**
     * List of players that were entered in textField
     * */
    private ArrayList<Player> players;

    /**
     * Stage that stores the GUI elements
     * */
    private Stage stage;

    /**
     * Scene for PlayView screen
     * */
    private Scene scene;

    /**
     * ScreenReader object used to read text for visually impaired mode
     */
    private ScreenReader screenReader;

    /**
     * Selected board size
     */
    private int boardSize;

    /**
     * Radio button for board of size 4
     */
    @FXML
    RadioButton boardFour;

    /**
     * Radio button for board of size 5
     */
    @FXML
    private RadioButton boardFive;

    /**
     * Done button to finish submitting and start game
     */

    @FXML
    public Button done;

    /**
     * Submit player button to submit player
     */
    @FXML
    public Button submitPlayer;

    /**
     * Text field for writing player name(s)
     */
    @FXML
    private TextField textField;

    /**
     * Label to update, in case of error
     */
    @FXML
    private Label error;

    /**
     * Text read out for board selection
     */
    private final String boards = "Welcome to Player Initialization.  Press ESCAPE to exit.  Select 4 or 5 for Board size.  Press ENTER when complete.";

    /**
     * Text read out to confirm board selection
     */
    private final String boardSelection = "You have selected board size %d.  Press ENTER to confirm.";

    /**
     * Text read for board size confirmation
     */
    private final String confirmBoard = "You have confirmed your board selection as %d.";

    /**
     * Text read out for inputting names
     */
    private final String names = "Please type out the names of all players.  To type in the name of the next player, hit SHIFT.  If you are done typing player names, hit ENTER to begin Bamboggled.";;

    /**
     * Flag to check if board size has been selected
     */
    private boolean board_flag;

    /**
     * Boolean attribute that checks if visually impaired mode is on or off
     */
    private boolean visImpaired;

    /**
     * Initializes board selection and player initialization screen
     * @param stage Stage for GUI elements to be contained in
     * @param visImpaired Boolean attribute that checks if visually impaired mode is on or off
     * @throws IOException Exception thrown if PlayView could not be loaded
     */
    public PlayView(Stage stage, boolean visImpaired) throws IOException {
        this.visImpaired = visImpaired;
        this.model = BoggleModel.getInstance();
        this.stage = stage;
        this.screenReader = new ScreenReader();
        if (stage == null) {
            System.out.println("Stage is null");
        }
        this.players = new ArrayList<>();
        start();

    }

    /**
     * Starts the playView screen
     * @throws IOException Exception thrown if PlayView could not be loaded
     * */
    private void start() throws IOException {
        // loading FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/PlayView.fxml"));
        loader.setController(this);
        Parent root = loader.load();
        Platform.runLater(root::requestFocus);

        // initializing buttons
        boardFour = (RadioButton) root.lookup("#boardFour");
        boardFive = (RadioButton) root.lookup("#boardFive");
        boardFive.setDisable(true); //TODO: remove when the the 5x5 board is implemented
        done = (Button) root.lookup("#done");
        submitPlayer = (Button) root.lookup("#submitPlayer");
        error = (Label) root.lookup("#error");
        textField = (TextField) root.lookup("#textField");
        error.setText("");

        //setting listeners for buttons
        boardFour.setOnAction(e -> {
            ScreenReader.voice.getAudioPlayer().cancel();
            boardSize = 4;
            this.visImpaired = false;
            board_flag = true;
        });
        boardFive.setOnAction(e -> {
            boardSize = 5;
            this.visImpaired = false;
            ScreenReader.voice.getAudioPlayer().cancel();
            board_flag = true;
        });
        submitPlayer.setOnAction(e -> {
            this.visImpaired = false;
            ScreenReader.voice.getAudioPlayer().cancel();
            submitPlayer(e);
        });
        done.setOnAction(e -> {
            this.visImpaired = false;
            ScreenReader.voice.getAudioPlayer().cancel();
            done(e);
        });
        textField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.SHIFT) {
                error.setText("");
                String text = textField.getText().strip();
                if (this.players == null) {
                    this.players = new ArrayList<>();
                }
                if (text.equals("")) {
                    error.setText("Please enter a name.");
                } else {
                    this.players.add(new Player(text));
                    textField.setText("");
                }
            } else if (keyEvent.getCode() == KeyCode.ENTER) {
                error.setText("");
                if (this.boardSize == 0) {
                    error.setText("Please select a board size.");
                } else if (this.players.size() == 0) {
                    error.setText("Please enter at least one player.");
                } else {
                    model.newGame(this.boardSize, this.players);
                    new BoardGameView((Stage) ((Node) keyEvent.getSource()).getScene().getWindow(), this.visImpaired);
                }
            }
        });

        // listener for root
        root.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                System.exit(0);
            } else if (e.isControlDown() && e.getCode() == KeyCode.B) {
                ScreenReader.voice.getAudioPlayer().cancel();
                this.visImpaired = false;
            }
            if (!board_flag) {
                if (e.getCode().getCode() == 52 || e.getCode().getCode() == 100) {
                    this.boardSize = 4;
                    screenReader.speak(String.format(boardSelection, this.boardSize));
                    boardFour.setSelected(true);
                } else if (e.getCode().getCode() == 53 || e.getCode().getCode() == 101) {
                    this.boardSize = 5;
                    screenReader.speak(String.format(boardSelection, this.boardSize));
                    boardFive.setSelected(true);
                } else if (e.getCode() == KeyCode.ENTER) {
                    ScreenReader.voice.getAudioPlayer().cancel();
                    board_flag = true;
                    textField.requestFocus();
                    screenReader.speak(String.format(confirmBoard, this.boardSize));
                    screenReader.speak(names);
                }
            }
        });

        if (boardFive.isSelected() || boardFour.isSelected()) {
            textField.requestFocus();
        }

        // setting stage properties
        this.scene = new Scene(root);
        this.stage.setTitle("Initialize");
        this.stage.setScene(scene);
        stage.getScene().getWindow().requestFocus();
        this.stage.show();

        if (this.visImpaired) {
            this.screenReader.speak(boards);
        }

        textField.setText("");
    }

    /**
     * Action method for the Submit Player button
     * @param e ActionEvent that led to the button being clicked
     * */
    public void submitPlayer(ActionEvent e) {
        error.setText("");
        String text = textField.getText().strip();
        if (this.players == null) {
            this.players = new ArrayList<>();
        }
        if (text.equals("")) {
            error.setText("Please enter a name.");
        } else {
            this.players.add(new Player(text));
            textField.setText("");
        }
    }

    /**
     * Action method for the Submit button
     * @param e ActionEvent that led to the button being clicked
     * */
    public void done(ActionEvent e) {
        error.setText("");
        if (this.boardSize == 0) {
            error.setText("Please select a board size.");
        } else if (this.players.size() == 0) {
            error.setText("Please enter at least one player.");
        } else {
            model.newGame(this.boardSize, this.players);
            new BoardGameView((Stage) ((Node) e.getSource()).getScene().getWindow(), this.visImpaired);
        }
    }

}

