package com.hyperjump.game.applicationcode.domainmodel.dice;

import com.hyperjump.game.applicationcode.domainmodel.value.DiceRoll;

import java.util.Arrays;
import java.util.List;

public class FixedSingleDiceShaker implements DiceShakerFactory {

    private final int[] shakes = new int[]{1, 2, 3, 4, 5, 6};
    private int index;

    public void reset() {
        index = 0;
    }

    public List<Integer> getRolls() {
        return Arrays.stream(shakes).boxed().toList();
    }

    private int next() {
        int value = shakes[index];
        index = (index + 1) % shakes.length;
        return value;
    }

    private int[] toArray() {
        return new int[]{next()};
    }

    @Override
    public DiceRoll roll() {
        return new DiceRoll(toArray());
    }

}