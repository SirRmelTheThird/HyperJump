package com.hyperjump.game.applicationcode.usecase;

import com.hyperjump.game.applicationcode.domainmodel.board.BoardFactory;
import com.hyperjump.game.applicationcode.domainmodel.gameenum.Colour;
import com.hyperjump.game.applicationcode.domainmodel.movement.Movement;
import com.hyperjump.game.applicationcode.domainmodel.player.Player;
import com.hyperjump.game.applicationcode.domainmodel.player.PlayerTurn;
import com.hyperjump.game.applicationcode.domainmodel.player.RoundRobinPlayerSelector;
import com.hyperjump.game.applicationcode.domainmodel.player.strategy.FourPlayerPosition;
import com.hyperjump.game.applicationcode.domainmodel.player.strategy.PlayerPositionStrategy;
import com.hyperjump.game.applicationcode.domainmodel.player.strategy.TwoPlayerPosition;
import com.hyperjump.game.applicationcode.domainmodel.shared.DomainException;
import com.hyperjump.game.applicationcode.domainmodel.state.GameStateMachine;
import com.hyperjump.game.applicationcode.domainmodel.value.Position;
import com.hyperjump.game.applicationcode.port.out.*;

import java.util.Arrays;
import java.util.List;

public class InitialisePlayerUseCase {

    private final List<TurnObserverPort> turnObservers;
    private final List<GameEndedObserverPort> gameEndedObservers;
    private final DiceShaker diceShaker;
    private final List<PlayerPositionStrategy> positionStrategies;

    private List<Player> players;
    private RoundRobinPlayerSelector selector;

    public InitialisePlayerUseCase(
            DiceShaker diceShaker,
            List<TurnObserverPort> turnObservers,
            List<GameEndedObserverPort> gameEndedObservers
    ) {
        this.diceShaker = diceShaker;
        this.turnObservers = turnObservers;
        this.gameEndedObservers = gameEndedObservers;
        this.positionStrategies = List.of(
                new TwoPlayerPosition(),
                new FourPlayerPosition()
        );
    }

    public void setupPlayers(int playerCount, BoardFactory boardFactory) {
        players = createPlayers(playerCount);

        PlayerPositionStrategy strategy = positionStrategies.stream()
                .filter(s -> s.supports(playerCount))
                .findFirst()
                .orElseThrow(() -> new DomainException(
                        "Unsupported number of players: " + playerCount
                ));

        List<Position> starts = strategy.startPositions(
                boardFactory.getStartPosition(),
                boardFactory.getEndPosition(),
                boardFactory.getCols()
        );

        List<Position> ends = strategy.endPositions(
                boardFactory.getStartPosition(),
                boardFactory.getEndPosition(),
                boardFactory.getCols()
        );

        for (int i = 0; i < players.size(); i++) {
            List<Position> path = boardFactory.calculatePath(
                    starts.get(i),
                    ends.get(i)
            );
            players.get(i).setPath(path);
        }

        selector = new RoundRobinPlayerSelector(players);
    }

    public PlayerTurn startTurns(Movement movement) {
        PlayerTurn turn = new PlayerTurn(selector, movement);

        turnObservers.forEach(turn::addObserver);

        GameStateMachine stateMachine = new GameStateMachine(turn);
        stateMachine.play();

        return turn;
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