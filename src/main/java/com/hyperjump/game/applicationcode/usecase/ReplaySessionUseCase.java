package com.hyperjump.game.applicationcode.usecase;

import com.hyperjump.game.applicationcode.domainmodel.board.BoardFactory;
import com.hyperjump.game.applicationcode.domainmodel.movement.BasicMovement;
import com.hyperjump.game.applicationcode.domainmodel.movement.Movement;
import com.hyperjump.game.applicationcode.domainmodel.player.PlayerTurn;
import com.hyperjump.game.applicationcode.port.out.GameRule;

import java.util.List;

public class ReplaySessionUseCase {

    private final BoardFactory board;
    private final InitialisePlayerUseCase playerSetup;
    private final InitialiseRulesUseCase rulesSetup;

    public ReplaySessionUseCase(BoardFactory board, InitialisePlayerUseCase playerSetup, InitialiseRulesUseCase rulesSetup) {
        this.board = board;
        this.playerSetup = playerSetup;
        this.rulesSetup = rulesSetup;
    }

    public PlayerTurn play() {
        List<GameRule> selectedRules = rulesSetup.setupRules(board.getSize(), playerSetup.getPlayers());
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