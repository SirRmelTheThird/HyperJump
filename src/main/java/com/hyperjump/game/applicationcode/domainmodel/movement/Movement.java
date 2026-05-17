package com.hyperjump.game.applicationcode.domainmodel.movement;

import com.hyperjump.game.applicationcode.domainmodel.player.Player;
import com.hyperjump.game.applicationcode.domainmodel.value.DiceRoll;

public interface Movement {
    TurnOutcome move(Player player, DiceRoll roll);
}
