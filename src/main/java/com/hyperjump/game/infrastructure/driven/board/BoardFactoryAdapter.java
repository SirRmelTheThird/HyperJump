package com.hyperjump.game.infrastructure.driven.board;

import com.hyperjump.game.applicationcode.domainmodel.board.BoardFactory;
import com.hyperjump.game.applicationcode.domainmodel.board.LargeBoard;
import com.hyperjump.game.applicationcode.domainmodel.board.SmallBoard;
import com.hyperjump.game.applicationcode.domainmodel.shared.DomainException;
import com.hyperjump.game.applicationcode.port.out.Board;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Supplier;

@Component
public class BoardFactoryAdapter implements Board {

    private final Map<Integer, Supplier<BoardFactory>> boardCreators;

    public BoardFactoryAdapter() {
        this(Map.of(
                2, SmallBoard::getInstance,
                4, LargeBoard::getInstance
        ));
    }

    public BoardFactoryAdapter(Map<Integer, Supplier<BoardFactory>> boardCreators) {
        this.boardCreators = Map.copyOf(boardCreators);
    }

    @Override
    public BoardFactory createBoard(int playerCount) {
        Supplier<BoardFactory> creator = boardCreators.get(playerCount);
        if (creator == null) {
            throw new DomainException("Unsupported number of players: " + playerCount);
        }
        return creator.get();
    }
}
