package com.hyperjump.game.applicationcode.domainmodel.rules.strategy;

import com.hyperjump.game.applicationcode.port.out.GameRule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RandomRuleSelection implements RuleSelectionStrategy {

    private final Random random = new Random();

    @Override
    public List<GameRule> select(List<GameRule> availableRules) {
        List<GameRule> shuffled = new ArrayList<>(availableRules);
        Collections.shuffle(shuffled);
        int count = random.nextInt(shuffled.size()) + 1;
        return shuffled.subList(0, count);
    }
}
