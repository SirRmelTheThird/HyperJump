package com.hyperjump.game.applicationcode.domainmodel.player;

import java.util.List;

public class RoundRobinPlayerSelector implements PlayerSelector {

    private final List<Player> players;
    private int currentIndex;
    private int count;

    public RoundRobinPlayerSelector(List<Player> players) {
        this.players = players;
        this.currentIndex = 0;
        this.count = 0;
    }

    @Override
    public int size() {
        return players.size();
    }

    @Override
    public Player getCurrentPlayer() {
        return players.get(currentIndex);
    }

    @Override
    public int getPlayerTurns() {
        return (count - 1) / players.size() + 1;
    }

    @Override
    public void next() {
        currentIndex = (currentIndex + 1) % players.size();
    }

    @Override
    public int getAllTurnsCount() {
        return count;
    }

    @Override
    public void hasTakenATurn() {
        count++;
    }

}