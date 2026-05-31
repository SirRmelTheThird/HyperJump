package com.hyperjump.game.applicationcode.domainmodel.rules;

import com.hyperjump.game.applicationcode.domainmodel.movement.Movement;
import com.hyperjump.game.applicationcode.domainmodel.movement.TeleportVariationDecorator;
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

    public TeleportRule(Map<Position, Position> wormholeMap) {
        this.wormholeMap = wormholeMap;
    }

    public Position getDestination(Position position) {
        return wormholeMap.getOrDefault(position, position);
    }

    @Override
    public Movement decorate(Movement movement) {
        return new TeleportVariationDecorator(movement, this);
    }

    @Override
    public GameRule rebuild(List<Player> players) {
        return new TeleportRule(wormholeMap);
    }

    @Override
    public String getDescription() {
        return "Teleport: Player is teleported to the other end of the wormhole, Wormholes: " + wormholeMap;
    }
}