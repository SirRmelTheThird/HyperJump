package com.hyperjump.game.infrastructure.driven.dice;

import com.hyperjump.game.applicationcode.domainmodel.value.DiceRoll;
import com.hyperjump.game.applicationcode.port.out.DiceShaker;

import java.util.List;

public class ReplayDiceShakerAdapter implements DiceShaker {

    private final List<DiceRoll> rolls;
    private int index;

    public ReplayDiceShakerAdapter(List<DiceRoll> rolls) {
        this.rolls = rolls;
        this.index = 0;
    }

    @Override
    public DiceRoll roll() {
        return rolls.get(index++);
    }

    @Override
    public String describe() {
        return "Replay sequence of dice rolls";
    }
}