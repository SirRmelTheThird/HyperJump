package com.hyperjump.game.applicationcode.usecase;

import com.hyperjump.game.applicationcode.domainmodel.board.BoardFactory;
import com.hyperjump.game.applicationcode.domainmodel.movement.BasicMovement;
import com.hyperjump.game.applicationcode.domainmodel.movement.Movement;
import com.hyperjump.game.applicationcode.domainmodel.player.Player;
import com.hyperjump.game.applicationcode.domainmodel.player.PlayerTurn;
import com.hyperjump.game.applicationcode.domainmodel.replay.GameConfiguration;
import com.hyperjump.game.applicationcode.domainmodel.replay.SavedGame;
import com.hyperjump.game.applicationcode.port.in.StartGameUseCase;
import com.hyperjump.game.applicationcode.port.out.*;

import java.util.List;

public class GameSessionUseCase implements StartGameUseCase {

    private final int                           playerCount;
    private final Board                         boardProvider;
    private final InitialisePlayerUseCase       playerSetup;
    private final InitialiseRulesUseCase        rulesSetup;
    private final RecordingDiceShakerPort       diceShaker;
    private final SavedGameRepository           savedGameRepository;
    private final List<GameStartedObserverPort> gameStartedObservers;
    private final List<GameEndedObserverPort>   gameEndedObservers;
    private final List<GameSavedObserverPort>   gameSavedObservers;

    private BoardFactory board;

    public GameSessionUseCase(int playerCount, Board boardProvider, InitialisePlayerUseCase playerSetup, InitialiseRulesUseCase rulesSetup, RecordingDiceShakerPort diceShaker, SavedGameRepository savedGameRepository, List<GameStartedObserverPort> gameStartedObservers, List<GameEndedObserverPort> gameEndedObservers, List<GameSavedObserverPort> gameSavedObservers) {
        this.playerCount          = playerCount;
        this.boardProvider        = boardProvider;
        this.playerSetup          = playerSetup;
        this.rulesSetup           = rulesSetup;
        this.diceShaker           = diceShaker;
        this.savedGameRepository  = savedGameRepository;
        this.gameStartedObservers = gameStartedObservers;
        this.gameEndedObservers   = gameEndedObservers;
        this.gameSavedObservers   = gameSavedObservers;
    }

    @Override
    public void play() {
        board = boardProvider.createBoard(playerCount);
        playerSetup.setupPlayers(playerCount, board);

        List<GameRule> selectedRules = rulesSetup.setupRules(board.getSize(), playerSetup.getPlayers());

        SavedGame savedGame = new SavedGame(
                savedGameRepository.nextId(),
                new GameConfiguration(playerCount, selectedRules)
        );

        diceShaker.startRecording(savedGame);
        notifyGameStarted(selectedRules, playerSetup.getPlayers());

        PlayerTurn playerTurn = playerSetup.startTurns(buildMovement(selectedRules));

        savedGameRepository.save(savedGame);
        notifyGameEnded(playerTurn, savedGame);
        notifyGameSaved(savedGame);
    }

    private Movement buildMovement(List<GameRule> rules) {
        Movement movement = new BasicMovement();
        for (GameRule rule : rules) {
            movement = rule.decorate(movement);
        }
        return movement;
    }

    private void notifyGameStarted(List<GameRule> rules, List<Player> players) {
        GameStartedObserverPort.GameStartInfo info = new GameStartedObserverPort.GameStartInfo(board, rules, diceShaker, players);
        gameStartedObservers.forEach(o -> o.onGameStarted(info));
    }

    private void notifyGameEnded(PlayerTurn playerTurn, SavedGame savedGame) {
        GameEndedObserverPort.GameEndInfo info = new GameEndedObserverPort.GameEndInfo(playerTurn.getWinner(), playerTurn.getTotalTurns(), savedGame);
        gameEndedObservers.forEach(o -> o.onGameEnded(info));
    }

    private void notifyGameSaved(SavedGame savedGame) {
        gameSavedObservers.forEach(o -> o.onGameSaved(savedGame));
    }
}