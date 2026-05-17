package com.hyperjump.game.applicationcode.port.out;

import java.util.List;

public interface GameRulesObserverPort {
    void onRulesSelected(List<GameRule> rules);
}
