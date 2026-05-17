package com.hyperjump.game.applicationcode.usecase;

import com.hyperjump.game.applicationcode.domainmodel.board.BoardFactory;
import com.hyperjump.game.applicationcode.domainmodel.movement.BasicMovement;
import com.hyperjump.game.applicationcode.domainmodel.movement.Movement;
import com.hyperjump.game.applicationcode.domainmodel.movement.strategy.ExactEndVariation;
import com.hyperjump.game.applicationcode.domainmodel.movement.strategy.HitVariation;
import com.hyperjump.game.applicationcode.domainmodel.movement.strategy.MovementDecoratorStrategy;
import com.hyperjump.game.applicationcode.domainmodel.movement.strategy.TeleportVariation;
import com.hyperjump.game.applicationcode.domainmodel.player.Player;
import com.hyperjump.game.applicationcode.domainmodel.state.InPlayState;
import com.hyperjump.game.applicationcode.port.in.StartGameUseCase;
import com.hyperjump.game.applicationcode.port.out.Board;
import com.hyperjump.game.applicationcode.port.out.GameRule;
import com.hyperjump.game.applicationcode.port.out.GameRulesObserverPort;
import com.hyperjump.game.applicationcode.port.out.GameStartObserverPort;
import com.hyperjump.game.applicationcode.port.out.PathFactory;

import java.util.ArrayList;
import java.util.List;

public class GameSessionUseCase implements StartGameUseCase {

    private final int playerCount;
    private final Board boardProvider;
    private final InitialisePlayerUseCase playerSetup;
    private final InitialiseRulesUseCase rulesSetup;
    private final PathFactory pathFactory;
    private final List<GameStartObserverPort> observers = new ArrayList<>();
    private final List<GameRulesObserverPort> rulesObservers;

    private BoardFactory board;

    public GameSessionUseCase(int playerCount, Board boardProvider, InitialisePlayerUseCase playerSetup, InitialiseRulesUseCase rulesSetup, PathFactory pathFactory, List<GameRulesObserverPort> rulesObservers) {
        this.playerCount = playerCount;
        this.boardProvider = boardProvider;
        this.playerSetup = playerSetup;
        this.rulesSetup = rulesSetup;
        this.pathFactory = pathFactory;
        this.rulesObservers = rulesObservers;
    }

    @Override
    public void play() {
        setupGame();
        startGame();
    }

    public void addObserver(GameStartObserverPort observer) {
        observers.add(observer);
    }

    private void setupGame() {
        board = boardProvider.createBoard(playerCount);
        playerSetup.setupPlayers(playerCount, board, pathFactory);
    }

    private void startGame() {
        List<GameRule> selectedRules = selectRules();
        notifyRulesSelected(selectedRules);
        notifyGameStarted(playerSetup.getPlayers());
        Movement movement = buildMovement(selectedRules);
        playerSetup.startTurns(movement, new InPlayState());
    }

    private List<GameRule> selectRules() {
        return rulesSetup.setupRules(board.getSize(), playerSetup.getPlayers());
    }

    private Movement buildMovement(List<GameRule> selectedRules) {
        Movement movement = new BasicMovement();

        List<MovementDecoratorStrategy> strategies = List.of(new TeleportVariation(), new ExactEndVariation(), new HitVariation());

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