package com.hyperjump.game.infrastructure.driven.path.strategy;

import com.hyperjump.game.applicationcode.domainmodel.value.Position;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ForwardPathCalculation implements PathCalculationStrategy {

    @Override
    public List<Position> calculate(List<Position> fullBoard, int cols, Position startPos) {
        int totalRows = fullBoard.size() / cols;
        int startIndex = fullBoard.indexOf(startPos);
        int startRow = startIndex / cols;

        List<Position> path = new ArrayList<>();

        for (int i = 0; i < totalRows; i++) {
            int row = (startRow + i) % totalRows;

            List<Position> rowPositions = new ArrayList<>(fullBoard.subList(row * cols, row * cols + cols));

            Collections.reverse(rowPositions);
            path.addAll(rowPositions);
        }
        return List.copyOf(path);
    }
}