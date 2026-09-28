package com.hyperjump.game.applicationcode.domainmodel.movement.events;

import com.hyperjump.game.applicationcode.domainmodel.player.Player;

public class TeleportEvent implements GameEvent {

    @Override
    public String describe(Player player) {
        return player.getColour() + " is teleported ";
    }
}