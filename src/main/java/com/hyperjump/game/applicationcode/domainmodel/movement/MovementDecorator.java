package com.hyperjump.game.applicationcode.domainmodel.movement;

import com.hyperjump.game.applicationcode.domainmodel.player.Player;
import com.hyperjump.game.applicationcode.domainmodel.value.DiceRoll;

public abstract class MovementDecorator implements Movement {

    protected final Movement wrapped;

    public MovementDecorator(Movement wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public TurnOutcome move(Player player, DiceRoll roll) {
        return wrapped.move(player, roll);
    }
}