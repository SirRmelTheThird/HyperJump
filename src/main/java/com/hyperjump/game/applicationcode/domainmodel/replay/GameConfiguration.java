package com.hyperjump.game.applicationcode.domainmodel.replay;

import com.hyperjump.game.applicationcode.port.out.GameRule;

import java.util.List;

public class GameConfiguration {

    private final int playerCount;
    private final List<GameRule> rules;

    public GameConfiguration(int boardSize, int playerCount, List<GameRule> rules) {
        this.playerCount = playerCount;
        this.rules       = rules;
    }

    public int getPlayerCount()    { return playerCount; }
    public List<GameRule> getRules() { return rules; }
}