package com.hyperjump.game.applicationcode.port.out;

import com.hyperjump.game.applicationcode.domainmodel.player.Player;
import java.util.List;

public interface GameStartObserverPort {
    void onGameStarted(List<Player> players);
}
