package com.hyperjump.game.infrastructure.driving;

import com.hyperjump.game.applicationcode.domainmodel.replay.SavedGame;
import com.hyperjump.game.applicationcode.port.in.ReplayGameUseCase;
import com.hyperjump.game.applicationcode.port.in.StartGameUseCase;
import com.hyperjump.game.applicationcode.port.out.SavedGameRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class GameConsoleRunner implements CommandLineRunner {

    private static final int GAME_COUNT = 7;

    private final StartGameUseCase    startGameUseCase;
    private final ReplayGameUseCase   replayGameUseCase;
    private final SavedGameRepository savedGameRepository;

    public GameConsoleRunner(
            StartGameUseCase    startGameUseCase,
            ReplayGameUseCase   replayGameUseCase,
            SavedGameRepository savedGameRepository) {
        this.startGameUseCase    = startGameUseCase;
        this.replayGameUseCase   = replayGameUseCase;
        this.savedGameRepository = savedGameRepository;
    }

    @Override
    public void run(String... args) throws InterruptedException {

        // Play and save games
        for (int i = 0; i < GAME_COUNT; i++) {
            System.out.println("\n=== GAME " + (i + 1) + " ===\n");
            startGameUseCase.play();
            Thread.sleep(500);
        }

        // Replay every saved game
        System.out.println("\n\n════════════════════════════════");
        System.out.println("  REPLAYING ALL SAVED GAMES");
        System.out.println("════════════════════════════════");

        for (SavedGame saved : savedGameRepository.findAll()) {
            replayGameUseCase.replay(saved.getId());
            Thread.sleep(500);
        }
    }
}