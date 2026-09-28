package com.hyperjump.game.applicationcode.domainmodel.dice;

import com.hyperjump.game.applicationcode.domainmodel.value.DiceRoll;

public class RandomDoubleDiceShaker extends AbstractDiceShaker {

    @Override
    public DiceRoll roll() {
        int dice1 = singleDie();
        int dice2 = singleDie();
        return new DiceRoll(new int[]{dice1, dice2});
    }
}