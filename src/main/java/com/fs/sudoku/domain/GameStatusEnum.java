package com.fs.sudoku.domain;

public enum GameStatusEnum {
    NON_STARTED("not started"),
    INCOMPLETED("incompleted"),
    COMPLETED("completed"),;

    private final String label;

    GameStatusEnum(final String label){
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
