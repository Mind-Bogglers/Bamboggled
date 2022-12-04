
package com.bamboggled.views;

import com.bamboggled.model.model.BoggleModel;
import com.bamboggled.model.player.Player;
import com.bamboggled.screenreader.ScreenReader;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class PlayView {

    public ToggleGroup board;

    private BoggleModel model;

    private ArrayList<Player> players;

    private Stage stage;

    private Scene scene;

    private ScreenReader screenReader;

    private int boardSize;

    @FXML
    RadioButton boardFour;

    @FXML
    public Button submit;
    @FXML
    public Button submitPlayer;

    @FXML
    private RadioButton boardFive;

    @FXML
    private TextField textField;

    @FXML
    private Label error;

    private final String boards = "Welcome to Player Initialization.  Select 4 or 5 for Board size.  Press ENTER when complete.";

    private final String names = "Please type out the names of all players.  To type in the name of the next player, hit SHIFT.  If you are done typing player names, hit ENTER to begin Bamboggled.";

    private final String confirmBoard = "You have confirmed your board selection as %d.";

    private final String boardSelection = "You have selected board size %d.  Press ENTER to confirm.";

    private boolean board_flag;
    private boolean visImpaired;

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


//        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/PlayView.fxml")));
//        this.scene = new Scene(root);
//        this.players = new ArrayList<>();
//        this.boardSize = 4;
//
//        this.stage = stage;
//        this.stage.setTitle("Initialize");
//        this.stage.setScene(this.scene);
//        this.stage.show();

    }




    private void start() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/PlayView.fxml"));
        loader.setController(this);
        Parent root = loader.load();
        Platform.runLater(root::requestFocus);

        boardFour = (RadioButton) root.lookup("#boardFour");
        boardFive = (RadioButton) root.lookup("#boardFive");
        boardFive.setDisable(true); //TODO: remove when the the 5x5 board is implemented
        submit = (Button) root.lookup("#submit");
        submitPlayer = (Button) root.lookup("#submitPlayer");
        error = (Label) root.lookup("#error");
        error.setText("");
        boardFour.setOnAction(e -> {
            boardSize = 4;
            this.visImpaired = false;
            board_flag = true;
        });
        boardFive.setOnAction(e -> {
            boardSize = 5;
            this.visImpaired = false;
            board_flag = true;
        });
        submitPlayer.setOnAction(e -> {
            this.visImpaired = false;
            submitPlayer(e);
        });
        submit.setOnAction(e -> {
            this.visImpaired = false;
            submit(e);
        });

        this.scene = new Scene(root);

        this.stage.setTitle("Initialize");
        this.stage.setScene(scene);

        stage.getScene().getWindow().requestFocus();

        this.stage.show();

        if (visImpaired) {
            this.screenReader.speak(boards);
        }

        this.setListeners();
        textField.setText("");
    }


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


    public void submit(ActionEvent e) {
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

    private void setListeners() {
        this.stage.getScene().setOnKeyPressed(e -> {
            System.out.println("pressed");
            if (e.isControlDown() && e.getCode() == KeyCode.B) {
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
                    screenReader.speak(String.format(confirmBoard, this.boardSize));
                    screenReader.speak(names);
                    board_flag = true;
                    textField.requestFocus();
                }
            }
        });
        if (boardFive.isSelected() || boardFour.isSelected()) {
                textField.requestFocus();
        }
        textField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.SHIFT) {
                submitPlayer.fire();
            } else if (keyEvent.getCode() == KeyCode.ENTER) {
                submit.fire();
            }
        });
    }
}

