package com.hyperjump.game.applicationcode.domainmodel.state;

public interface GameState {
    void turn();
    boolean isGameOver();
}