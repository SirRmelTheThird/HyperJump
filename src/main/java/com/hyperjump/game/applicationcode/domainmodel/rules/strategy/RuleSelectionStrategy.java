package com.hyperjump.game.applicationcode.domainmodel.rules.strategy;

import com.hyperjump.game.applicationcode.port.out.GameRule;

import java.util.List;

public interface RuleSelectionStrategy {
    List<GameRule> select(List<GameRule> availableRules);
    default boolean hasPreselectedRules() {
        return false;
    }
}