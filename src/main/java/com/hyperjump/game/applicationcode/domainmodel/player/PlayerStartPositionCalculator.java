package com.hyperjump.game.applicationcode.domainmodel.player;

import com.hyperjump.game.applicationcode.domainmodel.shared.DomainException;
import com.hyperjump.game.applicationcode.domainmodel.value.Position;

import java.util.List;

public class PlayerStartPositionCalculator {

    private final int start;
    private final int end;
    private final int cols;

    public PlayerStartPositionCalculator(int start, int end, int cols) {
        this.start = start;
        this.end = end;
        this.cols = cols;
    }

    public List<Position> getStartPositions(int playerCount) {
        return switch (playerCount) {
            case 2 -> List.of(
                new Position(start),
                new Position(end));
            case 4 -> List.of(
                new Position(start),
                new Position(end - cols + 1),
                new Position(end),
                new Position(start + cols - 1));
            default -> throw new DomainException("Unsupported number of players: " + playerCount);
        };
    }

    public List<Position> getEndPositions(int playerCount) {
        return switch (playerCount) {
            case 2 -> List.of(
                new Position(end),
                new Position(start));
            case 4 -> List.of(
                new Position(end),
                new Position(start + cols - 1),
                new Position(start),
                new Position(end - cols + 1));
            default -> throw new DomainException("Unsupported number of players: " + playerCount);
        };
    }
}
