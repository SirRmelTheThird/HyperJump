package com.hyperjump.game;

import com.hyperjump.game.applicationcode.domainmodel.rules.strategy.RuleSelectionStrategy;
import com.hyperjump.game.applicationcode.port.in.StartGameUseCase;
import com.hyperjump.game.applicationcode.port.out.*;
import com.hyperjump.game.applicationcode.usecase.GameSessionUseCase;
import com.hyperjump.game.applicationcode.usecase.InitialisePlayerUseCase;
import com.hyperjump.game.applicationcode.usecase.InitialiseRulesUseCase;
import com.hyperjump.game.applicationcode.usecase.StartGameService;

import com.hyperjump.game.infrastructure.driving.ConsoleDisplayAdapter;
import com.hyperjump.game.infrastructure.driving.GameOverDisplayAdapter;
import com.hyperjump.game.infrastructure.driving.PathsDisplayAdapter;
import com.hyperjump.game.infrastructure.driving.RulesDisplayAdapter;
import com.hyperjump.game.infrastructure.driving.TurnDisplayAdapter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class AppConfig {

    @Bean
    public ConsoleDisplayAdapter consoleDisplay() {
        return new ConsoleDisplayAdapter();
    }

    @Bean
    public PlayerTurnObserverPort turnDisplayAdapter(ConsoleDisplayAdapter display) {
        return new TurnDisplayAdapter(display);
    }

    @Bean
    public GameOverObserverPort gameOverDisplayAdapter(ConsoleDisplayAdapter display) {
        return new GameOverDisplayAdapter(display);
    }

    @Bean
    public GameStartObserverPort pathsDisplayAdapter(ConsoleDisplayAdapter display) {
        return new PathsDisplayAdapter(display);
    }

    @Bean
    public GameRulesObserverPort rulesDisplayAdapter(ConsoleDisplayAdapter display) {
        return new RulesDisplayAdapter(display);
    }

    @Bean
    public InitialisePlayerUseCase initialisePlayerUseCase(DiceShaker diceShaker, List<PlayerTurnObserverPort> turnObservers, List<GameOverObserverPort> gameOverObservers) {
        return new InitialisePlayerUseCase(diceShaker, turnObservers, gameOverObservers);
    }

    @Bean
    public InitialiseRulesUseCase initialiseRulesUseCase(RuleSelectionStrategy ruleSelectionStrategy) {
        return new InitialiseRulesUseCase(ruleSelectionStrategy);
    }

    @Bean
    public GameSessionUseCase gameSessionUseCase(Board board, InitialisePlayerUseCase playerSetup, InitialiseRulesUseCase rulesSetup, PathFactory pathFactory, List<GameRulesObserverPort> rulesObservers) {
        return new GameSessionUseCase(board, playerSetup, rulesSetup, pathFactory, rulesObservers);
    }

    @Bean
    public StartGameUseCase startGameUseCase(GameSessionUseCase gameSessionUseCase, List<GameStartObserverPort> gameStartObservers) {
        StartGameService service = new StartGameService(gameSessionUseCase);

        gameStartObservers.forEach(gameSessionUseCase::addObserver);

        return service;
    }
}