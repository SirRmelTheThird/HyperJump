package com.hyperjump.game.applicationcode.domainmodel.gameenum;

public enum Colour {
    RED("\u001B[31m"),
    BLUE("\u001B[34m"),
    YELLOW("\u001B[33m"),
    GREEN("\u001B[32m");

    private static final String RESET = "\u001B[0m";
    private final String code;

    Colour(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return code + name() + RESET;
    }
}