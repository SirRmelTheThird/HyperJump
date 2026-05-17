package com.hyperjump.game.applicationcode.domainmodel.player;

public interface PlayerSelector {
    int size();
    int getAllTurnsCount();
    Player getCurrentPlayer();
    int getPlayerTurns();
    void next();
    void hasTakenATurn();
}
