package com.hyperjump.game.infrastructure.driven.path.strategy;

import com.hyperjump.game.applicationcode.domainmodel.value.Position;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RotatedPathCalculation implements PathCalculationStrategy {

    @Override
    public List<Position> calculate(List<Position> fullBoard, int cols, Position startPos) {
        List<Position> path = new ArrayList<>(fullBoard);
        int startIndex = path.indexOf(startPos);
        Collections.rotate(path, -startIndex);
        return List.copyOf(path);
    }
}