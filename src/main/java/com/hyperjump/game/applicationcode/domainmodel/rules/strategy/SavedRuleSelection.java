package com.hyperjump.game.applicationcode.domainmodel.rules.strategy;

import com.hyperjump.game.applicationcode.domainmodel.player.Player;
import com.hyperjump.game.applicationcode.domainmodel.replay.SavedRule;
import com.hyperjump.game.applicationcode.port.out.GameRule;

import java.util.List;

public class SavedRuleSelection implements RuleSelectionStrategy {

    private final List<SavedRule> savedRules;
    private final List<Player>   replayPlayers;

    public SavedRuleSelection(List<SavedRule> savedRules, List<Player> replayPlayers) {
        this.savedRules    = savedRules;
        this.replayPlayers = replayPlayers;
    }

    @Override
    public List<GameRule> select(List<GameRule> availableRules) {
        return savedRules.stream()
                .map(rule -> rule.rebuild(replayPlayers))
                .toList();
    }
}