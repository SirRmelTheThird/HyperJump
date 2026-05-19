package com.hyperjump.game.infrastructure.driven.dice;

import com.hyperjump.game.applicationcode.domainmodel.dice.DiceShakerFactory;
import com.hyperjump.game.applicationcode.domainmodel.dice.RandomDoubleDiceShaker;
import com.hyperjump.game.applicationcode.domainmodel.dice.RandomSingleDiceShaker;
import com.hyperjump.game.applicationcode.port.out.DiceShaker;
import com.hyperjump.game.applicationcode.domainmodel.value.DiceRoll;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
@Profile("real")
public class RandomDiceShakerAdapter implements DiceShaker {

    private final List<DiceShakerFactory> diceTypes;
    private final Random random = new Random();

    public RandomDiceShakerAdapter() {
        diceTypes = List.of(
                new RandomSingleDiceShaker(),
                new RandomDoubleDiceShaker()
        );
    }

    @Override
    public DiceRoll roll() {
        return diceTypes.get(random.nextInt(diceTypes.size())).roll();
    }

    @Override
    public String describe() {
        return "Random sequence of dice rolls";
    }
}