package com.hyperjump.game.infrastructure.driving;

import com.hyperjump.game.applicationcode.domainmodel.replay.SavedGame;
import com.hyperjump.game.applicationcode.domainmodel.value.DiceRoll;
import com.hyperjump.game.applicationcode.port.out.DisplayPort;
import com.hyperjump.game.applicationcode.port.out.GameSavedObserverPort;

import java.util.stream.Collectors;

public class ConsoleGameSavedAdapter implements GameSavedObserverPort {

    private final DisplayPort display;

    public ConsoleGameSavedAdapter(DisplayPort display) {
        this.display = display;
    }

    @Override
    public void onGameSaved(SavedGame savedGame) {
        String rolls = savedGame.getDiceRolls().stream()
                .map(DiceRoll::toString)
                .collect(Collectors.joining(", "));
        display.show("Dice rolls: {" + rolls + "}");
        display.show("Game Id: " + savedGame.getId() + " saved.");
        display.show("");
    }
}