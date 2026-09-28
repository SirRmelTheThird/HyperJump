package com.hyperjump.game.applicationcode.domainmodel.rules.strategy;

import com.hyperjump.game.applicationcode.domainmodel.player.Player;
import com.hyperjump.game.applicationcode.domainmodel.value.Position;

import java.util.List;
import java.util.Map;

public interface TeleportGenerationStrategy {
    Map<Position, Position> generate(int boardSize, List<Player> players);
}