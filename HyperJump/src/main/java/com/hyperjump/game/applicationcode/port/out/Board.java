package com.hyperjump.game.applicationcode.port.out;

import com.hyperjump.game.applicationcode.domainmodel.board.BoardFactory;

public interface Board {
    BoardFactory createBoard(int playerCount);
}
