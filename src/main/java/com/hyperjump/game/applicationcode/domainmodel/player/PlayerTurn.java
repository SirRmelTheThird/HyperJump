package com.hyperjump.game.applicationcode.domainmodel.player;

import com.hyperjump.game.applicationcode.domainmodel.movement.Movement;
import com.hyperjump.game.applicationcode.domainmodel.movement.TurnOutcome;
import com.hyperjump.game.applicationcode.domainmodel.state.GameOverState;
import com.hyperjump.game.applicationcode.port.out.GameOverObserverPort;
import com.hyperjump.game.applicationcode.port.out.PlayerTurnObserverPort;
import com.hyperjump.game.applicationcode.domainmodel.state.GameState;
import com.hyperjump.game.applicationcode.domainmodel.value.DiceRoll;

import java.util.ArrayList;
import java.util.List;

public class PlayerTurn {

    private final PlayerSelector playerSelector;
    private final Movement movement;

    private GameState state;

    private DiceRoll roll;
    private TurnOutcome turnOutcome;

    private final List<PlayerTurnObserverPort> turnObservers = new ArrayList<>();
    private final List<GameOverObserverPort> gameOverObservers = new ArrayList<>();

    public PlayerTurn(PlayerSelector playerSelector, Movement movement, GameState state) {

        this.playerSelector = playerSelector;
        this.movement = movement;
        this.state = state;
    }

    public void addObserver(PlayerTurnObserverPort observer) {
        turnObservers.add(observer);
    }

    public void addGameOverObserver(GameOverObserverPort observer) {
        gameOverObservers.add(observer);
    }

    public void play() {
        while (!state.isGameOver()) {
            playTurn();
        }
    }

    public void playTurn() {

        Player currentPlayer = playerSelector.getCurrentPlayer();

        roll = currentPlayer.roll();
        turnOutcome = movement.move(currentPlayer, roll);

        playerSelector.hasTakenATurn();

        notifyTurnPlayed();

        updateState(currentPlayer);

        if (state.isGameOver()) {
            notifyGameOver();
            return;
        }
        playerSelector.next();
    }

    private void updateState(Player currentPlayer) {
        state.handle(currentPlayer, this);
        state = state.nextState();
    }

    public Player getWinner() {
        if (state instanceof GameOverState gameOverState) {
            return gameOverState.getWinner();
        }
        return null;
    }


    private void notifyTurnPlayed() {
        for (PlayerTurnObserverPort observer : turnObservers) {
            observer.onTurnPlayed(this);
        }
    }

    private void notifyGameOver() {
        for (GameOverObserverPort observer : gameOverObservers) {
            observer.onGameOver(this);
        }
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