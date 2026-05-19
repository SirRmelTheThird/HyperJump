package com.hyperjump.game.applicationcode.domainmodel.player.strategy;

import com.hyperjump.game.applicationcode.domainmodel.value.Position;

import java.util.List;

public class TwoPlayerPosition implements PlayerPositionStrategy {

    @Override
    public boolean supports(int playerCount) {
        return playerCount == 2;
    }

    @Override
    public List<Position> startPositions(int start, int end, int cols) {
        return List.of(new Position(start), new Position(end));
    }

    @Override
    public List<Position> endPositions(int start, int end, int cols) {
        return List.of(new Position(end), new Position(start));
    }
}