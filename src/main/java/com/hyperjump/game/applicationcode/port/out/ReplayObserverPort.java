package com.hyperjump.game.applicationcode.port.out;

import com.hyperjump.game.applicationcode.domainmodel.replay.SavedGame;

public interface ReplayObserverPort {
    void onReplayStarted(SavedGame savedGame);

    default void onReplayNotFound(int gameId) {}
}