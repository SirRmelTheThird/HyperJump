package com.hyperjump.game.applicationcode.domainmodel.rules.strategy;

import com.hyperjump.game.applicationcode.domainmodel.player.Player;
import com.hyperjump.game.applicationcode.domainmodel.value.Position;

import java.util.List;

public class SamePositionHit implements HitStrategy {

    private final List<Player> players;

    public SamePositionHit(List<Player> players) {
        this.players = players;
    }

    @Override
    public Player getHitPlayer(Player currentPlayer, Position position) {

        for (Player other : players) {
            if (isHit(currentPlayer, other, position)) {
                return other;
            }
        }
        return null;
    }

    private boolean isHit(Player currentPlayer, Player other, Position position) {
        return other != currentPlayer && other.getCurrentPos().value() == position.value();
    }
}