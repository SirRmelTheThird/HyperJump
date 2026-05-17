package com.hyperjump.game.applicationcode.usecase;

import com.hyperjump.game.applicationcode.domainmodel.board.BoardFactory;
import com.hyperjump.game.applicationcode.domainmodel.movement.BasicMovement;
import com.hyperjump.game.applicationcode.domainmodel.movement.Movement;
import com.hyperjump.game.applicationcode.domainmodel.movement.strategy.ExactEndVariation;
import com.hyperjump.game.applicationcode.domainmodel.movement.strategy.HitVariation;
import com.hyperjump.game.applicationcode.domainmodel.movement.strategy.MovementDecoratorStrategy;
import com.hyperjump.game.applicationcode.domainmodel.movement.strategy.TeleportVariation;
import com.hyperjump.game.applicationcode.domainmodel.player.Player;
import com.hyperjump.game.applicationcode.port.out.Board;
import com.hyperjump.game.applicationcode.port.out.GameRule;
import com.hyperjump.game.applicationcode.port.out.GameStartObserverPort;
import com.hyperjump.game.applicationcode.port.out.GameRulesObserverPort;
import com.hyperjump.game.applicationcode.port.out.PathFactory;
import com.hyperjump.game.applicationcode.domainmodel.rules.ExactEndRule;
import com.hyperjump.game.applicationcode.domainmodel.rules.HitRule;
import com.hyperjump.game.applicationcode.domainmodel.rules.TeleportRule;
import com.hyperjump.game.applicationcode.domainmodel.rules.strategy.RandomTeleportGeneration;
import com.hyperjump.game.applicationcode.domainmodel.rules.strategy.SamePositionHit;
import com.hyperjump.game.applicationcode.domainmodel.state.InPlayState;

import java.util.ArrayList;
import java.util.List;

public class GameSessionUseCase {

    private static final int PLAYER_COUNT = 2;

    private final Board boardProvider;
    private final InitialisePlayerUseCase playerSetup;
    private final InitialiseRulesUseCase  rulesSetup;
    private final PathFactory pathFactory;
    private final List<GameStartObserverPort> observers = new ArrayList<>();
    private final List<GameRulesObserverPort> rulesObservers;

    private BoardFactory board;

    public GameSessionUseCase(Board boardProvider, InitialisePlayerUseCase playerSetup, InitialiseRulesUseCase rulesSetup, PathFactory pathFactory, List<GameRulesObserverPort> rulesObservers) {
        this.boardProvider = boardProvider;
        this.playerSetup = playerSetup;
        this.rulesSetup = rulesSetup;
        this.pathFactory = pathFactory;
        this.rulesObservers = rulesObservers;
    }

    public void setupGame() {
        board = boardProvider.createBoard(PLAYER_COUNT);
        playerSetup.setupPlayers(PLAYER_COUNT, board, pathFactory);
    }

    public void startGame() {
        List<GameRule> selectedRules = selectRules();
        notifyRulesSelected(selectedRules);
        notifyGameStarted(playerSetup.getPlayers());
        Movement movement = buildMovement(selectedRules);
        playerSetup.startTurns(movement, new InPlayState());
    }

    public void addObserver(GameStartObserverPort observer) {
        observers.add(observer);
    }

    private List<GameRule> selectRules() {
        return rulesSetup.setupRules(board.getSize(), playerSetup.getPlayers());
    }

    private Movement buildMovement(List<GameRule> selectedRules) {
        Movement movement = new BasicMovement();

        List<MovementDecoratorStrategy> strategies = List.of(
            new TeleportVariation(),
            new ExactEndVariation(),
            new HitVariation()
        );

        for (GameRule rule : selectedRules) {
            for (MovementDecoratorStrategy strategy : strategies) {
                if (strategy.supports(rule)) {
                    movement = strategy.decorate(movement, rule);
                }
            }
        }

        return movement;
    }

    private void notifyRulesSelected(List<GameRule> rules) {
        for (GameRulesObserverPort observer : rulesObservers) {
            observer.onRulesSelected(rules);
        }
    }

    private void notifyGameStarted(List<Player> players) {
        for (GameStartObserverPort observer : observers) {
            observer.onGameStarted(players);
        }
    }
}
