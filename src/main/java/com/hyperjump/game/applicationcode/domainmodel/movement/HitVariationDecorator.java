package com.hyperjump.game.applicationcode.domainmodel.movement;

import com.hyperjump.game.applicationcode.domainmodel.movement.events.HitEvent;
import com.hyperjump.game.applicationcode.domainmodel.player.Player;
import com.hyperjump.game.applicationcode.domainmodel.rules.HitRule;
import com.hyperjump.game.applicationcode.domainmodel.value.DiceRoll;

public class HitVariationDecorator extends MovementDecorator {

    private final HitRule hitRule;

    public HitVariationDecorator(Movement wrapped, HitRule hitRule) {
        super(wrapped);
        this.hitRule = hitRule;
    }

    @Override
    public TurnOutcome move(Player player, DiceRoll roll) {
        TurnOutcome result = wrapped.move(player, roll);

        Player hitPlayer = hitRule.getHitPlayer(player, result.getEndPosition());

        if (hitPlayer == null) {
            return result;
        }

        result.addEvent(new HitEvent(hitPlayer.getColour().toString(), result.getEndPosition()));
        player.moveToIndex(player.getPath().indexOf(result.getPreviousPosition()));
        result.updateEndPosition(result.getPreviousPosition());

        return result;
    }
}
