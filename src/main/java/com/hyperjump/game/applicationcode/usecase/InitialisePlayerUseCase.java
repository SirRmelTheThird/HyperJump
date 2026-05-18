package com.hyperjump.game.applicationcode.usecase;

import com.hyperjump.game.applicationcode.domainmodel.board.BoardFactory;
import com.hyperjump.game.applicationcode.domainmodel.gameenum.Colour;
import com.hyperjump.game.applicationcode.domainmodel.movement.Movement;
import com.hyperjump.game.applicationcode.domainmodel.path.PathFactory;
import com.hyperjump.game.applicationcode.domainmodel.player.Player;
import com.hyperjump.game.applicationcode.domainmodel.player.PlayerStartPositionCalculator;
import com.hyperjump.game.applicationcode.domainmodel.player.PlayerTurn;
import com.hyperjump.game.applicationcode.domainmodel.player.RoundRobinPlayerSelector;
import com.hyperjump.game.applicationcode.port.out.DiceShaker;
import com.hyperjump.game.applicationcode.port.out.GameOverObserverPort;
import com.hyperjump.game.applicationcode.port.out.Path;
import com.hyperjump.game.applicationcode.port.out.PlayerTurnObserverPort;
import com.hyperjump.game.applicationcode.domainmodel.state.GameState;
import com.hyperjump.game.applicationcode.domainmodel.value.Position;

import java.util.Arrays;
import java.util.List;

public class InitialisePlayerUseCase {

    private final List<PlayerTurnObserverPort> turnObservers;
    private final List<GameOverObserverPort> gameOverObservers;

    private List<Player> players;
    private RoundRobinPlayerSelector selector;
    private final DiceShaker diceShaker;

    public InitialisePlayerUseCase(DiceShaker diceShaker, List<PlayerTurnObserverPort> turnObservers, List<GameOverObserverPort> gameOverObservers) {
        this.diceShaker = diceShaker;
        this.turnObservers = turnObservers;
        this.gameOverObservers = gameOverObservers;
    }

    public void setupPlayers(int playerCount, BoardFactory boardFactory, Path pathFactory) {
        players = createPlayers(playerCount);

        PlayerStartPositionCalculator calculator = new PlayerStartPositionCalculator(boardFactory.getStartPosition(), boardFactory.getEndPosition(), boardFactory.getCols());

        List<Position> starts = calculator.getStartPositions(playerCount);
        List<Position> ends   = calculator.getEndPositions(playerCount);

        for (int i = 0; i < players.size(); i++) {
            PathFactory path = pathFactory.createPath(boardFactory, starts.get(i), ends.get(i));
            players.get(i).setPath(path.getPositions());
        }
        selector = new RoundRobinPlayerSelector(players);
    }

    public void startTurns(Movement movement, GameState state) {
        PlayerTurn turn = new PlayerTurn(selector, movement, state);

        turnObservers.forEach(turn::addObserver);
        gameOverObservers.forEach(turn::addGameOverObserver);

        turn.play();
    }

    public List<Player> getPlayers() {
        return players;
    }

    private List<Player> createPlayers(int playerCount) {
        Colour[] colours = Colour.values();
        return Arrays.stream(colours)
                .limit(playerCount)
                .map(colour -> new Player(colour, diceShaker))
                .toList();
    }
}
