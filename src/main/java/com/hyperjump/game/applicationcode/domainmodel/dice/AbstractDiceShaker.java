package com.hyperjump.game.applicationcode.domainmodel.dice;

import java.util.Random;

public abstract class AbstractDiceShaker implements DiceShakerFactory {

    private final Random random = new Random();

    protected int singleDie() {
        return random.nextInt(6) + 1;
    }
}