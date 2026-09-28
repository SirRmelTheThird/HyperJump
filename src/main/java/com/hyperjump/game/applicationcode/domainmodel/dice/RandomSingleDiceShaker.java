package com.hyperjump.game.applicationcode.domainmodel.dice;

import com.hyperjump.game.applicationcode.domainmodel.value.DiceRoll;

public class RandomSingleDiceShaker extends AbstractDiceShaker {

    @Override
    public DiceRoll roll() {
        int dice = singleDie();
        return new DiceRoll(new int[]{dice});
    }
}