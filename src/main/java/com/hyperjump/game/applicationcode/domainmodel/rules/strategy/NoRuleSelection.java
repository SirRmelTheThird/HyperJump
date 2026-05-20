package com.hyperjump.game.applicationcode.domainmodel.rules.strategy;

import com.hyperjump.game.applicationcode.port.out.GameRule;

import java.util.List;

public class NoRuleSelection implements RuleSelectionStrategy {

    @Override
    public List<GameRule> select(List<GameRule> availableRules) {
        return List.of();
    }
}