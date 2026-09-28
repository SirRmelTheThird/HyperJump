package com.hyperjump.game.applicationcode.usecase;

import com.hyperjump.game.applicationcode.domainmodel.board.BoardFactory;
import com.hyperjump.game.applicationcode.domainmodel.player.Player;
import com.hyperjump.game.applicationcode.domainmodel.player.PlayerTurn;
import com.hyperjump.game.applicationcode.domainmodel.replay.SavedGame;
import com.hyperjump.game.applicationcode.domainmodel.rules.strategy.NoOpTeleportGeneration;
import com.hyperjump.game.applicationcode.domainmodel.rules.strategy.SavedRuleSelection;
import com.hyperjump.game.applicationcode.port.in.ReplayGameUseCase;
import com.hyperjump.game.applicationcode.port.out.*;

import java.util.List;

public class ReplaySessionUseCase implements ReplayGameUseCase {

    private final SavedGameRepository           repository;
    private final Board                         board;
    private final List<TurnObserverPort>        turnObservers;
    private final List<GameStartedObserverPort> gameStartedObservers;
    private final List<GameEndedObserverPort>   gameEndedObservers;
    private final List<GameRunnerObserverPort> gameRunnerObservers;
    private final ReplayDiceShaker replayDiceShaker;

    public ReplaySessionUseCase(SavedGameRepository repository, Board board, List<TurnObserverPort> turnObservers, List<GameStartedObserverPort> gameStartedObservers, List<GameEndedObserverPort> gameEndedObservers, List<GameRunnerObserverPort> gameRunnerObservers, ReplayDiceShaker replayDiceShaker) {
        this.repository              = repository;
        this.board                   = board;
        this.turnObservers           = turnObservers;
        this.gameStartedObservers    = gameStartedObservers;
        this.gameEndedObservers      = gameEndedObservers;
        this.gameRunnerObservers         = gameRunnerObservers;
        this.replayDiceShaker = replayDiceShaker;
    }

    @Override
    public void replay(int gameId) {
        SavedGame savedGame = repository.findById(gameId);

        if (savedGame == null) {
            notifyReplayNotFound(gameId);
            return;
        }

        notifyReplayStarted(savedGame);

        int playerCount = savedGame.getConfiguration().getPlayerCount();

        DiceShaker diceShaker = replayDiceShaker.createReplayDiceShaker(savedGame.getDiceRolls());
        BoardFactory fullBoard = board.createBoard(playerCount);

        InitialisePlayerUseCase playerSetup = new InitialisePlayerUseCase(diceShaker, turnObservers);
        playerSetup.setupPlayers(playerCount, fullBoard);

        SavedRuleSelection savedRuleSelection = new SavedRuleSelection(savedGame.getConfiguration().getRules(), playerSetup.getPlayers());

        InitialiseRulesUseCase ruleSetup = new InitialiseRulesUseCase(savedRuleSelection, new NoOpTeleportGeneration());

        List<GameRule> selectedRules = ruleSetup.setupRules(fullBoard.getSize(), playerSetup.getPlayers());
        notifyGameStarted(fullBoard, selectedRules, diceShaker, playerSetup.getPlayers());

        InitialiseReplayUseCase session = new InitialiseReplayUseCase(playerSetup, selectedRules);
        PlayerTurn playerTurn = session.play();

        notifyGameEnded(playerTurn, savedGame);
    }

    private void notifyGameStarted(BoardFactory board, List<GameRule> rules, DiceShaker dice, List<Player> players) {
        GameStartedObserverPort.GameStartInfo info = new GameStartedObserverPort.GameStartInfo(board, rules, dice, players);
        gameStartedObservers.forEach(o -> o.onGameStarted(info));
    }

    private void notifyGameEnded(PlayerTurn playerTurn, SavedGame savedGame) {
        GameEndedObserverPort.GameEndInfo info = new GameEndedObserverPort.GameEndInfo(playerTurn.getWinner(), playerTurn.getTotalTurns(), savedGame);
        gameEndedObservers.forEach(o -> o.onGameEnded(info));
    }

    private void notifyReplayStarted(SavedGame savedGame) {
        gameRunnerObservers.forEach(o -> o.onReplaySelected(savedGame));
    }

    private void notifyReplayNotFound(int gameId) {
        gameRunnerObservers.forEach(o -> o.onReplayNotFound(gameId));
    }
}