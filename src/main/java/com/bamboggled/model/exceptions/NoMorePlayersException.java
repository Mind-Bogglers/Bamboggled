package com.bamboggled.model.exceptions;

/**
 * Exception thrown when there are no more players left to play a boggle game.
 * @author Hassan El-Sheikha
 */
public class NoMorePlayersException extends Exception {
    public NoMorePlayersException(String message) {
        super(message);
    }
}
