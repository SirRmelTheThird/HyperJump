package com.hyperjump.game.applicationcode.domainmodel.player;

import com.hyperjump.game.applicationcode.domainmodel.gameenum.Colour;
import com.hyperjump.game.applicationcode.port.out.DiceShaker;
import com.hyperjump.game.applicationcode.domainmodel.value.DiceRoll;
import com.hyperjump.game.applicationcode.domainmodel.value.Position;

import java.util.List;

public class Player {

    private final Colour colour;
    private final DiceShaker diceShaker;
    private List<Position> path;
    private int pathIndex;

    public Player(Colour colour, DiceShaker diceShaker) {
        this.colour = colour;
        this.diceShaker = diceShaker;
        this.pathIndex = 0;
    }

    public DiceRoll roll() {
        return diceShaker.roll();
    }

    public Colour getColour() {
        return colour;
    }

    public List<Position> getPath() {
        return path;
    }

    public int getPathIndex() {
        return pathIndex;
    }

    public Position getStartPos() {
        return path.getFirst();
    }

    public Position getEndPos() {
        return path.getLast();
    }

    public Position getCurrentPos() {
        return path.get(pathIndex);
    }

    public void setPath(List<Position> path) {
        this.path = List.copyOf(path);
        this.pathIndex = 0;
    }

    public void moveToIndex(int index) {
        pathIndex = index;
    }

    public void teleportTo(Position position) {
        pathIndex = path.indexOf(position);
    }

    public boolean isWinner() {
        return pathIndex == path.size() - 1;
    }
}