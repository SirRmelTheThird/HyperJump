package com.hyperjump.game.applicationcode.domainmodel.state.states;

import com.hyperjump.game.applicationcode.domainmodel.player.PlayerTurn;
import com.hyperjump.game.applicationcode.domainmodel.state.GameContext;
import com.hyperjump.game.applicationcode.domainmodel.state.GameState;

public class ReadyState implements GameState {

    private final GameContext context;
    private final PlayerTurn playerTurn;

    public ReadyState(GameContext context, PlayerTurn playerTurn) {
        this.context = context;
        this.playerTurn = playerTurn;
    }

    @Override
    public void turn() {
        GameState nextState = new InPlayState(context, playerTurn);
        context.setGameState(nextState);
        nextState.turn();
    }
    @Override
    public boolean isGameOver() {
        return false;
    }
}