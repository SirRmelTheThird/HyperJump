package com.hyperjump.game.applicationcode.port.out;

import com.hyperjump.game.applicationcode.domainmodel.replay.SavedGame;

import java.util.List;

public interface SavedGameRepository {
    int nextId();
    void save(SavedGame savedGame);
    SavedGame findById(int id);
    List<SavedGame> findAll();
}