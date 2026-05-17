package com.hyperjump.game.applicationcode.domainmodel.board;

import com.hyperjump.game.applicationcode.domainmodel.value.Position;

import java.util.List;

public interface BoardFactory {
    int getSize();
    int getCols();
    int getStartPosition();
    int getEndPosition();
    List<Position> getPositions();
    int indexOf(Position position);
    Position getPosition(int index);
}
