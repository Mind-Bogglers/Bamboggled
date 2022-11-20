package com.bamboggled.model.path;

import com.bamboggled.model.exceptions.NoPathException;
import com.bamboggled.model.grid.BoggleGrid;

import java.util.ArrayList;
import java.util.List;

/**
 * A utility class for the PossiblePathContainer and Path classes.
 * @author Hassan El-Sheikha
 */
public class PathContainerUtils {

    /**
     * Given a list of paths, and a new letter, returns a new list of all the paths that can be made by adding the new
     * letter to the end of the paths (if possible).
     * @param existingContainer The list of paths to add to.
     * @param boggleGrid The BoggleGrid to use.
     * @param latestKey The new letter to add to the paths.
     * @return A new list of paths that can be made by adding the new letter to the end of the paths (if possible).
     * @throws NoPathException If no paths can be made by adding the new letter to the end of the paths.
     */
    public static PossiblePathContainer fetchContainer(PossiblePathContainer existingContainer, BoggleGrid boggleGrid, char latestKey) throws NoPathException {
        PossiblePathContainer newContainer = new PossiblePathContainer();
        if (existingContainer == null) {
            initFirstLetterContainer(newContainer, boggleGrid, latestKey);
        } else {
            for (Path existingPath : existingContainer.getPaths()) {
                for (Path newPath : fetchPaths(existingPath, boggleGrid, latestKey)) {
                    newContainer.addPath(newPath);
                }
            }
        }
        if (newContainer.getPaths().size() == 0) {
            throw new NoPathException("There is no path for the current word.");

        }
        return newContainer;
    }

    /**
     * In the case where the existing container is null, this method will create a new container that contains all the
     * possible 1-length paths that start with the given letter.
     * @param container The container to add the paths to. This container should be empty.
     * @param boggleGrid The BoggleGrid to use.
     * @param latestKey The letter to use.
     */
    private static void initFirstLetterContainer(PossiblePathContainer container, BoggleGrid boggleGrid, char latestKey) {
        for (int i = 0; i < boggleGrid.numRows(); i++) {
            for (int j = 0; j < boggleGrid.numCols(); j++) {
                if (boggleGrid.getCharAt(i, j) == latestKey) {
                    container.addPath(new Path(new Position(i, j)));
                }
            }
        }

    }

    /**
     * Given a path, and a new letter, returns a list of all the paths that can be made by adding the new letter to the
     * end of the path (if possible).
     * @param existingPath The path to add to.
     * @param boggleGrid The BoggleGrid to use.
     * @param latestKey The new letter to add to the path.
     */
    private static List<Path> fetchPaths(Path existingPath, BoggleGrid boggleGrid, char latestKey) {
        List<Position> positions = existingPath.getPositions();
        int rowAt = positions.get(positions.size() - 1).getRow();
        int colAt = positions.get(positions.size() - 1).getCol();
        ArrayList<Path> ans = new ArrayList<>();
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (rowAt + i >= boggleGrid.numRows() || rowAt + i < 0 || colAt + j >= boggleGrid.numCols() || colAt + j < 0) {
                    continue;
                }
                if (boggleGrid.getCharAt(rowAt + i, colAt + j) == latestKey) {
                    if (!existingPath.contains(rowAt + i, colAt + j)) {
                        ans.add(new Path(existingPath, new Position(rowAt + i, colAt + j)));
                    }
                }

            }
        }
        return ans;
    }
}
