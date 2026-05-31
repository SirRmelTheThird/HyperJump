package com.hyperjump.game.applicationcode.port.out;

import com.hyperjump.game.applicationcode.domainmodel.replay.SavedGame;

public interface RecordingDiceShakerPort extends DiceShaker {
    void startRecording(SavedGame savedGame);
}
