package com.hyperjump.game.applicationcode.usecase;

import com.hyperjump.game.applicationcode.domainmodel.player.Player;
import com.hyperjump.game.applicationcode.domainmodel.rules.ExactEndRule;
import com.hyperjump.game.applicationcode.domainmodel.rules.HitRule;
import com.hyperjump.game.applicationcode.domainmodel.rules.TeleportRule;
import com.hyperjump.game.applicationcode.domainmodel.rules.strategy.*;
import com.hyperjump.game.applicationcode.port.out.GameRule;

import java.util.List;

public class InitialiseRulesUseCase {

    private final RuleSelectionStrategy      ruleSelectionStrategy;
    private final TeleportGenerationStrategy teleportGenerationStrategy;

    public InitialiseRulesUseCase(RuleSelectionStrategy ruleSelectionStrategy, TeleportGenerationStrategy teleportGenerationStrategy) {
        this.ruleSelectionStrategy      = ruleSelectionStrategy;
        this.teleportGenerationStrategy = teleportGenerationStrategy;
    }

    public List<GameRule> setupRules(int boardSize, List<Player> players) {
        List<GameRule> availableRules = createAvailableRules(boardSize, players);

        return ruleSelectionStrategy.select(availableRules);
    }

    private List<GameRule> createAvailableRules(int boardSize, List<Player> players) {
        if (ruleSelectionStrategy.hasPreselectedRules()) return List.of();

        return List.of(
                new ExactEndRule(),
                new TeleportRule(boardSize, players, teleportGenerationStrategy),
                new HitRule(new SamePositionHit(players))
        );
    }
}