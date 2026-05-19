package com.hyperjump.game.applicationcode.domainmodel.movement;

import com.hyperjump.game.applicationcode.domainmodel.movement.events.TeleportEvent;
import com.hyperjump.game.applicationcode.domainmodel.player.Player;
import com.hyperjump.game.applicationcode.domainmodel.rules.TeleportRule;
import com.hyperjump.game.applicationcode.domainmodel.value.DiceRoll;
import com.hyperjump.game.applicationcode.domainmodel.value.Position;

public class TeleportVariationDecorator extends MovementDecorator {

    private final TeleportRule teleportRule;

    public TeleportVariationDecorator(Movement wrapped, TeleportRule teleportRule) {
        super(wrapped);
        this.teleportRule = teleportRule;
    }

    @Override
    public TurnOutcome move(Player player, DiceRoll roll) {
        TurnOutcome  result = wrapped.move(player, roll);
        Position currentPosition = result.getEndPosition();
        Position destination = teleportRule.getDestination(result.getEndPosition());

        if (destination.value() == result.getEndPosition().value()) {
            return result;
        }
        player.teleportTo(destination);
        result.updateEndPosition(destination);
        result.addEvent(new TeleportEvent(currentPosition, destination));
        return result;
    }
}