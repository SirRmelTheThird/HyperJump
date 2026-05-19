package com.hyperjump.game.infrastructure.driven.dice;

import com.hyperjump.game.applicationcode.domainmodel.value.DiceRoll;
import com.hyperjump.game.applicationcode.port.out.DiceShaker;
import com.hyperjump.game.applicationcode.port.out.ReplayDiceShakerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReplayDiceShakerFactoryAdapter implements ReplayDiceShakerFactory {
    @Override
    public DiceShaker createReplayDiceShaker(List<DiceRoll> rolls) {
        return new ReplayDiceShakerAdapter(rolls);
    }
}
