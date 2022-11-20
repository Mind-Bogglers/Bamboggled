package com.bamboggled.model.exceptions;

/**
 * Exception thrown when a new game is attempted to be started when one is currently in progress.
 * @author Hassan El-Sheikha
 */
public class GameAlreadyInProgressException extends Exception {
    public GameAlreadyInProgressException(String message) {
        super(message);
    }
}
