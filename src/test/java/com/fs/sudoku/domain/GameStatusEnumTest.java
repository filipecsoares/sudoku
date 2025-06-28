package com.fs.sudoku.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameStatusEnumTest {
    @Test
    void testLabels() {
        assertEquals("not started", GameStatusEnum.NON_STARTED.getLabel());
        assertEquals("incompleted", GameStatusEnum.INCOMPLETED.getLabel());
        assertEquals("completed", GameStatusEnum.COMPLETED.getLabel());
    }
}

