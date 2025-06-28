package com.fs.sudoku.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SpaceTest {
    @Test
    void testFixedSpaceInitialization() {
        Space space = new Space(5, true);
        assertEquals(5, space.getActual());
        assertTrue(space.isFixed());
    }

    @Test
    void testNonFixedSpaceInitialization() {
        Space space = new Space(3, false);
        assertNull(space.getActual());
        assertFalse(space.isFixed());
    }

    @Test
    void testSetActualOnNonFixed() {
        Space space = new Space(2, false);
        space.setActual(7);
        assertEquals(7, space.getActual());
    }

    @Test
    void testSetActualOnFixed() {
        Space space = new Space(2, true);
        space.setActual(8);
        assertEquals(2, space.getActual()); // Should not change
    }

    @Test
    void testClearSpace() {
        Space space = new Space(4, false);
        space.setActual(9);
        space.clearSpace();
        assertNull(space.getActual());
    }
}

