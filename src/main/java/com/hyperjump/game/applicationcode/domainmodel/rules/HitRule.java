package com.hyperjump.game.applicationcode.domainmodel.rules;

import com.hyperjump.game.applicationcode.domainmodel.movement.HitVariationDecorator;
import com.hyperjump.game.applicationcode.domainmodel.movement.Movement;
import com.hyperjump.game.applicationcode.domainmodel.player.Player;
import com.hyperjump.game.applicationcode.domainmodel.replay.SavedRule;
import com.hyperjump.game.applicationcode.port.out.GameRule;
import com.hyperjump.game.applicationcode.domainmodel.rules.strategy.HitStrategy;
import com.hyperjump.game.applicationcode.domainmodel.rules.strategy.SamePositionHit;
import com.hyperjump.game.applicationcode.domainmodel.value.Position;

import java.util.List;

public class HitRule implements GameRule {

    private final HitStrategy strategy;

    public HitRule(HitStrategy strategy) {
        this.strategy = strategy;
    }

    public Player getHitPlayer(Player currentPlayer, Position position) {
        return strategy.getHitPlayer(currentPlayer, position);
    }

    @Override
    public Movement decorate(Movement movement) {
        return new HitVariationDecorator(movement, this);
    }

    @Override
    public SavedRule toSavedRule() {
        return new SavedRule("HitRule");
    }

    @Override
    public GameRule rebuild(List<Player> players) {
        return new HitRule(new SamePositionHit(players));
    }

    @Override
    public String getDescription() {
        return "Hit: Player's turn is forfeit if the player would HIT another player";
    }
}