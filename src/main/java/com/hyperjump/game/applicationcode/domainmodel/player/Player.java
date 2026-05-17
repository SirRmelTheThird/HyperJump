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
    private int previousPathIndex;
    private Position previousPos;

    public Player(Colour colour, DiceShaker diceShaker) {
        this.colour = colour;
        this.diceShaker = diceShaker;
        this.pathIndex = 0;
    }

    public DiceRoll roll() {
        return diceShaker.roll();
    }

    public Colour getColour()              { return colour; }
    public List<Position> getPath()        { return path; }
    public int getPathIndex()              { return pathIndex; }
    public Position getStartPos()          { return path.getFirst(); }
    public Position getEndPos()            { return path.getLast(); }
    public Position getCurrentPos()        { return path.get(pathIndex); }
    public Position getPreviousPos()       { return previousPos; }

    public void setPath(List<Position> path) {
        this.path = List.copyOf(path);
        this.pathIndex = 0;
    }

    public void moveToIndex(int index) {
        previousPos = getCurrentPos();
        previousPathIndex = pathIndex;
        pathIndex = index;
    }

    public void moveToPreviousIndex() {
        pathIndex = previousPathIndex;
    }

    public void teleportTo(Position position) {
        previousPos = getCurrentPos();
        pathIndex = path.indexOf(position);
    }

    public boolean isWinner() {
        return pathIndex == path.size() - 1;
    }
}