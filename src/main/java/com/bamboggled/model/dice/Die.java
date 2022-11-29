package com.bamboggled.model.dice;

import java.util.concurrent.ThreadLocalRandom;

/**
 * The Die class represents a single die in a set of dice used to play Boggle.
 * Each die has a set of letters on it.
 */
public class Die {
    private final String faces; // The faces of the die.

    /**
     * Constructor for the Die class.
     * @param faces The faces of the die.
     */
    public Die(String faces) {
        this.faces = faces;
    }

    /**
     * Rolls the die.
     * @return The face of the die that is facing up.
     */
    public char roll() {
        return faces.charAt(ThreadLocalRandom.current().nextInt(0, faces.length()));
    }
}
