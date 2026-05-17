package com.hyperjump.game.applicationcode.port.out;

import com.hyperjump.game.applicationcode.domainmodel.value.DiceRoll;

public interface DiceShaker {
    DiceRoll roll();
    default void reset() {}
}