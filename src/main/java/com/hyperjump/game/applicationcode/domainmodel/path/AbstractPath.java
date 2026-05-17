package com.hyperjump.game.applicationcode.domainmodel.path;

import com.hyperjump.game.applicationcode.domainmodel.value.Position;
import java.util.List;

public abstract class AbstractPath implements Path {

    private final List<Position> positions;

    protected AbstractPath(List<Position> positions) {
        this.positions = List.copyOf(positions);
    }

    @Override
    public List<Position> getPositions() {
        return positions;
    }
}
