package com.hyperjump.game.infrastructure.driving;

import com.hyperjump.game.applicationcode.port.in.StartGameUseCase;
import com.hyperjump.game.applicationcode.port.out.DiceShaker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class GameConsoleRunner implements CommandLineRunner {

    private final StartGameUseCase startGameUseCase;
    private final DiceShaker diceShaker;

    public GameConsoleRunner(StartGameUseCase startGameUseCase, DiceShaker diceShaker) {
        this.startGameUseCase = startGameUseCase;
        this.diceShaker = diceShaker;
    }

    @Override
    public void run(String... args) throws InterruptedException {
        for (int i = 0; i < 7; i++) {
            diceShaker.reset();
            System.out.println("\n=== GAME " + (i + 1) + " ===\n");
            startGameUseCase.play();
            Thread.sleep(3000);
        }
    }
}
