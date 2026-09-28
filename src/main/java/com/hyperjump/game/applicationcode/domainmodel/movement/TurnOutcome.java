package com.hyperjump.game.applicationcode.domainmodel.movement;

import com.hyperjump.game.applicationcode.domainmodel.movement.events.GameEvent;
import com.hyperjump.game.applicationcode.domainmodel.value.Position;

import java.util.ArrayList;
import java.util.List;

public class TurnOutcome {

    private final Position previousPosition;
    private final Position endPosition;
    private final List<GameEvent> events;

    public TurnOutcome(Position previousPosition, Position endPosition) {
        this.previousPosition = previousPosition;
        this.endPosition = endPosition;
        this.events = List.of();
    }

    private TurnOutcome(Position previousPosition, Position endPosition, List<GameEvent> events) {
        this.previousPosition = previousPosition;
        this.endPosition = endPosition;
        this.events = List.copyOf(events);
    }

    public TurnOutcome withEvent(GameEvent event) {
        List<GameEvent> updatedEvents = new ArrayList<>(events);
        updatedEvents.add(event);

        return new TurnOutcome(previousPosition, endPosition, updatedEvents);
    }

    public TurnOutcome withEndPosition(Position position) {
        return new TurnOutcome(previousPosition, position, events);
    }

    public Position getPreviousPosition() {
        return previousPosition;
    }

    public Position getEndPosition() {
        return endPosition;
    }

    public List<GameEvent> getEvents() {
        return events;
    }
}