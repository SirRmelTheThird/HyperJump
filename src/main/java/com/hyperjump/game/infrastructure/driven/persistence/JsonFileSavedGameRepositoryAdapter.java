package com.hyperjump.game.infrastructure.driven.persistence;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyperjump.game.applicationcode.domainmodel.replay.SavedGame;
import com.hyperjump.game.applicationcode.domainmodel.shared.DomainException;
import com.hyperjump.game.applicationcode.port.out.SavedGameRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Component
@Profile("file")
public class JsonFileSavedGameRepositoryAdapter implements SavedGameRepository {

    private static final TypeReference<List<SavedGame>> SAVED_GAME_LIST = new TypeReference<>() {};
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Path directory = Path.of(System.getProperty("user.home"), ".hyperjump");
    private final Path file = directory.resolve("saved-games.json");

    @Override
    public int nextId() {

        return findAll().stream()
                .mapToInt(SavedGame::getId)
                .max()
                .orElse(0) + 1;
    }

    @Override
    public void save(SavedGame savedGame) {
        List<SavedGame> games = new ArrayList<>(findAll());
        games.removeIf(game -> game.getId() == savedGame.getId());
        games.add(savedGame);
        writeGames(games);
    }

    @Override
    public SavedGame findById(int id) {
        return findAll().stream()
                .filter(game -> game.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<SavedGame> findAll() {
        doesFileExists();
        try {
            return objectMapper.readValue(file.toFile(), SAVED_GAME_LIST);
        } catch (IOException e) {
            throw new DomainException("Could not read saved games", e);
        }
    }

    private void writeGames(List<SavedGame> games) {
        doesDirectoryExists();
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file.toFile(), games);
        } catch (IOException e) {
            throw new DomainException("Could not write saved games", e);
        }
    }

    private void doesFileExists() {
        doesDirectoryExists();
        if (Files.exists(file)) {
            return;
        }

        try {
            Files.writeString(file, "[]");
        } catch (IOException e) {
            throw new DomainException("Could not create saved games file", e);
        }
    }

    private void doesDirectoryExists() {
        try {
            Files.createDirectories(directory);
        } catch (IOException e) {
            throw new DomainException("Could not create save directory", e);
        }
    }
}