package com.hyperjump.game.applicationcode.domainmodel.movement;

import com.hyperjump.game.applicationcode.domainmodel.movement.events.ExactEndEvent;
import com.hyperjump.game.applicationcode.domainmodel.player.Player;
import com.hyperjump.game.applicationcode.domainmodel.rules.ExactEndRule;
import com.hyperjump.game.applicationcode.domainmodel.value.DiceRoll;

public class ExactEndVariationDecorator extends MovementDecorator {

    private final ExactEndRule exactEndRule;

    public ExactEndVariationDecorator(Movement wrapped, ExactEndRule exactEndRule) {
        super(wrapped);
        this.exactEndRule = exactEndRule;
    }

    @Override
    public TurnOutcome move(Player player, DiceRoll roll) {
        TurnOutcome  result = wrapped.move(player, roll);

        int previousIndex = player.getPath().indexOf(result.getPreviousPosition());
        int newIndex = previousIndex + roll.total();

        int bouncedIndex = exactEndRule.getBounceIndex(player, newIndex);

        if (bouncedIndex == -1) {
            return result;
        }

        player.moveToIndex(bouncedIndex);
        return result.withEndPosition(player.getCurrentPos());
    }
}
