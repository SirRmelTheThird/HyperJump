package com.hyperjump.game.applicationcode.domainmodel.movement.strategy;

import com.hyperjump.game.applicationcode.domainmodel.movement.Movement;
import com.hyperjump.game.applicationcode.port.out.GameRule;

public interface MovementDecoratorStrategy {
    boolean supports(GameRule rule);
    Movement decorate(Movement movement, GameRule rule);
}