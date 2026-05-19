package com.hyperjump.game.applicationcode.domainmodel.movement;


import com.hyperjump.game.applicationcode.domainmodel.player.Player;
import com.hyperjump.game.applicationcode.domainmodel.value.DiceRoll;
import com.hyperjump.game.applicationcode.domainmodel.value.Position;

public class BasicMovement implements Movement {

    @Override
    public TurnOutcome move(Player player, DiceRoll roll) {
        Position previous = player.getCurrentPos();

        int newIndex = player.calculateMoveIndex(roll.total());
        int maxIndex = player.getPath().size() - 1;

        player.moveToIndex(Math.min(newIndex, maxIndex));

        Position current = player.getCurrentPos();

        return new TurnOutcome (previous, current);
    }
}