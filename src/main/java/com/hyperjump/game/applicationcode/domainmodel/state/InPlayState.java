package com.hyperjump.game.applicationcode.domainmodel.state;

import com.hyperjump.game.applicationcode.domainmodel.player.Player;
import com.hyperjump.game.applicationcode.domainmodel.player.PlayerTurn;

public class InPlayState implements GameState {

    private GameState nextState = this;

    @Override
    public void handle(Player currentPlayer, PlayerTurn playerTurn) {

        if (!currentPlayer.isWinner()) {
            return;
        }

        nextState = new GameOverState(currentPlayer);
    }

    @Override
    public boolean isGameOver() {
        return nextState instanceof GameOverState;
    }

    @Override
    public GameState nextState() {
        return nextState;
    }
}