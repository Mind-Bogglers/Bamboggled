package com.bamboggled.model.word;

import com.bamboggled.model.grid.BoggleGrid;
import com.bamboggled.model.path.Position;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class WordUtils {

    /**
     * Finds all words (with length greater than four) in a BoggleGrid that are in a Dictionary.
     *
     * @param boggleDict Dictionary
     * @param boggleGrid BoggleGrid
     * @return Set of words
     */
    public static Set<String> findAllWords(BoggleDictionary boggleDict, BoggleGrid boggleGrid) {
        HashSet<String> allWords = new HashSet<>();
        for (int i = 0; i < boggleGrid.numRows(); i++) {
            for (int j = 0; j < boggleGrid.numCols(); j++) {
                ArrayList<Position> path = new ArrayList<>();
                path.add(new Position(i, j));
                String word = Character.toString(boggleGrid.getCharAt(i, j));
                allWords(word, path, boggleDict, allWords, boggleGrid);
            }
        }
        return allWords;
    }

    private static void allWords(String currentWord, ArrayList<Position> visited, BoggleDictionary boggleDict, HashSet<String> allWords, BoggleGrid boggleGrid) {
        if (boggleDict.containsWord(currentWord)) {
            if (currentWord.length() >= 4) {
                allWords.add(currentWord);
            }
        } else if (!boggleDict.isPrefix(currentWord)) {
            return;
        }
        ArrayList<Position> nextPositions = findNextPositions(visited, boggleGrid);
        if (nextPositions.size() == 0) {
            return;
        }
        for (Position p: nextPositions) {
            ArrayList<Position> path = new ArrayList<>(visited);
            path.add(p);
            String pathWord = currentWord + boggleGrid.getCharAt(p.getRow(), p.getCol());
            allWords(pathWord, path, boggleDict, allWords, boggleGrid);
        }

    }


    private static ArrayList<Position> findNextPositions(ArrayList<Position> visited, BoggleGrid boggleGrid) {
        int rowAt = visited.get(visited.size() - 1).getRow();
        int colAt = visited.get(visited.size() - 1).getCol();
        ArrayList<Position> ans = new ArrayList<>();
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (rowAt + i >= boggleGrid.numRows() || rowAt + i < 0 || colAt + j >= boggleGrid.numCols() || colAt + j < 0) {
                    continue;
                }
                boolean seenBefore = false;
                for (Position p: visited) {
                    if (p.getRow() == rowAt + i && p.getCol() == colAt + j) {
                        seenBefore = true;
                        break;
                    }
                }
                if (!seenBefore) {
                    ans.add(new Position(rowAt + i, colAt + j));
                }

            }
        }
        return ans;
    }
}
