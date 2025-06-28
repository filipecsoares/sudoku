package com.fs.sudoku;

import com.fs.sudoku.domain.Board;
import com.fs.sudoku.domain.Space;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static com.fs.sudoku.util.BoardTemplate.BOARD_TEMPLATE;

public class PlayGame {
    private final Scanner scanner = new Scanner(System.in);
    private Board board;
    private final int BOARD_LIMIT = 9;
    private final Map<String, String> positions;

    public PlayGame(Map<String, String> positions) {
        this.positions = positions;
    }

    public void run() {
        int option = -1;
        while (true) {
            showCurrentGame();
            System.out.println("Select one of the following options");
            System.out.println("1 - Start a new game");
            System.out.println("2 - Insert a new number");
            System.out.println("3 - Remove a number");
            System.out.println("4 - View current game");
            System.out.println("5 - Check game status");
            System.out.println("6 - Clear game");
            System.out.println("7 - Finish game");
            System.out.println("8 - Exit");

            option = scanner.nextInt();

            switch (option) {
                case 1 -> startGame();
                case 2 -> inputNumber();
                case 3 -> removeNumber();
                case 4 -> showCurrentGame();
                case 5 -> showGameStatus();
                case 6 -> clearGame();
                case 7 -> finishGame();
                case 8 -> System.exit(0);
                default -> System.out.println("Invalid option, please select a menu option");
            }
        }
    }

    private void startGame() {
        if (board != null) {
            System.out.println("The game has already started");
            return;
        }
        List<List<Space>> spaces = new ArrayList<>();
        for (int i = 0; i < BOARD_LIMIT; i++) {
            spaces.add(new ArrayList<>());
            for (int j = 0; j < BOARD_LIMIT; j++) {
                var key = "%s,%s".formatted(i, j);
                var positionConfig = positions.get(key);
                int expected = 0;
                boolean fixed = false;
                if (positionConfig != null) {
                    var parts = positionConfig.split(",");
                    expected = Integer.parseInt(parts[0]);
                    fixed = Boolean.parseBoolean(parts[1]);
                }
                var currentSpace = new Space(expected, fixed);
                spaces.get(i).add(currentSpace);
            }
        }
        board = new Board(spaces);
        System.out.println("The game is ready to start");
    }

    private void inputNumber() {
        if (board == null) {
            System.out.println("The game has not started yet");
            return;
        }
        System.out.println("Enter the column where the number will be inserted");
        var col = runUntilGetValidNumber(0, 8);
        System.out.println("Enter the row where the number will be inserted");
        var row = runUntilGetValidNumber(0, 8);
        System.out.printf("Enter the number to insert at position [%s,%s]\n", col, row);
        var value = runUntilGetValidNumber(1, 9);
        if (!board.changeValue(col, row, value)) {
            System.out.printf("The position [%s,%s] has a fixed value\n", col, row);
        }
    }

    private void removeNumber() {
        if (board == null) {
            System.out.println("The game has not started yet");
            return;
        }
        System.out.println("Enter the column of the number to remove");
        var col = runUntilGetValidNumber(0, 8);
        System.out.println("Enter the row of the number to remove");
        var row = runUntilGetValidNumber(0, 8);
        if (!board.clearValue(col, row)) {
            System.out.printf("The position [%s,%s] has a fixed value\n", col, row);
        }
    }

    private void showCurrentGame() {
        if (board == null) {
            System.out.println("The game has not started yet.");
            return;
        }
        var args = new Object[81];
        var argPos = 0;
        for (int i = 0; i < BOARD_LIMIT; i++) {
            for (var col : board.getSpaces()) {
                args[argPos++] = " " + ((col.get(i).getActual() == null) ? " " : col.get(i).getActual());
            }
        }
        System.out.println("Your current game board:");
        System.out.printf((BOARD_TEMPLATE) + "\n", args);
    }

    private void showGameStatus() {
        if (board == null) {
            System.out.println("The game has not started yet");
            return;
        }
        System.out.printf("The game is currently in status: %s\n", board.getStatus().getLabel());
        if (board.hasErrors()) {
            System.out.println("The game contains errors");
        } else {
            System.out.println("The game does not contain errors");
        }
    }

    private void clearGame() {
        if (board == null) {
            System.out.println("The game has not started yet");
            return;
        }
        System.out.println("Are you sure you want to clear your game and lose all progress?");
        var confirm = scanner.next();
        while (!confirm.equalsIgnoreCase("yes") && !confirm.equalsIgnoreCase("no")) {
            System.out.println("Please enter 'yes' or 'no'");
            confirm = scanner.next();
        }
        if (confirm.equalsIgnoreCase("yes")) {
            board.reset();
        }
    }

    private void finishGame() {
        if (board == null) {
            System.out.println("The game has not started yet");
            return;
        }
        if (board.gameIsFinished()) {
            System.out.println("Congratulations, you completed the game!");
            showCurrentGame();
            board = null;
        } else if (board.hasErrors()) {
            System.out.println("Your game contains errors, please check your board and fix them");
        } else {
            System.out.println("You still need to fill in some spaces");
        }
    }

    private int runUntilGetValidNumber(final int min, final int max) {
        var current = scanner.nextInt();
        while (current < min || current > max) {
            System.out.printf("Please enter a number between %s and %s\n", min, max);
            current = scanner.nextInt();
        }
        return current;
    }
}
