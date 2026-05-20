package com.hyperjump.game.applicationcode.domainmodel.state.states;

import com.hyperjump.game.applicationcode.domainmodel.player.Player;
import com.hyperjump.game.applicationcode.domainmodel.state.GameContext;
import com.hyperjump.game.applicationcode.domainmodel.state.GameState;

public class GameOverState implements GameState {

    private final GameContext context;

    public GameOverState(GameContext context) {
        this.context = context;
    }

    @Override
    public void turn() {
    }

    @Override
    public boolean isGameOver() {
        return true;
    }

}