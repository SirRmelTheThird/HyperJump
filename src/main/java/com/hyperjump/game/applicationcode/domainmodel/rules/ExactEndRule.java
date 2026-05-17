package com.hyperjump.game.applicationcode.domainmodel.rules;

import com.hyperjump.game.applicationcode.domainmodel.player.Player;
import com.hyperjump.game.applicationcode.port.out.GameRule;

public class ExactEndRule implements GameRule {

    public int getBounceIndex(Player player, int newIndex) {
        int maxIndex = player.getPath().size() - 1;

        if (newIndex <= maxIndex) {
            return -1;
        }

        while (newIndex > maxIndex) {
            newIndex = maxIndex - (newIndex - maxIndex);
        }
        return Math.max(0, newIndex);
    }

    @Override
    public String getDescription() {
        return "Exact End: Player must land exactly on the END position to win else the player bounces back";
    }
}