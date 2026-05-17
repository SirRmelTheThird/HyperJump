package com.hyperjump.game.applicationcode.domainmodel.board;

import com.hyperjump.game.applicationcode.domainmodel.value.Position;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractBoard implements BoardFactory {

    private final List<Position> positions;

    protected AbstractBoard(int[] grid) {
        List<Position> list = new ArrayList<>();
        for (int value : grid) list.add(new Position(value));
        this.positions = List.copyOf(list);
    }

    @Override
    public List<Position> getPositions() {
        return positions;
    }

    @Override
    public int indexOf(Position position) {
        return positions.indexOf(position);
    }

    @Override
    public Position getPosition(int index) {
        return positions.get(index);
    }
}
