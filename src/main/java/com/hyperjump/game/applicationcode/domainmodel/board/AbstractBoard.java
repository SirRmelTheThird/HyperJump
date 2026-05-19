package com.hyperjump.game.applicationcode.domainmodel.board;

import com.hyperjump.game.applicationcode.domainmodel.board.path.*;
import com.hyperjump.game.applicationcode.domainmodel.value.Position;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractBoard implements BoardFactory {

    private final List<Position> positions;

    private final PathCalculationStrategy FORWARD_STRATEGY = new ForwardPathCalculation();
    private final PathCalculationStrategy BACKWARD_STRATEGY = new BackwardPathCalculation();
    private final PathCalculationStrategy ROTATED_STRATEGY = new RotatedPathCalculation();
    private final PathCalculationStrategy REVERSED_ROTATED_STRATEGY = new ReversedRotatedPathCalculation();

    protected AbstractBoard(int[] grid) {
        this.positions = Arrays.stream(grid)
                .mapToObj(Position::new)
                .toList();
    }

    @Override
    public int getRows() {
        return getSize() / getCols();
    }

    @Override
    public int indexOf(Position position) {
        return positions.indexOf(position);
    }

    @Override
    public Position getPosition(int index) {
        return positions.get(index);
    }

    @Override
    public List<Position> calculatePath(Position startPos, Position endPos) {
        int startIndex = indexOf(startPos);
        int endIndex = indexOf(endPos);

        PathCalculationStrategy strategy = selectStrategy(startIndex, endIndex);

        return strategy.calculate(positions, getCols(), startPos);
    }

    private PathCalculationStrategy selectStrategy(int startIndex, int endIndex) {
        if (startIndex < endIndex && startIndex % getCols() == 0) return ROTATED_STRATEGY;
        if (startIndex > endIndex && endIndex % getCols() == 0) return REVERSED_ROTATED_STRATEGY;
        if (startIndex > endIndex) return BACKWARD_STRATEGY;
        return FORWARD_STRATEGY;
    }
}