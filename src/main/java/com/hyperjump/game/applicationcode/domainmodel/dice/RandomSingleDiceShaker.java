package com.hyperjump.game.applicationcode.domainmodel.dice;

import com.hyperjump.game.applicationcode.domainmodel.value.DiceRoll;

public class RandomSingleDiceShaker extends AbstractDiceShaker {
    private int dice;

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public int next() {
        dice = this.singleDie();
        return dice;
    }

    @Override
    public int[] toArray() {
        return new int[]{dice};
    }

    @Override
    public DiceRoll roll() {
        dice = singleDie();
        return new DiceRoll(new int[]{dice});
    }
}