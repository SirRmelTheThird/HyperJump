package com.hyperjump.game.applicationcode.port.out;
import com.hyperjump.game.applicationcode.domainmodel.board.BoardFactory;
import com.hyperjump.game.applicationcode.domainmodel.player.Player;


import java.util.List;

public interface GameStartedObserverPort {

    void onGameStarted(GameStartInfo gameStartInfo);

    record GameStartInfo(
            BoardFactory board,
            List<GameRule> rules,
            DiceShaker diceShaker,
            List<Player> players
    ) {}
}