package com.hyperjump.game.applicationcode.domainmodel.board;

public class LargeBoard extends AbstractBoard {

    private static final int COLS = 6;
    private static final int[] GRID = {
            1,  2,  3,  4,  5,  6,
            7,  8,  9, 10, 11, 12,
            13, 14, 15, 16, 17, 18,
            19, 20, 21, 22, 23, 24,
            25, 26, 27, 28, 29, 30,
            31, 32, 33, 34, 35, 36
    };

    private static final LargeBoard INSTANCE = new LargeBoard();

    private LargeBoard() {
        super(GRID);
    }

    public static LargeBoard getInstance() {
        return INSTANCE;
    }

    @Override public int getSize() {
        return GRID.length;
    }

    @Override public int getCols() {
        return COLS;
    }

    @Override public int getStartPosition() {
        return 1;
    }

    @Override public int getEndPosition() {
        return GRID.length;
    }

    @Override public boolean supports(int playerCount) {
        return playerCount == 4;
    }
}