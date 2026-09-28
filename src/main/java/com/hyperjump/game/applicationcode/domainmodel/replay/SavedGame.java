package com.hyperjump.game.applicationcode.domainmodel.replay;

import com.hyperjump.game.applicationcode.domainmodel.value.DiceRoll;

import java.util.ArrayList;
import java.util.List;

public class SavedGame {

    private int id;
    private GameConfiguration configuration;
    private final List<DiceRoll> diceRolls = new ArrayList<>();

    public SavedGame() {
    }

    public SavedGame(int id, GameConfiguration configuration) {
        this.id = id;
        this.configuration = configuration;
    }

    public void recordRoll(DiceRoll roll) {
        diceRolls.add(roll);
    }

    public int getId() {
        return id;
    }

    public GameConfiguration getConfiguration() {
        return configuration;
    }

    public List<DiceRoll> getDiceRolls() {
        return diceRolls;
    }
}