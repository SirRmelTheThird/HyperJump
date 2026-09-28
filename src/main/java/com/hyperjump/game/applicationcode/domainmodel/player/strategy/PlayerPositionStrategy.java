package com.hyperjump.game.applicationcode.domainmodel.player.strategy;

import com.hyperjump.game.applicationcode.domainmodel.value.Position;

import java.util.List;

public interface PlayerPositionStrategy {
    boolean supports(int playerCount);
    List<Position> startPositions(int start, int end, int cols);
    List<Position> endPositions(int start, int end, int cols);
}