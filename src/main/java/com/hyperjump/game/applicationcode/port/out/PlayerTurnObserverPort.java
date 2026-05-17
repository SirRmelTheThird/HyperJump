package com.hyperjump.game.applicationcode.port.out;

import com.hyperjump.game.applicationcode.domainmodel.player.PlayerTurn;

public interface PlayerTurnObserverPort {
    void onTurnPlayed(PlayerTurn playerTurn);
}
