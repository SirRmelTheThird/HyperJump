package com.hyperjump.game.applicationcode.domainmodel.movement.strategy;

import com.hyperjump.game.applicationcode.domainmodel.movement.ExactEndVariationDecorator;
import com.hyperjump.game.applicationcode.domainmodel.movement.Movement;
import com.hyperjump.game.applicationcode.port.out.GameRule;
import com.hyperjump.game.applicationcode.domainmodel.rules.ExactEndRule;

public class ExactEndVariation implements MovementDecoratorStrategy {

    @Override
    public boolean supports(GameRule rule) {
        return rule instanceof ExactEndRule;
    }

    @Override
    public Movement decorate(Movement movement, GameRule rule) {
        return new ExactEndVariationDecorator(movement, (ExactEndRule) rule);
    }
}