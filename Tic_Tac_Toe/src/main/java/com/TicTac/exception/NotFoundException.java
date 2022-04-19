package com.TicTac.exception;

/**
 *  Wyswietla informacje dotyczaca braku mozliwosci polaczenia z gra
 */
public class NotFoundException extends Exception {
    private String message;

    public NotFoundException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
