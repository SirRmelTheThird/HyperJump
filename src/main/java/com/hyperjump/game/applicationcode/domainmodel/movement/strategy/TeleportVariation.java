package com.hyperjump.game.applicationcode.domainmodel.movement.strategy;

import com.hyperjump.game.applicationcode.domainmodel.movement.Movement;
import com.hyperjump.game.applicationcode.domainmodel.movement.TeleportVariationDecorator;
import com.hyperjump.game.applicationcode.port.out.GameRule;
import com.hyperjump.game.applicationcode.domainmodel.rules.TeleportRule;

public class TeleportVariation implements MovementDecoratorStrategy {

    @Override
    public boolean supports(GameRule rule) {
        return rule instanceof TeleportRule;
    }

    @Override
    public Movement decorate(Movement movement, GameRule rule) {
        return new TeleportVariationDecorator(movement, (TeleportRule) rule);
    }
}
