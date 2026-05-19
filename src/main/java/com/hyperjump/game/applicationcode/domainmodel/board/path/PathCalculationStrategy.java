package com.hyperjump.game.applicationcode.domainmodel.board.path;

import com.hyperjump.game.applicationcode.domainmodel.value.Position;

import java.util.List;

public interface PathCalculationStrategy {
    List<Position> calculate(List<Position> fullBoard, int cols, Position startPos);
}