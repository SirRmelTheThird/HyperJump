package com.hyperjump.game.applicationcode.usecase;

import com.hyperjump.game.applicationcode.port.in.StartGameUseCase;
import org.springframework.stereotype.Service;

@Service
public class StartGameService implements StartGameUseCase {

    private final GameSessionUseCase gameSessionUseCase;

    public StartGameService(GameSessionUseCase gameSessionUseCase) {
        this.gameSessionUseCase = gameSessionUseCase;
    }

    @Override
    public void play() {
        gameSessionUseCase.setupGame();
        gameSessionUseCase.startGame();
    }
}
