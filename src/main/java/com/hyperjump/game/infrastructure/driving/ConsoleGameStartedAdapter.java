package com.hyperjump.game.infrastructure.driving;

import com.hyperjump.game.applicationcode.domainmodel.player.Player;
import com.hyperjump.game.applicationcode.domainmodel.value.Position;
import com.hyperjump.game.applicationcode.port.out.GameRule;
import com.hyperjump.game.applicationcode.port.out.GameStartedObserverPort;

import java.util.stream.Collectors;

public class ConsoleGameStartedAdapter implements GameStartedObserverPort {

    private final DisplayPort display;

    public ConsoleGameStartedAdapter(DisplayPort display) {
        this.display = display;
    }

    @Override
    public void onGameStarted(GameStartInfo info) {
        displayNumber(info);
        displayBoardSize(info);
        displayRules(info);
        displayDice(info);
        displayBoard(info);
        displayPaths(info);
        display.show("");
    }

    private void displayNumber(GameStartInfo info) {
        display.show("Players: " + info.players().size());
        display.show("");
    }

    private void displayBoardSize(GameStartInfo info) {
        display.show("Board Size: " + info.board().getSize());
        display.show("");
    }


    private void displayRules(GameStartInfo info) {
        if (info.rules().isEmpty()) {
            display.show("Game Rules: None");
            display.show("");
            return;
        }
        display.show("Game Rules:");
        for (GameRule rule : info.rules()) {
            display.show(" - " + rule.getDescription());
        }
        display.show("");
    }

    private void displayDice(GameStartInfo info) {
        display.show(info.diceShaker().describe());
        display.show("");
    }

    private void displayBoard(GameStartInfo info) {
        display.show("Board: rows = " + info.board().getRows() + " columns = " + info.board().getCols());
        display.show("");
    }

    private void displayPaths(GameStartInfo info) {
        for (Player player : info.players()) {
            String path = player.getPath()
                    .stream()
                    .map(position -> formatPosition(player, position))
                    .collect(Collectors.joining(", "));

            display.show(path);
        }
    }

    private String formatPosition(Player player, Position position) {
        if (position.equals(player.getStartPos())) return player.getColour() + " Home (Position " + position + ")";
        if (position.equals(player.getEndPos())) return "End (Position " + position + ")";
        return position.toString();
    }
}