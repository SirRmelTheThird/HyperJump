package com.hyperjump.game.applicationcode.domainmodel.movement;

import com.hyperjump.game.applicationcode.domainmodel.movement.events.GameEvent;
import com.hyperjump.game.applicationcode.domainmodel.value.Position;

import java.util.ArrayList;
import java.util.List;

public class TurnOutcome {

    private final Position previousPosition;
    private final Position landedPosition;

    private Position endPosition;

    private final int newIndex;

    private final List<GameEvent> events = new ArrayList<>();

    public TurnOutcome(
            Position previousPosition,
            Position landedPosition,
            Position endPosition,
            int newIndex
    ) {
        this.previousPosition = previousPosition;
        this.landedPosition = landedPosition;
        this.endPosition = endPosition;
        this.newIndex = newIndex;
    }

    public void addEvent(GameEvent event) {
        events.add(event);
    }

    public void updateEndPosition(Position endPosition) {
        this.endPosition = endPosition;
    }

    public Position getPreviousPosition() {
        return previousPosition;
    }

    public Position getLandedPosition() {
        return landedPosition;
    }

    public Position getEndPosition() {
        return endPosition;
    }

    public int getNewIndex() {
        return newIndex;
    }

    public List<GameEvent> getEvents() {
        return events;
    }
}