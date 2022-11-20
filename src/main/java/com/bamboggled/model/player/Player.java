package com.bamboggled.model.player;

import java.util.HashSet;
import java.util.Set;

/**
 * Class to hold information about a boggle player.
 */
public class Player {
    private final String name;
    private int score;
    private Set<String> foundWords;
    boolean played;

    /**
     * Player constructor.
     * @param name The name of the player.
     */
    public Player(String name) {
        this.name = name;
        this.score = 0;
        this.foundWords = new HashSet<>();
        this.played = false;
    }

    /**
     * Adds a word to the player's found words.
     * @param word The word to add.
     */
    public void addWord(String word) {
        this.foundWords.add(word);
        this.score += word.length() - 3;
    }

    /**
     * Returns the player's name.
     * @return the player's name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Resets the player's score to 0.
     */
    public void resetScore() {
        this.score = 0;
    }

    /**
     * Returns the player's score.
     * @return the player's score
     */
    public int getScore() {
        return this.score;
    }

    /**
     * Returns the player's found words.
     * @return the player's found words
     */
    public Set<String> getFoundWords() {
        return this.foundWords;
    }

    /**
     * Clears the player's found words.
     */
    public void clearFoundWords() {
        this.foundWords.clear();
    }

    /**
     * Sets the player's played status.
     * @param played The new played status.
     */
    public void setPlayed(boolean played) {
        this.played = played;
    }

    /**
     * Returns the player's played status.
     * @return the player's played status
     */
    public boolean hasPlayed() {
        return this.played;
    }
}
