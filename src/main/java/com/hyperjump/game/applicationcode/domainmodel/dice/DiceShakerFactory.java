package com.hyperjump.game.applicationcode.domainmodel.dice;

import com.hyperjump.game.applicationcode.domainmodel.value.DiceRoll;

public interface DiceShakerFactory {
    boolean hasNext();
    int next();
    int[] toArray();
    DiceRoll roll();
}