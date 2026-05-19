package com.hyperjump.game.applicationcode.domainmodel.replay;

import java.util.List;

public class GameConfiguration {

    private int boardSize;
    private int playerCount;
    private List<SavedRule> rules;

    public GameConfiguration() {
    }

    public GameConfiguration(
            int boardSize,
            int playerCount,
            List<SavedRule> rules
    ) {
        this.boardSize = boardSize;
        this.playerCount = playerCount;
        this.rules = rules;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public List<SavedRule> getRules() {
        return rules;
    }
}