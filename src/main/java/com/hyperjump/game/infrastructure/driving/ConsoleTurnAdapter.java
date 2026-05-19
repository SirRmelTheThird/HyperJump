package com.hyperjump.game.infrastructure.driving;

import com.hyperjump.game.applicationcode.domainmodel.movement.TurnOutcome;
import com.hyperjump.game.applicationcode.domainmodel.movement.events.GameEvent;
import com.hyperjump.game.applicationcode.domainmodel.player.Player;
import com.hyperjump.game.applicationcode.domainmodel.player.PlayerTurn;
import com.hyperjump.game.applicationcode.domainmodel.value.DiceRoll;
import com.hyperjump.game.applicationcode.port.out.TurnObserverPort;

public class ConsoleTurnAdapter implements TurnObserverPort {

    private final DisplayPort display;

    public ConsoleTurnAdapter(DisplayPort display) {
        this.display = display;
    }

    @Override
    public void onTurnPlayed(PlayerTurn playerTurn) {
        Player player = playerTurn.getCurrentPlayer();
        DiceRoll roll = playerTurn.getRoll();
        TurnOutcome outcome = playerTurn.getVariation();

        display.show(buildTurnMessage(player, roll, playerTurn));
        display.show(buildMovementMessage(player, outcome));
        display.show("");
    }

    private String buildTurnMessage(Player player, DiceRoll roll, PlayerTurn playerTurn) {
        return player.getColour() + "'s turn " + playerTurn.getTurns() + " rolls " + formatRoll(roll);
    }

    private String formatRoll(DiceRoll roll) {
        int[] dice = roll.getValue();

        if (dice.length == 1) {
            return "Single Dice " + dice[0];
        }

        return "Double Dice " + dice[0] + " + " + dice[1];
    }

    private String buildMovementMessage(Player player, TurnOutcome outcome) {
        StringBuilder message = new StringBuilder();

        for (GameEvent event : outcome.getEvents()) {
            message.append(event.describe(player)).append("\n");
        }

        message.append(player.getColour()).append(" moves from Position ").append(outcome.getPreviousPosition().value()).append(" to ").append(outcome.getEndPosition().value());
        return message.toString();
    }
}