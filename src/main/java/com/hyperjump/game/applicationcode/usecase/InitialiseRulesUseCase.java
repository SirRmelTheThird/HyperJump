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
    private final TeleportGenerationStrategy teleportGenerationStrategy;

    public InitialiseRulesUseCase(RuleSelectionStrategy ruleSelectionStrategy, TeleportGenerationStrategy teleportGenerationStrategy) {
        this.ruleSelectionStrategy = ruleSelectionStrategy;
        this.teleportGenerationStrategy = teleportGenerationStrategy;
    }

    public InitialiseRulesUseCase(RuleSelectionStrategy ruleSelectionStrategy) {
        this(ruleSelectionStrategy, null);
    }

    public List<GameRule> setupRules(int boardSize, List<Player> players) {

        if (teleportGenerationStrategy == null) {
            return ruleSelectionStrategy.select(List.of());
        }

        List<GameRule> availableRules = List.of(
                new ExactEndRule(),
                new TeleportRule(boardSize, players, teleportGenerationStrategy),
                new HitRule(new SamePositionHit(players))
        );

        return ruleSelectionStrategy.select(availableRules);
    }
}