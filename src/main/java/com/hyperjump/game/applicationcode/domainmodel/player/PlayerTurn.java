package com.hyperjump.game.applicationcode.domainmodel.player;

import com.hyperjump.game.applicationcode.domainmodel.movement.Movement;
import com.hyperjump.game.applicationcode.domainmodel.movement.TurnOutcome;
import com.hyperjump.game.applicationcode.domainmodel.value.DiceRoll;
import com.hyperjump.game.applicationcode.port.out.TurnObserverPort;

import java.util.ArrayList;
import java.util.List;

public class PlayerTurn {

    private final PlayerSelector playerSelector;
    private final Movement movement;

    private DiceRoll roll;
    private TurnOutcome turnOutcome;
    private Player winner;

    private final List<TurnObserverPort> turnObservers = new ArrayList<>();

    public PlayerTurn(PlayerSelector playerSelector, Movement movement) {
        this.playerSelector = playerSelector;
        this.movement = movement;
    }

    public void playTurn() {
        Player currentPlayer = playerSelector.getCurrentPlayer();

        roll = currentPlayer.roll();
        turnOutcome = movement.move(currentPlayer, roll);

        playerSelector.hasTakenATurn();

        notifyTurnPlayed();

        if (updateWinner(currentPlayer)) {
            return;
        }

        playerSelector.next();
    }

    private boolean updateWinner(Player player) {
        if (player.isWinner()) {
            winner = player;
            return true;
        }

        return false;
    }

    public void addObserver(TurnObserverPort observer) {
        turnObservers.add(observer);
    }

    private void notifyTurnPlayed() {
        for (TurnObserverPort observer : turnObservers) {
            observer.onTurnPlayed(this);
        }
    }

    public Player getWinner() {
        return winner;
    }

    public int getTurns() {
        return playerSelector.getPlayerTurns();
    }

    public int getTotalTurns() {
        return playerSelector.getAllTurnsCount();
    }

    public Player getCurrentPlayer() {
        return playerSelector.getCurrentPlayer();
    }

    public DiceRoll getRoll() {
        return roll;
    }

    public TurnOutcome getVariation() {
        return turnOutcome;
    }
}