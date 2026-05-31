package com.hyperjump.game.applicationcode.port.out;

import com.hyperjump.game.applicationcode.domainmodel.movement.Movement;
import com.hyperjump.game.applicationcode.domainmodel.player.Player;

import java.util.List;

public interface GameRule {
    String getDescription();
    GameRule rebuild(List<Player> players);
    Movement decorate(Movement movement);
}