package com.hyperjump.game.applicationcode.domainmodel.movement.events;

import com.hyperjump.game.applicationcode.domainmodel.player.Player;
import com.hyperjump.game.applicationcode.domainmodel.value.Position;

public class MoveEvent implements GameEvent {

    private final Position from;
    private final Position to;

    public MoveEvent(Position from, Position to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public String describe(Player player) {
        return player.getColour() + " moves from " + from.value() + " to " + to.value();
    }
}