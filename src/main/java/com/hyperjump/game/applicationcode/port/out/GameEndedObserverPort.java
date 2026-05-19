package com.hyperjump.game.applicationcode.port.out;

import com.hyperjump.game.applicationcode.domainmodel.player.Player;
import com.hyperjump.game.applicationcode.domainmodel.replay.SavedGame;

public interface GameEndedObserverPort {

    void onGameEnded(GameEndInfo info);

    record GameEndInfo(
            Player winner,
            int totalTurns,
            SavedGame savedGame
    ) {}
}