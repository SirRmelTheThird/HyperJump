package com.hyperjump.game.infrastructure.driving;

import com.hyperjump.game.applicationcode.domainmodel.player.PlayerTurn;
import com.hyperjump.game.applicationcode.port.out.GameOverObserverPort;

public class GameOverDisplayAdapter implements GameOverObserverPort {

    private final DisplayPort display;

    public GameOverDisplayAdapter(DisplayPort display) {
        this.display = display;
    }

    @Override
    public void onGameOver(PlayerTurn playerTurn) {
        display.show(buildWinnerMessage(playerTurn));
        display.show("Total turns: " + playerTurn.getTotalTurns());
        display.show("Game State: InPlayState → GameOverState");
        display.show("");
    }

    private String buildWinnerMessage(PlayerTurn playerTurn) {
        return playerTurn.getWinner().getColour() + " wins! in " + playerTurn.getTurns() + " turns";
    }
}
