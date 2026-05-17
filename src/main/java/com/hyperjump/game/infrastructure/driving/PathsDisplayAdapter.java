package com.hyperjump.game.infrastructure.driving;

import com.hyperjump.game.applicationcode.domainmodel.player.Player;
import com.hyperjump.game.applicationcode.port.out.GameStartObserverPort;
import com.hyperjump.game.applicationcode.domainmodel.value.Position;

import java.util.List;

public class PathsDisplayAdapter implements GameStartObserverPort {

    private final DisplayPort display;

    public PathsDisplayAdapter(DisplayPort display) {
        this.display = display;
    }

    @Override
    public void onGameStarted(List<Player> players) {
        for (Player player : players) {
            display.show(buildPath(player));
        }
        display.show("");
    }

    private String buildPath(Player player) {
        StringBuilder message = new StringBuilder();
        List<Position> path = player.getPath();
        message.append(player.getColour()).append(" Home (Position ").append(player.getStartPos().value()).append(") ");

        for (Position position : path.subList(1, path.size() - 1)) {
            message.append(position.value()).append(", ");
        }

        message.append("End (Position ").append(player.getEndPos().value()).append(")");
        return message.toString();
    }
}
