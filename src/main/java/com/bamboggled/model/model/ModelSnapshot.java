package com.bamboggled.model.model;

import com.bamboggled.model.dice.BoardLetterGeneratorBig;
import com.bamboggled.model.dice.BoardLetterGeneratorSmall;
import com.bamboggled.model.grid.BoggleGrid;
import com.bamboggled.model.path.PossiblePathContainer;
import com.bamboggled.model.player.Player;
import com.bamboggled.model.word.BoggleDictionary;

import java.util.List;
import java.util.Set;

/**
 * This class is a memento of the BoggleModel class. It is used to store the state of the BoggleModel and can
 * be used to restore the state of the BoggleModel at a later time.
 * @author Hassan El-Sheikha
 */
public class ModelSnapshot {
    protected BoggleGrid currentGrid;
    protected Set<String> allWordsOnGrid;
    protected String currentWord;
    protected PossiblePathContainer possiblePaths;
    protected List<Player> players;
    protected Player currentPlayer;
    protected int currentPlayerIndex;

    /**
     * Constructor for ModelSnapshot
     * @param model The BoggleModel to take a snapshot of.
     */
    public ModelSnapshot(BoggleModel model) {
        this.currentGrid = model.currentGrid;
        this.allWordsOnGrid = model.allWordsOnGrid;
        this.currentWord = model.currentWord;
        this.possiblePaths = model.possiblePaths;
        this.players = model.players;
        this.currentPlayer = model.currentPlayer;
        this.currentPlayerIndex = model.currentPlayerIndex;
    }


}
