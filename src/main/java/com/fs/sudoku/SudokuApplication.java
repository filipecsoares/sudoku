package com.fs.sudoku;

import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public class SudokuApplication {
    public static void main(String[] args) {
        Map<String, String> positions = Stream.of(args)
                .collect(toMap(
                        k -> k.split(";")[0],
                        v -> v.split(";")[1]
                ));
        PlayGame playGame = new PlayGame(positions);
        playGame.run();
    }
}
