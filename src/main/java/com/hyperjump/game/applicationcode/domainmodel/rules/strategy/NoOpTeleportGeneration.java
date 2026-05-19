package com.hyperjump.game.applicationcode.domainmodel.rules.strategy;

import com.hyperjump.game.applicationcode.domainmodel.player.Player;
import com.hyperjump.game.applicationcode.domainmodel.shared.DomainException;
import com.hyperjump.game.applicationcode.domainmodel.value.Position;

import java.util.List;
import java.util.Map;

public class NoOpTeleportGeneration implements TeleportGenerationStrategy {

    @Override
    public Map<Position, Position> generate(int boardSize, List<Player> players) {
        throw new DomainException("Teleport generation should not be used during replay");
    }
}