
package com.bamboggled.views;

import com.bamboggled.model.model.BoggleModel;
import com.bamboggled.model.player.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class PlayView {

    public ToggleGroup board;

    private BoggleModel model;
    private WelcomeView parentView;

    private ArrayList<Player> players;

    private Stage stage;

    private Scene scene;

    private int boardSize;

    @FXML
    RadioButton boardFour;

    @FXML
    public Button submit;
    @FXML
    public Button next;

    @FXML
    private RadioButton boardFive;

    @FXML
    private TextField textField;

    @FXML
    private Label error;

    public PlayView(Stage stage) throws IOException {
        this.model = BoggleModel.getInstance();
        this.stage = stage;
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

    public PlayView() {
    }

    private void start() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/PlayView.fxml"));
        loader.setController(this);
        Parent root = loader.load();
        boardFour = (RadioButton) root.lookup("#boardFour");
        boardFive = (RadioButton) root.lookup("#boardFive");
        boardFive.setDisable(true); //TODO: remove when the the 5x5 board is implemented
        submit = (Button) root.lookup("#submit");
        next = (Button) root.lookup("#next");
        error = (Label) root.lookup("#error");
        error.setText("");
        boardFour.setOnAction(e -> boardSize = 4);
        boardFive.setOnAction(e -> boardSize = 5);
        next.setOnAction(this::nextPlayer);
        submit.setOnAction(this::submit);


        this.stage.setTitle("Initialize");
        this.stage.setScene(new Scene(root));
        this.stage.show();
    }


    public void nextPlayer(ActionEvent e) {
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
        String text = textField.getText().strip();
        if (this.boardSize == 0) {
            error.setText("Please select a board size.");
        } else if (this.players.size() == 0 && text.equals("")) {
            error.setText("Please enter at least one player.");
        } else {
            if (!text.equals("")) {
                this.players.add(new Player(text));
            }
            model.newGame(this.boardSize, this.players);
            new BoardGameView((Stage) ((Node) e.getSource()).getScene().getWindow());
        }
    }

}
