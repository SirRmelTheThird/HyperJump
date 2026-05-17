package com.hyperjump.game.infrastructure.driven.path;

import com.hyperjump.game.applicationcode.domainmodel.board.BoardFactory;
import com.hyperjump.game.applicationcode.domainmodel.path.*;
import com.hyperjump.game.applicationcode.domainmodel.value.Position;
import com.hyperjump.game.applicationcode.port.out.PathFactory;
import com.hyperjump.game.infrastructure.driven.path.strategy.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BoardPathFactoryAdapter implements PathFactory {

    private final PathCalculationStrategy forwardStrategy = new ForwardPathCalculation();
    private final PathCalculationStrategy backwardStrategy = new BackwardPathCalculation();
    private final PathCalculationStrategy rotatedStrategy = new RotatedPathCalculation();
    private final PathCalculationStrategy reversedRotatedStrategy = new ReversedRotatedPathCalculation();

    @Override
    public Path createPath(BoardFactory boardFactory, Position startPos, Position endPos) {

        List<Position> fullBoard = boardFactory.getPositions();
        int cols = boardFactory.getCols();
        int startIndex = fullBoard.indexOf(startPos);
        int endIndex = fullBoard.indexOf(endPos);

        if (startIndex < endIndex && startIndex % cols == 0) {
            return new RotatedPath(rotatedStrategy.calculate(fullBoard, cols, startPos));
        }

        if (startIndex > endIndex && endIndex % cols == 0) {
            return new ReversedRotatedPath(reversedRotatedStrategy.calculate(fullBoard, cols, startPos));
        }

        if (startIndex > endIndex) {
            return new BackwardRowPath(backwardStrategy.calculate(fullBoard, cols, startPos));
        }

        return new ForwardRowPath(forwardStrategy.calculate(fullBoard, cols, startPos));
    }
}