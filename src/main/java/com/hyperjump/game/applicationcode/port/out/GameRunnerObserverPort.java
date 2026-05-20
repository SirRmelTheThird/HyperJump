package com.hyperjump.game.applicationcode.port.out;

import com.hyperjump.game.applicationcode.domainmodel.replay.SavedGame;

public interface GameRunnerObserverPort {
    void onGameRunStarted(int gameNumber);
    void onReplayRunStarted();
    void onReplaySelected(SavedGame savedGame);
    default void onReplayNotFound(int gameId) {}
}