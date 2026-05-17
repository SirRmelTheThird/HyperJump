package com.hyperjump.game.infrastructure.driven.board;

import com.hyperjump.game.applicationcode.domainmodel.board.BoardFactory;
import com.hyperjump.game.applicationcode.domainmodel.board.LargeBoard;
import com.hyperjump.game.applicationcode.domainmodel.board.SmallBoard;
import com.hyperjump.game.applicationcode.port.out.Board;
import com.hyperjump.game.applicationcode.domainmodel.shared.DomainException;
import org.springframework.stereotype.Component;

@Component
public class BoardFactoryAdapter implements Board {

    @Override
    public BoardFactory createBoard(int playerCount) {
        return switch (playerCount) {
            case 2 -> new SmallBoard();
            case 4 -> new LargeBoard();
            default -> throw new DomainException("Unsupported number of players: " + playerCount);
        };
    }
}
