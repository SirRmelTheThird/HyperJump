package com.hyperjump.game.applicationcode.usecase;

import com.hyperjump.game.applicationcode.domainmodel.board.BoardFactory;
import com.hyperjump.game.applicationcode.domainmodel.movement.BasicMovement;
import com.hyperjump.game.applicationcode.domainmodel.movement.Movement;
import com.hyperjump.game.applicationcode.domainmodel.player.PlayerTurn;
import com.hyperjump.game.applicationcode.port.out.GameRule;

import java.util.List;

public class ReplaySessionUseCase {

    private final InitialisePlayerUseCase playerSetup;
    private final List<GameRule> selectedRules;

    public ReplaySessionUseCase(InitialisePlayerUseCase playerSetup, List<GameRule> selectedRules) {
        this.playerSetup = playerSetup;
        this.selectedRules = selectedRules;
    }

    public PlayerTurn play() {
        Movement movement = buildMovement(selectedRules);
        return playerSetup.startTurns(movement);
    }

    private Movement buildMovement(List<GameRule> rules) {
        Movement movement = new BasicMovement();
        for (GameRule rule : rules) {
            movement = rule.decorate(movement);
        }
        return movement;
    }
}