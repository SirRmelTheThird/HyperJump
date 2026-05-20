package com.hyperjump.game.infrastructure.driving;

import com.hyperjump.game.applicationcode.domainmodel.replay.SavedGame;
import com.hyperjump.game.applicationcode.port.out.DisplayPort;
import com.hyperjump.game.applicationcode.port.out.GameRunnerObserverPort;

public class ConsoleGameRunnerDisplayAdapter implements GameRunnerObserverPort {

    private final DisplayPort display;

    public ConsoleGameRunnerDisplayAdapter(DisplayPort display) {
        this.display = display;
    }

    @Override
    public void onGameRunStarted(int gameNumber) {
        display.show("\n=== GAME " + gameNumber + " ===\n");
    }

    @Override
    public void onReplayRunStarted() {
        display.show("\n=== REPLAYING SAVED GAMES ===\n");
    }

    @Override
    public void onReplaySelected(SavedGame savedGame) {
        display.show("\n=== REPLAY GAME " + savedGame.getId() + " ===\n");
    }

    @Override
    public void onReplayNotFound(int gameId) {
        display.show("No saved game found with id: " + gameId);
    }
}