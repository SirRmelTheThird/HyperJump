package com.hyperjump.game.infrastructure.driving;

import com.hyperjump.game.applicationcode.port.out.GameRule;
import com.hyperjump.game.applicationcode.port.out.GameRulesObserverPort;

import java.util.List;

public class RulesDisplayAdapter implements GameRulesObserverPort {

    private final DisplayPort display;

    public RulesDisplayAdapter(DisplayPort display) {
        this.display = display;
    }

    @Override
    public void onRulesSelected(List<GameRule> rules) {
        display.show("Game Rules:");
        for (GameRule rule : rules) {
            display.show(" - " + rule.getDescription());
        }
        display.show("");
    }
}
