package com.hyperjump.game.applicationcode.domainmodel.rules.strategy;

import com.hyperjump.game.applicationcode.domainmodel.player.Player;
import com.hyperjump.game.applicationcode.domainmodel.value.Position;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FixedTeleportGeneration implements TeleportGenerationStrategy {

    private static final int[][] PAIRS = {
            {4, 9},
            {18, 15}
    };

    @Override
    public Map<Position, Position> generate(int boardSize, List<Player> players) {
        Map<Position, Position> wormholes = new HashMap<>();
        for (int[] pair : PAIRS) {
            wormholes.put(new Position(pair[0]), new Position(pair[1]));
        }
        return wormholes;
    }
}