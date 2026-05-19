package com.hyperjump.game.applicationcode.domainmodel.board;

import com.hyperjump.game.applicationcode.domainmodel.value.Position;
import com.hyperjump.game.applicationcode.domainmodel.board.path.*;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractBoard implements BoardFactory {

    private final List<Position> positions;

    private final PathCalculationStrategy forwardStrategy = new ForwardPathCalculation();
    private final PathCalculationStrategy backwardStrategy = new BackwardPathCalculation();
    private final PathCalculationStrategy rotatedStrategy = new RotatedPathCalculation();
    private final PathCalculationStrategy reversedRotatedStrategy = new ReversedRotatedPathCalculation();

    protected AbstractBoard(int[] grid) {
        this.positions = Arrays.stream(grid)
                .mapToObj(Position::new)
                .toList();
    }

    @Override
    public List<Position> getPositions() {
        return positions;
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

        return strategy.calculate(positions, getCols(), startPos
        );
    }

    private PathCalculationStrategy selectStrategy(int startIndex, int endIndex) {
        if (startIndex < endIndex && startIndex % getCols() == 0) return rotatedStrategy;
        if (startIndex > endIndex && endIndex % getCols() == 0) return reversedRotatedStrategy;
        if (startIndex > endIndex) return backwardStrategy;

        return forwardStrategy;
    }
}