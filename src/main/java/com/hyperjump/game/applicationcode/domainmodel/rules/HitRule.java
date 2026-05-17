package com.hyperjump.game.applicationcode.domainmodel.rules;

import com.hyperjump.game.applicationcode.domainmodel.player.Player;
import com.hyperjump.game.applicationcode.port.out.GameRule;
import com.hyperjump.game.applicationcode.domainmodel.rules.strategy.HitStrategy;
import com.hyperjump.game.applicationcode.domainmodel.value.Position;

public class HitRule implements GameRule {

    private final HitStrategy strategy;

    public HitRule(HitStrategy strategy) {
        this.strategy = strategy;
    }

    public Player getHitPlayer(Player currentPlayer, Position position) {
        return strategy.getHitPlayer(currentPlayer, position);
    }

    @Override
    public String getDescription() {
        return "Hit: Player's turn is forfeit if the player would HIT another player";
    }
}
