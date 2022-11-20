package com.bamboggled.model.exceptions;

/**
 * Exception thrown when there is no path found for a certain word.
 * @author Hassan El-Sheikha
 */
public class NoPathException extends Exception {
    public NoPathException(String message) {
        super(message);
    }
}
