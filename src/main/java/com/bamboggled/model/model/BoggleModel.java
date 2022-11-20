package com.bamboggled.model.model;

import com.bamboggled.model.exceptions.*;
import com.bamboggled.model.dice.BoardLetterGeneratorBig;
import com.bamboggled.model.dice.BoardLetterGeneratorSmall;
import com.bamboggled.model.grid.BoggleGrid;
import com.bamboggled.model.path.Path;
import com.bamboggled.model.path.PathContainerUtils;
import com.bamboggled.model.path.PossiblePathContainer;
import com.bamboggled.model.player.Player;
import com.bamboggled.model.word.BoggleDictionary;
import com.bamboggled.model.word.WordUtils;

import java.util.List;
import java.util.Set;


/**
 * Represents a Boggle Model for the Boggle game.
 * @author Hassan El-Sheikha
 */
public class BoggleModel implements IBoggleModel {
    private final BoardLetterGeneratorSmall smallWordGenerator;
    private final BoardLetterGeneratorBig bigWordGenerator;
    private final BoggleGrid smallBoggleGrid;
    private final BoggleGrid bigBoggleGrid;
    private BoggleGrid currentGrid;
    private final BoggleDictionary dictionary;
    private Set<String> allWordsOnGrid;
    private String currentWord;
    private PossiblePathContainer possiblePaths;
    private List<Player> players;
    private Player currentPlayer;
    private int currentPlayerIndex;
    public final int GREEN = 0;
    public final int GRAY = 1;
    public final int RED = 2;


    /**
     * Constructor for the BoggleModel class.
     */
    public BoggleModel() {
        this.smallWordGenerator = new BoardLetterGeneratorSmall();
        this.bigWordGenerator = new BoardLetterGeneratorBig();
        this.smallBoggleGrid = new BoggleGrid(4);
        this.bigBoggleGrid = new BoggleGrid(5);
        this.dictionary = new BoggleDictionary("src/main/java/com/bamboggled/model/wordlist2.txt");
        this.currentWord = "";
        this.possiblePaths = null;
        this.players = null;
        this.currentPlayer = null;
    }

    /**
     * Given the players participating in the game and the size of the grid, creates a new game.
     * @param dimensionsOfGrid The side length of the grid.
     * @param players The players in the game, without duplicate players.
     * @throws IllegalArgumentException If the dimensionsOfGrid is not 4 or 5, or if the player list is empty.
     * @WARNING Mutates all players in the player list to reset their found words and scores.
     */
    @Override
    public void newGame(int dimensionsOfGrid, List<Player> players) throws IllegalArgumentException{
        this.currentWord = "";
        this.possiblePaths = null;
        if (dimensionsOfGrid == 4) {
            this.currentGrid = this.smallBoggleGrid;
            this.currentGrid.initalizeBoard(this.smallWordGenerator.generateString());
        } else if (dimensionsOfGrid == 5) {
            this.currentGrid = this.bigBoggleGrid;
            this.currentGrid.initalizeBoard(this.bigWordGenerator.generateString());
        } else {
            throw new IllegalArgumentException("Dimensions of board must be 4 or 5");
        }
        this.allWordsOnGrid = WordUtils.findAllWords(dictionary, currentGrid);

        if (players == null || players.size() == 0) {
            throw new IllegalArgumentException("Must have at least one player");
        }
        this.players = players;
        // clear player scores and words from prior rounds (if any).
        for (Player player : this.players) {
            player.resetScore();
            player.clearFoundWords();
            player.setPlayed(false);
        }
        this.currentPlayerIndex = 0;
    }

    /**
     * Starts the game for the next player in the list of players.
     * @throws NoMorePlayersException If there are no more players to play.
     * @throws GameAlreadyInProgressException If the game is already in progress for another player.
     * @throws PlayerAlreadyPlayedException If the current player has already played this game.
     * @precondition There is no current player playing the game.
     */
    public void startGameForNextPlayer() throws NoMorePlayersException, GameAlreadyInProgressException, PlayerAlreadyPlayedException {
        if (this.currentPlayerIndex >= this.players.size()) {
            throw new NoMorePlayersException("No more players to start game for");
        }
        Player toPlay = this.players.get(this.currentPlayerIndex);
        this.currentPlayerIndex++;
        startGame(toPlay);
    }

    /**
     * Starts the game for the given player.
     * @param player The player to start the game for.
     * @throws GameAlreadyInProgressException If the game is already in progress for another player.
     * @throws PlayerAlreadyPlayedException If the current player has already played this game.
     */
    private void startGame(Player player) throws GameAlreadyInProgressException, PlayerAlreadyPlayedException {
        if (this.currentPlayer != null) {
            throw new GameAlreadyInProgressException("Game already started for another player");
        }
        if (player.hasPlayed()) {
            throw new PlayerAlreadyPlayedException("Player has already played");
        }
        this.currentPlayer = player;
        this.currentWord = ""; //wipe the previous player's word, if any.
        this.possiblePaths = null;
    }

    /**
     * Ends the game for the current player.
     * @throws GameNotInProgressException If the game is not in progress.
     */
    @Override
    public void endGame() throws GameNotInProgressException {
        if (this.currentPlayer == null) {
            throw new GameNotInProgressException("There is no player currently playing to end the game for.");
        }
        this.currentPlayer.setPlayed(true);
        this.currentPlayer.clearFoundWords();
        this.currentPlayer = null;
    }

    /**
     * Adds the letter to the current word and updates the possible paths.
     * @param letter The letter to add to the current word.
     * @throws NoPathException if there is no path to current word given the new letter.
     */
    @Override
    public void addLetterToCurrentWord(char letter) throws NoPathException {
        this.possiblePaths = PathContainerUtils.fetchContainer(this.possiblePaths, this.currentGrid, Character.toUpperCase(letter));
        this.currentWord += Character.toUpperCase(letter);
    }

    /**
     * Check if the current word is a valid word, and if so, add it to the current player's valid words. If not, do not
     * add it.
     * In either case, the current word and possible paths are reset.
     * @return If the current word is a valid word or not.
     */
    @Override
    public boolean submitCurrentWord() {
        if (this.allWordsOnGrid.contains(this.currentWord)) {
            if (!this.currentPlayer.getFoundWords().contains(this.currentWord)) {
                this.currentPlayer.addWord(this.currentWord);
                this.currentWord = "";
                this.possiblePaths = null;
                return true;
            }
        }
        this.currentWord = "";
        this.possiblePaths = null;
        return false;
    }

    /**
     * Check if the current word is a valid word, and if so, add it to the current player's valid words. If not, do not
     * add it.
     * @return GREEN if the word is accepted, GRAY if the word is valid but has already been guessed before, or RED if
     * the word is invalid.
     */
    public int submitCurrentWordColored() {
        if (this.allWordsOnGrid.contains(this.currentWord)) {
            if (!this.currentPlayer.getFoundWords().contains(this.currentWord)) {
                this.currentPlayer.addWord(this.currentWord);
                this.currentWord = "";
                this.possiblePaths = null;
                return this.GREEN;
            }
            this.currentWord = "";
            this.possiblePaths = null;
            return this.GRAY;
        } else {
            this.currentWord = "";
            this.possiblePaths = null;
            return this.RED;
        }
    }

    /**
     * Returns a possible path to the current word.
     * @return a possible path to the current word.
     * @throws EmptyWordException there is no current word.
     */
    @Override
    public Path getPathToWord() throws EmptyWordException {
        if (this.possiblePaths == null) {
            throw new EmptyWordException("There is no current word to find a path to. Add at least one letter to the current word.");
        }
        return possiblePaths.getPaths().get(0);
    }

    /**
     * Returns the grid of letters.
     * @return the current grid being used.
     */
    @Override
    public BoggleGrid getCurrentGrid() {
        return this.currentGrid;
    }

    /**
     * Returns the current player playing the game.
     * @return the current player playing the game.
     */
    @Override
    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    /**
     * Returns all players playing this game.
     * @return all players playing this game.
     */
    @Override
    public List<Player> getPlayers() {
        return this.players;
    }

    /**
     * Returns the current word.
     * @return the current word.
     */
    @Override
    public String getCurrentWord() {
        return this.currentWord;
    }

    /**
     * Returns all the words that can be found on the grid corresponding to the current game.
     * @return all the words that can be found on the grid corresponding to the current game
     */
    @Override
    public Set<String> getAllWordsOnGrid() {
        return this.allWordsOnGrid;
    }

}
