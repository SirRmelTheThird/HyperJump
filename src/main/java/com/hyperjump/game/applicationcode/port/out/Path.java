package com.hyperjump.game.applicationcode.port.out;

import com.hyperjump.game.applicationcode.domainmodel.board.BoardFactory;
import com.hyperjump.game.applicationcode.domainmodel.path.PathFactory;
import com.hyperjump.game.applicationcode.domainmodel.value.Position;

public interface Path {
    PathFactory createPath(BoardFactory boardFactory, Position startPos, Position endPos);
}
