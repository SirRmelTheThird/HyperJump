package com.hyperjump.game.applicationcode.domainmodel.movement.events;

import com.hyperjump.game.applicationcode.domainmodel.player.Player;
import com.hyperjump.game.applicationcode.domainmodel.value.Position;

public class TeleportEvent implements GameEvent {

    private final Position from;
    private final Position to;

    public TeleportEvent(Position from, Position to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public String describe(Player player) {
        return player.getColour() + " has teleported from Position " + from.value() + " to Position " + to.value();
    }
}