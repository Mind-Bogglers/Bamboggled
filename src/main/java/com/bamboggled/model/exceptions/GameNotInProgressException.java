package com.bamboggled.model.exceptions;

/**
 * Exception thrown when a call to endGame in the Model class is made when there is no game in progress.
 * @author Hassan El-Sheikha
 */
public class GameNotInProgressException extends Exception {
    public GameNotInProgressException(String message) {
        super(message);
    }
}
