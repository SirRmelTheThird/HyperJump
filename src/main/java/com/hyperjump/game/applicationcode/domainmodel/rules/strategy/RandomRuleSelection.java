package com.hyperjump.game.applicationcode.domainmodel.rules.strategy;

import com.hyperjump.game.applicationcode.port.out.GameRule;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Component
@Profile("real")
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
