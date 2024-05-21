package com.dugimto.exception;

public class GameNotFoundException extends RuntimeException {
    public GameNotFoundException() {
        super();
    }

    public GameNotFoundException(Long id) {
        super("Game not found with id: " + id);
    }

    public GameNotFoundException(String message) {
        super(message);
    }

    public GameNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public GameNotFoundException(Throwable cause) {
        super(cause);
    }

    protected GameNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
