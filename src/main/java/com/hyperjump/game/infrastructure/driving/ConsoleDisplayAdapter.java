package com.hyperjump.game.infrastructure.driving;

import com.hyperjump.game.applicationcode.port.out.DisplayPort;

public class ConsoleDisplayAdapter implements DisplayPort {

    @Override
    public void show(String message) {
        System.out.println(message);
    }
}