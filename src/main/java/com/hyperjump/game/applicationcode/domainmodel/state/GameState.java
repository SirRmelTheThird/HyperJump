package com.hyperjump.game.applicationcode.domainmodel.state;

import com.hyperjump.game.applicationcode.domainmodel.player.Player;
import com.hyperjump.game.applicationcode.domainmodel.player.PlayerTurn;

public interface GameState {
    void handle(Player currentPlayer, PlayerTurn playerTurn);
    boolean isGameOver();
    GameState nextState();
}
