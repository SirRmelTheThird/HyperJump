package com.hyperjump.game.applicationcode.domainmodel.state.states;

import com.hyperjump.game.applicationcode.domainmodel.player.Player;
import com.hyperjump.game.applicationcode.domainmodel.state.GameContext;
import com.hyperjump.game.applicationcode.domainmodel.state.GameState;

public class GameOverState implements GameState {

    private final GameContext context;
    private final Player winner;

    public GameOverState(GameContext context, Player winner) {
        this.context = context;
        this.winner = winner;
    }

    @Override
    public void turn() {
    }

    @Override
    public boolean isGameOver() {
        return true;
    }

    public Player getWinner() {
        return winner;
    }
}