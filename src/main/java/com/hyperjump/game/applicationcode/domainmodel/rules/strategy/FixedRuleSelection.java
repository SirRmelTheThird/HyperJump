package com.hyperjump.game.applicationcode.domainmodel.rules.strategy;

import com.hyperjump.game.applicationcode.port.out.GameRule;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("test")
public class FixedRuleSelection implements RuleSelectionStrategy {

    private int index = 0;

    @Override
    public List<GameRule> select(List<GameRule> availableRules) {
        List<List<GameRule>> combos = List.of(
                List.of(availableRules.get(0)),
                List.of(availableRules.get(1)),
                List.of(availableRules.get(2)),
                List.of(availableRules.get(0), availableRules.get(1)),
                List.of(availableRules.get(0), availableRules.get(2)),
                List.of(availableRules.get(1), availableRules.get(2)),
                List.of(availableRules.get(0), availableRules.get(1), availableRules.get(2))
        );
        List<GameRule> selectedCombo = combos.get(index);
        index = (index + 1) % combos.size();

        return selectedCombo;
    }
}