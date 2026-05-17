package com.hyperjump.game.infrastructure.driving;

public class ConsoleDisplayAdapter implements DisplayPort {

    @Override
    public void show(String message) {
        System.out.println(message);
    }
}