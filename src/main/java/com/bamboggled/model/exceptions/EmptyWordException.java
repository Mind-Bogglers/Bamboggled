package com.bamboggled.model.exceptions;

/**
 * Exception thrown when the currentWord attribute in the Model class is empty.
 * @author Hassan El-Sheikha
 */
public class EmptyWordException extends Exception {
    public EmptyWordException(String message) {
        super(message);
    }
}
