package com.hyperjump.game;

import com.hyperjump.game.applicationcode.domainmodel.rules.strategy.RuleSelectionStrategy;
import com.hyperjump.game.applicationcode.domainmodel.rules.strategy.TeleportGenerationStrategy;
import com.hyperjump.game.applicationcode.port.in.ReplayGameUseCase;
import com.hyperjump.game.applicationcode.port.in.StartGameUseCase;
import com.hyperjump.game.applicationcode.port.out.*;
import com.hyperjump.game.applicationcode.usecase.GameSessionUseCase;
import com.hyperjump.game.applicationcode.usecase.InitialisePlayerUseCase;
import com.hyperjump.game.applicationcode.usecase.InitialiseRulesUseCase;
import com.hyperjump.game.applicationcode.usecase.ReplayGameService;
import com.hyperjump.game.infrastructure.driven.dice.RecordingDiceShakerAdapter;
import com.hyperjump.game.infrastructure.driving.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class AppConfig {

    private static final int PLAYER_COUNT = 2;

    private final DisplayPort              display          = new ConsoleDisplayAdapter();
    private final TurnObserverPort         turnDisplay      = new ConsoleTurnAdapter(display);
    private final GameEndedObserverPort    gameEndedDisplay = new ConsoleGameEndedAdapter(display);
    private final GameStartedObserverPort  gameStartedDisplay = new ConsoleGameStartedAdapter(display);
    private final GameSavedObserverPort    gameSavedDisplay = new ConsoleGameSavedAdapter(display);

    @Bean
    public RecordingDiceShakerPort recordingDiceShaker(DiceShaker diceShaker) {
        return new RecordingDiceShakerAdapter(diceShaker);
    }

    @Bean
    public GameRunnerObserverPort gameRunnerDisplayAdapter() {
        return new ConsoleGameRunnerDisplayAdapter(display);
    }

    @Bean
    public InitialisePlayerUseCase initialisePlayerUseCase(RecordingDiceShakerPort diceShaker) {
        return new InitialisePlayerUseCase(diceShaker, List.of(turnDisplay), List.of(gameEndedDisplay));
    }

    @Bean
    public InitialiseRulesUseCase initialiseRulesUseCase(RuleSelectionStrategy ruleSelectionStrategy, TeleportGenerationStrategy teleportGenerationStrategy) {
        return new InitialiseRulesUseCase(ruleSelectionStrategy, teleportGenerationStrategy);
    }

    @Bean
    public StartGameUseCase startGameUseCase(Board board, RecordingDiceShakerPort recordingDice, InitialisePlayerUseCase playerSetup, InitialiseRulesUseCase rulesSetup, SavedGameRepository savedGameRepository) {
        return new GameSessionUseCase(
                PLAYER_COUNT, board, playerSetup, rulesSetup,
                recordingDice, savedGameRepository,
                List.of(gameStartedDisplay),
                List.of(gameEndedDisplay),
                List.of(gameSavedDisplay)
        );
    }

    @Bean
    public ReplayGameUseCase replayGameUseCase(SavedGameRepository repository, Board board, ReplayDiceShakerFactory replayDiceShakerFactory, GameRunnerObserverPort gameRunnerDisplay) {
        return new ReplayGameService(
                repository,
                board,
                List.of(turnDisplay),
                List.of(gameStartedDisplay),
                List.of(gameEndedDisplay),
                List.of(gameRunnerDisplay),
                replayDiceShakerFactory
        );
    }
}