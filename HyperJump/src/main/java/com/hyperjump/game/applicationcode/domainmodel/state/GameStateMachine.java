package com.hyperjump.game.applicationcode.domainmodel.state;

import com.hyperjump.game.applicationcode.domainmodel.player.PlayerTurn;
import com.hyperjump.game.applicationcode.domainmodel.state.states.ReadyState;

public class GameStateMachine implements GameContext {

    private GameState gameState;

    public GameStateMachine(PlayerTurn playerTurn) {
        this.gameState = new ReadyState(this, playerTurn);
    }

    @Override
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public void play() {
        while (!gameState.isGameOver()) {
            gameState.turn();
        }
        gameState.turn();
    }
}