package com.bamboggled.model.dice;

import java.util.concurrent.ThreadLocalRandom;

//This class is used to generate a string of random letters, which is used to initialize a BoggleGrid.
public abstract class BoardLetterGenerator {
    private Die[] dice; // The dice used to generate the letters.

    /**
     * Constructor for the BoardLetterGenerator class.
     * @param dice The dice used to generate the letters.
*/
    BoardLetterGenerator(Die[] dice) {
        this.dice = dice;
    }

    /**
     * Generates a string of random letters, which is used for the board.
     * @return A string of random letters.
     */
    public String generateString() {
//        int randomIndex;
//        String ans = "";
//        HashSet<Integer> available = new HashSet<>(); // The indices of the dice that have not been used yet.
//        for (int i = 0; i < dice.length; i++) {
//            available.add(i);
//        }
//        for (int i = 0; i < dice.length; i++) {
//            randomIndex = ThreadLocalRandom.current().nextInt(0, available.size()); // Generate a random index.
//            ans += dice[randomIndex].roll(); // Roll the die at the random index and add the letter on the face to ans.
//            available.remove(randomIndex); // Remove the index from the set of available indices.
//        }
//        return ans;

        int n = (int) Math.sqrt(this.dice.length);
        Die[][] dice = new Die[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                dice[i][j] = this.dice[i * n + j];
            }
        }
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                Die temp = dice[row][col];
                int randomRow = ThreadLocalRandom.current().nextInt(0, n);
                int randomCol = ThreadLocalRandom.current().nextInt(0, n);
                dice[row][col] = dice[randomRow][randomCol];
                dice[randomRow][randomCol] = temp;
            }
        }
        String ans = "";
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                int randint = ThreadLocalRandom.current().nextInt(0, n + 1);
                ans += dice[row][col].roll();

            }
        }
        return ans;
    }
}
