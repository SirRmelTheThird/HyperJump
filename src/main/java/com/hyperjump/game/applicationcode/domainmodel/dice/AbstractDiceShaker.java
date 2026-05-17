package com.hyperjump.game.applicationcode.domainmodel.dice;

import com.hyperjump.game.applicationcode.domainmodel.value.DiceRoll;

import java.util.Random;

public abstract class AbstractDiceShaker implements DiceShakerFactory {

    private final Random random = new Random();

    protected int singleDie() {
        return random.nextInt(6) + 1;
    }

    @Override
    public DiceRoll roll() {
        return new DiceRoll(toArray());
    }
}