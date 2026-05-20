package com.hyperjump.game.applicationcode.domainmodel.state.states;

import com.hyperjump.game.applicationcode.domainmodel.player.Player;
import com.hyperjump.game.applicationcode.domainmodel.player.PlayerTurn;
import com.hyperjump.game.applicationcode.domainmodel.state.GameContext;
import com.hyperjump.game.applicationcode.domainmodel.state.GameState;

public class InPlayState implements GameState {

    private final GameContext context;

    private final PlayerTurn playerTurn;

    private static final int MAX_TURNS = 100;

    public InPlayState(GameContext context, PlayerTurn playerTurn) {
        this.context = context;
        this.playerTurn = playerTurn;
    }

    @Override
    public void turn() {
        if (playerTurn.getTurns() >= MAX_TURNS) {
            context.setGameState(new GameOverState(context));
            return;
        }
        playerTurn.playTurn();
        Player winner = playerTurn.getWinner();
        if (winner != null) context.setGameState(new GameOverState(context));
    }

    @Override
    public boolean isGameOver() {
        return false;
    }
}