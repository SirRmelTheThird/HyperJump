package com.hyperjump.game.infrastructure.driving;

import com.hyperjump.game.applicationcode.domainmodel.replay.SavedGame;
import com.hyperjump.game.applicationcode.port.out.ReplayObserverPort;

public class ConsoleReplayAdapter implements ReplayObserverPort {

    private final DisplayPort display;

    public ConsoleReplayAdapter(DisplayPort display) {
        this.display = display;
    }

    @Override
    public void onReplayStarted(SavedGame savedGame) {
        display.show("\n=== REPLAY GAME ID: " + savedGame.getId() + " ===\n"
        );
    }

    @Override
    public void onReplayNotFound(int gameId) {
        display.show("No saved game found with id: " + gameId
        );
    }
}