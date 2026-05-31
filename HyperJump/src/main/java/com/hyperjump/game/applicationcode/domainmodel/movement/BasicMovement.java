package com.hyperjump.game.applicationcode.domainmodel.movement;

import com.hyperjump.game.applicationcode.domainmodel.movement.events.ExactEndEvent;
import com.hyperjump.game.applicationcode.domainmodel.movement.events.MoveEvent;
import com.hyperjump.game.applicationcode.domainmodel.player.Player;
import com.hyperjump.game.applicationcode.domainmodel.value.DiceRoll;
import com.hyperjump.game.applicationcode.domainmodel.value.Position;

public class BasicMovement implements Movement {

    @Override
    public TurnOutcome move(Player player, DiceRoll roll) {
        Position previous = player.getCurrentPos();

        int newIndex = player.calculateMoveIndex(roll.total());
        int maxIndex = player.getPath().size() - 1;

        boolean overshoot = newIndex > maxIndex;

        player.moveToIndex(Math.min(newIndex, maxIndex));

        Position current = player.getCurrentPos();

        TurnOutcome outcome = new TurnOutcome(previous, current).withEvent(new MoveEvent(previous, current));
        if (overshoot) return outcome.withEvent(new ExactEndEvent());

        return outcome;
    }
}