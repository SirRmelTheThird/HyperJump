package com.hyperjump.game.applicationcode.usecase;

import com.hyperjump.game.applicationcode.domainmodel.player.Player;
import com.hyperjump.game.applicationcode.domainmodel.rules.ExactEndRule;
import com.hyperjump.game.applicationcode.domainmodel.rules.HitRule;
import com.hyperjump.game.applicationcode.domainmodel.rules.TeleportRule;
import com.hyperjump.game.applicationcode.domainmodel.rules.strategy.*;
import com.hyperjump.game.applicationcode.port.out.GameRule;

import java.util.List;

public class InitialiseRulesUseCase {

    private final RuleSelectionStrategy ruleSelectionStrategy;

    public InitialiseRulesUseCase(RuleSelectionStrategy ruleSelectionStrategy) {
        this.ruleSelectionStrategy = ruleSelectionStrategy;
    }

    public List<GameRule> setupRules(int boardSize, List<Player> players) {

        List<GameRule> randomRules = List.of(
                new ExactEndRule(),
                new TeleportRule(boardSize, players, new RandomTeleportGeneration()),
                new HitRule(new SamePositionHit(players))
        );

        List<GameRule> fixedRules = List.of(
                new ExactEndRule(),
                new TeleportRule(boardSize, players, new FixedTeleportGeneration()),
                new HitRule(new SamePositionHit(players))
        );

        List<GameRule> availableRules = ruleSelectionStrategy instanceof FixedRuleSelection ? fixedRules : randomRules;
        return ruleSelectionStrategy.select(availableRules);
    }
}