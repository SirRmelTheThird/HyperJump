package com.hyperjump.game.applicationcode.domainmodel.board;

import com.hyperjump.game.applicationcode.domainmodel.value.Position;

import java.util.List;

public interface BoardFactory {

    int getSize();
    int getCols();
    int getRows();
    int getStartPosition();
    int getEndPosition();
    int indexOf(Position position);
    boolean supports(int playerCount);
    List<Position> calculatePath(Position startPos, Position endPos);
}