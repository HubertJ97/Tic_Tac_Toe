package com.TicTac.exception;

/**
 * Wyswietla informacje dotyczaca blednego ID
 */
public class InvalidParamException extends Exception {

    private String message;

    public InvalidParamException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
