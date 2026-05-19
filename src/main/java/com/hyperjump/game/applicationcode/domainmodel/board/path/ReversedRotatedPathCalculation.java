package com.hyperjump.game.applicationcode.domainmodel.board.path;

import com.hyperjump.game.applicationcode.domainmodel.value.Position;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReversedRotatedPathCalculation implements PathCalculationStrategy {

    @Override
    public List<Position> calculate(List<Position> fullBoard, int cols, Position startPos) {
        List<Position> path = new ArrayList<>(fullBoard);
        Collections.reverse(path);
        int startIndex = path.indexOf(startPos);
        Collections.rotate(path, -startIndex);
        return List.copyOf(path);
    }
}