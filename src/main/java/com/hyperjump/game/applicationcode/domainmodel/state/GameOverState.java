package com.hyperjump.game.applicationcode.domainmodel.state;

import com.hyperjump.game.applicationcode.domainmodel.player.Player;
import com.hyperjump.game.applicationcode.domainmodel.player.PlayerTurn;

public class GameOverState implements GameState {

    private final Player winner;

    public GameOverState(Player winner) {
        this.winner = winner;
    }

    @Override
    public void handle(Player currentPlayer, PlayerTurn playerTurn) {
    }

    @Override
    public boolean isGameOver() {
        return true;
    }

    @Override
    public GameState nextState() {
        return this;
    }

    public Player getWinner() {
        return winner;
    }
}