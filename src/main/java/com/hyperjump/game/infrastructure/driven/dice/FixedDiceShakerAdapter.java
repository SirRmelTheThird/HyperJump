package com.hyperjump.game.infrastructure.driven.dice;

import com.hyperjump.game.applicationcode.domainmodel.dice.FixedSingleDiceShaker;
import com.hyperjump.game.applicationcode.domainmodel.value.DiceRoll;
import com.hyperjump.game.applicationcode.port.out.DiceShaker;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("test")
public class FixedDiceShakerAdapter implements DiceShaker {

    private final FixedSingleDiceShaker fixedDice = new FixedSingleDiceShaker();

    public void reset() {
        fixedDice.reset();
    }

    @Override
    public DiceRoll roll() {
        return fixedDice.roll();
    }
}