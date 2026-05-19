package com.hyperjump.game.applicationcode.domainmodel.value;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;

public final class DiceRoll {

    private final int[] dice;

    @JsonCreator
    public DiceRoll(@JsonProperty("dice") int[] dice) {
        this.dice = dice;
    }

    @JsonProperty("dice")
    public int[] getValue() {
        return dice.clone();
    }

    public int total() {
        int sum = 0;
        for (int d : dice) sum += d;
        return sum;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        DiceRoll that = (DiceRoll) obj;
        return Arrays.equals(dice, that.dice);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(dice);
    }

    @Override
    public String toString() {
        return Arrays.toString(dice).replaceAll("[\\[\\]]", "");
    }
}