package com.TicTac.exception;

/**
 * Wyswietla informacje na temat przerwanej gry lub po jej zakonczeniu
 */
public class InvalidGameException extends Exception {
    private String message;

    public InvalidGameException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
