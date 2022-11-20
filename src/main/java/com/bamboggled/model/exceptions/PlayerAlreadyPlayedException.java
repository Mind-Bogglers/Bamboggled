package com.bamboggled.model.exceptions;

/**
 * Exception thrown a player attempts to play a game they played before.
 * @author Hassan El-Sheikha
 */
public class PlayerAlreadyPlayedException extends Exception {
    public PlayerAlreadyPlayedException(String message) {
        super(message);
    }
}
