package com.hyperjump.game.applicationcode.domainmodel.rules;

import com.hyperjump.game.applicationcode.domainmodel.movement.ExactEndVariationDecorator;
import com.hyperjump.game.applicationcode.domainmodel.movement.Movement;
import com.hyperjump.game.applicationcode.domainmodel.player.Player;
import com.hyperjump.game.applicationcode.port.out.GameRule;

import java.util.List;

public class ExactEndRule implements GameRule {

    public int getBounceIndex(Player player, int newIndex) {
        int maxIndex = player.getPath().size() - 1;
        if (newIndex <= maxIndex) return -1;
        while (newIndex > maxIndex) {
            newIndex = maxIndex - (newIndex - maxIndex);
        }
        return Math.max(0, newIndex);
    }

    @Override
    public Movement decorate(Movement movement) {
        return new ExactEndVariationDecorator(movement, this);
    }

    @Override
    public GameRule rebuild(List<Player> players) {
        return new ExactEndRule();
    }

    @Override
    public String getDescription() {
        return "Exact End: Player must land exactly on the END position to win else the player bounces back";
    }
}