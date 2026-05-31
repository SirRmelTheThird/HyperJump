package com.hyperjump.game.infrastructure.driven.dice;

import com.hyperjump.game.applicationcode.domainmodel.replay.SavedGame;
import com.hyperjump.game.applicationcode.domainmodel.value.DiceRoll;
import com.hyperjump.game.applicationcode.port.out.DiceShaker;
import com.hyperjump.game.applicationcode.port.out.RecordingDiceShakerPort;

public class RecordingDiceShakerAdapter implements RecordingDiceShakerPort {

    private final DiceShaker delegate;
    private SavedGame savedGame;

    public RecordingDiceShakerAdapter(DiceShaker delegate) {
        this.delegate = delegate;
    }

    @Override
    public void startRecording(SavedGame savedGame) {
        this.savedGame = savedGame;
    }

    @Override
    public DiceRoll roll() {
        DiceRoll result = delegate.roll();
        if (savedGame != null) savedGame.recordRoll(result);
        return result;
    }

    @Override
    public String describe() {
        return delegate.describe();
    }

    @Override
    public void reset() {
        delegate.reset();
    }
}