package com.hyperjump.game.applicationcode.domainmodel.movement.events;

import com.hyperjump.game.applicationcode.domainmodel.player.Player;
import com.hyperjump.game.applicationcode.domainmodel.value.Position;

public class HitEvent implements GameEvent {

    private final String hitPlayerColor;
    private final Position hitPosition;

    public HitEvent(String hitPlayerColor, Position hitPosition) {
        this.hitPlayerColor = hitPlayerColor;
        this.hitPosition = hitPosition;
    }

    @Override
    public String describe(Player player) {
        return player.getColour() + " hits " + hitPlayerColor + " at Position " + hitPosition.value();
    }
}