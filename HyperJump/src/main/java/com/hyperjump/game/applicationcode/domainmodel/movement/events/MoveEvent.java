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
        return player.getColour() + " moves from " + formatPosition(player, from) + " to " + formatPosition(player, to);
    }

    private String formatPosition(Player player, Position position) {
        if (position.equals(player.getStartPos())) {
            return "Home (Position " + position + ")";
        }
        if (position.equals(player.getEndPos())) {
            return "End (Position " + position + ")";
        }
        return String.valueOf(position.value());
    }
}