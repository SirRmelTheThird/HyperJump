package com.hyperjump.game.applicationcode.domainmodel.path;

import com.hyperjump.game.applicationcode.domainmodel.player.Player;
import com.hyperjump.game.applicationcode.domainmodel.shared.DomainException;

import java.util.Map;

public class PlayerPathRegistry {

    private final Map<Player, Path> paths;

    public PlayerPathRegistry(Map<Player, Path> paths) {
        this.paths = Map.copyOf(paths);
    }

    public Path getPath(Player player) {
        if (!paths.containsKey(player)) {
            throw new DomainException("No path found for player: " + player.getColour());
        }
        return paths.get(player);
    }

    public boolean hasPath(Player player) {
        return paths.containsKey(player);
    }
}
