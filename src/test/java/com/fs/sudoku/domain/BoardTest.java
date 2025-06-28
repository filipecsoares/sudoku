package com.fs.sudoku.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static com.fs.sudoku.domain.GameStatusEnum.*;

class BoardTest {
    private List<List<Space>> spaces;
    private Board board;

    @BeforeEach
    void setUp() {
        spaces = new ArrayList<>();
        // 3x3 board, all spaces expected=1, not fixed
        for (int i = 0; i < 3; i++) {
            List<Space> row = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                row.add(new Space(1, false));
            }
            spaces.add(row);
        }
        board = new Board(spaces);
    }

    @Test
    void testGetSpacesReturnsReference() {
        assertSame(spaces, board.getSpaces());
    }

    @Test
    void testGetStatusNonStarted() {
        // All spaces actual=null, not fixed
        assertEquals(NON_STARTED, board.getStatus());
    }

    @Test
    void testGetStatusIncompleted() {
        spaces.get(0).get(0).setActual(1);
        assertEquals(INCOMPLETED, board.getStatus());
    }

    @Test
    void testGetStatusCompleted() {
        for (List<Space> row : spaces) {
            for (Space s : row) {
                s.setActual(1);
            }
        }
        assertEquals(COMPLETED, board.getStatus());
    }

    @Test
    void testHasErrorsFalseWhenNonStarted() {
        assertFalse(board.hasErrors());
    }

    @Test
    void testHasErrorsTrue() {
        // Set one space to wrong value
        spaces.get(0).get(0).setActual(2);
        assertTrue(board.hasErrors());
    }

    @Test
    void testHasErrorsFalseWhenNoErrors() {
        for (List<Space> row : spaces) {
            for (Space s : row) {
                s.setActual(1);
            }
        }
        assertFalse(board.hasErrors());
    }

    @Test
    void testChangeValueOnFixedSpace() {
        // Create a fixed space
        Space fixed = new Space(5, true);
        List<Space> row = new ArrayList<>();
        row.add(fixed);
        spaces.set(0, row);
        Board b = new Board(spaces);
        assertFalse(b.changeValue(0, 0, 3));
        assertEquals(5, fixed.getActual());
    }

    @Test
    void testChangeValueOnNonFixedSpace() {
        assertTrue(board.changeValue(0, 0, 7));
        assertEquals(7, spaces.get(0).get(0).getActual());
    }

    @Test
    void testClearValueOnFixedSpace() {
        Space fixed = new Space(4, true);
        List<Space> row = new ArrayList<>();
        row.add(fixed);
        spaces.set(0, row);
        Board b = new Board(spaces);
        assertFalse(b.clearValue(0, 0));
        assertEquals(4, fixed.getActual());
    }

    @Test
    void testClearValueOnNonFixedSpace() {
        board.changeValue(0, 0, 2);
        assertTrue(board.clearValue(0, 0));
        assertNull(spaces.get(0).get(0).getActual());
    }

    @Test
    void testResetClearsAllNonFixedSpaces() {
        for (List<Space> row : spaces) {
            for (Space s : row) {
                s.setActual(9);
            }
        }
        board.reset();
        for (List<Space> row : spaces) {
            for (Space s : row) {
                assertNull(s.getActual());
            }
        }
    }

    @Test
    void testResetDoesNotClearFixedSpaces() {
        Space fixed = new Space(8, true);
        List<Space> row = new ArrayList<>();
        row.add(fixed);
        spaces.set(0, row);
        Board b = new Board(spaces);
        b.reset();
        assertEquals(8, fixed.getActual());
    }

    @Test
    void testGameIsFinishedTrue() {
        for (List<Space> row : spaces) {
            for (Space s : row) {
                s.setActual(1);
            }
        }
        assertTrue(board.gameIsFinished());
    }

    @Test
    void testGameIsFinishedFalseDueToErrors() {
        for (List<Space> row : spaces) {
            for (Space s : row) {
                s.setActual(2);
            }
        }
        assertFalse(board.gameIsFinished());
    }

    @Test
    void testGameIsFinishedFalseDueToIncomplete() {
        for (List<Space> row : spaces) {
            for (Space s : row) {
                s.setActual(1);
            }
        }
        spaces.get(0).get(0).setActual(null);
        assertFalse(board.gameIsFinished());
    }
}
