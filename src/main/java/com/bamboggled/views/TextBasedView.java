package com.bamboggled.views;

import com.bamboggled.model.exceptions.*;
import com.bamboggled.model.grid.BoggleGrid;
import com.bamboggled.model.model.BoggleModel;
import com.bamboggled.model.path.Path;
import com.bamboggled.model.player.Player;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

/**
 * A text-based view for the Boggle game. This view features a multiplayer mode, and character-by-character word input.
 * @author Hassan El-Sheikha
 */
public class TextBasedView {
    private BoggleModel model;
    private Scanner scanner;

    public static void main(String[] args) {
        TextBasedView view = new TextBasedView(new BoggleModel());
        view.play();
    }

    public TextBasedView(BoggleModel model) {
        this.scanner = new Scanner(System.in);
        this.model = model;
    }

    public void play() {
        int boardSize;
        System.out.println("Welcome to Boggle!");
        while (true) {
            System.out.println("Enter 1 to play on a big (5x5) grid; 2 to play on a small (4x4) one:");
            String choiceGrid = scanner.nextLine();
            //get grid size preference
            if (Objects.equals(choiceGrid, "")) break; //end game if user inputs nothing
            while (!choiceGrid.equals("1") && !choiceGrid.equals("2")) {
                System.out.println("Please try again.");
                System.out.println("Enter 1 to play on a big (5x5) grid; 2 to play on a small (4x4) one:");
                choiceGrid = scanner.nextLine();
            }
            if (choiceGrid.equals("1")) {
                boardSize = 5;
            } else {
                boardSize = 4;
            }
            System.out.println("For each player, enter their name and press enter. When you are done, press enter again.");
            //get player names and add them to player list
            ArrayList<Player> players = new ArrayList<>();
            String name = scanner.nextLine();
            while (!name.equals("")) {
                players.add(new Player(name));
                name = scanner.nextLine();
            }
            //start game
            model.newGame(boardSize, players);
            while (true) {
                try {
                    model.startGameForNextPlayer();
                } catch (NoMorePlayersException e) {
                    System.out.println("Game over!");
                    break;
                } catch (GameAlreadyInProgressException | PlayerAlreadyPlayedException e) {
                    throw new RuntimeException(e);
                }
                Player currentPlayer = model.getCurrentPlayer();
                    System.out.println("It is " + currentPlayer.getName() + "'s turn.");
                    System.out.println("The board is:");
                    System.out.println(model.getCurrentGrid().toString());
                    System.out.println("For every letter of the word you want to enter, enter it and press enter. When you are done, press enter again.");
                    String letter = "*"; //random placeholder
                    while (true) {
                        while (true) {
                            letter = scanner.nextLine();
                            if (letter.equals("")) break;
                            try {
                                model.addLetterToCurrentWord(letter.charAt(0));
                            } catch (NoPathException e) {
                                System.out.println("No path exists for the current word. Please try again.");
                                continue;
                            }
                            System.out.println("The current word is: " + model.getCurrentWord());
                            System.out.println("The board is:");
                            try {
                                printBoardWithPath(model.getCurrentGrid(), model.getPathToWord());
                            } catch (EmptyWordException e) {
                                throw new RuntimeException(e);
                            }

                        }
                        if (model.submitCurrentWord()) {
                            System.out.println("You got a word!");
                            System.out.println("The board is:");
                            System.out.println(model.getCurrentGrid().toString());
                        } else {
                            System.out.println("You did not get a word.");
                            System.out.println("The board is:");
                            System.out.println(model.getCurrentGrid().toString());
                        }
                        System.out.println("Press 1 to keep guessing, 2 to end your turn.");
                        String choice = scanner.nextLine();
                        while (!choice.equals("1") && !choice.equals("2")) {
                            System.out.println("Please try again.");
                            System.out.println("Press 1 to keep guessing, 2 to end your turn.");
                            choice = scanner.nextLine();
                        }
                        if (choice.equals("2")) {
                            System.out.println("Good game!. Your score was " + currentPlayer.getScore());
                            try {
                                model.endGame();
                            } catch (GameNotInProgressException e) {
                                throw new RuntimeException(e);
                            }
                            break;
                        }
                    }



            }


        }
    }

    private void printBoardWithPath(BoggleGrid grid, Path path) {
        for (int i = 0; i < grid.numRows(); i++) {
            for (int j = 0; j < grid.numCols(); j++) {
                if (path.contains(i, j)) {
                    System.out.print("[" + grid.getCharAt(i, j) + "]" + "\t");
                } else {
                    System.out.print(grid.getCharAt(i, j) + "\t");
                }
            }
            System.out.println();
        }
    }

}
