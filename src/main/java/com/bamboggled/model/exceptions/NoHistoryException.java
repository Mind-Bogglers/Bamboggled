package com.bamboggled.model.exceptions;

/**
 * This exception is thrown when the user tries to undo a move when there is no history to undo.
 */
public class NoHistoryException extends Exception {
    public NoHistoryException(String message) {
        super(message);
    }
}
