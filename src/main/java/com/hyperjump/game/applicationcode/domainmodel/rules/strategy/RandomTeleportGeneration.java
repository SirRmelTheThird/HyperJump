package com.hyperjump.game.applicationcode.domainmodel.rules.strategy;

import com.hyperjump.game.applicationcode.domainmodel.player.Player;
import com.hyperjump.game.applicationcode.domainmodel.value.Position;

import java.util.*;

public class RandomTeleportGeneration implements TeleportGenerationStrategy {

    private static final int MAX_WORMHOLES = 2;

    @Override
    public Map<Position, Position> generate(int boardSize, List<Player> players) {
        Map<Position, Position> wormholes = new HashMap<>();
        Set<Integer> excluded = getExcludedPositions(players);
        List<Integer> positions = getAvailablePositions(boardSize, excluded);

        Collections.shuffle(positions);

        int maxPossibleWormholes = positions.size() / 2;
        int count = Math.min(MAX_WORMHOLES, maxPossibleWormholes);

        for (int i = 0; i < count * 2; i += 2) {
            Position from = new Position(positions.get(i));
            Position to = new Position(positions.get(i + 1));

            wormholes.put(from, to);
        }
        return wormholes;
    }

    private Set<Integer> getExcludedPositions(List<Player> players) {
        Set<Integer> excluded = new HashSet<>();

        for (Player player : players) {
            excluded.add(player.getStartPos().value());
            excluded.add(player.getEndPos().value());
        }
        return excluded;
    }

    private List<Integer> getAvailablePositions(int boardSize, Set<Integer> excluded) {
        List<Integer> positions = new ArrayList<>();

        for (int i = 1; i <= boardSize; i++) {
            if (!excluded.contains(i)) {
                positions.add(i);
            }
        }
        return positions;
    }
}