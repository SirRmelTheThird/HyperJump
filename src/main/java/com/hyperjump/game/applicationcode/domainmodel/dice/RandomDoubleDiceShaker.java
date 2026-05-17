package com.hyperjump.game.applicationcode.domainmodel.dice;

import com.hyperjump.game.applicationcode.domainmodel.value.DiceRoll;

public class RandomDoubleDiceShaker extends AbstractDiceShaker {

    private int dice1;
    private int dice2;

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public int next() {
        dice1 = singleDie();
        dice2= singleDie();
        return dice1 + dice2;
    }

    @Override
    public int[] toArray() {
        return new int[]{dice1, dice2};
    }

    @Override
    public DiceRoll roll() {
        dice1 = singleDie();
        dice2 = singleDie();
        return new DiceRoll(new int[]{dice1, dice2});
    }
}