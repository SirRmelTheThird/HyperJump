package com.hyperjump.game.applicationcode.domainmodel.movement.events;

import com.hyperjump.game.applicationcode.domainmodel.player.Player;

public interface GameEvent {
    String describe(Player player);
}