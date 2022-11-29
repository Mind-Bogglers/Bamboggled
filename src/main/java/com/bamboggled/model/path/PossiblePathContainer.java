package com.bamboggled.model.path;

import java.util.ArrayList;
import java.util.List;

/**
 * Aggregation of possible Paths to a word.
 * @author Hassan El-Sheikha
 */
public class PossiblePathContainer {
    private List<Path> paths;

    public PossiblePathContainer() {
        this.paths = new ArrayList<>();
    }

    /**
     * Adds a path to the container.
     * @param path The path to add.
     */
    public void addPath(Path path) {
        this.paths.add(path);
    }


    /**
     * Returns all paths in the container.
     * @return all paths in the container
     */
    public List<Path> getPaths() {
        if (this.paths == null) {
            return new ArrayList<>();
        }
        return this.paths;
    }

    @Override
    public String toString() {
        return this.paths.toString();
    }

}
