package com.hyperjump.game.applicationcode.port.out;

import com.hyperjump.game.applicationcode.domainmodel.value.DiceRoll;

import java.util.List;

public interface ReplayDiceShaker {
    DiceShaker createReplayDiceShaker(List<DiceRoll> rolls);
}
