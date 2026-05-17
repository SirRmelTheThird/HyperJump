package com.hyperjump.game.applicationcode.domainmodel.movement.strategy;

import com.hyperjump.game.applicationcode.domainmodel.movement.HitVariationDecorator;
import com.hyperjump.game.applicationcode.domainmodel.movement.Movement;
import com.hyperjump.game.applicationcode.port.out.GameRule;
import com.hyperjump.game.applicationcode.domainmodel.rules.HitRule;


public class HitVariation implements MovementDecoratorStrategy {

    @Override
    public boolean supports(GameRule rule) {
        return rule instanceof HitRule;
    }

    @Override
    public Movement decorate(Movement movement, GameRule rule) {
        return new HitVariationDecorator(movement, (HitRule) rule);
    }
}
