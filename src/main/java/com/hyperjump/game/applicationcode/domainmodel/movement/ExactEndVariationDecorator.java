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
        int bouncedIndex = exactEndRule.getBounceIndex(player, result.getNewIndex());

        if (bouncedIndex == -1) {
            return result;
        }

        player.moveToIndex(bouncedIndex);
        result.updateEndPosition(player.getCurrentPos());
        result.addEvent(new ExactEndEvent());

        return result;
    }
}
