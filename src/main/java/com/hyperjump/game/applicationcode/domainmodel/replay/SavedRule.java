package com.hyperjump.game.applicationcode.domainmodel.replay;

import com.hyperjump.game.applicationcode.domainmodel.player.Player;
import com.hyperjump.game.applicationcode.domainmodel.rules.ExactEndRule;
import com.hyperjump.game.applicationcode.domainmodel.rules.HitRule;
import com.hyperjump.game.applicationcode.domainmodel.rules.TeleportRule;
import com.hyperjump.game.applicationcode.domainmodel.rules.strategy.SamePositionHit;
import com.hyperjump.game.applicationcode.domainmodel.shared.DomainException;
import com.hyperjump.game.applicationcode.domainmodel.value.Position;
import com.hyperjump.game.applicationcode.port.out.GameRule;

import java.util.List;
import java.util.Map;

public class SavedRule {

    private String type;
    private Map<Position, Position> data;

    public SavedRule(String type) {
        this.type = type;
    }

    public SavedRule(String type, Map<Position, Position> data) {
        this.type = type;
        this.data = data;
    }

    public GameRule rebuild(List<Player> players) {
        return switch (type) {
            case "HitRule" -> new HitRule(new SamePositionHit(players));
            case "ExactEndRule" -> new ExactEndRule();
            case "TeleportRule" -> new TeleportRule(data);
            default -> throw new DomainException("Unknown rule type: " + type);
        };
    }
}