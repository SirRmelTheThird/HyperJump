package com.hyperjump.game.infrastructure.driving;

import com.hyperjump.game.applicationcode.domainmodel.replay.SavedGame;
import com.hyperjump.game.applicationcode.port.in.ReplayGameUseCase;
import com.hyperjump.game.applicationcode.port.in.StartGameUseCase;
import com.hyperjump.game.applicationcode.port.out.GameRunnerObserverPort;
import com.hyperjump.game.applicationcode.port.out.SavedGameRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class GameConsoleRunner implements CommandLineRunner {

    private static final int GAME_COUNT = 8;

    private final StartGameUseCase startGameUseCase;
    private final ReplayGameUseCase replayGameUseCase;
    private final SavedGameRepository savedGameRepository;
    private final GameRunnerObserverPort gameRunnerObserver;

    public GameConsoleRunner(StartGameUseCase startGameUseCase, ReplayGameUseCase replayGameUseCase, SavedGameRepository savedGameRepository, GameRunnerObserverPort gameRunnerObserver) {
        this.startGameUseCase = startGameUseCase;
        this.replayGameUseCase = replayGameUseCase;
        this.savedGameRepository = savedGameRepository;
        this.gameRunnerObserver = gameRunnerObserver;
    }

    @Override
    public void run(String... args) throws InterruptedException {

        savedGameRepository.clearAll();

        for (int i = 0; i < GAME_COUNT; i++) {
            gameRunnerObserver.onGameRunStarted(i + 1);
            startGameUseCase.play();
            Thread.sleep(3000);
        }

        gameRunnerObserver.onReplayRunStarted();

        for (SavedGame saved : savedGameRepository.findAll()) {
            replayGameUseCase.replay(saved.getId());
            Thread.sleep(3000);
        }
    }
}