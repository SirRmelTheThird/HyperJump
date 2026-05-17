package com.hyperjump.game.applicationcode.domainmodel.rules;

import com.hyperjump.game.applicationcode.domainmodel.player.Player;
import com.hyperjump.game.applicationcode.port.out.GameRule;
import com.hyperjump.game.applicationcode.domainmodel.rules.strategy.TeleportGenerationStrategy;
import com.hyperjump.game.applicationcode.domainmodel.value.Position;

import java.util.List;
import java.util.Map;

public class TeleportRule implements GameRule {

    private final Map<Position, Position> wormholeMap;

    public TeleportRule(int boardSize, List<Player> players, TeleportGenerationStrategy strategy) {
        this.wormholeMap = strategy.generate(boardSize, players);
    }

    public Position getDestination(Position position) {
        return wormholeMap.getOrDefault(position, position);
    }

    public Map<Position, Position> getWormholeMap() {
        return wormholeMap;
    }

    @Override
    public String getDescription() {
        return "Teleport: Player is teleported to the other end of the wormhole, Wormholes: " + getWormholeMap();
    }
}
