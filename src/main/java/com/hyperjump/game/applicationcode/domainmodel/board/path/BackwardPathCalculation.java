package com.hyperjump.game.applicationcode.domainmodel.board.path;

import com.hyperjump.game.applicationcode.domainmodel.value.Position;

import java.util.ArrayList;
import java.util.List;

public class BackwardPathCalculation implements PathCalculationStrategy {

    @Override
    public List<Position> calculate(List<Position> fullBoard, int cols, Position startPos) {
        int totalRows = fullBoard.size() / cols;
        int startIndex = fullBoard.indexOf(startPos);
        int startRow = startIndex / cols;

        List<Position> path = new ArrayList<>();

        for (int i = 0; i < totalRows; i++) {
            int row = (startRow - i + totalRows) % totalRows;
            path.addAll(fullBoard.subList(row * cols, row * cols + cols));
        }

        return List.copyOf(path);
    }
}
