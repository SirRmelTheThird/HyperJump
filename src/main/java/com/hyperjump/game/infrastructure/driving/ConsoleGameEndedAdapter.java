package com.hyperjump.game.infrastructure.driving;

import com.hyperjump.game.applicationcode.port.out.GameEndedObserverPort;

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
        display.show("");
    }
}