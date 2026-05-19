package com.hyperjump.game.infrastructure.driving;

import com.hyperjump.game.applicationcode.domainmodel.gameenum.GameMode;
import com.hyperjump.game.applicationcode.domainmodel.value.DiceRoll;
import com.hyperjump.game.applicationcode.port.out.GameEndedObserverPort;

import java.util.stream.Collectors;

public class ConsoleGameEndedAdapter implements GameEndedObserverPort {

    private final DisplayPort display;

    public ConsoleGameEndedAdapter(DisplayPort display) {
        this.display = display;
    }

    @Override
    public void onGameEnded(GameEndInfo info) {
        display.show(info.winner().getColour() + " wins! in " + info.totalTurns() + " turns");
        display.show("Total turns: " + info.totalTurns());
        display.show("Game State: InPlay → GameOver");

        if (info.mode() == GameMode.NORMAL && info.savedGame() != null) {
            display.show(formatSavedGame(info));
        }

        display.show("");
    }

    private String formatSavedGame(GameEndInfo info) {
        String rolls = info.savedGame().getDiceRolls()
                .stream()
                .map(DiceRoll::toString)
                .collect(Collectors.joining(","));

        return "Dice rolls: { " + rolls + " }\n" + "Game Id: " + info.savedGame().getId() + " saved.";
    }
}