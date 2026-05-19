package com.hyperjump.game.infrastructure.driven.persistence;

import com.hyperjump.game.applicationcode.domainmodel.replay.SavedGame;
import com.hyperjump.game.applicationcode.port.out.SavedGameRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Profile("memory")
public class InMemorySavedGameRepositoryAdapter implements SavedGameRepository {

    private final Map<Integer, SavedGame> games = new LinkedHashMap<>();
    private int nextId = 1;

    @Override
    public int nextId() {
        return nextId++;
    }

    @Override
    public void save(SavedGame savedGame) {
        games.put(savedGame.getId(), savedGame);
    }

    @Override
    public SavedGame findById(int id) {
        return games.get(id);
    }

    @Override
    public List<SavedGame> findAll() {
        return new ArrayList<>(games.values());
    }
}