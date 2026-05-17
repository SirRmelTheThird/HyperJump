package com.hyperjump.game.applicationcode.port.out;

import com.hyperjump.game.applicationcode.domainmodel.board.BoardFactory;
import com.hyperjump.game.applicationcode.domainmodel.path.Path;
import com.hyperjump.game.applicationcode.domainmodel.value.Position;

public interface PathFactory {
    Path createPath(BoardFactory boardFactory, Position startPos, Position endPos);
}
