package com.hyperjump.game.applicationcode.domainmodel.movement;

import com.hyperjump.game.applicationcode.domainmodel.movement.events.HitEvent;
import com.hyperjump.game.applicationcode.domainmodel.movement.events.MoveEvent;
import com.hyperjump.game.applicationcode.domainmodel.player.Player;
import com.hyperjump.game.applicationcode.domainmodel.rules.HitRule;
import com.hyperjump.game.applicationcode.domainmodel.value.DiceRoll;
import com.hyperjump.game.applicationcode.domainmodel.value.Position;

public class HitVariationDecorator extends MovementDecorator {

    private final HitRule hitRule;

    public HitVariationDecorator(Movement wrapped, HitRule hitRule) {
        super(wrapped);
        this.hitRule = hitRule;
    }

    @Override
    public TurnOutcome move(Player player, DiceRoll roll) {
        TurnOutcome result = wrapped.move(player, roll);

        Player hitPlayer =
                hitRule.getHitPlayer(player, result.getEndPosition());

        if (hitPlayer == null) {
            return result;
        }

        Position hitPosition = result.getEndPosition();
        Position returnPosition = result.getPreviousPosition();

        player.moveToIndex(player.getPath().indexOf(returnPosition));

        return result.withEvent(new HitEvent(hitPlayer.getColour().toString(), hitPosition))
                .withEvent(new MoveEvent(hitPosition, returnPosition))
                .withEndPosition(returnPosition);
    }
}