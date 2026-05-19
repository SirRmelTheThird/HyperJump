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

    @Bean
    public DisplayPort displayPort() {
        return new ConsoleDisplayAdapter();
    }

    @Bean
    public GameStartedObserverPort gameStartedDisplay(DisplayPort display) {
        return new ConsoleGameStartedAdapter(display);
    }

    @Bean
    public TurnObserverPort turnDisplay(DisplayPort display) {
        return new ConsoleTurnAdapter(display);
    }

    @Bean
    public ConsoleGameEndedAdapter gameEndedDisplay(DisplayPort display) {
        return new ConsoleGameEndedAdapter(display);
    }

    @Bean
    public ReplayObserverPort replayDisplay(DisplayPort display) {
        return new ConsoleReplayAdapter(display);
    }

    @Bean
    public RecordingDiceShakerPort recordingDiceShaker(DiceShaker diceShaker) {
        return new RecordingDiceShakerAdapter(diceShaker);
    }

    @Bean
    public InitialisePlayerUseCase initialisePlayerUseCase(RecordingDiceShakerPort diceShaker, TurnObserverPort turnDisplay, GameEndedObserverPort gameEndedDisplay) {
        return new InitialisePlayerUseCase(diceShaker, List.of(turnDisplay), List.of(gameEndedDisplay));
    }

    @Bean
    public InitialiseRulesUseCase initialiseRulesUseCase(RuleSelectionStrategy ruleSelectionStrategy, TeleportGenerationStrategy teleportGenerationStrategy) {
        return new InitialiseRulesUseCase(ruleSelectionStrategy, teleportGenerationStrategy);
    }

    @Bean
    public StartGameUseCase startGameUseCase(Board board, RecordingDiceShakerPort recordingDice, InitialisePlayerUseCase playerSetup, InitialiseRulesUseCase rulesSetup, SavedGameRepository savedGameRepository, GameStartedObserverPort gameStartedDisplay, GameEndedObserverPort gameEndedDisplay) {
        return new GameSessionUseCase(
                PLAYER_COUNT,
                board,
                playerSetup,
                rulesSetup,
                recordingDice,
                savedGameRepository,
                List.of(gameStartedDisplay),
                List.of(gameEndedDisplay)
        );
    }

    @Bean
    public ReplayGameUseCase replayGameUseCase(SavedGameRepository repository, Board board, ReplayDiceShakerFactory replayDiceShakerFactory, TurnObserverPort turnDisplay, GameStartedObserverPort gameStartedDisplay, GameEndedObserverPort gameEndedDisplay, ReplayObserverPort replayDisplay) {
        return new ReplayGameService(
                repository,
                board,
                List.of(turnDisplay),
                List.of(gameStartedDisplay),
                List.of(gameEndedDisplay),
                List.of(replayDisplay),
                replayDiceShakerFactory
        );
    }
}